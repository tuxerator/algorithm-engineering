package riesterer.exam;

import java.util.ArrayList;

public class Graph {
    
    private ArrayList<Edge> edgeList;
    private ArrayList<Vertex> vertexList;
    private ArrayList<ArrayList<Vertex>> adjacencyList;
    private Boolean adjacencyListInitialized;

    public Graph(ArrayList<Vertex> vertices){
        this.vertexList = new ArrayList<Vertex>();
        this.vertexList = vertices;
        this.adjacencyListInitialized = false;
    }

    public ArrayList<Vertex> getVertexList(){
        return this.vertexList;
    }

    public ArrayList<Edge> getEdgeList(){
        return this.edgeList;
    }

    public void setEdgeList(ArrayList<Edge> edges){
        this.edgeList = new ArrayList<Edge>();
        this.edgeList = edges;
    }

    public void addEdge(Edge e){
        this.edgeList.add(e);
    }

    public void createAdjacencyList(){
        this.adjacencyList = new ArrayList<ArrayList<Vertex>>(this.vertexList.size());
        this.adjacencyListInitialized = true;
        int i = 0;
        while(i < this.vertexList.size()){
            this.adjacencyList.add(new ArrayList<Vertex>());
            i++;
        }
        for(Edge e : this.edgeList){
            addNeighbor(e.getFirst().getId(), e.getSecond());
            addNeighbor(e.getSecond().getId(), e.getFirst());
        }
    }

    public ArrayList<ArrayList<Vertex>> getAdjacencyList(){
        return this.adjacencyList;
    }

    private void addNeighbor(int vertexId, Vertex neighbor){
        this.adjacencyList.get(vertexId).add(neighbor);
    }

    public int getNumberCutEdgesOfVertex(int vertexId){
        if(adjacencyListInitialized){
            int count = 0;
            int group = this.vertexList.get(vertexId).getGroup();
            for(Vertex v : this.adjacencyList.get(vertexId)){
                if(v.getGroup() != group){
                    count++;
                }
            }
            return count;
        }
        return -1;
    }

    public int getNumberAllCutEdges() {
        int count = 0;
        for(Edge e : this.edgeList){
            if(e.getFirst().getGroup() != e.getSecond().getGroup()){
                count++;
            }
        }
        return count;
    }
}
