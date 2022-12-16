package ae.sanowskiriesterer;

import java.util.ArrayList;

public class Graph {
  private ArrayList<ArrayList<Boolean>> adjacencyMatrix;

  public Graph(int numVertecies, Edge ... edges) {
    adjacencyMatrix.ensureCapacity(numVertecies);
    adjacencyMatrix.forEach((x) -> x.ensureCapacity(numVertecies));
    addEdges(edges);
  }

  public int[] addVertecies(Vertex ... vertecies) {
    int[] ids = new int[vertecies.length];
    int i = 0;
    for (Vertex vertex : vertecies) {
      adjacencyMatrix.forEach((x) -> {
        x.add(false);
      });
      ids[i] = adjacencyMatrix.size() - 1;
      i++;
    }
  }

  public void addEdges (Edge ... edges) {
    for (Edge edge : edges) {
      adjacencyMatrix.get(edge.v).set(edge.w, true);
      adjacencyMatrix.get(edge.w).set(edge.v, true);
    }
  }

  public void removeEdges(Edge ... edges) {
    for (Edge edge : edges) {
      adjacencyMatrix.get(edge.v).set(edge.w, false);
      adjacencyMatrix.get(edge.w).set(edge.v, false);
    }
  }
  
  public void removeIncidentEdges(int vertexID) {
    adjacencyMatrix.get(vertexID).forEach((vertex) -> vertex = false);
  }
}
