package riesterer.exam;

// import java.util.ArrayList;
import java.util.Random;

public class ApproxMaxCut {

    // public static Graph g;

    public static void main(String args[]) throws Exception{
        Graph graph = Reader.read(args[0]);
        graph.createAdjacencyList();
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
        // g.createAdjacencyList();
        // System.out.println(start(g));
        // for(Vertex v : g.getVertexList()){
        //     System.out.println(v.getGroup());
        // }
    }

    /**
     * 
     * @param g
     * @return
     */
    public static int start(Graph g){
        //Maybe start with best cut from HeurMaxCut (just an idea, would increase running time)

        // start with arbitrary cut
        groupVertices(g);
        Boolean stillChanging = true;
        // as long as we can swap vertices to make the cut better
        while(stillChanging){
            stillChanging = false;
            for(int i = 0; i < g.getVertexList().size(); i++){
                if(isSwappedBetter(g, i)){
                    //if swapping the group would be better, swap it
                    swapGroup(g.getVertexList().get(i));
                    stillChanging = true;
                }
            }
        }
        //return number of edges in the cut
        return g.getNumberAllCutEdges();
    }

    //need to set the following methods public in order to test them

    /**
     * 
     * @param g
     */
    public static void groupVertices(Graph g){
        Random rand = new Random();
        for(Vertex v : g.getVertexList()){
            v.setGroup(rand.nextInt(2));
        }
    }

    /**
     * 
     * @param g
     * @param vertexId
     * @return
     */
    public static Boolean isSwappedBetter(Graph g, int vertexId){
        int numberCutEdges = g.getNumberCutEdgesOfVertex(vertexId);
        int numberNonCutEdges = g.getAdjacencyList().get(vertexId).size() - numberCutEdges;

        return numberCutEdges < numberNonCutEdges;
    }

    /**
     * 
     * @param v
     */
    public static void swapGroup(Vertex v){
        if(v.getGroup() == 0){
            v.setGroup(1);
        } else {
            v.setGroup(0);
        }
    }
    
}
