package ae.sanowskiriesterer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
  private PartIntArray arr;
  private int threshhold;
  private int p;

  public static void parallelQuickSort(int[] arr, int p) {
    ForkJoinPool forkJoinPool = new ForkJoinPool(p);

    ParallelQuickSort parallelQuickSortTask = new ParallelQuickSort(new PartIntArray(arr, 0, arr.length), 500, p);
    forkJoinPool.invoke(parallelQuickSortTask);

    forkJoinPool.shutdown();
  }

  public ParallelQuickSort(PartIntArray arr, int threshhold, int p) {
    this.arr = arr;
    this.threshhold = threshhold;
    this.p = p;
  }

  protected void compute() {
    //if (arr.length <= 1) {
     // return;
    //}
    if (arr.length <= threshhold) {
       QuickSort.quickSort(arr);
       return;
     }

    int i_pivot = (int) (Math.random() * (arr.length - 1));
    int pivot = arr.get(i_pivot);
    arr.set(arr.get(arr.length - 1), i_pivot);
    arr.set(pivot, arr.length - 1);

    // split around pivot
    Pair  <PartIntArray,PartIntArray> splitResult = new ParallelSplit(new PartIntArray(arr.getArray(), arr.getStart(), arr.length - 1), threshhold, pivot).invoke();

    if (splitResult.getValue().length > 0) {
      arr.set(splitResult.getValue().get(0), arr.length - 1);
      splitResult.getValue().set(pivot, 0);
      splitResult.getValue().setStart(splitResult.getValue().getStart() + 1);
    }

    ParallelQuickSort left = new ParallelQuickSort(splitResult.getKey(), threshhold, p);
    ParallelQuickSort right = new ParallelQuickSort(splitResult.getValue(), threshhold, p);

    invokeAll(left, right);

//    left.fork();
//    right.fork();
//
//    left.join();
//    right.join();
    return;
  }
}
