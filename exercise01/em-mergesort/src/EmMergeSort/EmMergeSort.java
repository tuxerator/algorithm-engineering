package EmMergeSort;

import java.util.Arrays;
import java.lang.runtime.*;

public class EmMergeSort {
  static Runtime runtime;

  public static void main(String[] args) {
    runtime = Runtime.getRuntime();
    System.out.println("Using " + runtime.totalMemory() + " bytes for EM-MergeSort.");

    int bBlockSize = Integer.parseInt(args[0]);
    long memSize = Integer.parseInt(args[1]);
    String inputFile = args[2];
    String outputFile = args[3];

    try {
      Reader reader = new Reader(inputFile);
      Writer writer = new Writer(outputFile);
      long bPartitionSize = partition(bBlockSize, memSize, reader, writer);

      reader.close();
      writer.close();

      reader = new Reader(outputFile);
      writer = new Writer(inputFile);


      outputFile = emMerge(bBlockSize, bPartitionSize, reader, writer, outputFile, inputFile);
      reader = new Reader(outputFile);

    } catch (Exception x) {
      x.printStackTrace();
    }

  }

  /**
   * Sort each block with classical merge-sort.
   * 
   * @param blockSize Size of the blocks in bytes
   * @return size of one partition in bytes
   **/
  public static long partition(int bBlockSize, long memSize, Reader reader, Writer writer) throws Exception {
    long bFreeMem;
    if (memSize == 0) {
      bFreeMem = runtime.freeMemory();
    }
    else {
      bFreeMem = memSize;
    }

    long bFileSize = reader.fileSize();
    long bPartitionSize = bFileSize > bFreeMem / 2 ? ((bFreeMem / 2) / bBlockSize) * bBlockSize : bFileSize;
    // long eTotalBlocks = (bFileSize + bBlockSize - 1) / bBlockSize;
    // long ePartitionBlocks = bPartitionSize / bBlockSize;

    if (bPartitionSize < 1) {
      System.err.println("Buffer does not fit into memory or input file is empty.");
      System.exit(1);
    }

    int[] partition = new int[(int) bPartitionSize / 4];

    int nread = 0;
    int runs = 0;

    while (nread != -1) {
      nread = reader.readIntBuffer(partition);

      // exit loop if stream is empty
      if (nread == -1) {
        break;
      }

      // Trim array to the number of elements read
      if (nread < bPartitionSize) {
        partition = Arrays.copyOf(partition, nread / 4);
      }

      partition = MergeSort.sort(partition);

      writer.write(partition);
      runs++;
    }

    System.out.println("Partitioned file in " + runs + " runs");

    return bPartitionSize;
  }

  
  /**
   * @param bBlockSize
   * @param bPartitionSize
   * @param reader
   * @param writer
   * @param inputFile
   * @param outputFile
   * @return
   * @throws Exception
   */
  public static String emMerge(int bBlockSize, long bPartitionSize, Reader reader, Writer writer, String inputFile, String outputFile) throws Exception{  
    long bFileSize = reader.fileSize();
    int numPart = (int)(( bFileSize + bPartitionSize - 1 ) / bPartitionSize);
    mergePartition(0, bPartitionSize, bPartitionSize, bBlockSize, bFileSize, reader, writer);

    for (int partitions = numPart; bPartitionSize < bFileSize; partitions = (partitions + 1) / 2) {
      long part1 = 0;
      long part2 = bPartitionSize;
      for (int merges = partitions / 2; merges > 0; merges--) {
        mergePartition(part1, part2, bPartitionSize, bBlockSize, bFileSize, reader, writer);
        part1 = part2 + bPartitionSize;
        part2 = part1 + bPartitionSize;
      }
      
      bPartitionSize *= 2;
      reader.close();
      writer.close();

      String tmp = outputFile;
      outputFile = inputFile;
      inputFile = tmp;
      reader = new Reader(inputFile);
      writer = new Writer(outputFile);

      System.out.println("Run finished. Merged " + partitions + " partitions.");
    }

    return inputFile;
  }

