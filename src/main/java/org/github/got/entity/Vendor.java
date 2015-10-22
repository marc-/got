package org.github.got.entity;

import static org.github.got.Resources.GAME_DESCRIBE_VENDOR_ITEM;
import static org.github.got.Resources.MALE;
import static org.github.got.Resources.RACE_ORC;
import static org.github.got.TextUtil.NL;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.Formulas;
import org.github.got.Item;
import org.github.got.Quest;
import org.github.got.Resources;
import org.github.got.commands.Engine;
import org.github.got.item.Consumable;
import org.github.got.item.Consumable.Effect;
import org.github.got.item.ItemStack;
import org.github.got.item.Requirement;
import org.github.got.item.Weapon;

public class Vendor extends NonPlayerCharacter {

  private final static float COMMISSION = 0.5f;
  private final List<Item> sells;

  public Vendor(final String name, final String race, final String gender, final Clazz clazz, final Attitude attitude,
      final String description, final String welcomeMessage, final String goodbyeMessage, final List<Quest> quests,
      final List<Item> sells) {
    super(name, race, gender, clazz, attitude, description, welcomeMessage, goodbyeMessage, quests);
    this.sells = sells;
  }

  public static Vendor defautVendor() {
    return new Vendor(Resources.VENDOR, RACE_ORC, Resources.getString(MALE), Clazz.VENDOR, Attitude.FRIENDLY,
        Resources.getString(Resources.STORY_VENDOR_DESCRIPTION), Resources.STORY_VENDOR_WELCOME,
        Resources.STORY_VENDOR_FAREWELL,
        Arrays.asList(new Quest(Resources.getString("story.quest2.name"), 2, Resources.VENDOR, Resources.VENDOR,
            Resources.getString("story.quest2.start"), "story.quest2.end",
            Arrays.asList(new Requirement(Requirement.Type.LOOT, "Boar meat", 3)),
            Arrays.asList(new ItemStack(new Weapon("Light sword", 10, Clazz.WARRIOR, 2, 0, 0, 0, 0), 1),
                new ItemStack(new Weapon("Light bow", 10, Clazz.HUNTER, 3, 0, 0, 0, 0), 1),
                new ItemStack(new Weapon("Light stuff", 10, Clazz.MAGE, 1, 0, 0, 0, 4), 1)),
            Formulas.MIN_EXP * 2)),
        Arrays.asList(new Consumable("Health potion", Effect.RESTORES_HEALH, 10, 2),
            new Consumable("Mana potion", Effect.RESTORES_MANA, 10, 2)));

  }

  public String describeItems() {
    return IntStream.range(0, sells.size()).mapToObj(i -> {
      final Item item = sells.get(i);
      return Resources.getString(GAME_DESCRIBE_VENDOR_ITEM, i + 1, item.describe(), getSellPrice(item));
    }).collect(Collectors.joining(NL));
  }

  public int getBuyPrice(final Item item) {
    return (int) (item.getPrice() * Vendor.COMMISSION);
  }

  public int getSellPrice(final Item item) {
    return (int) (item.getPrice() / Vendor.COMMISSION);
  }

  public Item itemLookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, sells);
  }
}
