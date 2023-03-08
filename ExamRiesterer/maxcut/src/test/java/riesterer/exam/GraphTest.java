package riesterer.exam;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class GraphTest {

    // TODO: test counting all cut edges

    @Test
    public void testGetNumberAllCutEdges(){
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

        assertTrue(g.getNumberAllCutEdges() == 1);
    }

    // TODO: test counting cut edges of a vertex

    @Test
    public void testGetNumberCutEdgesOfVertex(){
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
        vertices.get(1).setGroup(0);
        vertices.get(2).setGroup(1);
        vertices.get(3).setGroup(1);

        Graph g = new Graph(vertices);
        g.setEdgeList(edges);
        g.createAdjacencyList();

        assertTrue(g.getNumberCutEdgesOfVertex(1) == 2);
    }
    // TODO: test create AdjacencyList
    
    @Test
    public void testCreateAdjacencyList(){
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
        g.createAdjacencyList();

        assertTrue(isIdentical(edges, g));


    }

    public Boolean isIdentical(ArrayList<Edge> edges, Graph g){
        for(Edge e : edges){
            int first = e.getFirst().getId();
            int second = e.getSecond().getId();
            Boolean isRepresented = false;
            for(Vertex v : g.getAdjacencyList().get(first)){
                if(v.getId() == second){
                    isRepresented = true;
                }
            }
            if(!isRepresented){
                return false;
            } else {
                isRepresented = false;
                for(Vertex v : g.getAdjacencyList().get(second)){
                    if(v.getId() == first){
                        isRepresented = true;
                    }
                }
                if(!isRepresented){
                    return false;
                }
            }
        }
        return true;
    }
}
