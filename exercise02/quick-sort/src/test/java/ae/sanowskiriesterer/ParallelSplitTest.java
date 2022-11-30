package ae.sanowskiriesterer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParallelSplitTest {
  private static final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
  private static ParallelSplit parallelSplit;
  private static final int[] arr = {2,34,5,66,7,3,45,67,832,46,78,9,4,6,57,78,34,656,778,343};
  private static int pivot;
  private static int i_pivot;

  @BeforeAll
  static void beforAll() {
    int i_pivot = (int) (Math.random() * arr.length); 
    pivot = arr[i_pivot];

    arr[i_pivot] = arr[arr.length - 1];

    PartIntArray partArr = new PartIntArray(arr, 0, arr.length - 1);

    parallelSplit = new ParallelSplit(partArr, (arr.length - 1) / 4, pivot);
  }

  @Test
  void parallelSplit() {
    ForkJoinTask<Pair<PartIntArray,PartIntArray>> future =  forkJoinPool.submit(parallelSplit);

    Pair<PartIntArray,PartIntArray> result;
    
    try {
      result = future.get();

      System.out.println("Lower start index: " + result.getKey().getStart());
      System.out.println("Lower length: " + result.getKey().length);
      System.out.println("Higher start index: " + result.getValue().getStart());
      System.out.println("Higher length: " + result.getValue().length);


      arr[arr.length - 1] = result.getValue().get(0);
      result.getValue().set(pivot, 0);

      System.out.println("pivot: " + pivot);
      System.out.println(Arrays.toString(arr));

      for (int i = 0; i < result.getValue().getStart(); i++) {
        assertTrue(arr[i] < pivot);
      }

      for (int i = result.getValue().getStart(); i < arr.length; i++) {
        assertTrue(arr[i] >= pivot);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
