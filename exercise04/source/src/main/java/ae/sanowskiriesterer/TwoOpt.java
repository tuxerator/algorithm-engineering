package ae.sanowskiriesterer;

//import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;

public class TwoOpt {

    public static LinkedList<Edge> edges;
    public static LinkedList<Node> nodes;

    public static void main(String[] args) throws Exception{

        //CoordSystem c = Reader.read(args[0]);
        nodes = new LinkedList<Node>();
        //nodes = c.nodes;
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(5,1);
        Node e = new Node(3,4);
        Node f = new Node(6,4);
        Node g = new Node(7,2);

        nodes.add(a);
        nodes.add(b);
        nodes.add(c);
        nodes.add(d);
        nodes.add(e);
        nodes.add(f);
        nodes.add(g);

        createTour();

        for(Edge edge : edges){
            System.out.println(edge.nodeA.xCoord+","+edge.nodeA.yCoord+";"+edge.nodeB.xCoord+","+edge.nodeB.yCoord);
        }

        //TODO write test
        
    }

    // /**
    //  * Calculates the weights of each edge
    //  */
    // public static void calculateWeights() {
    //     for(Edge e : edges) {
    //         e.weight = euclidianDistance(e.nodeA, e.nodeB);
    //     }
    // }

    /**
     * 
     */
    public static void createTour() {
        edges = new LinkedList<Edge>();
        for(int i = 1; i < nodes.size(); i++) {
            Node a = nodes.get(i-1);
            Node b = nodes.get(i);
            edges.add(new Edge(a,b));
        }
        Node a = nodes.get(nodes.size()-1);
        Node b = nodes.get(0);
        edges.add(new Edge(a,b));
        System.out.println("Initial tour created, start improving");
        improveTour();
    }

    /**
     * Searches for edges that would improve the tour if swapped and swaps them
     */
    private static void improveTour(){
        Boolean stillSwapping = true;
        //as long as there are edges that can be improved, search for them
        while(stillSwapping){
            stillSwapping = false;
            for(int i = 0; i < edges.size(); i++){
                for(int j = i+2; i==0 ?  j < edges.size() - 1 : j < edges.size(); j++){ //j=i+2 because you can't swap two incident edges
                    if(isSwappedBetter(i, j)){
                        stillSwapping = true;
                        swapEdges(i, j);
                    }
                    
                }
            }
        }

    }

    /**
     * @param edgeA
     * @param edgeB
     * @return True if swapping two edges would yield to a better weight, false otherwise
     */
    private static Boolean isSwappedBetter(int edgeA, int edgeB){

        return (euclidianDistance(edges.get(edgeA).nodeA, edges.get(edgeB).nodeA)+euclidianDistance(edges.get(edgeA).nodeB, edges.get(edgeB).nodeB)) < (edges.get(edgeA).weight + edges.get(edgeB).weight);
    }

    /**
     * @param edgeA
     * @param edgeB
     * Swaps the two edges A and B to get two new edges: (headA,headB) and (tailA,tailB)
     */
    public static void swapEdges(int edgeA, int edgeB) {

        Edge a = new Edge(edges.get(edgeA).nodeA, edges.get(edgeB).nodeA);
        Edge b = new Edge(edges.get(edgeA).nodeB,edges.get(edgeB).nodeB);
        edges.remove(edgeA);
        edges.add(edgeA, a);
        edges.remove(edgeB);
        edges.add(edgeB, b);

        changePathDirection(edgeA+1, edgeB-1);

    }

    /**
     * @param start (index of first edge on path)
     * @param end   (index of last edge on path)
     * Changes the direction of the directed path from start to end
     */
    public static void changePathDirection(int start, int end) {

        Boolean isOddLength = (end - start) % 2 == 0;

        while(start < end){
            //first change the direction of each edge
            Edge newStart = new Edge(edges.get(start).nodeB, edges.get(start).nodeA);
            Edge newEnd = new Edge(edges.get(end).nodeB, edges.get(end).nodeA);
            //second swap the positions of the two edges
            edges.remove(start);
            edges.add(start,newEnd);
            edges.remove(end);
            edges.add(end, newStart);
            start++;
            end--;
        }
        //one node left, if path has odd length, change the direction of that node
        if(isOddLength){
            Edge newEdge = new Edge(edges.get(start).nodeB, edges.get(start).nodeA);
            edges.remove(start);
            edges.add(start,newEdge);
        }

    }

    /**
     * @param a
     * @param b
     * @return euclidean distance of Node a and b
     */
    public static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }

    // /**
    //  * @param line
    //  * @param a
    //  * @param b
    //  * @return
    //  */
    // private static Boolean intersection(Line line, Node a, Node b){ //two edges
    //     //if a.yCoord is above the line, then b must lie under the line and between the two newly created lines
    //     if(a.yCoord > line.solveForY(a.xCoord)) {

    //         Line aToLineA = new Line(a,line.a);
    //         Line aToLineB = new Line(a,line.b);
    //         return (b.yCoord < line.solveForY(b.xCoord) && b.xCoord > aToLineA.solveForX(b.yCoord) && b.xCoord < aToLineB.solveForX(b.yCoord));

    //     //if b.yCoord is above the line, then c must lie under the line and between the two newly created lines
    //     } else if(b.yCoord > line.solveForY(b.xCoord)){

    //         Line aToLineA = new Line(b,line.a);
    //         Line aToLineB = new Line(b,line.b);
    //         return (a.yCoord < line.solveForY(a.xCoord) && a.xCoord > aToLineA.solveForX(a.yCoord) && a.xCoord < aToLineB.solveForX(a.yCoord));

    //     }

    //     return false;
    // }

    // /**
    //  * @param a
    //  * @param b
    //  * @return
    //  */
    // private static Boolean intersection(Edge a, Edge b){
    //     Line line;
    //     if(a.nodeA.xCoord == a.nodeB.xCoord){
    //         line = new Line(b.nodeA, b.nodeB); //equals edge b
    //         return intersection(line, a.nodeA, a.nodeB);
    //     } else {
    //         line = new Line(a.nodeA,a.nodeB); //equals edge a
    //         return intersection(line, b.nodeA, b.nodeB);
    //     }
    // }
   
}
