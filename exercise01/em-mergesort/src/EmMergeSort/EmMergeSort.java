package EmMergeSort;

import java.util.Arrays;
import java.lang.runtime.*;

public class EmMergeSort {
  static Runtime runtime;

  public static void main(String[] args) {
    runtime = Runtime.getRuntime();
    System.out.println("Using " + runtime.totalMemory() + " bytes for EM-MergeSort.");
    try {
      Reader reader = new Reader("test_data", 100);
      Writer writer = new Writer("output");

      partition(100, reader, writer);
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
    long bFileSize = reader.fileSize();
    long bPartitionSize = bFileSize > bFreeMem / 2 ? ((bFreeMem / 2) / bBlockSize) * bBlockSize : bFileSize;
    // long eTotalBlocks = (bFileSize + bBlockSize - 1) / bBlockSize;
    // long ePartitionBlocks = bPartitionSize / bBlockSize;

    if (bPartitionSize < 1) {
      System.err.println("Buffer does not fit into memory. \nPlease use a smaller buffer size.");
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

    System.out.println("Partitioned file in " + runs + "runs");

    return bPartitionSize;
  }

  public static void emMerge(int bBlockSize, int bPartitionSize, Reader reader, Writer writer) {
    int[] block1 = new int[bBlockSize];
    int[] block2 = new int[bBlockSize];
    int[] out = new int[bBlockSize];

    int blockPos1 = 0;
    int blockPos2 = 0;

    int partStart1 = 0;
    int partStart2 = bPartitionSize;

    int nextPart = 2 * bPartitionSize;

    int filePos1 = 0;
    int filePos2 = bPartitionSize;

    reader.readIntBuffer(block1, pos1);
    filePos1 += bBlockSize;
    reader.readIntBuffer(block2, pos2);
    filePos2 += bBlockSize;

    for (int i = 0; i < out.length; i++) {
      // Find smaller element
      if (block1[blockPos1] <= block2[blockPos2]) {
        out[i] = block1[blockPos1];
        // Check for end of block
        if (blockPos1 < block1.length - 1) {
          blockPos1++;
        } 
        else {                                            // read new block
          if (filePos1 < partStart1 + bPartitionSize) {
            reader.readIntBuffer(block1, filePos1);
            filePos1 += bBlockSize;
            blockPos1 = 0;
          } 
          else {                                          // write remaining elements
            out[i] = block2[blockPos2];
            blockPos2++;
          }
        }
      } else {
        out[i] = block2[blockPos2];
        blockPos2++;
      }
    }
  }

  public static void mergePartition(int pPart1, int pPart2, int bPartitionSize, int bBlockSize, Reader reader, Writer writer) {
    int pNextBlock1 = pPart1;
    int pNextBlock2 = pPart2;
    
    int[] block1 = new int[bBlockSize];
    int[] block2 = new int[bBlockSize];
    int[] out = new int[bBlockSize];

    int indexBlock1 = 0;
    int indexBlock2 = 0;

    reader.readIntBuffer

  while (pNextBlock1 < pPart1 + bPartitionSize && pNextBlock2 < pPart2 + bPartitionSize){
      for (int i = 0; i < out.length; i++) {
        if (block1[blockPos1] <= block2[blockPos2]) {
          out[i] = block1[blockPos1];
          blockPos1++;
          // Check for end of block
        }
        else {
          out[i] = block2[blockPos2];
          blockPos2++;
          // TODO check for end of block
        }
      }
    // TODO write out to file
    }

  }
}
