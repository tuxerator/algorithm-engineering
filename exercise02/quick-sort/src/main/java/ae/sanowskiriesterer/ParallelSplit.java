package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/** */
public class ParallelSplit extends RecursiveTask<Pair<PartIntArray, PartIntArray>> {
  private PartIntArray arr;
  private int threshhold;
  private int pivot;

  public ParallelSplit(PartIntArray arr, int threshhold, int pivot) {
    this.arr = arr;
    this.threshhold = threshhold;
    this.pivot = pivot;
  }

  protected Pair<PartIntArray, PartIntArray> split() {
    int j = 0;

    for (int i = 0; i < arr.length; i++) {
      // swap if arr[i] is smaller than pivot
      if (arr.get(i) < pivot) {
        int tmp = arr.get(j);
        arr.set(arr.get(i), j);
        arr.set(tmp, i);
        j++;
      }
    }

    return new Pair<PartIntArray, PartIntArray>(
        new PartIntArray(arr.getArray(), arr.getStart(), j),
        new PartIntArray(arr.getArray(), arr.getStart() + j, arr.length - j));
  }

  protected Pair<PartIntArray, PartIntArray> compute() {
    if (arr.length <= threshhold) {
      return split();
    }

    Collection<ParallelSplit> futures = ForkJoinTask.invokeAll(createSubtasks());

    Collection<Pair<PartIntArray, PartIntArray>> results = new ArrayList<>();

    for (ParallelSplit future : futures) {
      try {
        results.add(future.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return mergeSplitResult(results);
  }

  /**
   * @return A Collection<ParallelSplit> with all sub tasks.
   */
  protected Collection<ParallelSplit> createSubtasks() {
    Collection<ParallelSplit> subTasks = new ArrayList<>();
    int lenght = arr.length / ((arr.length + threshhold - 1) / threshhold);
    // int lenght = threshhold;

    for (int i = 0; i < arr.length; i += lenght) {
      if (i + lenght >= arr.length) {
        lenght = arr.length - i;
      }

      subTasks.add(
          new ParallelSplit(
              new PartIntArray(arr.getArray(), arr.getStart() + i, lenght), threshhold, pivot));
    }

    return subTasks;
  }

  protected Pair<PartIntArray, PartIntArray> mergeSplitResult(
      Collection<Pair<PartIntArray, PartIntArray>> splitResults) {
    // int[] tmp = copyOfRange(arr.getArray(), arr.getStart(), arr.getStart() + arr.length);
    int[] tmp = new int[arr.length];
    new ParallelMemCopy(arr, tmp, 0).invoke();
    Collection<ParallelMemCopy> copyTasks = new ArrayList<>();

    int lowerPrfxSum = arr.getStart();

    for (Pair<PartIntArray, PartIntArray> result : splitResults) {
      result.getKey().setArray(tmp); // Set PartIntArray to tmp so arr doesn't get overwritten.
      result.getKey().setStart(result.getKey().getStart() - arr.getStart());
     copyTasks.add(new ParallelMemCopy(result.getKey(), arr.getArray(), lowerPrfxSum));
      // writeTo(result.getKey(), arr.getArray(), lowerPrfxSum);

      lowerPrfxSum = lowerPrfxSum + result.getKey().length;
    }

    int higherPrfxSum = lowerPrfxSum;

    for (Pair<PartIntArray, PartIntArray> result : splitResults) {
      result.getValue().setArray(tmp); // Set PartIntArray to tmp so arr doesn't get overwritten.
      result.getValue().setStart(result.getValue().getStart() - arr.getStart());
     copyTasks.add(
         new ParallelMemCopy(result.getValue(), arr.getArray(), higherPrfxSum));
      // writeTo(result.getValue(), arr.getArray(),higherPrfxSum);

      higherPrfxSum = higherPrfxSum + result.getValue().length;
    }

    invokeAll(copyTasks);

    // Array of all elements smaller than pivot
    PartIntArray lower =
        new PartIntArray(arr.getArray(), arr.getStart(), lowerPrfxSum - arr.getStart());

    // Array of all elemts bigger or equal than pivot
    PartIntArray higher =
        new PartIntArray(
            arr.getArray(), lowerPrfxSum, arr.length - (lowerPrfxSum - arr.getStart()));

    return new Pair<PartIntArray, PartIntArray>(lower, higher);
  }

  private static int[] copyOfRange(int[] arr, int from, int to) {
    int[] copy = new int[to - from];
    for (int i = 0; i < copy.length; i++) {
      copy[i] = arr[from + i];
    }
    return copy;
  }

  protected void writeTo(PartIntArray arr, int[] dest, int index) {
    for (int i = 0; i < arr.length; i++) {
      dest[index + i] = arr.get(i);
    }
  }

    public class ParallelMemCopy extends RecursiveAction {
    private PartIntArray arr;
    private int[] dest;
    private int index;
    private int threshhold;

    public ParallelMemCopy(PartIntArray arr, int[] dest, int index) {
      this.arr = arr;
      this.dest = dest;
      this.index = index;

      this.threshhold = arr.length >= 100000 ? arr.length / Runtime.getRuntime().availableProcessors() : arr.length;
    }

    public void compute() {
      if (arr.length <= threshhold) {
        writeTo(arr, dest, index);
        return;
      }

      Collection<ParallelMemCopy> subTasks = new ArrayList<>();
      int length = arr.length / ((arr.length + threshhold - 1) / threshhold);
      // int lenght = threshhold;

      for (int i = 0; i < arr.length; i += length) {
        if (i + length >= arr.length) {
          length = arr.length - i;
        }

        subTasks.add(
            new ParallelMemCopy(
                new PartIntArray(arr.getArray(), arr.getStart() + i, length),
                dest,
                index + i));
      }

      invokeAll(subTasks);
    }

    protected void writeTo(PartIntArray arr, int[] dest, int index) {
      for (int i = 0; i < arr.length; i++) {
        dest[index + i] = arr.get(i);
      }
    }
  }
}
