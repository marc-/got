package org.github.got.entity;

import static org.github.got.Resources.GAME_ACTIONS_ERROR_NO_QUESTS;
import static org.github.got.Resources.GAME_DESCRIBE_NPC_QUEST;
import static org.github.got.TextUtil.NL;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.Entity;
import org.github.got.Quest;
import org.github.got.Resources;
import org.github.got.commands.Engine;

/**
 * Quest giver.
 *
 * @author Maksim Chizhov
 */
public class NonPlayerCharacter extends Entity {

  protected final List<Quest> quests;

  public NonPlayerCharacter(final String name, final String race, final String gender, final Clazz clazz,
      final Attitude affection, final String description, final String welcomeMessage, final String farewellMessage,
      final List<Quest> quests) {
    super(name, race, gender, clazz, affection, description, welcomeMessage, farewellMessage);
    this.quests = quests;
  }

  public String describeQuests(final Player player) {
    final List<Quest> aligbleQuests = quests.stream()
        .filter(q -> playerAligbleForQuest(player, q))
        .collect(Collectors.toList());
    final String d = IntStream.range(0, aligbleQuests.size()).mapToObj(i -> {
      final Quest quest = aligbleQuests.get(i);
      return Resources.getString(GAME_DESCRIBE_NPC_QUEST, i + 1, quest.getName());
    }).collect(Collectors.joining(NL));
    if (!d.isEmpty()) {
      return d;
    }
    return Resources.getString(GAME_ACTIONS_ERROR_NO_QUESTS);
  }

  public static boolean playerAligbleForQuest(final Player player, final Quest quest) {
    return quest.getLevel() <= player.getLevel() && !player.getQuests().contains(quest)
        && !player.isQuestCompleted(quest.getNameLookup());
  }

  public Quest questLookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, quests);
  }

}
