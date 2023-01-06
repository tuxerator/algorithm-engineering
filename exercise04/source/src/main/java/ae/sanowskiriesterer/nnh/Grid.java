package ae.sanowskiriesterer.nnh;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;

public class Grid {
  private ArrayList<ArrayList<LinkedList<Node>>> grid;

  private int spacing;

  public Grid() {
    this(0,0,1);
  }

  public Grid(int spacing) {
    this(0,0,spacing);
  }

  public Grid(int size_x, int size_y, int spacing) {
    this.spacing = spacing;
    grid = new ArrayList<ArrayList<LinkedList<Node>>>(size_x);
    for (ArrayList<LinkedList<Node>> row : grid) {
      row = new ArrayList<LinkedList<Node>>(size_y);
    }
  }

  public int getSize_x() {
    return size_x;
  }

  public int getSize_y() {
    return size_y;
  }

  public void add(Node node) {
    int x = (int) node.getX() / spacing;
    int y = (int) node.getY() / spacing;

    grid.get(x).get(y).add(node);
  }

  public LinkedList<Node> getTile(int x, int y) {
    return grid[x][y];
  }

  private void adjustSize(int x, int y) {
    if (x >= size_x) {
      increaseX(x);
    }
    if (y >= size_y) {
      increaseY(y);
    }
  }

  private void increaseX(int x) {
    if (x < size_x)
      throw IllegalArgumentExeption;

    for (LinkedList<Node>[] row: grid) {
      row = Arrays.copyOf(row, x - 1);
    }
     size_x = x - 1;
  }

  private void increaseY(int y) {
    if (y < size_y)
      throw IllegalArgumentExeption;

    grid = Arrays.copyOf(grid, y - 1);
    
    size_y = y - 1;
  }
}
