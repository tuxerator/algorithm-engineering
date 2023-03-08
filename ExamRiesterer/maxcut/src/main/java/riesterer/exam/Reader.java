package riesterer.exam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Reader {

    public static void main(String[] args) throws Exception{
        // Graph g = read(args[0]);
        // System.out.println(args[0]+","+g.getVertexList().size()+","+g.getEdgeList().size());
        
    }

    public static Graph read(String pathToFile) throws Exception {

        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        ArrayList<Edge> edgeList = new ArrayList<Edge>();
    
        BufferedReader reader = new BufferedReader(new FileReader(new File(pathToFile)));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.charAt(0) == 'c') {
    
            } else if(line.charAt(0) == 'p') {
                String[] words = line.split(" ");
                // System.out.println(words[2]);
                for(int i = 0; i < Integer.parseInt(words[2]); i++){
                    vertexList.add(new Vertex(i));
                }
                // Graph graph = new Graph(vertexList);
            } else {
                String[] vIds = line.split(" ");
                edgeList.add(new Edge(vertexList.get(Integer.parseInt(vIds[0])-1),vertexList.get(Integer.parseInt(vIds[1])-1)));
            }
        }
        reader.close();
        Graph graph = new Graph(vertexList);
        graph.setEdgeList(edgeList);
        return graph;
    }
}

