package ae.sanowskiriesterer;

import java.util.Random;

/**
 * Static class containing classical quick-sort
 *
 */
public class QuickSort {
  private static Random random = new Random();

  public static void quickSort(int[] arr) {
    quickSort(new PartIntArray(arr, 0, arr.length));
  }

  public static void quickSort(PartIntArray arr) {
    if (arr.length <= 1) {
      return;
    }
      int i_pivot = partition(arr);
      
      quickSort(new PartIntArray(arr.getArray(), arr.getStart(), i_pivot));
      quickSort(new PartIntArray(arr.getArray(), arr.getStart() + i_pivot + 1, arr.length - i_pivot - 1));
  }

  public static int partition(PartIntArray arr) {
    int i_pivot = random.nextInt(0, arr.length);
    int pivot = arr.get(i_pivot);

    arr.set(arr.get(arr.length - 1), i_pivot);
    arr.set(pivot, arr.length - 1);

    int j = 0;

    for (int i = 0; i < arr.length - 1; i++) {
      // swap if arr[i] is smaller than pivot
      if (arr.get(i) < pivot) {
        int tmp = arr.get(j);
        arr.set(arr.get(i), j);
        arr.set(tmp, i);
        j++;
      }
    }

    // swap pivot with arr[j]
    
    arr.set(arr.get(j), (arr.length - 1));
    arr.set(pivot, j);

    // return index of pivot
    return j;
  }
}
