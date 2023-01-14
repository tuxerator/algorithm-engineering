package ae.sanowskiriesterer.nnh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Reader {

    public static void main(String[] args){
        
    }
    
    public static Grid read(String pathToFile) throws Exception {

        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;
        LinkedList<Node> nodes = new LinkedList<Node>();

        BufferedReader reader = new BufferedReader(new FileReader(new File(pathToFile)));
        String line;
        int id = 0;
        while (!(line = reader.readLine()).equals("EOF")) {
            String[] words = line.split(" ");
            if(words[0].matches("[0-9]*")) {
                double x = Double.parseDouble(words[1]);
                double y = Double.parseDouble(words[2]);
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
                nodes.add(new Node(x, y, id));
          id++;
            }
        }
        reader.close();
    Grid grid = new Grid((int) (Math.max(maxX, maxY) / id), id);
    grid.putAll(nodes);
    // System.out.println(grid);

    return grid;        
    }
}
