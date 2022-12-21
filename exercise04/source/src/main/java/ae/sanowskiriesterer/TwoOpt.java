package ae.sanowskiriesterer;

//import java.lang.Math;
import java.util.ArrayList;
import java.util.LinkedList;

public class TwoOpt {

    public static LinkedList<Edge> edges;
    public static CoordSystem g;

    public static void main(String[] args) throws Exception{

        //g = Reader.read(args[0]);
        LinkedList<Node> nodes = new LinkedList<Node>();
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

        //TODO see if solution is (a,b),(b,d),(d,g),(g,f),(f,e),(e,c),(c,a) (maybe write test)
        //TODO write javadocs

        
    }

    /**
     * Calculates the weights of each edge
     */
    public static void calculateWeights() {
        for(Edge e : edges) {
            e.weight = euclidianDistance(e.nodeA, e.nodeB);
        }
    }

    /**
     * 
     */
    public static void createArbitraryTour() {
        edges = new LinkedList<Edge>();
        for(int i = 1; i < g.nodes.size(); i++) {
            Node a = g.nodes.get(i-1);
            Node b = g.nodes.get(i);
            edges.add(new Edge(a,b,0));
        }
        Node a = g.nodes.get(g.nodes.size()-1);
        Node b = g.nodes.get(0);
        //weights do not matter right now, since swapping intersecting edges leads to a smaller euclidian distance. Setting weight in the end is enough
        edges.add(new Edge(a,b,0));
        removeIntersections();
    }

    /**
     * 
     */
    private static void removeIntersections(){
        for(int i = 0; i < edges.size(); i++){
            for(int j = i+2; j < edges.size(); j++){ //j=i+2 because there can't be an intersection between two incident edges
                if(intersection(edges.get(i), edges.get(j))) {
                    swapEdges(i, j);
                    break;
                }
                
            }
        }

        calculateWeights();

    }

    /**
     * @param edgeA
     * @param edgeB
     */
    private static void swapEdges(int edgeA, int edgeB) {

        Node temp = edges.get(edgeA).nodeB;
        edges.get(edgeA).nodeB = edges.get(edgeB).nodeA;
        edges.get(edgeB).nodeA = temp;

        changePathDirection(edgeA+1, edgeB-1);

    }

    /**
     * @param start
     * @param end
     */
    private static void changePathDirection(int start, int end) {

        Boolean isOddLength = (end - start) % 2 == 0;

        while(start < end){
            //first change the direction of each edge
            Node tempN = edges.get(start).nodeA;
            edges.get(start).nodeA = edges.get(start).nodeB;
            edges.get(start).nodeB = tempN;
            tempN = edges.get(end).nodeA;
            edges.get(end).nodeA = edges.get(end).nodeB;
            edges.get(end).nodeB = tempN;
            //second swap the positions of the two edges
            Edge tempE = edges.get(start);
            edges.remove(start);
            edges.add(start,edges.get(end));
            edges.remove(end);
            edges.add(end, tempE);
            start++;
            end--;
        }
        //one node left, if path has odd length, change the direction of that node
        if(isOddLength){
            Node tempN = edges.get(start).nodeA;
            edges.get(start).nodeA = edges.get(start).nodeB;
            edges.get(start).nodeB = tempN;
            tempN = edges.get(end).nodeA;
            edges.get(end).nodeA = edges.get(end).nodeB;
            edges.get(end).nodeB = tempN;
        }

    }

    /**
     * @param a
     * @param b
     * @return
     */
    private static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }

    /**
     * @param line
     * @param a
     * @param b
     * @return
     */
    private static Boolean intersection(Line line, Node a, Node b){ //two edges
        //if a.yCoord is above the line, then b must lie under the line and between the two newly created lines
        if(a.yCoord > line.solveForY(a.xCoord)) {

            Line aToLineA = new Line(a,line.a);
            Line aToLineB = new Line(a,line.b);
            return (b.yCoord < line.solveForY(b.xCoord) && b.xCoord > aToLineA.solveForX(b.yCoord) && b.xCoord < aToLineB.solveForX(b.yCoord));

        //if b.yCoord is above the line, then c must lie under the line and between the two newly created lines
        } else if(b.yCoord > line.solveForY(b.xCoord)){

            Line aToLineA = new Line(b,line.a);
            Line aToLineB = new Line(b,line.b);
            return (a.yCoord < line.solveForY(a.xCoord) && a.xCoord > aToLineA.solveForX(a.yCoord) && a.xCoord < aToLineB.solveForX(a.yCoord));

        }

        return false;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    private static Boolean intersection(Edge a, Edge b){
        Line line;
        if(a.nodeA.xCoord == a.nodeB.xCoord){
            line = new Line(b.nodeA, b.nodeB); //equals edge b
            return intersection(line, a.nodeA, a.nodeB);
        } else {
            line = new Line(a.nodeA,a.nodeB); //equals edge a
            return intersection(line, b.nodeA, b.nodeB);
        }
    }
    
}
