package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

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
      
      System.out.println("Start index: " + arr.getStart());
      System.out.println("j: " + j);
      System.out.println("split result: " + arr.toString());
      System.out.println();
    }

    return new Pair<PartIntArray,PartIntArray>(new PartIntArray(arr.getArray(), arr.getStart(), j), new PartIntArray(arr.getArray(), arr.getStart() + j, arr.length - j));
  }

  protected Pair<PartIntArray,PartIntArray> compute() {
    System.out.println();
    if (arr.length <= threshhold) {
      return split();
    }

    Collection<ParallelSplit> futures = ForkJoinTask.invokeAll(createSubtasks());

    Collection<Pair<PartIntArray,PartIntArray>> results = new ArrayList<>();

    for (ParallelSplit future : futures) {
      try {
        System.out.println("Executing Task");

        results.add(future.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return mergeSplitResult(results);
  }

  protected Collection<ParallelSplit> createSubtasks() {
    System.out.println("Creating sub tasks.");
    Collection<ParallelSplit> subTasks = new ArrayList<>();

    for (int i = 0; i < arr.length; i += threshhold) {
      if (i + threshhold >= arr.length) {
        threshhold = arr.length - i;
      }

      System.out.println("Subarray:");
      System.out.println("Start index: " + i);
      System.out.println("length: " + threshhold);
      System.out.println();

      subTasks.add(new ParallelSplit(new PartIntArray(arr.getArray(), i, threshhold), threshhold, pivot));
    }

    return subTasks;
  }

  protected Pair<PartIntArray,PartIntArray> mergeSplitResult(Collection<Pair<PartIntArray,PartIntArray>> splitResults) {
    int[] tmp = arr.getArray().clone();

    int lowerPrfxSum = 0;


    for (Pair<PartIntArray,PartIntArray> result : splitResults) {
      result.getKey().setArray(tmp);                                // Set PartIntArray to tmp so arr doesn't get overwritten.
      writeTo(arr.getArray(), result.getKey(), lowerPrfxSum);

      lowerPrfxSum = lowerPrfxSum + result.getKey().length;
    }

    // Array of all elements smaller than pivot
    PartIntArray lower = new PartIntArray(arr.getArray(), 0, lowerPrfxSum);

    int higherPrfxSum = lowerPrfxSum;

    for (Pair<PartIntArray,PartIntArray> result : splitResults) {
      result.getValue().setArray(tmp);                              // Set PartIntArray to tmp so arr doesn't get overwritten.
      writeTo(arr.getArray(), result.getValue(), higherPrfxSum);
      
      higherPrfxSum = higherPrfxSum + result.getValue().length;
    }

    // Array of all elemts bigger or equal than pivot 
    PartIntArray higher = new PartIntArray(arr.getArray(), lowerPrfxSum, arr.length - lowerPrfxSum);

    System.out.println("lower: " + lower.toString());
    System.out.println("higher: " + higher.toString());

    return new Pair<PartIntArray,PartIntArray>(lower, higher);
  } 

  protected void writeTo(int[] dest, PartIntArray arr, int index) {
    System.out.println("arr length: " + arr.length);
    System.out.println("dest length: " + dest.length);
    for (int i = 0; i < arr.length; i++) {
      dest[index + i] = arr.get(i);
    }
  }
}
