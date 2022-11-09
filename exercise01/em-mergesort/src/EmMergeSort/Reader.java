package EmMergeSort;

import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.*;
import java.nio.file.Files;
import static java.nio.file.StandardOpenOption.*;
import java.nio.channels.FileChannel;

public class Reader {
  int bufSize;
  Path path;
  FileChannel fc;

  public Reader(String file, int bufSize) throws Exception {
    this.bufSize = bufSize;
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

  public int[] readIntBuffer(int position) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(bufSize);
    IntBuffer intBuffer = buffer.order(ByteOrder.BIG_ENDIAN).asIntBuffer();
    fc.position(position);
    int nread = 0;

    do {
      fc.read(buffer);
    } while (nread != -1 && buffer.hasRemaining());

    int[] array = new int[intBuffer.remaining()];
    intBuffer.get(array);

    return array;
  }

  public long fileSize() throws Exception {
    return fc.size();
  }

  public int readInts(int[] dst) throws Exception {
    int nread = 0;
    ByteBuffer buffer = ByteBuffer.allocate(dst.length * 4);
    IntBuffer intBuffer = buffer.order(ByteOrder.BIG_ENDIAN).asIntBuffer();

    do {
      nread = fc.read(buffer);
    } while (nread != -1 && buffer.hasRemaining());

    intBuffer.get(dst);

    return nread;
  }
}
