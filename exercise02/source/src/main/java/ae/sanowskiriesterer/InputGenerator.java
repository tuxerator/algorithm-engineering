package ae.sanowskiriesterer;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class InputGenerator extends RecursiveAction {
  private PartIntArray arr;
  private int threshhold;
  private int start;
  private static int p = Runtime.getRuntime().availableProcessors();

  protected InputGenerator(PartIntArray arr, int threshhold, int start) {
    this.arr = arr;
    this.threshhold = threshhold;
    this.start = start;
  }

  public static int[] generate(int quantity) {
    PartIntArray arr = new PartIntArray(new int[quantity], 0, quantity);
    if (true) {
      fill(arr, 0);
      shuffle(arr, arr.length);
      return arr.getArray();
    }

    ForkJoinPool pool = new ForkJoinPool(p);

    pool.invoke(new InputGenerator(arr, arr.length / p, 0));

    pool.shutdown();

    System.out.println(arr);

    return arr.getArray();
  }

  public void compute() {
    if (arr.length <= threshhold) {
      fill(arr, start);
      shuffle(arr, arr.length / p);
      return;
    }

    InputGenerator left = new InputGenerator(new PartIntArray(arr.getArray(), arr.getStart(), arr.length / 2), threshhold, arr.getStart());
    InputGenerator right = new InputGenerator(new PartIntArray(arr.getArray(), arr.getStart() + arr.length / 2, arr.length - arr.length / 2), threshhold, arr.getStart() + arr.length / 2);

    invokeAll(left, right);

    shuffle(arr, arr.length / p);
  }

  private static void fill(PartIntArray arr, int start) {

    for (int i = 0; i < arr.length; i++) {
      arr.set(i + start, i);
    }
  }

  private static void shuffle(PartIntArray arr, int swaps) {
    Random rand = new Random();

    for (int i = 0; i < swaps; i++) {
      int randomIndex = rand.nextInt(arr.length);
      int tmp = arr.get(randomIndex);
      arr.set(arr.get(i), randomIndex);
      arr.set(tmp, i);
    }

  }
}
