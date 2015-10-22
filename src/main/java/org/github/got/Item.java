package org.github.got;

public class Item implements Searchable {

  private final String name;
  private final String nameLookup;
  private final int price;

  public Item(final String name, final int price) {
    this.name = name;
    nameLookup = name.toLowerCase();
    this.price = price;
    World.registerItem(this);
  }

  public String describe() {
    return Resources.getString("game.describe.item", name);
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  @Override
  public String getNameLookup() {
    return nameLookup;
  }
}
