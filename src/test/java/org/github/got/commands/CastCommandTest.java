package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_UNKNOWN_ABILITY;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

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

public class CastCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    final Player player = spy(TestHelper.PLAYER);
    when(player.getAbility(eq("test"))).thenReturn(Ability.HEROIC_STRIKE);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());
    when(location.lookup(anyString())).thenReturn(target);
    when(world.getLocation()).thenReturn(location);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(target);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("cast test");
    new CastCommand().issue(context);

    verify(printStream, times(2)).println(contains(TestHelper.MOB_MELEE.getName()));
    verify(printStream, times(2)).println(contains(Ability.HEROIC_STRIKE.getName()));
    verify(player, atMost(1)).reduceHealth(anyInt());
    verify(player, atMost(1)).reduceMana(anyInt());
    verify(target, atMost(1)).reduceHealth(anyInt());
    verify(target, atMost(1)).reduceMana(anyInt());
  }

  @Test
  public void testIssue_KILL() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    final Player player = spy(TestHelper.PLAYER);
    when(player.getAbility(eq("test"))).thenReturn(Ability.HEROIC_STRIKE);
    // Make sure there are no misses.
    when(player.getLevel()).thenReturn(100, 100, 100, 100, 100, 200);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    doNothing().when(player).addCoins(anyInt());
    doNothing().when(player).addExperience(anyInt());
    doNothing().when(player).addToInventory(any());
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());
    doReturn(true).when(target).isDead();
    when(location.lookup(anyString())).thenReturn(target);
    when(world.getLocation()).thenReturn(location);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(target);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("cast test");
    new CastCommand().issue(context);

    verify(printStream, atMost(2)).println(contains(TestHelper.MOB_MELEE.getName()));
    verify(printStream, atMost(1)).println(contains(Ability.HEROIC_STRIKE.getName()));
    verify(printStream, times(1)).println(contains("***YAY!LOOT!***"));
    verify(printStream, times(1)).println(contains("coin(s)"));
    verify(printStream, atMost(1)).println(contains("You hit"));
    verify(printStream, atMost(1)).println(contains("*** Congratulations!"));
    verify(player, never()).reduceHealth(anyInt());
    verify(player, times(1)).reduceMana(anyInt());
    verify(player, times(1)).addCoins(anyInt());
    verify(player, times(1)).addExperience(anyInt());
    verify(target, atMost(1)).reduceHealth(anyInt());
    verify(target, never()).reduceMana(anyInt());
    verify(context, times(1)).setTarget(eq(null));

  }

  @Test
  public void testIssue_DEAD() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    final Player player = spy(TestHelper.PLAYER);
    when(player.getAbility(eq("test"))).thenReturn(Ability.HEROIC_STRIKE);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    doNothing().when(player).restore();
    doReturn(true).when(player).isDead();
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());
    doReturn(1000).when(target).getLevel();
    when(location.lookup(anyString())).thenReturn(target);
    when(world.getLocation()).thenReturn(location);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(target);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("cast test");
    new CastCommand().issue(context);

    verify(printStream, atMost(4)).println(contains(TestHelper.MOB_MELEE.getName()));
    verify(printStream, times(2)).println(contains(Ability.HEROIC_STRIKE.getName()));
    verify(player, atMost(1)).reduceHealth(anyInt());
    verify(player, times(1)).reduceMana(anyInt());
    verify(player, times(1)).restore();
    verify(target, atMost(1)).reduceHealth(anyInt());
    verify(target, times(1)).reduceMana(anyInt());
    verify(context, times(1)).setTarget(eq(null));
    verify(context, times(1)).setScope(same(Scope.TOWN));

  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getTarget()).thenReturn(TestHelper.MOB_MELEE);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("cast test");
    new CastCommand().issue(context);

    verify(printStream, times(1))
    .println(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_UNKNOWN_ABILITY, "Test")));
  }

}
