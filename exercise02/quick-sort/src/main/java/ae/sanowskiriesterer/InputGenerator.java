package ae.sanowskiriesterer;

import java.util.Random;

public class InputGenerator {

    public static int[] generate(int quantity){

        int[] array = new int[quantity];
        Random rand = new Random();
        for(int i = 0; i < quantity; i++){
            array[i] = i;
        }
        for(int i = 0; i < quantity; i++){
            int randomIndex = rand.nextInt(quantity);
            int tmp = array[randomIndex];
            array[randomIndex] = array[i];
            array[i] = tmp;
        }

        return array;
    }

}
