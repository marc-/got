package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_ERROR_NOT_ENOUGH_ITEMS;
import static org.github.got.Resources.GAME_ACTIONS_SELL_SOLD;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_ITEM_NOT_FOUND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_VENDOR;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.entity.Player;
import org.github.got.entity.Vendor;
import org.github.got.item.ItemStack;

@CommandA(value = { "sell (?<name>([a-z- ]+|[0-9]+))( (?<amount>[0-9]+))?$" }, starts = Scope.TALK)
public class SellCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("sell (?<name>([a-z- ]+|[0-9]+))( (?<amount>[0-9]+))?$");

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof Vendor) {
      final Vendor vendor = (Vendor) context.getTarget();
      final String name = extractName(context.getCommand(), PATTERN);
      final int amount = extractAmount(context.getCommand(), PATTERN, 1);
      final Player player = context.getPlayer();
      final ItemStack itemStack = player.itemLookup(name);
      if (itemStack == null) {
        system(context, GAME_SYSTEM_ERROR_ITEM_NOT_FOUND, capitalize(name));
      } else {
        final int price = vendor.getBuyPrice(itemStack.getItem()) * amount;
        if (itemStack.getAmount() < amount) {
          game(context, GAME_ACTIONS_ERROR_NOT_ENOUGH_ITEMS, itemStack.describe());
        } else {
          player.removeItemFromInventory(itemStack.getItem(), amount);
          player.addCoins(amount);
          game(context, GAME_ACTIONS_SELL_SOLD, itemStack.describe(), price, player.getCoins());
        }
      }

    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_VENDOR, context.getTarget().getName());
    }
  }
}
