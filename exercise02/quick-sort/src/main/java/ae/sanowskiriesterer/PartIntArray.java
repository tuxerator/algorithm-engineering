package ae.sanowskiriesterer;

/**
 * PartArray
 */
public class PartIntArray {
  private int[] arr;
  private int i_start;
  public final int length;
  
  public PartIntArray(int[] arr, int i_start, int length) {
    if (i_start + length > arr.length) {
      throw new IndexOutOfBoundsException();
    }
    this.arr = arr;
    this.i_start = i_start;
    this.length = length;
  }

  public int getStart() {
    return i_start;
  }
  
  public int[] getArray() {
    return arr;
  }

  public void setArray(int[] arr) {
    this.arr = arr;
  }

  public int get(int i) {
    if (i >= length)
      throw new IndexOutOfBoundsException();
    return arr[i_start + i];
  }
  
  public void set(int elem, int i) {
    if (i >= length)
      throw new IndexOutOfBoundsException();
    arr[i_start + i] = elem;
  }

  public String toString() {
    String string = "";
    for (int i = 0; i < length; i++) {
      string = string + " " + arr[i_start + i];
    }

    return string;
  }
}
