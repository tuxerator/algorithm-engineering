package ae.sanowskiriesterer;

import java.util.ArrayList;

public class Frame {
  private ArrayList<Vertex> graph;
  private ArrayList<Vertex[]> removedEdges = new ArrayList<>();
  private Vertex v;
  private Vertex w;
  private boolean visitedV = false;
  private boolean visitedW = false;
  private boolean noEdges = true;
  private int k;
  private Frame parent;

  public Frame(ArrayList<Vertex> graph, int k) {
    this.graph = graph;
    this.k = k;
    for (Vertex vertex : graph) {
      if (vertex.neighbors.size() > 0) {
        noEdges = false;
        this.v = vertex;
        this.w = vertex.neighbors.get(0);
      }
    }
  }

  public boolean isNoEdges() {
    return noEdges;
  }

  public void setNoEdges(boolean noEdges) {
    this.noEdges = noEdges;
  }

  public boolean isVisitedV() {
    return visitedV;
  }

  public boolean isVisitedW() {
    return visitedW;
  }

  public ArrayList<Vertex> getGraph() {
    return graph;
  }

  public void setGraph(ArrayList<Vertex> graph) {
    this.graph = graph;
  }

  public Vertex getV() {
    return v;
  }

  public void setV(Vertex v) {
    this.v = v;
  }

  public Vertex getW() {
    return w;
  }

  public void setW(Vertex w) {
    this.w = w;
  }

  public void setVisitedV(boolean visitedV) {
    this.visitedV = visitedV;
  }

  public void setVisitedW(boolean visitedW) {
    this.visitedW = visitedW;
  }

  public int getK() {
    return k;
  }

  public void setK(int k) {
    this.k = k;
  }

  public ArrayList<Vertex[]> getRemovedEdges() {
    return removedEdges;
  }

  public void setRemovedEdges(ArrayList<Vertex[]> removedEdges) {
    this.removedEdges = removedEdges;
  }

  public Frame getParent() {
    return parent;
  }

  public void setParent(Frame parent) {
    this.parent = parent;
  }
}
