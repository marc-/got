package org.github.got;

import static org.github.got.Resources.GAME_DESCRIBE_QUEST;
import static org.github.got.Resources.GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM;
import static org.github.got.Resources.GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM_PROGRESS;
import static org.github.got.Resources.GAME_DESCRIBE_QUEST_REWARDS_EXPERIENCE;
import static org.github.got.Resources.GAME_DESCRIBE_QUEST_REWARDS_ITEM;
import static org.github.got.Resources.GAME_QUEST_REQUIREMENT_MET;
import static org.github.got.Resources.GAME_QUEST_REQUIREMENT_NOT_MET;
import static org.github.got.TextUtil.COLON_NL;
import static org.github.got.TextUtil.DOT;
import static org.github.got.TextUtil.EMPTY;
import static org.github.got.TextUtil.NL;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.commands.Engine;
import org.github.got.item.ItemStack;
import org.github.got.item.Requirement;

/**
 * Describes game quests.
 *
 * @author Maksim Chizhov
 *
 */
public class Quest implements Searchable {
  private static final Logger LOGGER = Logger.getLogger(Quest.class.getPackage().getName());
  private final String name;
  private final String nameLookup;
  /**
   * Quest level. Only players with same or higher level can start this quest.
   */
  private final int level;
  /**
   * NPC who gave quest.
   */
  private final String questGiver;
  /**
   * NPC who will accept completed quest.
   */
  private final String questReciever;
  private final String description;
  /**
   * Message displayed upon completion.
   */
  private final String completeMessage;
  /**
   * Quest requirement.
   */
  private final List<Requirement> requirements;
  /**
   * Quest rewards.
   */
  private final List<ItemStack> rewards;
  /**
   * Experience awards by complication. There si no restriction on player level.
   * All level gets same amount of experience.
   */
  private final int experience;

  public Quest(final String name, final int level, final String questGiver, final String questReciever,
      final String description, final String completeMessage, final List<Requirement> requirements,
      final List<ItemStack> rewards, final int experience) {
    this.name = name;
    nameLookup = name.toLowerCase();
    this.level = level;
    this.questGiver = questGiver;
    this.questReciever = questReciever;
    this.description = description;
    this.completeMessage = completeMessage;
    this.requirements = requirements != null ? requirements : Collections.emptyList();
    this.rewards = rewards != null ? rewards : Collections.emptyList();
    this.experience = experience;
    World.registerQuest(this);
  }

  /**
   * If requirements are met and quest can be surrounded to questReciever.
   *
   * @return
   */
  public boolean meetRequirement() {
    return getRequirements().isEmpty() || getRequirements().stream().allMatch(r -> r.getProgress() >= r.getAmount());
  }

  /**
   * Full formatted quest description.
   *
   * @return
   */
  public String describe() {
    return Resources.getString(GAME_DESCRIBE_QUEST, name, description, describeRequrements(), describeRewards());
  }

  /**
   * Formatted requirements description.
   *
   * @return
   */
  public String describeRequrements() {
    return getRequirements().stream().map(r -> Resources.getString(GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM, r.describe()))
        .collect(Collectors.joining(NL));
  }

  /**
   * Formatted quest completion status.
   *
   * @return
   */
  public String status() {
    final String reqs = getRequirements().stream().map(r -> Resources
        .getString(GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM_PROGRESS, r.getName(), r.getProgress(), r.getAmount()))
        .collect(Collectors.joining(NL));
    if (meetRequirement()) {
      return Resources.getString(GAME_QUEST_REQUIREMENT_MET, reqs.isEmpty() ? DOT : COLON_NL + reqs);
    } else {
      return Resources.getString(GAME_QUEST_REQUIREMENT_NOT_MET, reqs);
    }
  }

  public int getLevel() {
    return level;
  }

  public String getQuestGiver() {
    return questGiver;
  }

  @Override
  public String getNameLookup() {
    return nameLookup;
  }

  public String getName() {
    return name;
  }

  public int getExperience() {
    return experience;
  }

  /**
   * If quest has rewards or only experience.
   *
   * @return
   */
  public boolean hasRewards() {
    return !rewards.isEmpty();
  }

  /**
   * Formatted rewards description.
   *
   * @return
   */
  public String describeRewards() {
    return IntStream.range(0, rewards.size()).mapToObj(i -> {
      final ItemStack reward = rewards.get(i);
      return Resources.getString(GAME_DESCRIBE_QUEST_REWARDS_ITEM, i + 1, reward.describe());
    }).collect(Collectors.joining(TextUtil.OR))
        + (experience > 0 ? Resources.getString(GAME_DESCRIBE_QUEST_REWARDS_EXPERIENCE, experience) : EMPTY);
  }

  /**
   * Look for specified reward.
   *
   * @param nameOrIndex
   *          name or index (starting from 1) in list
   * @return
   */
  public ItemStack rewardLookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, rewards);
  }

  public String getQuestReciever() {
    return questReciever;
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public String getCompleteMessage() {
    return completeMessage;
  }

}