package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_BUY_PURCHASED;
import static org.github.got.Resources.GAME_ACTIONS_ERROR_NOT_ENOUGH_MONEY;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_ITEM_NOT_FOUND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_VENDOR;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Item;
import org.github.got.entity.Player;
import org.github.got.entity.Vendor;

/**
 * Buy item from vendor. Name or index can be specified.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "buy (?<name>([a-z- ]+|[0-9]+))( (?<amount>[0-9]+))?$" }, starts = Scope.TALK)
public class BuyCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("buy (?<name>([a-z- ]+|[0-9]+))( (?<amount>[0-9]+))?$");

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof Vendor) {
      final Vendor vendor = (Vendor) context.getTarget();
      final String name = extractName(context.getCommand(), PATTERN);
      final int amount = extractAmount(context.getCommand(), PATTERN, 1);
      final Item item = vendor.itemLookup(name);
      if (item == null) {
        system(context, GAME_SYSTEM_ERROR_ITEM_NOT_FOUND, capitalize(name));
      } else {
        final int price = vendor.getSellPrice(item) * amount;
        final Player player = context.getPlayer();
        if (player.getCoins() < price) {
          game(context, GAME_ACTIONS_ERROR_NOT_ENOUGH_MONEY, amount, item.describe(), price, player.getCoins());
        } else {
          player.addToInventory(item, amount);
          player.pay(price);
          game(context, GAME_ACTIONS_BUY_PURCHASED, amount, item.describe(), price, player.getCoins());
        }
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_VENDOR, context.getTarget().getName());
    }
  }

}
