// package ae.sanowskiriesterer.nnh;

// import java.util.Arrays;
// import java.util.LinkedList;
// import java.util.Random;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertEquals;


// public class GridTest {

//   private Grid grid = new Grid();

//   @BeforeEach
//   void init() {
//     Random rand = new Random();
//     rand.doubles(0, 50);
//     LinkedList<Node> nodes = new LinkedList<>(Arrays.asList(
//     new Node(1.32,5.45, 0),
//     new Node(1.92,5.45, 1),
//     new Node(1.32,9.45, 2),
//     new Node(0.82,5.25, 3),
//     new Node(7.32,3.48, 4),
//     new Node(7.18,5.00, 5),
//     new Node(1.32,5.45, 6),
//     new Node(100.32,543.45, 7),
//     new Node(105.32,544.45, 8)
//     ));

//     grid.putAll(nodes);
//   }

//   @Test
//   void testGet() {

//     LinkedList<Node> result = grid.getCell(1, 5);
//     assertEquals(new LinkedList<Node>(Arrays.asList(
//       new Node(1.32,5.45, 0),
//       new Node(1.92,5.45, 1),
//       new Node(1.32,5.45, 6)
//     )), result);

//     result = grid.getCell(100, 543);
//     assertEquals(new LinkedList<Node>(Arrays.asList(
//       new Node(100.32,543.45, 7)
//     )), result);

//     result = grid.getCell(102, 543);
//     assertEquals(null, result);
//   }
// }
