package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_QUEST_GIVER;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.entity.NonPlayerCharacter;

/**
 * Lists NPC quest players is eligible start. Only usable when talk to NPC.
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "list quests", "ls q" }, scope = Scope.TALK)
public class ListQuestsCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof NonPlayerCharacter) {
      final NonPlayerCharacter npc = (NonPlayerCharacter) context.getTarget();
      gameRaw(context, npc.describeQuests(context.getPlayer()));
    } else {
      game(context, GAME_SYSTEM_ERROR_NOT_QUEST_GIVER, context.getTarget().getName());
    }
  }
}
