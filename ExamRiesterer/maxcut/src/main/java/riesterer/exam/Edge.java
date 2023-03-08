package riesterer.exam;

public class Edge {
    
    private Vertex one;
    private Vertex two;

    public Edge(Vertex a, Vertex b){
        this.one = a;
        this.two = b;
    }

    public Vertex getFirst(){
        return this.one;
    }

    public Vertex getSecond(){
        return this.two;
    }

}
