package App;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergesort extends RecursiveAction {

    int[] input;
    static ForkJoinPool fjp;// = new ForkJoinPool(Runtime.getRuntime().availableProcessors()-1);
    static int smallestPartition;

    public static void main(String[] args){
        int quantity = Integer.parseInt(args[1]);
        int processors = Integer.parseInt(args[0]);
        int processorsAvailabel = Runtime.getRuntime().availableProcessors();
        if(processors > processorsAvailabel){
            System.err.println("Number of processors limited to "+processorsAvailabel);
            processors = processorsAvailabel;
        }
        double start = System.nanoTime();
        int[] unsorted = Inputgenerator.generate(quantity);
        double end = System.nanoTime();
        System.out.println((end-start)/1000000+" ms needed for input generation");
        smallestPartition = unsorted.length/16;
        System.out.println("Parallel Mergesort started with "+processors+" processors and input size "+quantity);
        double startTime = System.nanoTime();
        fjp = new ForkJoinPool(processors);
        fjp.invoke(new ParallelMergesort(unsorted));
        double endTime = System.nanoTime();
        double totalTime = (endTime-startTime)/1000000;
        //System.out.println(Arrays.toString(unsorted));
        System.out.println("Total running time of parallel Mergesort was " +totalTime+" ms");

    }

    public ParallelMergesort(int[] array){
        this.input = array;
    }

    @Override
    protected void compute() {
        //if(this.input.length == 1){
        //In the lecture we started with input.length == 1, but since we do not have unlimited number of processors, our smallest partition is different
        if(this.input.length <= smallestPartition){
            Arrays.sort(this.input);

        } else {
            //System.out.println(fjp.getActiveThreadCount());
            int[] left = Arrays.copyOf(input, input.length/2 + input.length % 2);
            int[] right = Arrays.copyOfRange(input, input.length/2 + input.length % 2, input.length);
            ParallelMergesort pms = new ParallelMergesort(left);
            ParallelMergesort pMS = new ParallelMergesort(right);
            invokeAll(pms,pMS);
            //System.out.println(fjp.getActiveThreadCount()+"\n");
            //System.out.println(Arrays.toString(pms.input)+ "merged into"+ Arrays.toString(this.input));
            pms.merge(this.input, pMS.input);
            //System.out.println("Result :" +Arrays.toString(this.input));
            pMS.merge(this.input, pms.input);
        }
    }

    public void merge(int[] result, int[] otherHalf){
        //System.out.println("Merging "+ Arrays.toString(this.input)+" into "+Arrays.toString(result)+" :");
        //System.out.println("Other half :"+Arrays.toString(otherHalf));
        for(int i = 0; i < this.input.length; i++) {

            int index = (-1)*(Arrays.binarySearch(otherHalf, this.input[i]))-1 + i;
            result[index] = this.input[i];
        }

        //System.out.println("Result : "+Arrays.toString(result));
        //System.out.println("End of merging");

    }

}