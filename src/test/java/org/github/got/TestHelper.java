package org.github.got;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_CANT_START;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_UNKNOWN_COMMAND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_WRONG_SCOPE;
import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import org.github.got.Entity.Attitude;
import org.github.got.commands.Engine;
import org.github.got.entity.Clazz;
import org.github.got.entity.Mob;
import org.github.got.entity.Mob.Mood;
import org.github.got.entity.NonPlayerCharacter;
import org.github.got.entity.Player;
import org.github.got.entity.Vendor;
import org.github.got.item.ItemStack;

public final class TestHelper {
  public static class PlayerExt extends Player {

    public PlayerExt() {
    }

    public PlayerExt(final String name, final String race, final String gender, final Clazz clazz) {
      super(name, race, gender, clazz);
    }

    @Override
    public List<ItemStack> getInventory() {
      return super.getInventory();
    }

    @Override
    public Set<String> getCompletedQuests() {
      return super.getCompletedQuests();
    }
  }

  public static class MobExt extends Mob {

    public MobExt(final String name, final int level, final String race, final String gender, final Clazz clazz,
        final Attitude attitude, final Mood mood, final List<Ability> abilities) {
      super(name, level, race, gender, clazz, attitude, mood, abilities);
    }

  }

  public static final Entity ENTITY = new Entity("TEST", 1, "race", "gender", Clazz.WARRIOR, Attitude.HOSTILE,
      GAME_SYSTEM_ERROR_CANT_START, GAME_SYSTEM_ERROR_WRONG_SCOPE, GAME_SYSTEM_ERROR_UNKNOWN_COMMAND, null) {
  };
  public static final Mob MOB_CASTER = new MobExt("TEST", 1, "race", "gender", Clazz.WARRIOR, Attitude.HOSTILE,
      Mood.ANGRY, Collections.singletonList(Ability.FIREBALL));
  public static final Mob MOB_MELEE = new MobExt("TEST", 1, "race", "gender", Clazz.MAGE, Attitude.HOSTILE, Mood.ANGRY,
      Collections.singletonList(Ability.HEROIC_STRIKE));;
      public static final Player PLAYER = new Player("TEST", "race", "gender", Clazz.WARRIOR);
      public static final Pattern PATTERN = Pattern.compile("have (?<name>([a-z- ]+)|([0-9]+))( (?<amount>[0-9]+))?$");
      public static final Quest QUEST = new Quest("TEST", 0, "TEST", "TEST", "description",
          GAME_SYSTEM_ERROR_UNKNOWN_COMMAND, null, null, 100);
      public static final Vendor VENDOR = new Vendor("TEST", "race", "gender", Clazz.VENDOR, Attitude.FRIENDLY,
          GAME_SYSTEM_ERROR_CANT_START, GAME_SYSTEM_ERROR_WRONG_SCOPE, GAME_SYSTEM_ERROR_UNKNOWN_COMMAND,
          Collections.singletonList(QUEST), null);
      public static final NonPlayerCharacter NPC = new NonPlayerCharacter("TEST", "race", "gender", Clazz.VENDOR,
          Attitude.FRIENDLY, GAME_SYSTEM_ERROR_CANT_START, GAME_SYSTEM_ERROR_WRONG_SCOPE, GAME_SYSTEM_ERROR_UNKNOWN_COMMAND,
          null);

      private TestHelper() {
      }

      public static Engine engine() {
        return spy(new Engine(mock(PrintStream.class), TestHelper.scanner()));
      }

      public static Scanner scanner() {
        return new Scanner(EMPTY);
      }

      public static void setEngine(final Engine engine) {
        Game.setEngine(engine);
      }

      public static String uuid() {
        return UUID.randomUUID().toString();
      }

      public static void assertPlayers(final PlayerExt player1, final PlayerExt player2) {
        assertEquals(player1.getCoins(), player2.getCoins());
        assertEquals(player1.getStamina(), player2.getStamina());
        assertEquals(player1.getStrength(), player2.getStrength());
        assertEquals(player1.getAgility(), player2.getAgility());
        assertEquals(player1.getIntellect(), player2.getIntellect());
        assertEquals(player1.getClazz(), player1.getClazz());
        assertEquals(player1.getGender(), player2.getGender());
        assertEquals(player1.getLevel(), player2.getLevel());
        assertEquals(player1.getName(), player2.getName());
        assertEquals(player1.getRace(), player2.getRace());
        assertEquals(player1.getQuests(), player2.getQuests());
        assertEquals(player1.getCompletedQuests(), player2.getCompletedQuests());
        final ItemStack isExpected = player1.getInventory().get(0);
        final ItemStack isActual = player2.getInventory().get(0);
        assertEquals(isExpected.getAmount(), isActual.getAmount());
        assertEquals(isExpected.getItem(), isActual.getItem());
      }
}
