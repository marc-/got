package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_VENDOR;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.entity.Vendor;

/**
 * Lists vendor's items for sell. Only usable when talk to vendor.
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "list items", "ls i" }, scope = Scope.TALK)
public class ListItemsCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof Vendor) {
      final Vendor vendor = (Vendor) context.getTarget();
      gameRaw(context, vendor.describeItems());
    } else {
      game(context, GAME_SYSTEM_ERROR_NOT_VENDOR, context.getTarget().getName());
    }
  }

}
