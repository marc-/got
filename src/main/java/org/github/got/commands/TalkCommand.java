package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_INVALID_COMMAND_FORMAT;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NPC_DONT_WANT_TO_TALK;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NPC_NOT_FOUND;
import static org.github.got.Resources.GAME_TIP_GAME_TALK_NPC;
import static org.github.got.Resources.GAME_TIP_GAME_TALK_NPC_VENDOR;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.Entity.Attitude;
import org.github.got.entity.NonPlayerCharacter;
import org.github.got.entity.Vendor;

/**
 * Start conversation with NPC.
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "(talk|tt) (to )?(?<name>[a-z-]+)$" }, scope = Scope.TOWN, starts = Scope.TALK)
public class TalkCommand extends AbstractCommand {
  private static Pattern PATTERN = Pattern.compile("(talk|tt) (to )?(?<name>[a-z- ]+)$");

  @Override
  public void issue(final Context context) {
    final String name = extractName(context.getCommand(), PATTERN);
    if (!name.isEmpty()) {
      final Entity target = findTarget(context, name);
      if (target == null) {
        system(context, GAME_SYSTEM_ERROR_NPC_NOT_FOUND, capitalize(name));
        context.setResult(false);
      } else {
        message(context, target.getAttitude().messageType(), target.getWelcomeMessage());
        if (!Attitude.HOSTILE.equals(target.getAttitude()) && target instanceof NonPlayerCharacter) {
          if (target instanceof Vendor) {
            system(context, GAME_TIP_GAME_TALK_NPC_VENDOR);
          }
          system(context, GAME_TIP_GAME_TALK_NPC);
          context.setTarget(target);
        } else {
          system(context, GAME_SYSTEM_ERROR_NPC_DONT_WANT_TO_TALK, target.describe());
          context.setResult(false);
        }
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_INVALID_COMMAND_FORMAT);
      context.setResult(false);
    }
  }

}
