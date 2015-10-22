package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_GOTO_ERROR_NOWAY;
import static org.github.got.Resources.GAME_ACTIONS_GOTO_LEFT_LOCATION;
import static org.github.got.Resources.GAME_EVENT_YOU_BEEN_RESURRECTED;
import static org.github.got.Resources.STORY_ORACLE_RESURRECT;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.LinkedList;

import org.github.got.Ability;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Location;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.github.got.World;
import org.github.got.entity.Mob;
import org.github.got.entity.Player;
import org.junit.Test;

public class CombatCommandsTest {

  private static CombatCommands COMBAT_COMMANDS_INT = new CombatCommands() {
    @Override
    public void issue(final Context context) {
    }
  };

  @Test
  public void testRevive() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).restore();
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    doNothing().when(location).repopulate();
    when(world.getVisitedLocations()).thenReturn(new LinkedList<>(Arrays.asList(location, location)));
    when(context.getPlayer()).thenReturn(player);
    when(context.getWorld()).thenReturn(world);
    when(context.getPrintStream()).thenReturn(printStream);
    COMBAT_COMMANDS_INT.revive(context);
    verify(player, times(1)).restore();
    verify(context, times(1)).setScope(same(Scope.TOWN));
  }

  @Test
  public void testSendSpiritToTombStone() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = TestHelper.PLAYER;
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    doNothing().when(location).repopulate();
    when(world.getVisitedLocations()).thenReturn(new LinkedList<>(Arrays.asList(location, location)));
    when(context.getPlayer()).thenReturn(player);
    when(context.getWorld()).thenReturn(world);
    when(context.getPrintStream()).thenReturn(printStream);
    COMBAT_COMMANDS_INT.sendSpiritToTombStone(context);
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(getString(GAME_EVENT_YOU_BEEN_RESURRECTED))));
    verify(printStream, times(1))
    .println(eq(MessageType.ORACLE.wrap(getString(STORY_ORACLE_RESURRECT, player.getName()))));
    verify(printStream, times(1)).println(contains("town"));
    verify(world, times(2)).setLocation(same(world.getHome()));
    verify(location, times(1)).repopulate();
  }

  @Test
  public void testGoTo() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).restore();
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    doNothing().when(location).repopulate();
    when(world.getVisitedLocations()).thenReturn(new LinkedList<>(Arrays.asList(location, location)));
    when(context.getPlayer()).thenReturn(player);
    when(context.getWorld()).thenReturn(world);
    when(context.getPrintStream()).thenReturn(printStream);

    COMBAT_COMMANDS_INT.goTo(context, location);
    verify(printStream, times(1))
    .println(eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_GOTO_LEFT_LOCATION, location.getName()))));
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(location.describe())));
    verify(location, times(1)).repopulate();
    verify(world, times(1)).setLocation(same(location));
    verify(player, times(1)).restore();
  }

  @Test
  public void testGoTo_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    COMBAT_COMMANDS_INT.goTo(context, null);

    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_GOTO_ERROR_NOWAY))));
  }

  @Test
  public void testAttack() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    final Player player = spy(TestHelper.PLAYER);
    when(player.getMana()).thenReturn(10000);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    doNothing().when(player).addCoins(anyInt());
    doNothing().when(player).addExperience(anyInt());
    doNothing().when(player).addToInventory(any());
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());

    COMBAT_COMMANDS_INT.attack(context, player, target, Ability.FIREBALL, false);
    verify(printStream, times(1)).println(contains(TestHelper.MOB_MELEE.getName()));
    verify(printStream, times(1)).println(contains(Ability.FIREBALL.getName()));
    verify(player, never()).reduceHealth(anyInt());
    verify(player, times(1)).reduceMana(anyInt());
    verify(target, atMost(1)).reduceHealth(anyInt());
    verify(target, never()).reduceMana(anyInt());

    COMBAT_COMMANDS_INT.attack(context, player, target, Ability.FIREBALL, true);
    verify(printStream, times(2)).println(contains(TestHelper.MOB_MELEE.getName()));
    verify(printStream, atMost(2)).println(contains("crit"));
    verify(player, never()).reduceHealth(anyInt());
    verify(player, atMost(2)).reduceMana(anyInt());
    verify(target, atMost(2)).reduceHealth(anyInt());
    verify(target, never()).reduceMana(anyInt());

  }

  @Test
  public void testAttack_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    doNothing().when(player).addCoins(anyInt());
    doNothing().when(player).addExperience(anyInt());
    doNothing().when(player).addToInventory(any());
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());

    COMBAT_COMMANDS_INT.attack(context, player, target, Ability.FIREBALL, false);
    verify(printStream, times(1)).println(contains("out of mana"));
    verify(player, never()).reduceHealth(anyInt());
    verify(player, never()).reduceMana(anyInt());
    verify(target, never()).reduceHealth(anyInt());
    verify(target, never()).reduceMana(anyInt());

  }

}
