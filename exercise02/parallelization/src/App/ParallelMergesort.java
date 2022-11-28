package App;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergesort extends RecursiveAction {

    int[] input;
    static ForkJoinPool fjp;// = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-1);

    public static void main(String[] args){
        int quantity = Integer.parseInt(args[1]);
        int processors = Integer.parseInt(args[0]);
        int processorsAvailabel = Runtime.getRuntime().availableProcessors();
        if(processors > processorsAvailabel){
            System.err.println("Number of processors limited to "+processorsAvailabel);
            processors = processorsAvailabel;
        }
        int[] unsorted = Inputgenerator.generate(quantity);
        double startTime = System.nanoTime();
        fjp = new ForkJoinPool(processors);
        fjp.invoke(new ParallelMergesort(unsorted));
        double endTime = System.nanoTime();
        double totalTime = (endTime-startTime)/1000000;
        System.out.println(totalTime);

    }

    public ParallelMergesort(int[] array){
        this.input = array;
    }

    @Override
    protected void compute() {
        if(this.input.length <= 1){

        } else {
            int[] left = Arrays.copyOf(input, input.length/2 + input.length % 2);
            int[] right = Arrays.copyOfRange(input, input.length/2 + input.length % 2, input.length);
            ParallelMergesort pms = new ParallelMergesort(left);
            ParallelMergesort pMS = new ParallelMergesort(right);
            invokeAll(pms,pMS);
            pms.merge(this.input, pMS.input);
            pMS.merge(this.input, pms.input);
        }
    }

    public void merge(int[] result, int[] otherHalf){
        for(int i = 0; i < this.input.length; i++) {

            int index = (-1)*(Arrays.binarySearch(otherHalf, this.input[i]))-1 + i;
            result[index] = this.input[i];
        }

    }

}