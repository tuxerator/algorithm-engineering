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
    long eTotalBlocks = (bFileSize + bBlockSize - 1) / bBlockSize;
    long ePartitionBlocks = bPartitionSize / bBlockSize;

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
}
