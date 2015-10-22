package org.github.got.item;

import static org.github.got.Resources.GAME_DESCRIBE_ITEM_STACK;

import org.github.got.Item;
import org.github.got.Resources;
import org.github.got.Searchable;

public class ItemStack implements Searchable {

  private final Item item;
  private int amount;

  public ItemStack(final Item item, final int amount) {
    this.item = item;
    this.amount = amount;

  }

  public Item getItem() {
    return item;
  }

  public int getAmount() {
    return amount;
  }

  public String describe() {
    return Resources.getString(GAME_DESCRIBE_ITEM_STACK, amount, item.describe());
  }

  public void setAmount(final int amount) {
    this.amount = amount;
  }

  public void increaseAmount(final int amount) {
    this.amount += amount;
  }

  public void descreaseAmount(final int amount) {
    this.amount -= amount;
  }

  @Override
  public String getNameLookup() {
    return getItem().getNameLookup();
  }


}
