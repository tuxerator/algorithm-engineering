package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 
 */
public class ParallelSplit extends RecursiveTask<Pair<PartIntArray,PartIntArray>> {
  private PartIntArray arr;
  private int threshhold;
  private int pivot;

  public ParallelSplit(PartIntArray arr, int threshhold, int pivot) {
    this.arr = arr;
    this.threshhold = threshhold;
    this.pivot = pivot;
  }
  
  protected Pair<PartIntArray,PartIntArray> split() {
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
    

    return new Pair<PartIntArray,PartIntArray>(new PartIntArray(arr.getArray(), arr.getStart(), j), new PartIntArray(arr.getArray(), arr.getStart() + j, arr.length - j));
  }

  protected Pair<PartIntArray,PartIntArray> compute() {
    if (arr.length <= threshhold) {
      return split();
    }

    Collection<ParallelSplit> futures = ForkJoinTask.invokeAll(createSubtasks());

    Collection<Pair<PartIntArray,PartIntArray>> results = new ArrayList<>();

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
    int lenght = threshhold;

    for (int i = 0; i < arr.length; i += lenght) {
      if (i + lenght >= arr.length) {
        lenght = arr.length - i;
      }

      subTasks.add(new ParallelSplit(new PartIntArray(arr.getArray(), arr.getStart() + i, lenght), threshhold, pivot));
    }

    return subTasks;
  }

  protected Pair<PartIntArray,PartIntArray> mergeSplitResult(Collection<Pair<PartIntArray,PartIntArray>> splitResults) {
    int[] tmp = arr.getArray().clone();

    int lowerPrfxSum = arr.getStart();


    for (Pair<PartIntArray,PartIntArray> result : splitResults) {
      result.getKey().setArray(tmp);                                // Set PartIntArray to tmp so arr doesn't get overwritten.
      writeTo(arr.getArray(), result.getKey(), lowerPrfxSum);

      lowerPrfxSum = lowerPrfxSum + result.getKey().length;
    }

    // Array of all elements smaller than pivot
    PartIntArray lower = new PartIntArray(arr.getArray(), arr.getStart(), lowerPrfxSum - arr.getStart());


    int higherPrfxSum = lowerPrfxSum;

    for (Pair<PartIntArray,PartIntArray> result : splitResults) {
      result.getValue().setArray(tmp);                              // Set PartIntArray to tmp so arr doesn't get overwritten.
      writeTo(arr.getArray(), result.getValue(), higherPrfxSum);
      
      higherPrfxSum = higherPrfxSum + result.getValue().length;
    }

    // Array of all elemts bigger or equal than pivot 
    PartIntArray higher = new PartIntArray(arr.getArray(), lowerPrfxSum, arr.length - (lowerPrfxSum - arr.getStart()));

    return new Pair<PartIntArray,PartIntArray>(lower, higher);
  } 

  protected void writeTo(int[] dest, PartIntArray arr, int index) {
    for (int i = 0; i < arr.length; i++) {
      dest[index + i] = arr.get(i);
    }
  }
}
