package ae.sanowskiriesterer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Reader {

    public static void main(String[] args){
        
    }
    
    public static CoordSystem read(String pathToFile) throws Exception {

        float minX = 999999999;
        float maxX = 0;
        float minY = 999999999;
        float maxY = 0;
        LinkedList<Node> nodes = new LinkedList<Node>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(pathToFile)));
        String line;
        while ((line = reader.readLine()) != "EOF") {
            String[] words = line.split(" ");
            if(words[0].matches("[0-9]*")) {
                float x = Float.parseFloat(words[1]);
                float y = Float.parseFloat(words[2]);
                if(x < minX){
                    minX = x;
                } else if(x > maxX){
                    maxX = x;
                }
                if(y < minY){
                    minY = y;
                } else if(y > maxY) {
                    maxY = y;
                }
                nodes.add(new Node (x, y));
            }
          
        }
        reader.close();
        return new CoordSystem(minX, maxX, minY, maxY, nodes);
            
    }
}
