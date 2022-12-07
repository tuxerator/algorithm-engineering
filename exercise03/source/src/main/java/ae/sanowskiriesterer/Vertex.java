package ae.sanowskiriesterer;

import java.util.ArrayList;

public class Vertex {
    
    int id;
    ArrayList<Vertex> neighbors;

    public Vertex(int id, ArrayList<Vertex> neigh) {
        this.id = id;
        this.neighbors = neigh;
    }

    public Vertex(int id) {
        this.id = id;
        this.neighbors = new ArrayList<Vertex>();
    }

    public void addNeighbors(Vertex... v){
        for(Vertex w : v){
            this.neighbors.add(w);
        }
    }

    public void removeNeighbors(Vertex... v) {
        for(Vertex w : v){
            this.neighbors.remove(w);
        }
    }

}
