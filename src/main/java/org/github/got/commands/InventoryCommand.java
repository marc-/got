package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;

/**
 * Lists items in players bag.
 * 
 * @author Maksim Chizhov
 */
@CommandA(value = { "inventory", "i" })
public class InventoryCommand extends AbstractCommand {
  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeInventory());
  }
}
