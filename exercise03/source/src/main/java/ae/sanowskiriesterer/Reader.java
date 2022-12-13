package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.Exception;
import java.lang.String;

public class Reader {

    public static void main(String[] args)throws Exception{
        /*String file = args[0];
        ArrayList<Vertex> graph = read(file);
        for(Vertex v : graph) {
            System.out.println(v.id+1+": ");
            for(Vertex u : v.neighbors){
                System.out.println(u.id+1);
            }
        }*/

    }

    public static ArrayList<Vertex> read(String pathToFile) throws Exception{

        ArrayList<Vertex> graph = new ArrayList<Vertex>();
        
        BufferedReader reader = new BufferedReader(new FileReader(new File (pathToFile)));
		String line;
        while ((line = reader.readLine()) != null) {
            if(line.charAt(0) == 'c') {

            } else if(line.charAt(0) == 'p') {
                String[] words = line.split(" ");
                for(int i = 0; i<Integer.parseInt(words[2]); i++){
                    graph.add(new Vertex(i));
                }
            } else {
                String[] vIds = line.split(" ");
                int[] ids = new int[2];
                ids[0] = Integer.parseInt(vIds[0]);
                ids[1] = Integer.parseInt(vIds[1]);
                if(!graph.get(Integer.parseInt(vIds[0])-1).neighbors.contains(graph.get(Integer.parseInt(vIds[1])-1))){
                    if(ids[0]==ids[1]){ //case for self loop
                        graph.get(ids[0]-1).neighbors.add(graph.get(ids[0]-1));  
                    } else {
                        graph.get(Integer.parseInt(vIds[0])-1).neighbors.add(graph.get(Integer.parseInt(vIds[1])-1));
                        graph.get(Integer.parseInt(vIds[1])-1).neighbors.add(graph.get(Integer.parseInt(vIds[0])-1));
                    }
                }
            }
        }
        reader.close();
        return graph;
        
    }
    
}
