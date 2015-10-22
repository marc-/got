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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.item.ItemStack;
import org.github.got.item.Requirement;

public class Quest implements Searchable {
  private static final Logger LOGGER = Logger.getLogger(Quest.class.getPackage().getName());
  private final String name;
  private final String nameLookup;
  private final int level;
  private final String questGiver;

  private final String questReciever;
  private final String description;
  private final String completeMessage;
  private final List<Requirement> requirements;
  private final List<ItemStack> rewards;
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

  public boolean meetRequirement() {
    return getRequirements().isEmpty() || getRequirements().stream().allMatch(r -> r.getProgress() >= r.getAmount());
  }

  public String describe() {
    return Resources.getString(GAME_DESCRIBE_QUEST, name, description, describeRequrements(), describeRewards());
  }

  public String describeRequrements() {
    return getRequirements().stream().map(r -> Resources.getString(GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM, r.describe()))
        .collect(Collectors.joining(NL));
  }

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

  public boolean hasRewards() {
    return !rewards.isEmpty();
  }

  public String describeRewards() {
    return IntStream.range(0, rewards.size()).mapToObj(i -> {
      final ItemStack reward = rewards.get(i);
      return Resources.getString(GAME_DESCRIBE_QUEST_REWARDS_ITEM, i + 1, reward.describe());
    }).collect(Collectors.joining(TextUtil.OR))
        + (experience > 0 ? Resources.getString(GAME_DESCRIBE_QUEST_REWARDS_EXPERIENCE, experience) : EMPTY);
  }

  public ItemStack rewardLookup(final String nameOrIndex) {
    final String name = nameOrIndex;
    int index = -1;
    try {
      index = Integer.parseInt(nameOrIndex) - 1;
    } catch (final NumberFormatException e) {
      LOGGER.log(Level.FINE, e.getMessage(), e);
    }
    if (index >= 0 && index < rewards.size()) {
      return rewards.get(index);
    }
    return rewards.stream().filter(i -> i.getItem().getNameLookup().equals(name)).findFirst().orElse(null);
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