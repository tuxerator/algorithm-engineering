package ae.sanowskiriesterer.nnh;

public class Cell {
  private int x;
  private int y;

  public Cell(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Cell)) {
      return super.equals(obj);
    }

    Cell oCell = (Cell) obj;
    return oCell.getX() == x && oCell.getY() == y;
  }

  @Override
  public int hashCode() {
    return x >= y ? x * x + x + y : x * y * y;
  }

  @Override
  public String toString() {
      return "(x: " + x + ", y: " + y + ")";
  }
}
