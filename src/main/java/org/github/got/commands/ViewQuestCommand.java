package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_ERROR_QUEST_NOT_FOUND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_QUEST_GIVER;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Quest;
import org.github.got.entity.NonPlayerCharacter;

/**
 * Show quest details. Only usable while talking to NPC. Will not work with
 * quest in journal.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "(view quest)|(vq) (?<name>[a-z 0-9-]+)" }, scope = Scope.TALK)
public class ViewQuestCommand extends AbstractCommand {

  static final Pattern PATTERN = Pattern.compile("(view quest)|(vq) (?<name>[a-z 0-9-]+)");

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof NonPlayerCharacter) {
      final NonPlayerCharacter npc = (NonPlayerCharacter) context.getTarget();
      final String name = extractName(context.getCommand(), PATTERN);
      final Quest quest = npc.questLookup(name);
      if (quest == null) {
        system(context, GAME_ACTIONS_ERROR_QUEST_NOT_FOUND, capitalize(name));
      } else {
        gameRaw(context, quest.describe());
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_QUEST_GIVER, context.getTarget().getName());
    }
  }

}
