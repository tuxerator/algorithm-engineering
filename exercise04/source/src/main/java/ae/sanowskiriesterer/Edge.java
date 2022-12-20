package ae.sanowskiriesterer;

public class Edge {

    Node a;
    Node b;
    Float weight;

    //need to already pass the euclidian edge weight
    public Edge(Node one, Node two, float w){
        a = one;
        b = two;
        weight = w; 
    }
    
}
