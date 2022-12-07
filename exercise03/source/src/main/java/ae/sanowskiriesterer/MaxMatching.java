package ae.sanowskiriesterer;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class MaxMatching {

    public static ArrayList<Vertex> cover;  //maybe private

    public static void main( String[] args ){
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);
        Vertex v6 = new Vertex(6);
        Vertex v7 = new Vertex(7);
        Vertex v8 = new Vertex(8);
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
        graph.add(v8);

        ArrayList<Vertex> result = getMatching(graph);

        for(Vertex v : result){
            System.out.println(v.id);
        }


    }

    public static ArrayList<Vertex> getMatching(ArrayList<Vertex> graph) {
        cover = new ArrayList<Vertex>();
        for(Vertex v : graph) {
            if(v.neighbors.size() >= 1){
                Vertex u = v.neighbors.get(0);
                cover.add(v);
                cover.add(u);
                System.out.println("Added edge: ("+v.id+","+u.id+")");
                for(Vertex w : v.neighbors){
                    w.neighbors.remove(v);
                }
                v.neighbors.clear();
                for(Vertex w : u.neighbors){
                    w.neighbors.remove(u);
                }
                u.neighbors.clear();
            }
        }
        return cover;
    }
}
