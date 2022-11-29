package ae.sanowskiriesterer;

/**
 * PartArray
 */
public class PartIntArray {
  private int[] arr;
  private int i_start;
  public final int length;
  
  public PartIntArray(int[] arr, int i_start, int length) {
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

  public int get(int i) {
    if (i_start + i >= length)
      throw new IndexOutOfBoundsException();
    return arr[i_start + i];
  }
  
  public void set(int elem, int i) {
    if (i_start + i >= length)
      throw new IndexOutOfBoundsException();
    arr[i_start + i] = elem;
  }
}
