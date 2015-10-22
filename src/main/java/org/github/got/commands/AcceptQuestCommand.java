package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_ERROR_QUEST_NOT_FOUND;
import static org.github.got.Resources.GAME_ACTIONS_QUEST_ACCEPTED;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_CANT_START;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_QUEST_GIVER;
import static org.github.got.Resources.GAME_TIP_GAME_LIST_QUESTS;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Quest;
import org.github.got.entity.NonPlayerCharacter;
import org.github.got.entity.Player;

@CommandA(value = { "(accept|aq) (?<name>([a-z- ]+|[0-9]+))" }, scope = Scope.TALK)
public class AcceptQuestCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("(accept|aq) (?<name>([a-z- ]+|[0-9]+))");

  @Override
  public void issue(final Context context) {
    if (context.getTarget() instanceof NonPlayerCharacter) {
      final NonPlayerCharacter target = (NonPlayerCharacter) context.getTarget();
      final String name = extractName(context.getCommand(), PATTERN);
      final Quest quest = target.questLookup(name);
      final Player player = context.getPlayer();

      if (quest == null) {
        system(context, GAME_ACTIONS_ERROR_QUEST_NOT_FOUND, capitalize(name));
      } else if (NonPlayerCharacter.playerAligbleForQuest(player, quest)) {
        player.accept(quest);
        game(context, GAME_ACTIONS_QUEST_ACCEPTED);
        gameRaw(context, quest.describe());
        system(context, GAME_TIP_GAME_LIST_QUESTS);
      } else {
        system(context, GAME_SYSTEM_ERROR_CANT_START);
      }
    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_QUEST_GIVER, context.getTarget().getName());
    }
  }
}
