package EmMergeSort;

import java.io.IOException;
import java.util.Arrays;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.*;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.*;
import java.nio.channels.FileChannel;

public class Reader {
  Path path;
  FileChannel fc;

  public Reader(String file) throws Exception {
    path = Paths.get(file);
    fc = FileChannel.open(path, READ);
  }

  public int[] readInt() {
    int[] array = {};
    try {
      byte[] bytes = Files.readAllBytes(path);
      IntBuffer intBuf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();

      array = new int[intBuf.remaining()];
      intBuf.get(array);
    } catch (IOException x) {
      System.err.println(x);
    }
    return array;
  }

  public int readIntBuffer(int[] dest, long position) throws Exception {
    System.out.println("readIntBuffer:");
    ByteBuffer buffer = ByteBuffer.allocate(dest.length * 4);
    IntBuffer intBuffer = buffer.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
    fc.position(position);
    int nread = 0;

    nread = fc.read(buffer);

    System.out.println("nread: " + nread);

    intBuffer.get(dest);

    System.out.println("BufferLength: " + dest.length);

    return nread;
  }

  public long fileSize() throws Exception {
    return fc.size();
  }

  public int readIntBuffer(int[] dst) throws Exception {
    int nread = 0;
    ByteBuffer buffer = ByteBuffer.allocate(dst.length * 4);
    IntBuffer intBuffer = buffer.order(ByteOrder.BIG_ENDIAN).asIntBuffer();

    nread = fc.read(buffer);

    intBuffer.get(dst);

    return nread;
  }

  public int[] trimRead(int[] dest, long position) throws Exception {
    System.out.println("trimRead: ");
    int nread = 0;

      nread = readIntBuffer(dest, position);

      // Trim array to the number of elements read
      if (nread < dest.length * 4) {
        dest = Arrays.copyOf(dest, nread / 4);
      }

    System.out.println("Trimed array length: " + dest.length);

    return dest;
  }

  public void close() throws Exception {
    fc.close();
  }
}
