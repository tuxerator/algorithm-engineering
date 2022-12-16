package ae.sanowskiriesterer;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class MaxMatching {

    public static ArrayList<Vertex> cover;  //maybe private

    public static void main( String[] args ) throws Exception{
        /*Vertex v1 = new Vertex(0);
        Vertex v2 = new Vertex(1);
        Vertex v3 = new Vertex(2);
        Vertex v4 = new Vertex(3);
        Vertex v5 = new Vertex(4);
        Vertex v6 = new Vertex(5);
        Vertex v7 = new Vertex(6);
        Vertex v8 = new Vertex(7);
        v1.addNeighbors(v2,v3,v4);
        v2.addNeighbors(v1,v3,v5);
        v3.addNeighbors(v1,v2,v4,v6,v7);
        v4.addNeighbors(v1,v3,v7);
        v5.addNeighbors(v2,v6,v8);
        v6.addNeighbors(v3,v5,v8);
        v7.addNeighbors(v3,v4,v8);
        v8.addNeighbors(v5,v6,v7);

        ArrayList<Vertex> graph = new ArrayList<Vertex>();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);*/
        ArrayList<Vertex> graph = Reader.read(args[0]);
        // int vertices = graph.size();
        // double start = System.nanoTime();
        ArrayList<Vertex> result = getMatching(graph);
        // double end = System.nanoTime();
        // System.out.println(vertices);
        // System.out.println((end-start)/1000000);
        // System.out.println(result.size());

        for(Vertex v : result){
            System.out.println(v.id+1);
        }


    }

    public static ArrayList<Vertex> getMatching(ArrayList<Vertex> graph) {
        cover = new ArrayList<Vertex>();
        for(Vertex v : graph) {
            if(v.neighbors.size() >= 1){
                Vertex u = graph.get(v.neighbors.get(0));
                cover.add(v);
                cover.add(u);
                for(Integer w : v.neighbors){
                    graph.get(w).neighbors.remove((Integer) v.id);
                }
                v.neighbors.clear();
                for(Integer w : u.neighbors){
                    graph.get(w).neighbors.remove((Integer) u.id);
                }
                u.neighbors.clear();
            }
        }
        return cover;
    }
}
