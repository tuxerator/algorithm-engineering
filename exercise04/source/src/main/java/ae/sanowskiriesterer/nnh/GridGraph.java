package ae.sanowskiriesterer.nnh;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

public class GridGraph {
  private Grid grid;
  private HashMap<Integer,Cell> coordinates;

  public GridGraph(Collection<Node> nodes) {
    grid = new Grid(1, nodes.size());
    this.addNodes(nodes);
  }

  public void addNode(Node node) {
    coordinates.put(node.getID(), new Cell((int) node.getX() / grid.getSpacing(), (int) node.getY() / grid.getSpacing()));
    grid.put(node);
  }

  public void addNodes(Collection<Node> nodes) {
    nodes.forEach((node) -> this.addNode(node));
  }

  public Node getNode(int ID) {
  }

  public Node getNearestNeighbor(Node node) {
    Node neighbor;
    Cell startCell = new Cell((int) node.getX() / grid.getSpacing(), (int) node.getY() / grid.getSpacing());

  }

  private Node greedyNeighborSearch(Node node) {
    LinkedList<Node> cell = grid.getCell(new Cell((int) node.getX() / grid.getSpacing(), (int) node.getY() / grid.getSpacing()));
  }
}
