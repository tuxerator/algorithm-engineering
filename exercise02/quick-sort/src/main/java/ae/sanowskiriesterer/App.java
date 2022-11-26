package ae.sanowskiriesterer;

import java.util.Arrays;

/**
 * Classical quick-sort
 *
 */
public class App {

  public static void main(String[] args) {
    int[] arr = {5,2,7,1,89,9,1,3,5,2,3,56647,1234,5,4,413,23425,431};
    quickSort(arr, 0, arr.length -1);
    System.out.println(Arrays.toString(arr));
  }

  public static void quickSort(int[] arr, int i_low, int i_high) {
    if (i_low < i_high) {
      int i_pivot = partition(arr, i_low, i_high);
      
      quickSort(arr, i_low, i_pivot - 1);
      quickSort(arr, i_pivot + 1, i_high);
    }
  }

  public static int partition(int[] arr, int i_low, int i_high) {
    int i_pivot = (int) (Math.random() * (i_high - i_low)) + i_low;
    int pivot = arr[i_pivot];

    arr[i_pivot] = arr[i_high];
    arr[i_high] = pivot;

    int j = i_low;

    for (int i = i_low; i < i_high; i++) {
      // swap if arr[i] is smaller than pivot
      if (arr[i] <= pivot) {
        int tmp = arr[j];
        arr[j] = arr[i];
        arr[i] = tmp;
        j++;
      }
    }

    // swap pivot with arr[j]
    arr[i_high] = arr[j];
    arr[j] =  pivot;

    // return index of pivot
    return j;
  }
}
