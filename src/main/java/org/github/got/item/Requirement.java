package org.github.got.item;

import org.github.got.Resources;

/**
 * Quest requirement.
 *
 * @author Maksim Chizhov
 *
 */
public class Requirement {
  /**
   * Type of requirement. Currently only loot does work.
   *
   * @author Maksim Chizhov
   */
  public enum Type {
    /** Loot specified amount of specific items to complete quest. */
    LOOT,
    /** Kill specified mobs. */
    KILL;

    public final String value = super.toString().toLowerCase();

    @Override
    public String toString() {
      return value;
    }
  }

  private final Type type;
  private final String name;
  private final String nameLookup;
  private final int amount;
  /** Progress >= amount -> quest completed. */
  private int progress;
  /** Reference to item in players inventory. */
  private ItemStack tracker;

  public Requirement(final Type type, final String name, final int amount) {
    this.type = type;
    this.name = name;
    nameLookup = name.toLowerCase();
    this.amount = amount;
  }

  public Type getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String getNameLookup() {
    return nameLookup;
  }

  public int getAmount() {
    return amount;
  }

  public int getProgress() {
    if (tracker != null) {
      return tracker.getAmount();
    }
    return progress;
  }

  public void setProgress(final int progress) {
    this.progress = progress;
  }

  public String describe() {
    return Resources.getString("game.describe.requirement", type, amount, name);
  }

  public void setTracker(final ItemStack tracker) {
    this.tracker = tracker;
  }

}
