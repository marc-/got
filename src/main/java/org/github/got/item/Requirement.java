package org.github.got.item;

import org.github.got.Resources;

public class Requirement {
  public enum Type {
    LOOT,
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
  private int progress;
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
