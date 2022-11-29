package ae.sanowskiriesterer;

import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends RecursiveAction {
  private int[] arr;
  private int threshhold;

  protected void split() {

  }

  protected void compute() {
    if (arr.length < threshhold) {
      split();
      return;
    }

    
  }
}
