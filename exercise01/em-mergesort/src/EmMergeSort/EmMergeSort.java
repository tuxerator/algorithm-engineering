package EmMergeSort;

import java.util.Arrays;
import java.lang.runtime.*;

public class EmMergeSort {
  static Runtime runtime;

  public static void main(String[] args) {
    runtime = Runtime.getRuntime();
    System.out.println("Using " + runtime.totalMemory() + " bytes for EM-MergeSort.");

    int bBlockSize = 60;
    try {
      Reader reader = new Reader("test_data", bBlockSize);
      Writer writer = new Writer("output");

      long bPartitionSize = partition(bBlockSize, reader, writer);


      reader = new Reader("output", bBlockSize);
      writer = new Writer("test_data");

      emMerge(bBlockSize, bPartitionSize, reader, writer);
      reader = new Reader("test_data", bBlockSize);
      System.out.println(Arrays.toString(reader.readInt()));

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
  public static long partition(int bBlockSize, Reader reader, Writer writer) throws Exception {
    long bFreeMem = runtime.freeMemory();
    bFreeMem = 1200;
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
      System.out.println(nread / 4 + " numbers read");

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

  public static void emMerge(int bBlockSize, long bPartitionSize, Reader reader, Writer writer) throws Exception{
    mergePartition(0, bPartitionSize, bPartitionSize, bBlockSize, reader, writer);
  }

  public static void mergePartition(long pPart1, long pPart2, long bPartitionSize, int bBlockSize, Reader reader, Writer writer) throws Exception {
    long pNextBlock1 = pPart1;
    long pNextBlock2 = pPart2;
    
    int[] block1 = new int[bBlockSize/4];
    int[] block2 = new int[bBlockSize/4];
    int[] out = new int[bBlockSize/4];

    int indexBlock1 = 0;
    int indexBlock2 = 0;

    reader.readIntBuffer(block1, pNextBlock1);
    pNextBlock1 += bBlockSize;
    block2 = reader.trimRead(block2, pNextBlock2);
    pNextBlock2 += bBlockSize;

  while (pNextBlock1 < pPart1 + bPartitionSize && pNextBlock2 < pPart2 + bPartitionSize){
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
              int j = i + 1;
              while (indexBlock2 < block2.length && j < out.length){
                out[j] = block2[indexBlock2];
                indexBlock2++;
                j++;
              }
              out = Arrays.copyOf(out, j);
              break;
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
              int j = i + 1;
              while(j < out.length && indexBlock1 < block1.length) {
                out[j] = block1[indexBlock1];
                indexBlock1++;
                j++;
              }
              out = Arrays.copyOf(out, j);
              break;
            } else {
              block2 = reader.trimRead(block2, pNextBlock2);
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
      pNextBlock1 += bBlockSize;
      if(pNextBlock1 == pPart1 + bPartitionSize){
        break;
      }
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
      pNextBlock2 += bBlockSize;
      if(pNextBlock2 == pPart2 + bPartitionSize){
        break;
      }
      }
      block2 = reader.trimRead(block2, pNextBlock2);
      pNextBlock2 += bBlockSize;
      writer.write(block2);
    }

  }
}
