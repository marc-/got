package org.github.got;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Various text processing accelerators.
 *
 * @author Maksim Chizhov
 */
public abstract class TextUtil {
  public static final String EMPTY = "";
  public static final String SPACE = " ";
  public static final String NL = "\n";
  public static final char NO = 'n';
  public static final char YES = 'y';
  public static final String AT = "@";
  public static final String PROMPT = ":$ ";
  public static final String SLASH = "/";
  public static final String X = "x";
  private static int SCREEN_WIDTH = 120;
  private static String[] MOB_HMANOID_NAMES = { "Bandit", "Goblin" };
  private static String[] MOB_ANIMAL_NAMES = { "Rabbit", "Boar", "Wild Boar", "DRAGON" };
  private static String[] GENDER = { "Male", "Female" };
  public static final String DOLLAR_SPACE = "$ ";
  public static final String COLON = ":";
  public static final String NAME = "name";
  public static final String AMOUNT = "amount";
  static final String COLON_NL = ":\n";
  static final String DOT = ".";
  static final String OR = "\n\tor\n";

  private TextUtil() {
  }

  public static String toEmpty(final Object obj) {
    if (obj == null) {
      return Resources.BKSPACE;
    }
    final String string = obj.toString();
    if (string.isEmpty()) {
      return Resources.BKSPACE;
    }
    return string;
  }

  public static boolean isEmpty(final String str) {
    return toEmpty(str).equals(Resources.BKSPACE);
  }

  public static String capitalize(final String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
  }

  public static String mobHumanoidName() {
    return RandomUtil.randomObject(MOB_HMANOID_NAMES);
  }

  public static String mobAnimalName() {
    return RandomUtil.randomObject(MOB_ANIMAL_NAMES);
  }

  public static String gender() {
    return RandomUtil.randomObject(GENDER);
  }

  public static String center(String str) {
    if (str == null) {
      return str;
    }
    final int strLen = str.length();
    final int pads = SCREEN_WIDTH - strLen;
    if (pads <= 0) {
      return str;
    }
    str = leftPad(str, pads / 2 + strLen);
    str = rightPad(str, pads / 2);
    return str;
  }

  public static String rightPad(final String str, final int count) {
    return String.format("%1s%2$" + count + "s", str, " ");
  }

  public static String leftPad(final String str, final int count) {
    return String.format("%1$" + count + "s", str);
  }

  public static String repeat(final String str, final int count) {
    return IntStream.range(0, count + 1).mapToObj(i -> EMPTY).collect(Collectors.joining(str));
  }

  public static String stripFirstPart(final String command) {
    final int lastIndexOf = command.indexOf(' ');
    if (lastIndexOf >= 0) {
      return command.substring(lastIndexOf + 1);
    }
    return EMPTY;
  }

  public static String collapseSpaces(final String string) {
    if (string == null) {
      return null;
    }
    return string.replaceAll("\\s+", " ");
  }

}
