package ae.sanowskiriesterer.nnh;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;
import java.util.stream.Stream;
import java.util.Iterator;


public class Grid {
  private HashMap<Cell, LinkedList<Node>> grid;
  private HashMap<Integer,Node> coordinates;

  private int spacing;

  public Grid() {
    this(1);
  }

  public Grid(int spacing) {
    this(spacing, 100);
  }

  public Grid(int spacing, int initialCapacity) {
    this(spacing, initialCapacity, 0.75f);
  }

  public Grid(int spacing, int initialCapacity, float loadFactor) {
    this.spacing = spacing;
    grid = new HashMap<>(initialCapacity, loadFactor);
  }

  public int getSpacing() {
    return spacing;
  }

  public int getSize() {
    return grid.size();
  }

  public void put(Node node) {
    Cell cell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    coordinates.put(node.getID(), node);

    if (grid.containsKey(cell)) {
      grid.get(cell).add(node);
      return;
    }

    grid.put(cell, new LinkedList<>(Arrays.asList(node)));
  }

  public void putAll(Collection<Node> nodes) {
    nodes.forEach((node) -> put(node));
  }

  public Node getNode(int ID) {
    Node node = coordinates.get(ID);
    LinkedList<Node> cell = this.getCell(new Cell((int) node.getX() / spacing, (int) node.getY() / spacing));
    ListIterator<Node> iter = cell.listIterator();
    while (iter.hasNext()) {
      Node n = iter.next();
      if (n.getID() == ID) {
        return n;
      }
    }
    return null;
  }

  public boolean removeFromGrid(int ID) {
    Node node = coordinates.get(ID);
    LinkedList<Node> cell = this.getCell(new Cell((int) node.getX() / spacing, (int) node.getY() / spacing));

    return cell.remove(node);
  }

  /**
   * Returns the cell with the specified coordinates within the grid or null if the cell is empty.
   *
   * @param x x-coordinate in the grid
   * @param y y-coordinate in the grid
   * @return A {@link LinkedList} containing all nodes within this cell
   */
  public LinkedList<Node> getCell(int x, int y) {
    return this.getCell(new Cell(x, y));
  }

  public LinkedList<Node> getCell(Cell cell) {
    return grid.get(cell);
  }

  public Node getNearestNeighbor(Node node) {
    removeFromGrid(node.getID());
    Node neighbor = greedyNeighborSearch(node);
    Cell startCell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);

    return neighbor;
  }

  private Node greedyNeighborSearch(Node node) {
    Cell startCell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    LinkedList<Node> cell = getCell(startCell);
    Node neighbor = nearestNode(node,grid.get(startCell));
    Cell currentCell = new Cell(startCell.getX() + 1, startCell.getY() - 1);
    Cell step = new Cell(0,1);
    int currentCircle = 1;
    int i = 0;

    while (neighbor != null) {
      if (cell != null) {
        neighbor = nearestNode(node, cell);
        break;
      }

      currentCell.setX(currentCell.getX() + step.getX());
      currentCell.setY(currentCell.getY() + step.getY());
      i++;

      if (currentCell.getX() - currentCircle == startCell.getX() && currentCell.getY() + currentCircle == startCell.getY()) {
        currentCell.setX(currentCell.getX() + 1);
        currentCell.setY(currentCell.getY() - 1);
        step.setX(0 * step.getX() + (-1 * step.getY()));
        step.setY(1 * step.getX() + (0 * step.getY()));
        currentCircle++;
        i = 0;
      }

      if (i == currentCircle * 2) {
        step.setX(0 * step.getX() + (-1 * step.getY()));
        step.setY(1 * step.getX() + (0 * step.getY()));
        i = 0;
      }
      
      cell = grid.get(currentCell);
    }

    return neighbor;
  }

  private Node nearestNode(Node node, Collection<Node> nodes) {
    return nodes.stream().min(new Comparator<Node>() {
      @Override
      public int compare(Node arg0, Node arg1) {
        double diff = Node.euclidianDistance(arg0, node) - Node.euclidianDistance(arg1, node);
        return (int) Math.signum(diff);
      }
    }).get();
  }
}
