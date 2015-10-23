package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_USE_CONSUMED;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_ITEM_CANT_BE_USED;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_ITEM_NOT_FOUND;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.entity.Player;
import org.github.got.item.Consumable;
import org.github.got.item.ItemStack;

/**
 * Use consumable item from inventory.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "use (?<name>([a-z- ]+|[0-9]+))" }, scope = { Scope.INGAME })
public class UseCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("use (?<name>([a-z- ]+|[0-9]+))");

  @Override
  public void issue(final Context context) {
    final String name = extractName(context.getCommand(), PATTERN);
    final Player player = context.getPlayer();
    final ItemStack itemStack = player.itemLookup(name);
    if (itemStack != null) {
      if (itemStack.getItem() instanceof Consumable) {
        final Consumable consumable = (Consumable) itemStack.getItem();
        consumable.getEffect().apply(player, consumable);
        player.removeItemFromInventory(consumable, 1);
        game(context, GAME_ACTIONS_USE_CONSUMED, player.describeStats(), consumable);
      } else {
        system(context, GAME_SYSTEM_ERROR_ITEM_CANT_BE_USED, capitalize(name));
        context.setResult(false);
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_ITEM_NOT_FOUND, capitalize(name));
      context.setResult(false);
    }

  }

}
