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
      System.err.println(x);
    }
  }

  /**
   * Sort each block with classical merge-sort.
   * 
   * @param blockSize Size of the blocks in bytes
   * @return size of one partition in bytes
   **/
  public static long partition(int blockSize, Reader reader, Writer writer) throws Exception {
    long freeMem = runtime.freeMemory();
    freeMem = 490;
    long fileSize = reader.fileSize();
    long numBlocks = fileSize / blockSize;
    int rest = (int) fileSize % blockSize;

    System.out.println("File size: " + fileSize);

    if (rest != 0) {
      numBlocks += 1;
    }

    long partitionSize = (freeMem / blockSize) * blockSize;
    if (fileSize < partitionSize) {
      partitionSize = fileSize;
    }

    long blocksPerPartition = partitionSize / blockSize;

    int nread = 0;

    int[] blocks = new int[(int) partitionSize / 4];

    for (int i = 0; nread != -1; i++) {
      long blocksRemaining = numBlocks - i * blocksPerPartition;
      if (blocksRemaining > 0 && blocksRemaining <= blocksPerPartition) {
        if (rest > 0) {
          blocks = new int[(int) ((blocksRemaining - 1) * blockSize + rest) / 4];
        } else {
          blocks = new int[(int) blocksRemaining * blockSize / 4];
        }
      }
      nread = reader.readInts(blocks);
      if (nread == -1) {
        break;
      }
      Arrays.sort(blocks);
      writer.write(blocks);
      System.out.println(Arrays.toString(blocks));
      System.out.println(nread);
      System.out.println("Run finished \n");
    }

    return partitionSize;
  }
}
