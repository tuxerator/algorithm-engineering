package ae.sanowskiriesterer;

import java.util.Random;
import java.util.concurrent.RecursiveAction;
// import jdk.internal.org.jline.terminal.Terminal;
// import jdk.internal.org.jline.terminal.TerminalBuilder;
// import jdk.internal.org.jline.utils.InfoCmp;

public class ParallelQuickSort extends RecursiveAction {
  private PartIntArray arr;
  private int threshhold;
  private int p;
  private static Random random = new Random();

  public static void parallelQuickSort(int[] arr, int p) {
    if (p == 1) {
     QuickSort.quickSort(arr);
      return;
    }

    ParallelQuickSort parallelQuickSortTask =
        new ParallelQuickSort(new PartIntArray(arr, 0, arr.length), arr.length / (p + p / 2), p);
    // forkJoinPool.submit(parallelQuickSortTask);

    // while (!parallelQuickSortTask.isDone()) {
    //   System.out.printf("Queued tasks: %d\n", forkJoinPool.getQueuedTaskCount());
    //   System.out.printf("Stolen tasks: %d\n", forkJoinPool.getStealCount());
    //   System.out.printf("Running tasks: %d", forkJoinPool.getRunningThreadCount());
    //   try {
    //     Thread.sleep(250);
    //     System.out.print("\033[2F\033[J");
    //     System.out.flush();  
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // }

    parallelQuickSortTask.invoke();
  }

  public ParallelQuickSort(PartIntArray arr, int threshhold, int p) {
    this.arr = arr;
    this.threshhold = threshhold;
    this.p = p;
  }

  protected void compute() {
    // if (arr.length <= 1) {
    // return;
    // }
    if (arr.length <= threshhold) {
      QuickSort.quickSort(arr);
      return;
    }

    int i_pivot = random.nextInt(0, arr.length);
    int pivot = arr.get(i_pivot);
    arr.set(arr.get(arr.length - 1), i_pivot);
    arr.set(pivot, arr.length - 1);

    // split around pivot
    Pair<PartIntArray, PartIntArray> splitResult =
        new ParallelSplit(
                new PartIntArray(arr.getArray(), arr.getStart(), arr.length - 1), threshhold, pivot)
            .invoke();

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
