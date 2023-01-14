package ae.sanowskiriesterer.nnh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ae.sanowskiriesterer.DrawTour;


public class GridTest {

  private Grid grid = new Grid();
  private Grid gird2 = new Grid(2);

  @BeforeEach
  void init() {
    Random rand = new Random();
    rand.doubles(0, 50);
    LinkedList<Node> nodes = new LinkedList<>(Arrays.asList(
      new Node(1.32,5.45, 0),
      new Node(1.92,5.45, 1),
      new Node(1.32,9.45, 2),
      new Node(0.82,5.25, 3),
      new Node(7.32,3.48, 4),
      new Node(7.18,5.00, 5),
      new Node(1.32,5.45, 6),
      new Node(100.32,543.45, 7),
      new Node(105.32,544.45, 8),
      new Node(50,50.99,9),
      new Node(51.99, 49, 10),
      new Node(49.99,50.99,11)
    ));

    grid.putAll(nodes);
    gird2.put(new Node(1, 1, 0));
  }

  @Test
  void testGet() {

    LinkedList<Node> result = grid.getCell(1, 5);
    assertEquals(new LinkedList<Node>(Arrays.asList(
      new Node(1.32,5.45, 0),
      new Node(1.92,5.45, 1),
      new Node(1.32,5.45, 6)
    )), result);

    result = grid.getCell(100, 543);
    assertEquals(new LinkedList<Node>(Arrays.asList(
      new Node(100.32,543.45, 7)
    )), result);

    result = grid.getCell(102, 543);
    assertEquals(null, result);
  }

  // @Test
  // void testRemoveFromGrid() {
  //   grid.removeFromGrid(0);
  //   assertEquals(null, grid.getNode(0));
  // }

  @Test
  void testGetNearestNeighbor() {
    assertEquals(new Node(1.32,5.45,6), grid.getNearestNeighbor(new Node(1.32,5.45, 0)));
    assertEquals(new Node(100.32,543.45,7), grid.getNearestNeighbor(new Node(105.32,544.45, 8)));
    assertEquals(new Node(49.99,50.99,11), grid.getNearestNeighbor(new Node(50, 50.99,9)));
    assertEquals(null, gird2.getNearestNeighbor(new Node(1, 1, 0)));
  }

  @Test
  void testFurthestPoint() {
    Cell cell = new Cell(2, 5);
    assertEquals(new Node(3, 6, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(1, 3);
    assertEquals(new Node(1, 3, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(2, 4);
    assertEquals(new Node(3, 4, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(3, 5);
    assertEquals(new Node(4, 6, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(3, 6);
    assertEquals(new Node(4, 7, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(1, 5);
    assertEquals(new Node(1, 6, 0), grid.furthestPoint(new Node(2.45, 4.23, 0), cell));
    cell = new Cell(0, 2);
    assertEquals(new Node(0, 6, 0), gird2.furthestPoint(new Node(2.45, 4.23, 0), cell));
  }

  @Test
  void testNearestNeighborHeuristic() throws Exception {
    DrawTour.draw(grid.nearestNeighborHeuristic(), "tour.svg");
  }
}
