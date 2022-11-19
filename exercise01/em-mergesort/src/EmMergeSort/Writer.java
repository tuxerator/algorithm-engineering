package EmMergeSort;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Writer {
  FileChannel fc;
  Path path;

  public Writer(String file) throws Exception {
    path = Paths.get(file);
    fc = FileChannel.open(path, CREATE, TRUNCATE_EXISTING, WRITE);
  }

  public void write(int[] array) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(array.length * 4);
    IntBuffer intBuffer = buffer.asIntBuffer();
    intBuffer.put(array);
    int nwrite = fc.write(buffer);
  }

  public void close() throws Exception {
    fc.close();
  }
}
