package ae.sanowskiriesterer;

import java.util.ArrayList;

public class Vertex {
    
    int id;
    ArrayList<Integer> neighbors;

    public Vertex(int id, ArrayList<Integer> neigh) {
        this.id = id;
        this.neighbors = neigh;
    }

    public Vertex(int id) {
        this.id = id;
        this.neighbors = new ArrayList<Integer>();
    }

    public void addNeighbors(Vertex... v){
        for(Vertex w : v){
            this.neighbors.add(w.id);
        }
    }

    public void removeNeighbors(Vertex... v) {
        for(Vertex w : v){
            this.neighbors.remove(w.id);
        }
    }

  // public String toString() {
  //   return "ID: " + id + " neighbors: " + neighbors.toString(); 
  // }
}
