package ae.sanowskiriesterer;

public class Edge {

    Node nodeA;
    Node nodeB;
    Float weight;

    //need to already pass the euclidian edge weight
    public Edge(Node one, Node two, float w){
        nodeA = one;
        nodeB = two;
        weight = w; 
    }

    
    
}
