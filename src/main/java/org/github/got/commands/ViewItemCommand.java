package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_ITEM_NOT_FOUND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_VENDOR;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Item;
import org.github.got.entity.Vendor;

/**
 * View item description. Only usable when talk to vendor. Will not work on
 * items in inventory.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "(view item)|(vi) (?<name>[a-z 0-9-]+)" }, scope = Scope.TALK)
public class ViewItemCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("(view item)|(vi) (?<name>[a-z 0-9-]+)");

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof Vendor) {
      final Vendor vendor = (Vendor) context.getTarget();
      final String name = extractName(context.getCommand(), PATTERN);
      final Item item = vendor.itemLookup(name);
      if (item == null) {
        system(context, GAME_SYSTEM_ERROR_ITEM_NOT_FOUND, capitalize(name));
      } else {
        game(context, item.describe());
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_VENDOR, context.getTarget().getName());
    }
  }

}
