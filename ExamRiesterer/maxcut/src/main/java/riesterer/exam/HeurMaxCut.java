package riesterer.exam;

// import java.util.ArrayList;
import java.util.Random;

public class HeurMaxCut {

    public static void main(String args[]) throws Exception{

        Graph graph = Reader.read(args[0]);
        long start = System.currentTimeMillis();
        int result = start(graph);
        long end = System.currentTimeMillis();
        System.out.println(args[0]+","+(end-start)+","+result);
        // Vertex v0 = new Vertex(0);
        // Vertex v1 = new Vertex(1);
        // Vertex v2 = new Vertex(2);
        // Edge e1 = new Edge(v0,v1);
        // Edge e2 = new Edge(v0,v2);
        // Edge e3 = new Edge(v1,v2);
        // ArrayList<Vertex> vertices = new ArrayList<Vertex>(3);
        // vertices.add(v0);
        // vertices.add(v1);
        // vertices.add(v2);
        // ArrayList<Edge> edges = new ArrayList<Edge>(3);
        // edges.add(e1);
        // edges.add(e2);
        // edges.add(e3);
        // Graph g = new Graph(vertices);
        // g.setEdgeList(edges);
        // System.out.println(start(g));

    }

    public static int start(Graph graph){
        int result = -1;
        for(int i = 0; i < 100; i++){
            groupVertices(graph);
            int numberCutEdges = graph.getNumberAllCutEdges();
            if(numberCutEdges > result){
                result = numberCutEdges;
            }
        }
        return result;
    }

    private static void groupVertices(Graph g){
        Random rand = new Random();
        for(Vertex v : g.getVertexList()){
            v.setGroup(rand.nextInt(2));
        }
    }
    
}
