package org.github.got;

import java.util.Random;

/**
 * Simplifies random numbers generation.
 *
 * @author Maksim Chizhov
 *
 */
public abstract class RandomUtil {

  private static Random RANDOM = new Random();

  private RandomUtil() {
  }

  public static int nextInt(final int bound) {
    return RANDOM.nextInt(bound);
  }

  public static int nextInt(final int minBound, final int maxBound) {
    return RANDOM.nextInt(maxBound - minBound) + minBound;
  }

  public static boolean nextBoolean() {
    return RANDOM.nextBoolean();
  }

  /**
   * Gets random item from array (like {@link Enum#values}).
   *
   * @param objects
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T randomObject(final T... objects) {
    return objects[nextInt(objects.length)];
  }

}
