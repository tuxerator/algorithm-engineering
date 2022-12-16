package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.util.Stack;

public class SimpleBSTIter {
  // public ArrayList<Vertex> graph;
  // public ArrayList<Vertex> cover;
  public static int k;
  public static Stack<ArrayList<Vertex[]>> stack = new Stack<ArrayList<Vertex[]>>();
  public static Stack<Vertex[]> edges = new Stack<Vertex[]>();

  public static void main(String[] args) throws Exception {
    // Vertex v1 = new Vertex(1);
    // Vertex v2 = new Vertex(2);
    // Vertex v3 = new Vertex(3);
    // Vertex v4 = new Vertex(4);
    // Vertex v5 = new Vertex(5);
    // Vertex v6 = new Vertex(6);
    // Vertex v7 = new Vertex(7);
    // Vertex v8 = new Vertex(8);
    // v1.addNeighbors(v2, v3, v4);
    // v2.addNeighbors(v1, v3, v5);
    // v3.addNeighbors(v1, v2, v4, v6, v7);
    // v4.addNeighbors(v1, v3, v7);
    // v5.addNeighbors(v2, v6, v8);
    // v6.addNeighbors(v3, v5, v8);
    // v7.addNeighbors(v3, v4, v8);
    // v8.addNeighbors(v5, v6, v7);

    // // ArrayList<Vertex> graph = new ArrayList<Vertex>();
    // graph.add(v1);
    // graph.add(v2);
    // graph.add(v3);
    // graph.add(v4);
    // graph.add(v5);
    // graph.add(v6);
    // graph.add(v7);
    // graph.add(v8);

    ArrayList<Vertex> graph = Reader.read(args[0]);

    k = Integer.parseInt(args[1]);

    // k = 5;
    Vertex[] cover = simpleBST(graph);
    System.out.println();
    System.out.println(cover.length + "\n");
    for (Vertex v : cover) {
      System.out.println(v.id);
    }
  }

  public static Vertex[] simpleBST(ArrayList<Vertex> graph) {
    Stack<Frame> callStack = new Stack<>();
    Stack<Vertex> cover = new Stack<>();
    callStack.push(new Frame(graph, k));
    while (!callStack.empty()) {
      Frame frame = callStack.peek();
      // System.out.println("noEdges: " + frame.isNoEdges());
      // System.out.println("Current cover:");
      // System.out.println(cover);

      if (cover.size() >= k && !frame.isNoEdges() && cover.peek().id != -1) {
        cover.push(new Vertex(-1));
        callStack.pop();
        continue;
      } else if (frame.isNoEdges()) {
        break;
      }

      if (!frame.isVisitedV()) {
        frame.setRemovedEdges(remove(frame.getV()));
        cover.push(frame.getV());
        callStack.push(new Frame(graph, k));
        frame.setVisitedV(true);
        continue;
      }

      if (cover.peek().id != -1) {
        callStack.pop();
        continue;
      }

      if (!frame.isVisitedW()) {
        cover.pop();
        cover.pop();

        addToGraph(frame.getRemovedEdges());
        frame.setRemovedEdges(remove(frame.getW()));
        cover.push(frame.getW());
        callStack.push(new Frame(graph, k));
        frame.setVisitedW(true);
        continue;
      }

      if (cover.peek().id == -1) {
        cover.pop();
        cover.pop();
        cover.push(new Vertex(-1));
      }

      addToGraph(frame.getRemovedEdges());
      callStack.pop();
    }

    return cover.toArray(new Vertex[cover.size()]);
  }

  public static ArrayList<Vertex[]> remove(Vertex v) {

    ArrayList<Vertex[]> edges = new ArrayList<Vertex[]>();
    for (Vertex w : v.neighbors) {
      Vertex[] edge = {v, w};
      edges.add(edge);
      for (Vertex x : w.neighbors) {
        if (x.id == v.id) {
          w.neighbors.remove(x);
          break;
        }
      }
    }
    v.neighbors.clear();
    return edges;
  }

  public static void addToGraph(ArrayList<Vertex[]> edges) {
    for (Vertex[] edge : edges) {
      edge[0].neighbors.add(edge[1]);
      edge[1].neighbors.add(edge[0]);
    }
  }
}
