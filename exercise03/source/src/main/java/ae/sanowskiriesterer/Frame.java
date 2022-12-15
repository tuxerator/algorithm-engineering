package ae.sanowskiriesterer;

import java.util.ArrayList;

public class Frame {
  private ArrayList<Vertex> graph;
  private Vertex[] edge = new Vertex[2];
  private boolean noEdges;
  private int k;
  private Frame parent;

  public Frame() {}

  public Frame(Vertex[] edge, boolean noEdges) {
    this.edge = edge;
    this.noEdges = noEdges;
  }

  public Vertex[] getEdge() {
    return edge;
  }

  public void setEdge(Vertex[] edge) {
    this.edge = edge;
  }

  public boolean isNoEdges() {
    return noEdges;
  }

  public void setNoEdges(boolean noEdges) {
    this.noEdges = noEdges;
  }

  public Frame createFrame(ArrayList<Vertex> graph) {
    Vertex[] edge = new Vertex[2];
    Boolean noEdges = true;
    for (Vertex v : graph) {
      if (v.neighbors.size() > 0) {
        noEdges = false;
        edge[0] = v;
        edge[1] = v.neighbors.get(0);
        break;
      }
    }

    return new Frame(edge, noEdges);
  }
}
