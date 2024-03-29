package EmMergeSort;

import java.util.Arrays;

public class MergeSort {
  public static void main(String[] args) throws Exception {
    Reader reader = new Reader(args[0]);
    int[] data = reader.readInt();
    reader.close();

    System.out.println("Sorting " + data.length * 4 + " bytes with classical merge sort.");

    long timer = System.nanoTime();

    data = sort(data);
    
    long timeSort = System.nanoTime() - timer;
    System.out.println("Sorting took " + ((double) timeSort / (double) 1000000000) + 's');

    Writer writer = new Writer(args[0]);
    writer.write(data);
  }

  /**
   * 
   * @param sorted1 (Array)
   * @param sorted2 (Array)
   * @return merged Array of sorted1 and aorted2
   */
  public static int[] merge(int[] sorted1, int[] sorted2) {
    int[] merged = new int[sorted1.length + sorted2.length];
    int index1 = 0;
    int index2 = 0;
    int indexMerged = 0;
    while (index1 < sorted1.length && index2 < sorted2.length) {
      if (sorted1[index1] <= sorted2[index2]) {
        merged[indexMerged] = sorted1[index1];
        indexMerged++;
        index1++;
      } else {
        merged[indexMerged] = sorted2[index2];
        indexMerged++;
        index2++;
      }
    }
    while (index1 < sorted1.length) {
      merged[indexMerged] = sorted1[index1];
      indexMerged++;
      index1++;
    }
    while (index2 < sorted2.length) {
      merged[indexMerged] = sorted2[index2];
      indexMerged++;
      index2++;
    }

    return merged;
  }

  /**
   * Split input recursively and merge sorted input together
   * 
   * @param input
   * @return sorted Input
   */
  public static int[] sort(int[] input) {
    if (input.length <= 1) {
      return input;
    } else {
      int inputLength = input.length;
      int[] half1 = Arrays.copyOfRange(input, 0, (inputLength / 2));
      int[] half2 = Arrays.copyOfRange(input, (inputLength / 2), inputLength);
      int half1Length = half1.length;
      int index2 = 0;

      // for (int i = 0; i < inputLength; i++) {
      // if (i < half1Length) {
      // half1[i] = input[i];
      // } else {
      // half2[index2] = input[i];
      // index2++;
      // }
      // }

      half1 = MergeSort.sort(half1);
      half2 = MergeSort.sort(half2);
      return MergeSort.merge(half1, half2);
    }
  }

}
