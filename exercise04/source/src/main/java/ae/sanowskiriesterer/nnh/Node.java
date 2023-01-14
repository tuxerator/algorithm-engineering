package ae.sanowskiriesterer.nnh;

public class Node {
  private double x;
  private double y;
  private final int ID;

  public static double euclidianDistance(Node a, Node b) {
    return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
  }

  public Node(double x, double y, int ID) {
    this.x = x;
    this.y = y;
    this.ID = ID;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public int getID() {
    return ID;
  }

  public ae.sanowskiriesterer.Node toBadNode() {
    return new ae.sanowskiriesterer.Node((float) x, (float) y);
  }

  @Override
  public String toString() {
    return "(ID: " + ID + ", x: " + x + ", y: " + y + ")";
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Node)) {
      return super.equals(obj);
    }

    Node nObj = (Node) obj;
    return nObj.getX() == x && nObj.getY() == y && nObj.getID() == ID;
  }
}
