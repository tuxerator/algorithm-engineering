package ae.sanowskiriesterer.nnh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import ae.sanowskiriesterer.Edge;

public class Grid {
  private HashMap<Cell, LinkedList<Node>> grid;
  private HashMap<Integer, Node> coordinates;

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
    coordinates = new HashMap<>(initialCapacity, loadFactor);
  }

  public Grid(Collection<ae.sanowskiriesterer.Node> nodes) {
    this();
    Iterator<ae.sanowskiriesterer.Node> nodeIter = nodes.iterator();
    for ( int i = 0; i < nodes.size(); i++) {
      ae.sanowskiriesterer.Node node = nodeIter.next();
      put(new ae.sanowskiriesterer.nnh.Node(node.xCoord, node.yCoord, i));
    }
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
    LinkedList<Node> cell =
        this.getCell(new Cell((int) node.getX() / spacing, (int) node.getY() / spacing));
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
    System.out.println(node);
    Cell key = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    LinkedList<Node> cell =
        this.getCell(key);

    boolean wasRemoved = cell.remove(node);

    System.out.println(cell);

    if (cell.isEmpty()) {
      grid.remove(key);
    }

    return wasRemoved;
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

    System.out.println("Searching neighbor for:" + node);
    System.out.println("Current grid size: " + grid.size());

    if (grid.isEmpty()) {
      return null;
    }
    Cell startCell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    System.out.println(grid.get(startCell));
    Node neighbor = nearestNode(node, grid.get(startCell));
    Cell currentCell = new Cell(startCell.getX() + 1, startCell.getY() - 1);
    Cell step = new Cell(0, 1);
    int currentCircle = 1;
    int i = 0;

    while (neighbor == null) {
      LinkedList<Node> cell = getCell(currentCell);
      if (cell != null) {
        neighbor = nearestNode(node, cell);
        System.out.println("Found: " + neighbor);
        return exactNearestNeighbor(neighbor, node, currentCircle);
      }
      else {
        currentCell.setX(currentCell.getX() + step.getX());
        currentCell.setY(currentCell.getY() + step.getY());
        i++;
      }
      if (currentCell.getX() - currentCircle == startCell.getX()
          && currentCell.getY() + currentCircle == startCell.getY()) {
        currentCell.setX(currentCell.getX() + 1);
        currentCell.setY(currentCell.getY() - 1);
        int stepX = step.getX();
        int stepY = step.getY();
        step.setX(0 * stepX + (-1 * stepY));
        step.setY(1 * stepX + (0 * stepY));
        currentCircle++;
        i = 0;
      }

      if (i == currentCircle * 2) {
        int stepX = step.getX();
        int stepY = step.getY();
        step.setX(0 * stepX + (-1 * stepY));
        step.setY(1 * stepX + (0 * stepY));
        i = 0;
      }
    }

    return neighbor;
  }

  private Node nearestNode(Node node, Collection<Node> nodes) {
    if (nodes == null) {
      return null;
    }

    return nodes.stream()
        .min(
            new Comparator<Node>() {
              @Override
              public int compare(Node arg0, Node arg1) {
                double diff =
                    Node.euclidianDistance(arg0, node) - Node.euclidianDistance(arg1, node);
                return (int) Math.signum(diff);
              }
            })
        .get();
  }

  protected Node furthestPoint(Node node, Cell currentCell) {
    Cell startCell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    Node furthestPoint = node;
    if (currentCell.getX() > startCell.getX()) {
      if (currentCell.getY() > startCell.getY()) {
        furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY() + spacing, 0);
      } else if (currentCell.getY() < startCell.getY()) {
        furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY(), 0);
      } else {
        if (node.getY() - currentCell.getY() >= ((double) spacing) / 2) {
          furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY(), 0);
        } else {
          furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY() + spacing, 0);
        }
      }
    } else if (currentCell.getX() < startCell.getX()) {
      if (currentCell.getY() > startCell.getY()) {
        furthestPoint = new Node(currentCell.getX(), currentCell.getY() + spacing, 0);
      } else if (currentCell.getY() < startCell.getY()) {
        furthestPoint = new Node(currentCell.getX(), currentCell.getY(), 0);
      } else {
        if (node.getY() - startCell.getY() >= ((double) spacing) / 2) {
          furthestPoint = new Node(currentCell.getX(), currentCell.getY(), 0);
        } else {
          furthestPoint = new Node(currentCell.getX(), currentCell.getY() + spacing, 0);
        }
      }
    } else {
      if (node.getX() - startCell.getX() >= ((double) spacing) / 2) {
        if (currentCell.getY() > startCell.getY()) {
          furthestPoint = new Node(currentCell.getX(), currentCell.getY() + spacing, 0);
        } else {
          furthestPoint = new Node(currentCell.getX(), currentCell.getY(), 0);
        }
      }
      else {
        if (currentCell.getY() > startCell.getY()) {
          furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY() + spacing, 0);
        } else {
          furthestPoint = new Node(currentCell.getX() + spacing, currentCell.getY(), 0);
        }

      }
    }

    return furthestPoint;
  }

  protected Node exactNearestNeighbor(Node neighbor, Node node, int currentCircle) {
    Cell startCell = new Cell((int) node.getX() / spacing, (int) node.getY() / spacing);
    LinkedList<Node> cell = getCell(startCell);
    Cell currentCell = new Cell(startCell.getX() + currentCircle, startCell.getY() - currentCircle);
    Cell step = new Cell(0, 1);
    int i = 0;
    boolean enclosed = true;

    while (true) {
      if (cell != null) {
        Node found = nearestNode(node, cell);
        if (Node.euclidianDistance(node, found) < Node.euclidianDistance(node, neighbor)) {
          return exactNearestNeighbor(found, node, currentCircle);
        }
      }

      if (enclosed == true) {
        double neighborDistance = Node.euclidianDistance(node, neighbor);
        Node furthestPoint = furthestPoint(node, currentCell);
        double furthestPointDistance = Node.euclidianDistance(node, furthestPoint);

        if (furthestPoint.getX() < node.getX() && furthestPoint.getY() < node.getY()) {
          enclosed = neighborDistance <= furthestPointDistance;
        }
        else {
          enclosed = neighborDistance < furthestPointDistance;
        }
      }

        currentCell.setX(currentCell.getX() + step.getX());
        currentCell.setY(currentCell.getY() + step.getY());
        i++;

      if (currentCell.getX() - currentCircle == startCell.getX()
          && currentCell.getY() + currentCircle == startCell.getY()) {
        if (enclosed == true) {
          return neighbor;
        }
        currentCell.setX(currentCell.getX() + 1);
        currentCell.setY(currentCell.getY() - 1);
        int stepX = step.getX();
        int stepY = step.getY();
        step.setX(0 * stepX + (-1 * stepY));
        step.setY(1 * stepX + (0 * stepY));
        currentCircle++;
        i = 0;
        enclosed = true;
      }

      if (i == currentCircle * 2) {
        int stepX = step.getX();
        int stepY = step.getY();
        step.setX(0 * stepX + (-1 * stepY));
        step.setY(1 * stepX + (0 * stepY));
        i = 0;
      }

      cell = grid.get(currentCell);
    }
  }

  public ArrayList<Edge> nearestNeighborHeuristic() {
    Node startNode = coordinates.get(0);
    ArrayList<Edge> tour = new ArrayList<>();

    Node a = startNode;
    Node b = getNearestNeighbor(a);

    while (!grid.isEmpty()) {
      Edge edge = new Edge(a.toBadNode(), b.toBadNode());
      tour.add(edge);
      a = b;
      b = getNearestNeighbor(a);
    }

    tour.add(new Edge(a.toBadNode(), startNode.toBadNode()));

    return tour;
  }

  public ArrayList<Edge> nearestNeighborHeuristicQuadratic() {
    Node startNode = coordinates.get(0);
    coordinates.remove(startNode.getID());
    Collection<Node> nodes = coordinates.values();
    ArrayList<Edge> tour = new ArrayList<>();

    Node a = startNode;
    Node b;

    while (!nodes.isEmpty()) {
      b = nearestNode(a,nodes);
      Edge edge = new Edge(a.toBadNode(), b.toBadNode());
      tour.add(edge);
      a = b;
      coordinates.remove(a.getID());
    }

    tour.add(new Edge(a.toBadNode(), startNode.toBadNode()));

    return tour;

  }

  @Override
  public String toString() {
      return coordinates.toString();
  }
}
