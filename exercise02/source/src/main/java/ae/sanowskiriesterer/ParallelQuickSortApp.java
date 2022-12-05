package ae.sanowskiriesterer;

import java.util.Arrays;

/**
 * Classical quick-sort
 *
 */
public class ParallelQuickSortApp {

  public static void main(String[] args) {
    int[] arr = InputGenerator.generate(Integer.parseInt(args[1]));
    int processors = Integer.parseInt(args[0]);
    int processorsAvailabel = Runtime.getRuntime().availableProcessors();
    if(processors <= 0){
      System.err.println("Number of processors limited to "+processorsAvailabel);
      processors = processorsAvailabel;
  }
    // arr = InputGenerator.generate(100000);
    double start = System.nanoTime();
    ParallelQuickSort.parallelQuickSort(arr, processors);
    double end = System.nanoTime();
    System.out.println((end-start)/1000000);
  }
}
