package ae.sanowskiriesterer;

public class Edge {

    public Node nodeA;
    public Node nodeB;
    public float weight;

    public Edge(Node one, Node two){
        this.nodeA = one;
        this.nodeB = two;
        this.weight = euclidianDistance(this.nodeA,this.nodeB); 
    }

    // public void setNodeA(Node a){
    //     this.nodeA = a;
    //     this.weight = TwoOpt.euclidianDistance(nodeA,nodeB);
    // }

    // public void setNodeB(Node b){
    //     this.nodeB = b;
    //     this.weight = TwoOpt.euclidianDistance(nodeA, nodeB);
    // }

    private static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }

    
    
}
