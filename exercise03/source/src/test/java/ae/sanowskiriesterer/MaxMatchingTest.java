package ae.sanowskiriesterer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MaxMatchingTest {

    static ArrayList<Vertex> graph;
    static ArrayList<Vertex> graphWSelfLoops;

    @Before
    public void initializeGraph() {
        graph = new ArrayList<Vertex>();
        graphWSelfLoops = new ArrayList<Vertex>();

        for(int i = 0; i < 8; i++){
            graph.add(new Vertex(i));
            graphWSelfLoops.add(new Vertex(i));
        }
        graph.get(0).addNeighbors(graph.get(1),graph.get(2),graph.get(3));
        graph.get(1).addNeighbors(graph.get(0),graph.get(2),graph.get(4));
        graph.get(2).addNeighbors(graph.get(0),graph.get(1),graph.get(3),graph.get(5),graph.get(6));
        graph.get(3).addNeighbors(graph.get(0),graph.get(2),graph.get(6));
        graph.get(4).addNeighbors(graph.get(1),graph.get(5),graph.get(7));
        graph.get(5).addNeighbors(graph.get(2),graph.get(4),graph.get(7));
        graph.get(6).addNeighbors(graph.get(2),graph.get(3),graph.get(7));
        graph.get(7).addNeighbors(graph.get(4),graph.get(5),graph.get(6));

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
    public void testWithoutSelfLoops() {
        ArrayList<Integer> result = new ArrayList<Integer>(8);
        for (int i = 0; i < 8; i++) {
            result.add((Integer) i);
        }

        ArrayList<Vertex> maxMatching = MaxMatching.getMatching(graph);
        ArrayList<Integer> matchingResult = new ArrayList<Integer>();
        for(Vertex v : maxMatching){
            matchingResult.add((Integer) v.id);
        }

        assertEquals(result, matchingResult);

    }

    @Test
    public void testWithSelfLoops() {
        ArrayList<Integer> result = new ArrayList<Integer>(8);
        for (int i = 0; i < 7; i++) {
            result.add((Integer) i);
        }

        ArrayList<Vertex> maxMatching = MaxMatching.getMatching(graphWSelfLoops);
        ArrayList<Integer> matchingResult = new ArrayList<Integer>();
        for(Vertex v : maxMatching){
            matchingResult.add((Integer) v.id);
        }

        assertEquals(result, matchingResult);
    }
    
}
