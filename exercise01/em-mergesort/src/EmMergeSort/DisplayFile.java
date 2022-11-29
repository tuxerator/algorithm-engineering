package EmMergeSort;

import java.util.Arrays;

public class DisplayFile { 
  public static void main(String[] args) throws Exception {
    Reader reader = new Reader(args[0]);
    System.out.println(Arrays.toString(reader.readInt()));  
  }
}
