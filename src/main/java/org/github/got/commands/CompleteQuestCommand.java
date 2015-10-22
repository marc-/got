package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_COMPLETE_QUEST_CHOOSE_REWARD;
import static org.github.got.Resources.GAME_ACTIONS_COMPLETE_QUEST_COMPLETED;
import static org.github.got.Resources.GAME_ACTIONS_COMPLETE_QUEST_ERROR_WRONG_NPC;
import static org.github.got.Resources.GAME_ACTIONS_COMPLETE_QUEST_NOT_COMPLETED;
import static org.github.got.Resources.GAME_ACTIONS_ERROR_QUEST_NOT_FOUND;
import static org.github.got.Resources.GAME_EVENT_LEVELUP;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_QUEST_GIVER;
import static org.github.got.TextUtil.capitalize;

import java.util.regex.Pattern;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Entity;
import org.github.got.Quest;
import org.github.got.entity.NonPlayerCharacter;
import org.github.got.entity.Player;
import org.github.got.item.ItemStack;

@CommandA({ "complete|cq (?<name>([a-z- ]+)|([0-9]+))" })
public class CompleteQuestCommand extends AbstractCommand {

  private static final Pattern PATTERN = Pattern.compile("(complete|cq) (?<name>([a-z- ]+)|([0-9]+))");

  @Override
  public void issue(final Context context) {
    final Entity target = context.getTarget();
    if (target instanceof NonPlayerCharacter) {
      final String name = extractName(context.getCommand(), PATTERN);
      final Player player = context.getPlayer();
      final Quest quest = player.questLookup(name);

      if (quest == null) {
        system(context, GAME_ACTIONS_ERROR_QUEST_NOT_FOUND, capitalize(name));
      } else if (quest.meetRequirement()) {
        if (!target.getName().equals(quest.getQuestReciever())) {
          system(context, GAME_ACTIONS_COMPLETE_QUEST_ERROR_WRONG_NPC, target.getName(), quest.getName());
          context.setResult(false);
        } else {
          message(context, target.getAttitude().messageType(), quest.getCompleteMessage(), player.getName());
          ItemStack reward = null;
          if (quest.hasRewards()) {
            while (reward == null) {
              game(context, GAME_ACTIONS_COMPLETE_QUEST_CHOOSE_REWARD, quest.describeRewards());
              reward = quest.rewardLookup(context.getScanner().nextLine().trim().toLowerCase());
            }
          }
          final int level = player.getLevel();
          player.complete(quest, reward);
          game(context, GAME_ACTIONS_COMPLETE_QUEST_COMPLETED, quest.describe());
          if (player.getLevel() > level) {
            game(context, GAME_EVENT_LEVELUP, player.describeStats());
          }
        }
      } else {
        system(context, GAME_ACTIONS_COMPLETE_QUEST_NOT_COMPLETED, quest.getName());
        game(context, quest.describe());
      }

    } else {
      system(context, GAME_SYSTEM_ERROR_NOT_QUEST_GIVER, target.getName());
    }

  }

}
