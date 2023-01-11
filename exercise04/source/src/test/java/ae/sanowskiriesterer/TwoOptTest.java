package ae.sanowskiriesterer;

// import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
// import java.beans.Transient;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.Before;
// import org.junit.Test;

public class TwoOptTest {

    @Test
    public void testChangePathDirection1(){
        System.out.println("Changepath1");
        TwoOpt.nodes = new ArrayList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(1,1);
        TwoOpt.nodes = new ArrayList<Node>();
        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);

        TwoOpt.edges = new ArrayList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));
        TwoOpt.changePathDirection(0, TwoOpt.edges.size()-2);

        ArrayList<Edge> result = new ArrayList<Edge>();
        result.add(new Edge(d,c));
        result.add(new Edge(c,b));
        result.add(new Edge(b,a));
        result.add(new Edge(d,a));

        assertTrue(isEqual(result,TwoOpt.edges));
        
    }
    @Test
    public void testChangePathDirection2(){
        System.out.println("changePath2");
        TwoOpt.nodes = new ArrayList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(1,1);
        TwoOpt.nodes = new ArrayList<Node>();
        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);

        TwoOpt.edges = new ArrayList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));
        TwoOpt.changePathDirection(TwoOpt.edges.size()-1, 1);

        ArrayList<Edge> result = new ArrayList<Edge>();
        result.add(new Edge(b,a));
        result.add(new Edge(a,d));
        result.add(new Edge(c,d));
        result.add(new Edge(c,b));

        assertTrue(isEqual(result,TwoOpt.edges));
        
    }

    @Test
    public void testSwappingEdges1(){
        System.out.println("Swapping1");
        TwoOpt.nodes = new ArrayList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(5,1);

        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);

        TwoOpt.edges = new ArrayList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));

        TwoOpt.swapEdges(0, TwoOpt.edges.size()-2);

        ArrayList<Edge> result = new ArrayList<Edge>();
        result.add(new Edge(a, c));
        result.add(new Edge(c, b));
        result.add(new Edge(b, d));
        result.add(new Edge(d, a));

        assertTrue(isEqual(result,TwoOpt.edges));

    }

    @Test
    public void swappingEdges2(){
        System.out.println("Swapping2");
        Node a = new Node (0,0);
        Node b = new Node (1,1);
        Node c = new Node (2,2);
        Node d = new Node (3,3);
        Node e = new Node (4,4);
        Node f = new Node (5,5);
        Node g = new Node (6,6);
        
        TwoOpt.nodes = new ArrayList<Node>();
        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);
        TwoOpt.nodes.add(e);
        TwoOpt.nodes.add(f);
        TwoOpt.nodes.add(g);

        TwoOpt.edges = new ArrayList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));
        TwoOpt.swapEdges(1, TwoOpt.edges.size()-1);

        ArrayList<Edge> result = new ArrayList<Edge>();
        result.add(new Edge(b,a));
        result.add(new Edge(a,c));
        result.add(new Edge(c,d));
        result.add(new Edge(d,e));
        result.add(new Edge(e,f));
        result.add(new Edge(f,g));
        result.add(new Edge(g,b));

        assertTrue(isEqual(result, TwoOpt.edges));
    }
    

    public boolean isEqual(ArrayList<Edge> a, ArrayList<Edge> b){
        if(a.size() != b.size()){
            System.out.println("First case failure");
            return false;
        } else {
            for(int i = 0; i < a.size(); i++){
                if(a.get(i).nodeA.xCoord != b.get(i).nodeA.xCoord || a.get(i).nodeA.yCoord != b.get(i).nodeA.yCoord
                   || a.get(i).nodeB.xCoord != b.get(i).nodeB.xCoord || a.get(i).nodeB.yCoord != b.get(i).nodeB.yCoord){
                    System.out.println("Second case failure for position "+i);
                    System.out.println("Expected "+a.get(i).nodeA.xCoord+","+a.get(i).nodeA.yCoord+";"+a.get(i).nodeB.xCoord+","+a.get(i).nodeB.yCoord);
                    System.out.println("But was "+b.get(i).nodeA.xCoord+","+b.get(i).nodeA.yCoord+";"+b.get(i).nodeB.xCoord+","+b.get(i).nodeB.yCoord);
                    return false;
                }
            }
        }
        System.out.println("no failure");
        return true;

    }
    
}
