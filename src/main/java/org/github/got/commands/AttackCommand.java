package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_ATTACK;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_CANT_ATTACK_FRIENDLY;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NPC_NOT_FOUND;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.Entity.Attitude;
import org.github.got.entity.Mob;

/**
 * Issues to attack neutral or hostile creature. Only create name can be used
 * currently. Engine doesn't distinguish anyhow mob with same name but different
 * class, level, etc. I.e. if there are two mobs with same name you can choose
 * who to fight. It will be first mob in the list. Command turns on combat mode.
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "(attack|atk) (?<name>[a-z- ]+)" }, scope = Scope.LOCATION, starts = Scope.COMBAT)
public class AttackCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("(attack|atk) (?<name>[a-z- ]+)");
  @Override
  public void issue(final Context context) {
    final String name = extractName(context.getCommand(), PATTERN);
    final Entity target = findTarget(context, name);
    if (target == null) {
      system(context, GAME_SYSTEM_ERROR_NPC_NOT_FOUND, capitalize(name));
      context.setResult(false);
    } else {
      if (target.getAttitude().ordinal() > Attitude.FRIENDLY.ordinal()) {
        hostile(context, GAME_ACTIONS_ATTACK, ((Mob) target).describeBreifly());
        target.setAttitude(Attitude.HOSTILE);
        context.setTarget(target);
      } else {
        system(context, GAME_SYSTEM_ERROR_CANT_ATTACK_FRIENDLY, capitalize(name));
      }
    }
  }

}
