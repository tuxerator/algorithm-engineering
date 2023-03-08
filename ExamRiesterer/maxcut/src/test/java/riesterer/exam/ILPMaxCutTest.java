package riesterer.exam;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class ILPMaxCutTest {
    
    // TODO: test solution with graph I know the optimal solution to

    @Test
    public void testILPMaxCut(){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(int i = 0; i < 4; i++){
            vertices.add(new Vertex(i));
        }
        edges.add(new Edge(vertices.get(0), vertices.get(1)));
        edges.add(new Edge(vertices.get(1), vertices.get(2)));
        edges.add(new Edge(vertices.get(1), vertices.get(3)));
        edges.add(new Edge(vertices.get(2), vertices.get(3)));

        Graph g = new Graph(vertices);
        g.setEdgeList(edges);
        
        assertEquals(3, ILPMaxCut.start(g,2));
    }
}
