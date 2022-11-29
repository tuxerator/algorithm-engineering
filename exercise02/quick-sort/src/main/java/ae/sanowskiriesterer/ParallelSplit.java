package ae.sanowskiriesterer;

import java.util.concurrent.RecursiveTask;

public class ParallelSplit extends RecursiveTask<Pair<PartIntArray,PartIntArray>[]> {
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
      if (arr.get(i) <= pivot) {
        int tmp = arr.get(j);
        arr.set(arr.get(j), i);
        arr.set(tmp, j);
        j++;
      }
    }

    return new Pair<PartIntArray,PartIntArray>(new PartIntArray(arr.getArray(), arr.getStart(), j), new PartIntArray(arr.getArray(), arr.getStart() + j, arr.length - j));
  }

  protected Pair<PartIntArray,PartIntArray>[] compute() {
    if (arr.length <= threshhold) {
      Pair<PartIntArray,PartIntArray>[] result = new Pair[1];
      result[0] = split();
      return result;
    }

    Pair<PartIntArray,PartIntArray>[] splitResult = new Pair[arr.length / threshhold];

    for (int i = 0; i < arr.length; i += threshhold) {
      if (i + threshhold >= arr.length) {
        threshhold = arr.length - i;
      }

      splitResult[i / threshhold]= new ParallelSplit(new PartIntArray(arr.getArray(), i, threshhold), threshhold, pivot).invoke()[0];
    }

    return splitResult;
  }
}
