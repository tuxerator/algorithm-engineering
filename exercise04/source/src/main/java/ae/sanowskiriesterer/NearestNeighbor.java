package ae.sanowskiriesterer;

public class NearestNeighbor {

    public static void main(String[] args){
        
    }
    
    //find node wit nearest euclidian distance, don't calcualte for every node, try and find possible candidates by using the coordinates
    //ex.: Finding nearest neighbor for Node(0,0) then Node (10,10) cant be nearer than Node(9,9) or Node (10,9) etc.

    public static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }
}
