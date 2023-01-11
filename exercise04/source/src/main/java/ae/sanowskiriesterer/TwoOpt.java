package ae.sanowskiriesterer;

import java.util.ArrayList;

public class TwoOpt {

    public static ArrayList<Edge> edges;
    public static ArrayList<Node> nodes;
    public static int edgeSwaps;

    public static void main(String[] args) throws Exception{

        nodes = new ArrayList<Node>();
        nodes = Reader.read(args[0]);
        float start = System.currentTimeMillis();
        // createTour();
        createTour(System.currentTimeMillis(), 30*60000); // duration: 30 min
        float end = System.currentTimeMillis();
        float runningTime = end - start;
        System.out.println(runningTime+","+edgeSwaps);
        
    }


    /**
     * 
     */
    // public static void createTour() throws Exception{
    public static void createTour(float startTime, float duration) throws Exception {
        edges = new ArrayList<Edge>();
        for(int i = 1; i < nodes.size(); i++) {
            Node a = nodes.get(i-1);
            Node b = nodes.get(i);
            edges.add(new Edge(a,b));
        }
        Node a = nodes.get(nodes.size()-1);
        Node b = nodes.get(0);
        edges.add(new Edge(a,b));
        // DrawTour.draw(edges,"initialLuxembourg.svg");
        // improveTour();
        improveTour(startTime, duration);
        // DrawTour.draw(edges, "resultLuxembourg.svg");
    }

    /**
     * Searches for edges that would improve the tour if swapped and swaps them
     */
    // public static void improveTour() throws Exception{
    public static void improveTour(float startTime, float duration) throws Exception{
        Boolean stillSwapping = true;
        edgeSwaps = 0;
        //as long as there are edges that can be improved, search for them
        While:
        while(stillSwapping){
            stillSwapping = false;
            for(int i = 0; i < edges.size(); i++){
                for(int j = i+2; i==0 ?  j < edges.size() - 1 : j < edges.size(); j++){ //j=i+2 because you can't swap two incident edges
                    if(isSwappedBetter(i, j)){
                        stillSwapping = true;
                        edgeSwaps++;
                        swapEdges(i, j);
                        // if(edgeSwaps == 2012){
                        //     DrawTour.draw(edges,"halfLuxembourg.svg");
                        // }
                    }
                    //if it is already running longer than duration
                    if(System.currentTimeMillis()-startTime > duration){
                        break While;
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
        //need to find out which way of changing paths would be shorter
        if(edgeB - edgeA - 1 <= (edges.size()-edgeB-1+edgeA)){
            Edge a = new Edge(edges.get(edgeA).nodeA, edges.get(edgeB).nodeA);
            Edge b = new Edge(edges.get(edgeA).nodeB,edges.get(edgeB).nodeB);
            edges.remove(edgeA);
            edges.add(edgeA, a);
            edges.remove(edgeB);
            edges.add(edgeB, b);

            changePathDirection(edgeA+1, edgeB-1);
        } else {
            Edge a = new Edge(edges.get(edgeB).nodeA, edges.get(edgeA).nodeA);
            Edge b = new Edge(edges.get(edgeB).nodeB,edges.get(edgeA).nodeB);
            edges.remove(edgeA);
            edges.add(edgeA, b);
            edges.remove(edgeB);
            edges.add(edgeB, a);
            if(edgeB < edges.size()-1){
                if(edgeA > 0){
                    changePathDirection(edgeB+1, edgeA-1);    
                } else {
                    changePathDirection(edgeB+1, edges.size()-1);
                }
            } else {
                if(edgeA > 0){
                    changePathDirection(0, edgeA-1);    
                } else {
                    //cannot happen
                    changePathDirection(0, edges.size()-1);
                }
            }
        }

    }

    /**
     * @param start (index of first edge on path)
     * @param end   (index of last edge on path)
     * Changes the direction of the directed path from start to end
     */
    public static void changePathDirection(int start, int end) {
        while(start > end){
            //first change the direction of each edge
            Edge newStart = new Edge(edges.get(start).nodeB, edges.get(start).nodeA);
            Edge newEnd = new Edge(edges.get(end).nodeB, edges.get(end).nodeA);
            //second swap the positions of the two edges
            edges.remove(start);
            edges.add(start,newEnd);
            edges.remove(end);
            edges.add(end, newStart);
            if(start + 1 > edges.size()-1){
                start = 0;
            } else {
                start++;
            }
            if(end - 1 < 0){
                end = edges.size() -1;
            } else {
                end--;
            }
        }
        if(start == 0 && end == edges.size()-1){
            //finished
        } else {
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

    }

    /**
     * @param a
     * @param b
     * @return euclidean distance of Node a and b
     */
    public static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }

   
}
