package App;

import java.util.Arrays;

public class Mergesort{

    public static void main(String[] args) {
        int quantity = Integer.parseInt(args[0]);
        //int processors = Integer.parseInt(args[0]);
        double start = System.nanoTime();
        int[] unsorted = Inputgenerator.generate(quantity);
        double end = System.nanoTime();
        System.out.println((end-start)/1000000+" ms needed for input generation");
        System.out.println("Mergesort startet for input size "+quantity);
        double startTime = System.nanoTime();
        Mergesort.sort(unsorted);
        double endTime = System.nanoTime();
        double totalTime = (endTime - startTime)/1000000;
        System.out.println("Total running time of Mergesort was "+totalTime+" ms");
    }

    public static int[] sort(int[] unsorted){
        if(unsorted.length <=1){
            return unsorted;
        } else {
            return Mergesort.merge(Mergesort.sort(Arrays.copyOf(unsorted, unsorted.length / 2)),Mergesort.sort(Arrays.copyOfRange(unsorted, unsorted.length / 2, unsorted.length)));
        }
    }

    public static int[] merge(int[] half1, int[] half2){
        int[] sorted = new int[half1.length+half2.length];
        int index1 = 0;
        int index2 = 0;
        int indexS = 0;
        while(index1 < half1.length && index2 < half2.length) {
            if(half1[index1] < half2[index2]) {
                sorted[indexS] = half1[index1];
                index1++;
            } else {
                sorted[indexS] = half2[index2];
                index2++;
            }

            indexS++;
        }

        while(index1 < half1.length) {
            sorted[indexS] = half1[index1];
            index1++;
            indexS++;
        }

        while(index2 < half2.length) {
            sorted[indexS] = half2[index2];
            index2++;
            indexS++;
        }

        return sorted;
    }

}