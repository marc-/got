package org.github.got;

import static org.github.got.Resources.GAME_DESCRIBE_ITEM;

/**
 * Describes in-game items.
 *
 * @author Maksim Chizhov
 *
 */
public class Item implements Searchable {

  private final String name;
  private final String nameLookup;
  /**
   * Base item price when sell and buy from vendor.
   */
  private final int price;

  public Item(final String name, final int price) {
    this.name = name;
    nameLookup = name.toLowerCase();
    this.price = price;
    World.registerItem(this);
  }

  /**
   * Formatted item description.
   *
   * @return
   */
  public String describe() {
    return Resources.getString(GAME_DESCRIBE_ITEM, name);
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
