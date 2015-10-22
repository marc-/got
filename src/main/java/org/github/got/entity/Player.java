package org.github.got.entity;

import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_INVENTORY;
import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_INVENTORY_EMPTY;
import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_INVENTORY_ITEM;
import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_JOURNAL_EMPTY;
import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_JOURNAL_ITEM;
import static org.github.got.Resources.GAME_DESCRIBE_PLAYER_STATS;
import static org.github.got.TextUtil.EMPTY;
import static org.github.got.TextUtil.NL;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.Ability;
import org.github.got.Entity;
import org.github.got.Formulas;
import org.github.got.Game;
import org.github.got.Item;
import org.github.got.Quest;
import org.github.got.Resources;
import org.github.got.commands.Engine;
import org.github.got.item.ItemStack;

public class Player extends Entity implements Externalizable {

  private static final long serialVersionUID = -2045840728303263845L;

  private final List<Quest> quests = new LinkedList<>();
  private final Set<String> completedQuests = new HashSet<>();
  private final List<ItemStack> inventory = new ArrayList<>(16);
  private int coins = 10;
  private int experience = 0;

  public Player() {
    this(EMPTY, null, null, null);
  }

  public Player(final String name, final String race, final String gender, final Clazz clazz) {
    super(name, race, gender, clazz, Attitude.FRIENDLY, Ability.byClass(clazz));
  }

  public String describeInventory() {
    String inv = IntStream.range(0, inventory.size()).mapToObj(i -> {
      final ItemStack slot = inventory.get(i);
      return Resources.getString(GAME_DESCRIBE_PLAYER_INVENTORY_ITEM, i + 1, slot.describe());
    }).collect(Collectors.joining(NL));
    if (inv.isEmpty()) {
      inv = Resources.getString(GAME_DESCRIBE_PLAYER_INVENTORY_EMPTY);
    }
    return Resources.getString(GAME_DESCRIBE_PLAYER_INVENTORY, inv, coins);
  }

  public int getCoins() {
    return coins;
  }

  public void pay(final int amount) {
    coins -= amount;
  }

  public void addToInventory(final Item item, final int amount) {
    final ItemStack is = itemLookup(item.getNameLookup());
    if (is == null) {
      final ItemStack newitem = new ItemStack(item, amount);
      inventory.add(newitem);
      quests.stream().forEach(q -> q.getRequirements().stream().forEach(r -> {
        r.setTracker(newitem);
      }));
    } else {
      is.increaseAmount(amount);
    }
  }

  public void removeItemFromInventory(final Item item, final int amount) {
    final ItemStack is = itemLookup(item.getNameLookup());
    if (is != null) {
      is.descreaseAmount(amount);
      if (is.getAmount() == 0) {
        inventory.remove(is);
      }
    }
  }

  public void addToInventory(final ItemStack items) {
    addToInventory(items.getItem(), items.getAmount());
  }

  public List<Quest> getQuests() {
    return quests;
  }

  public boolean isQuestCompleted(final String nameLookup) {
    return completedQuests.contains(nameLookup);
  }

  public void accept(final Quest quest) {
    quests.add(quest);
    quest.getRequirements().stream().forEach(r -> {
      final ItemStack itemStack = itemLookup(r.getNameLookup());
      if (itemStack != null) {
        r.setTracker(itemStack);
      }
    });
  }

  public void addCoins(final int amount) {
    coins += amount;
  }

  public ItemStack itemLookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, inventory);
  }

  public Quest questLookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, quests);
  }

  public String describeJournal() {
    final String j = IntStream.range(0, quests.size()).mapToObj(i -> {
      final Quest quest = quests.get(i);
      return Resources.getString(GAME_DESCRIBE_PLAYER_JOURNAL_ITEM, i + 1, quest.getName(), quest.status());
    }).collect(Collectors.joining(NL));
    if (!j.isEmpty()) {
      return j;
    }
    return Resources.getString(GAME_DESCRIBE_PLAYER_JOURNAL_EMPTY);
  }

  @Override
  public String describeStats() {
    return Resources.getString(GAME_DESCRIBE_PLAYER_STATS, super.describeStats(), experience, getExperienceToLevelup());
  }

  public void complete(final Quest quest, final ItemStack reward) {
    if (quest.meetRequirement() && quests.contains(quest)) {
      if (reward != null) {
        addToInventory(reward.getItem(), reward.getAmount());
      }
      addExperience(quest.getExperience());
      quests.remove(quest);
      completedQuests.add(quest.getNameLookup());
    }
  }

  public void addExperience(final int experience) {
    this.experience += experience;
    if (this.experience / getExperienceToLevelup() > 0) {
      levelUp();
    }
  }

  public void levelUp() {
    experience -= getExperienceToLevelup();
    incrementLevel(1);
    addStamina(getClazz().stamina);
    addStrength(getClazz().strength);
    addAgility(getClazz().agility);
    addIntellect(getClazz().intellect);
    setHealth(maxHealth());
    setMana(maxMana());
  }

  public int getExperienceToLevelup() {
    return Formulas.experienceToLevelup(getLevel());
  }

  public void restore() {
    retoreHealth(maxHealth());
    retoreMana(maxMana());
  }

  protected Set<String> getCompletedQuests() {
    return completedQuests;
  }

  protected List<ItemStack> getInventory() {
    return inventory;
  }

  @Override
  public void writeExternal(final ObjectOutput out) throws IOException {
    out.writeUTF(getName());
    out.writeUTF(getRace());
    out.writeUTF(getGender());
    out.writeObject(getClazz());
    out.writeInt(experience);
    out.writeInt(getLevel());
    out.writeInt(getStamina());
    out.writeInt(getStrength());
    out.writeInt(getAgility());
    out.writeInt(getIntellect());
    out.writeInt(coins);

    out.writeInt(inventory.size());
    for (final ItemStack is : inventory) {
      out.writeInt(is.getAmount());
      out.writeUTF(is.getItem().getNameLookup());
    }
    out.writeInt(completedQuests.size());
    for (final String q : completedQuests) {
      out.writeUTF(q);
    }

    out.writeInt(quests.size());
    for (final Quest q : quests) {
      out.writeUTF(q.getNameLookup());
    }
  }

  @Override
  public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
    setName(in.readUTF());
    setRace(in.readUTF());
    setGender(in.readUTF());
    setClazz((Clazz) in.readObject());
    setAbilities(Ability.byClass(getClazz()));
    experience = in.readInt();
    setLevel(in.readInt());
    setStamina(in.readInt());
    setStrength(in.readInt());
    setAgility(in.readInt());
    setIntellect(in.readInt());
    setHealth(getStamina() * 5);
    setMana(getIntellect() * 5);
    coins = in.readInt();

    final int inventorySize = in.readInt();
    for (int i = 0; i < inventorySize; i++) {
      final int amount = in.readInt();
      final String name = in.readUTF();
      final Item item = Game.getWorld().itemLookup(name);
      if (item != null) {
        inventory.add(new ItemStack(item, amount));
      }
    }

    final int completedQuestsNum = in.readInt();
    for (int i = 0; i < completedQuestsNum; i++) {
      final String name = in.readUTF();
      if (name != null) {
        completedQuests.add(name);
      }
    }

    final int incompletedQuestsNum = in.readInt();
    for (int i = 0; i < incompletedQuestsNum; i++) {
      final String name = in.readUTF();
      final Quest q = Game.getWorld().questLookup(name);
      if (q != null) {
        accept(q);
      }
    }
  }


}
