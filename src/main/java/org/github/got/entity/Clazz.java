package org.github.got.entity;

import static org.github.got.TextUtil.SLASH;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.RandomUtil;

public enum Clazz {

  WARRIOR(5, 5, 0, 0),
  MAGE(3, 0, 0, 8),
  HUNTER(4, 0, 6, 0),
  VENDOR(0, 0, 0, 0);

  public final int stamina;
  public final int strength;
  public final int agility;
  public final int intellect;

  private Clazz(final int stamina, final int strength, final int agility, final int intellect) {
    this.stamina = stamina;
    this.strength = strength;
    this.agility = agility;
    this.intellect = intellect;
  }

  public static Clazz fromString(final String str) {
    try {
      return Clazz.valueOf(str.toUpperCase());
    } catch (final IllegalArgumentException e) {
      // We can deal with that.
    }
    return null;
  }

  public static Clazz random() {
    return RandomUtil.randomObject(WARRIOR, HUNTER, MAGE);
  }

  @Override
  public String toString() {
    return super.toString().toLowerCase();
  }

  public static String available() {
    return IntStream.range(0, Clazz.values().length - 1).mapToObj(i -> Clazz.values()[i].toString())
        .collect(Collectors.joining(SLASH));
  }
}
