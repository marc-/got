package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;

@CommandA(value = { "inventory", "i" })
public class InventoryCommand extends AbstractCommand {
  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeInventory());
  }
}
