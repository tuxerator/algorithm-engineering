package ae.sanowskiriesterer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SimpleBSTTest {

    static ArrayList<Vertex> graphWOSelfLoops;
    static ArrayList<Vertex> graphWSelfLoops;

    @Before
    public void initializeGraphs() {
        graphWOSelfLoops = new ArrayList<Vertex>();
        graphWSelfLoops = new ArrayList<Vertex>();
        for (int i = 0; i < 8; i++) {
            graphWOSelfLoops.add(new Vertex(i));
            graphWSelfLoops.add(new Vertex(i));
        }
        graphWOSelfLoops.get(0).addNeighbors(graphWOSelfLoops.get(1),graphWOSelfLoops.get(2),graphWOSelfLoops.get(3));
        graphWOSelfLoops.get(1).addNeighbors(graphWOSelfLoops.get(0),graphWOSelfLoops.get(2),graphWOSelfLoops.get(4));
        graphWOSelfLoops.get(2).addNeighbors(graphWOSelfLoops.get(0),graphWOSelfLoops.get(1),graphWOSelfLoops.get(3),graphWOSelfLoops.get(5),graphWOSelfLoops.get(6));
        graphWOSelfLoops.get(3).addNeighbors(graphWOSelfLoops.get(0),graphWOSelfLoops.get(2),graphWOSelfLoops.get(6));
        graphWOSelfLoops.get(4).addNeighbors(graphWOSelfLoops.get(1),graphWOSelfLoops.get(5),graphWOSelfLoops.get(7));
        graphWOSelfLoops.get(5).addNeighbors(graphWOSelfLoops.get(2),graphWOSelfLoops.get(4),graphWOSelfLoops.get(7));
        graphWOSelfLoops.get(6).addNeighbors(graphWOSelfLoops.get(2),graphWOSelfLoops.get(3),graphWOSelfLoops.get(7));
        graphWOSelfLoops.get(7).addNeighbors(graphWOSelfLoops.get(4),graphWOSelfLoops.get(5),graphWOSelfLoops.get(6));

        graphWSelfLoops.get(0).addNeighbors(graphWSelfLoops.get(1),graphWSelfLoops.get(2),graphWSelfLoops.get(3));
        graphWSelfLoops.get(1).addNeighbors(graphWSelfLoops.get(0),graphWSelfLoops.get(2),graphWSelfLoops.get(4));
        graphWSelfLoops.get(2).addNeighbors(graphWSelfLoops.get(0),graphWSelfLoops.get(1),graphWSelfLoops.get(3),graphWSelfLoops.get(5),graphWSelfLoops.get(6));
        graphWSelfLoops.get(3).addNeighbors(graphWSelfLoops.get(0),graphWSelfLoops.get(2),graphWSelfLoops.get(6));
        graphWSelfLoops.get(4).addNeighbors(graphWSelfLoops.get(1),graphWSelfLoops.get(5),graphWSelfLoops.get(7));
        graphWSelfLoops.get(5).addNeighbors(graphWSelfLoops.get(2),graphWSelfLoops.get(4),graphWSelfLoops.get(7));
        graphWSelfLoops.get(6).addNeighbors(new Vertex(6),graphWSelfLoops.get(2),graphWSelfLoops.get(3),graphWSelfLoops.get(7));
        graphWSelfLoops.get(7).addNeighbors(graphWSelfLoops.get(4),graphWSelfLoops.get(5),graphWSelfLoops.get(6));
    }

    @Test
    public void testWithSelfLoops(){
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0);
        result.add(2);
        result.add(4);
        result.add(6);
        result.add(5);

        SimpleBST.k = 5;
        SimpleBST.cover.clear();
        SimpleBST.stack.clear();
        SimpleBST.graph = graphWSelfLoops;
        long start = System.currentTimeMillis();
        SimpleBST.simpleBST(start,1000);
        ArrayList<Integer> simpleBSTResult = new ArrayList<Integer>();
        for (Vertex v : SimpleBST.cover){
            simpleBSTResult.add((Integer) v.id);
        }

        assertEquals(result, simpleBSTResult);
    }

    @Test
    public void testWithoutSelfLoops() {

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(0);
        result.add(2);
        result.add(4);
        result.add(3);
        result.add(7);

        SimpleBST.k = 5;
        SimpleBST.cover.clear();
        SimpleBST.stack.clear();
        SimpleBST.graph = graphWOSelfLoops;
        long start = System.currentTimeMillis();
        SimpleBST.simpleBST(start,100);
        ArrayList<Integer> simpleBSTResult = new ArrayList<Integer>();
        for (Vertex v : SimpleBST.cover){
            simpleBSTResult.add((Integer) v.id);
        }

        assertEquals(result, simpleBSTResult);

    }
    
}
