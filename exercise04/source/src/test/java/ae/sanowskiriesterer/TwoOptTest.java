package ae.sanowskiriesterer;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TwoOptTest {

    @Test
    public void testChangePathDirection(){TwoOpt.nodes = new LinkedList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);

        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);

        TwoOpt.edges = new LinkedList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));
        TwoOpt.changePathDirection(0, TwoOpt.edges.size()-1);

        LinkedList<Edge> result = new LinkedList<Edge>();
        result.add(new Edge(a, c));
        result.add(new Edge(c, b));
        result.add(new Edge(b, a));

        assertTrue(isEqual(TwoOpt.edges,result));
        
    }

    @Test
    public void testSwappingEdges(){

        TwoOpt.nodes = new LinkedList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(5,1);

        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);

        TwoOpt.edges = new LinkedList<Edge>();
        for(int i = 1; i < TwoOpt.nodes.size(); i++) {
            Node a1 = TwoOpt.nodes.get(i-1);
            Node b1 = TwoOpt.nodes.get(i);
            TwoOpt.edges.add(new Edge(a1,b1));
        }
        Node a1 = TwoOpt.nodes.get(TwoOpt.nodes.size()-1);
        Node b1 = TwoOpt.nodes.get(0);
        TwoOpt.edges.add(new Edge(a1,b1));

        TwoOpt.swapEdges(0, TwoOpt.edges.size()-2);

        LinkedList<Edge> result = new LinkedList<Edge>();
        result.add(new Edge(a, c));
        result.add(new Edge(c, b));
        result.add(new Edge(b, d));
        result.add(new Edge(d, a));

        assertTrue(isEqual(result,TwoOpt.edges));

    }
    
    @Test
    public void testExampleGraph(){
        TwoOpt.nodes = new LinkedList<Node>();
        Node a = new Node(1,2);
        Node b = new Node(2,1);
        Node c = new Node(4,3);
        Node d = new Node(5,1);
        Node e = new Node(3,4);
        Node f = new Node(6,4);
        Node g = new Node(7,2);

        TwoOpt.nodes.add(a);
        TwoOpt.nodes.add(b);
        TwoOpt.nodes.add(c);
        TwoOpt.nodes.add(d);
        TwoOpt.nodes.add(e);
        TwoOpt.nodes.add(f);
        TwoOpt.nodes.add(g);
        TwoOpt.createTour();

        LinkedList<Edge> result = new LinkedList<Edge>();
        result.add(new Edge(a,b));
        result.add(new Edge(b,d));
        result.add(new Edge(d,g));
        result.add(new Edge(g,f));
        result.add(new Edge(f,c));
        result.add(new Edge(c,e));
        result.add(new Edge(e,a));

        assertTrue(isEqual(result,TwoOpt.edges));
    }

    public boolean isEqual(LinkedList<Edge> a, LinkedList<Edge> b){
        if(a.size() != b.size()){
            System.out.println("First case failure");
            return false;
        } else {
            for(int i = 0; i < a.size(); i++){
                if(a.get(i).nodeA.xCoord != b.get(i).nodeA.xCoord || a.get(i).nodeA.yCoord != b.get(i).nodeA.yCoord
                   || a.get(i).nodeB.xCoord != b.get(i).nodeB.xCoord || a.get(i).nodeB.yCoord != b.get(i).nodeB.yCoord){
                    System.out.println("Second case failure for position "+i);
                    return false;
                }
            }
        }
        System.out.println("no failure");
        return true;

    }
    
}