  /**
   * @param pPart1
   * @param pPart2
   * @param bPartitionSize
   * @param bBlockSize
   * @param fileSize
   * @param reader
   * @param writer
   * @throws Exception
   */
  public static void mergePartition(long pPart1, long pPart2, long bPartitionSize, int bBlockSize, long fileSize, Reader reader, Writer writer) throws Exception {
    long pNextBlock1 = pPart1;
    long pNextBlock2 = pPart2;
    
    int[] block1 = new int[bBlockSize/4];
    int[] block2 = new int[bBlockSize/4];
    int[] out = new int[bBlockSize/4];

    int indexBlock1 = 0;
    int indexBlock2 = 0;

    // Read first blocks of partitions
    reader.readIntBuffer(block1, pNextBlock1);
    pNextBlock1 += bBlockSize;
    // part2 may contain blocks smaller than bBlockSize
    block2 = reader.trimRead(block2, pNextBlock2);
    pNextBlock2 += bBlockSize;

    // merge all blocks of both partitions
    merging:
    while (pNextBlock1 <= pPart1 + bPartitionSize && indexBlock1 < block1.length && pNextBlock2 <= pPart2 + bPartitionSize && indexBlock2 < block2.length){
      for (int i = 0; i < out.length; i++) {
        if (block1[indexBlock1] <= block2[indexBlock2]) {
          out[i] = block1[indexBlock1];
          //not yet end of block 1
          if(indexBlock1<block1.length-1){
            indexBlock1++;
          //need to load new block
          } else {
            //check, if we loaded all blocks of partition
            if(pNextBlock1 == pPart1 + bPartitionSize) {
              //fill out with block2
              int j = i+1;
              for (int k = j; j < out.length && indexBlock2 < block2.length; j++){
                out[j] = block2[indexBlock2];
                indexBlock2++;
              }
              writer.write(Arrays.copyOf(out, j));
              break merging;
            } else {
              reader.readIntBuffer(block1, pNextBlock1);
              indexBlock1 = 0;
              pNextBlock1 += bBlockSize;
            }
          }
        }
        else {
          out[i] = block2[indexBlock2];

          if(indexBlock2<block2.length-1){
            indexBlock2++;
          //need to load new block
          } else {
            //check, if we loaded all blocks of partition
            if(pNextBlock2 == pPart2 + bPartitionSize) {
              //fill out with block1
              int j = i+1;
              for (int k = j; j < out.length && indexBlock1 < block1.length; j++){
                out[j] = block1[indexBlock1];
                indexBlock1++;
              }
              writer.write(Arrays.copyOf(out, j));
              break merging;
            } else {
              reader.readIntBuffer(block2, pNextBlock2);
              indexBlock2 = 0;
              pNextBlock2 += bBlockSize;
            }
          }
        }
      }
      writer.write(out);
    }

    while(pNextBlock1 < pPart1 + bPartitionSize) {
      //write partition 1 till finished
      if(indexBlock1 < block1.length) {
        int[] out2 = new int[block1.length - indexBlock1];
        for(int i = 0; i < out2.length; i++ ) {
          out2[i]=block1[indexBlock1];
          indexBlock1++;
        }
        writer.write(out2);
      }

      reader.readIntBuffer(block1, pNextBlock1);
      pNextBlock1 += bBlockSize;
      writer.write(block1);
    }

    while(pNextBlock2 < pPart2 + bPartitionSize){
      //write partition 2 till finished
      if(indexBlock2 < block2.length) {
        int[] out2 = new int[block2.length - indexBlock2];
        for(int i = 0; i < out2.length; i++ ) {
          out2[i]=block2[indexBlock2];
          indexBlock2++;
        }
        writer.write(out2);
      }

      block2 = reader.trimRead(block2, pNextBlock2);
      pNextBlock2 += bBlockSize;
      writer.write(block2);
    }

  }
}
