package ae.sanowskiriesterer;

/**
 * Classical quick-sort
 *
 */
public class QuickSortApp {

  public static void main(String[] args) {
    int[] arr = InputGenerator.generate(Integer.parseInt(args[0]));
    // arr = InputGenerator.generate(100000);
    double start = System.nanoTime();
    QuickSort.quickSort(arr);
    double end = System.nanoTime();
    System.out.println((end-start)/1000000);
    //System.out.println(Arrays.toString(arr));
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
