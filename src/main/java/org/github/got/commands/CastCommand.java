package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_UNKNOWN_ABILITY;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.Ability;
import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.entity.Mob;
import org.github.got.entity.Player;

/**
 * Cast specified spell or ability on current target. Command is only usable in
 * combat mod. Keep in mind that spell use mana. Once creature runs out of mana,
 * it won't do anything until battle ends. If player out of mana, mana potions
 * can be used. If no mana potions RUN FOR YOUR LIFE!!! Spell/ability name or
 * index can be used.
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "cast (?<name>([a-z- ]+|[0-9]+))" }, scope = Scope.COMBAT, starts = Scope.LOCATION)
public class CastCommand extends CombatCommands {

  private static final Pattern PATTERN = Pattern.compile("cast (?<name>([a-z- ]+|[0-9]+))");

  @Override
  public void issue(final Context context) {
    final String command = context.getCommand();
    final String name = extractName(command, PATTERN);
    final Entity target = context.getTarget();
    final Player player = context.getPlayer();
    final Ability ability = player.getAbility(name);
    if (ability != null) {
      attack(context, player, target, ability, false);
      if (!target.isDead()) {
        attack(context, target, player, ((Mob) target).getAbility(), false);
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_UNKNOWN_ABILITY, capitalize(name));
    }
    context.setResult(context.getTarget() == null);
  }

}
