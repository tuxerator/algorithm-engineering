package riesterer.exam;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class ApproxMaxCutTest {
    
    // TODO: test groupVertices

    @Test
    public void testGroupVertices(){
        ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
        for(int i = 0; i < 10; i++){
            vertexList.add(new Vertex(i));
        }
        Graph g = new Graph(vertexList);
        ApproxMaxCut.groupVertices(g);

        assertTrue(isGrouped(vertexList));
    }

    // TODO: test isSwappedBetter

    @Test
    public void testIsSwappedBetter(){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for(int i = 0; i < 4; i++){
            vertices.add(new Vertex(i));
        }
        edges.add(new Edge(vertices.get(0), vertices.get(1)));
        edges.add(new Edge(vertices.get(1), vertices.get(2)));
        edges.add(new Edge(vertices.get(1), vertices.get(3)));
        edges.add(new Edge(vertices.get(2), vertices.get(3)));
        vertices.get(0).setGroup(0);
        vertices.get(1).setGroup(1);
        vertices.get(2).setGroup(1);
        vertices.get(3).setGroup(1);

        Graph g = new Graph(vertices);
        g.setEdgeList(edges);
        g.createAdjacencyList();

        assertTrue(ApproxMaxCut.isSwappedBetter(g, 1));
    }

    // TODO: test swapGroup

    @Test
    public void testSwapGroup(){
        Vertex v =  new Vertex(0);
        v.setGroup(0);
        Vertex u = new Vertex(1);
        u.setGroup(1);
        ApproxMaxCut.swapGroup(v);
        ApproxMaxCut.swapGroup(u);

        assertTrue(v.getGroup() == 1 && u.getGroup() == 0);
    }


    public Boolean isGrouped(ArrayList<Vertex> vertices){
        for(Vertex v : vertices){
            if(v.getGroup() < 0 || v.getGroup() > 1){
                return false;
            }
        }
        return true;
    }
}
