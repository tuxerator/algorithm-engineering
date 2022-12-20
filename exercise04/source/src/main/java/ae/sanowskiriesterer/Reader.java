package ae.sanowskiriesterer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Reader {

    public static void main(String[] args){
        
    }
    
    public static LinkedList<Node> read(String pathToFile) throws Exception {

        LinkedList<Node> nodes = new LinkedList<Node>();
    
        BufferedReader reader = new BufferedReader(new FileReader(new File(pathToFile)));
        String line;
        while ((line = reader.readLine()) != "EOF") {
            String[] words = line.split(" ");
            if(words[0].matches("[0-9]*")) {
                nodes.add(new Node (Float.parseFloat(words[1]), Float.parseFloat(words[2])));
            }
          
        }
        reader.close();
        return nodes;
            
    }
}
