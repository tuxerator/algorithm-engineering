package ae.sanowskiriesterer;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

public class MergesortTest {
    
    @Test
    public void testMergingAscending(){
        int[] half1 = new int[20];
        int[] half2 = new int[20];
        int[] result = new int[40];

        for(int i = 0; i < 40; i++){
            if(i<20){
                half1[i] = i;
            } else {
                half2[i%20] = i;
            }
            result[i] = i;
        }
        assertArrayEquals(result, Mergesort.merge(half1,half2));
    }

    @Test
    public void testMergingDescending(){
        //shouldn't happen in practice...
        int[] half1 = new int[20];
        int[] half2 = new int[20];
        int[] result = new int[40];

        for(int i = 39; i >= 0; i--){
            if(i<20){
                half1[i] = i;
            } else {
                half2[i%20] = i;
            }
            result[i] = i;
        }
        assertArrayEquals(result, Mergesort.merge(half1,half2));

    }

    @Test
    public void testMergingRandom(){
        int[] half1 = new int[20];
        int j1 = 0;
        int[] half2 = new int[20];
        int j2 = 0;
        int[] result = new int[40];
        Random rand = new Random();

        for(int i = 0; i < 40; i++){
            int temp = rand.nextInt(2);
            if(temp == 0){
                if(j1 < 20){
                    half1[j1] = i;
                    j1++;
                } else {
                    half2[j2] = i;
                    j2++;
                }
            } else {
                if(j2 < 20){
                    half2[j2] = i;
                    j2++;
                } else {
                    half1[j1] = i;
                    j1++;
                }
            }
            result[i] = i;
        }
        assertArrayEquals(result, Mergesort.merge(half1, half2));
    }

}
