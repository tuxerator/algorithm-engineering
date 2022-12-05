package ae.sanowskiriesterer;

import static org.junit.Assert.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * ParallelQuickSortTest
 */
public class ParallelQuickSortTest {

  @ParameterizedTest
  @MethodSource("argumentProvider")
  void parallelQuickSortTest(int[] arr, int p) {
    ParallelQuickSort.parallelQuickSort(arr, p);

    assertTrue(isSorted(arr));
    assertTrue(hasSameNumbers(arr));
  }

  static Stream<Arguments> argumentProvider() {
    // Stream.Builder<Arguments> streamBuilder = Stream.<Arguments>builder();

    Stream<Arguments> stream = Stream.generate(() -> Arguments.arguments(InputGenerator.generate(1000000), (int) (Math.random() * Runtime.getRuntime().availableProcessors()) + 1));

    // for (int i = 0; i < 100; i++) {
    //   streamBuilder.add(arguments(InputGenerator.generate(100000), (int) (Math.random() * Runtime.getRuntime().availableProcessors()))); 
    // }

    return stream.limit(100L);
  }

  static boolean isSorted(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      if (arr[i] > arr[i + 1]) {
        return false;
      }
    }

    return true;
  }

  static boolean hasSameNumbers(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      if (arr[i + 1] != arr[i] + 1)
        return false;
    }
    return true;
  }
} 
