package ae.sanowskiriesterer.nnh;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {
  @Test
  void testEquals() {
    assertTrue(new Node(1.34, 4.56, 0).equals(new Node(1.34, 4.56, 0)));
  }
}
