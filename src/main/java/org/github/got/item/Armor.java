package org.github.got.item;

import org.github.got.Entity;
import org.github.got.Item;
import org.github.got.entity.Clazz;

/**
 * Not used.
 *
 * @author Maksim Chizhov
 */
public class Armor extends Item {

  enum Type {
    CLOTH,
    LEATHER,
    MAIL,
    PLATE;
  }

  enum Slot {
    HEAD,
    CHEST,
    WAIST,
    WRIST,
    LEGS,
    HANDS,
    SHOLDERS;
  }

  private final Slot slot;
  private final Type type;
  private final Clazz clazz;

  public Armor(final String name, final int price, final Clazz clazz, final Slot slot, final Type type) {
    super(name, price);
    this.clazz = clazz;
    this.slot = slot;
    this.type = type;
  }

  public boolean canWear(final Entity player) {
    return clazz.equals(player.getClazz());
  }

  public Slot getSlot() {
    return slot;
  }

  public Type getType() {
    return type;
  }

  public Clazz getClazz() {
    return clazz;
  }

}
