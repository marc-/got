package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_FLEE;
import static org.github.got.Resources.GAME_ACTIONS_GOTO_LEFT_LOCATION;
import static org.github.got.Resources.GAME_EVENT_SNEAK_FAILED;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
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

public class GoCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    when(location.lookup(anyString())).thenReturn(TestHelper.MOB_MELEE);
    when(location.getWest()).thenReturn(location);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).restore();

    when(world.getLocation()).thenReturn(location);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getPlayer()).thenReturn(player);
    when(context.getCommand()).thenReturn("go west");
    new GoCommand().issue(context);
    verify(printStream, times(1))
    .println(eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_GOTO_LEFT_LOCATION, location.getName()))));
    verify(context, times(1)).setTarget(eq(null));
  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(Location.generate(1000, null, null, null, null));
    final Mob mobMelee = spy(TestHelper.MOB_MELEE);
    when(mobMelee.getLevel()).thenReturn(1000);
    when(location.lookup(anyString())).thenReturn(mobMelee);
    when(location.getWest()).thenReturn(location);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).restore();

    when(world.getLocation()).thenReturn(location);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getPlayer()).thenReturn(player);
    when(context.getCommand()).thenReturn("go west");
    new GoCommand().issue(context);
    verify(printStream, atMost(1)).println(eq(MessageType.GAME.wrap(getString(GAME_EVENT_SNEAK_FAILED))));
    verify(printStream, atMost(1))
    .println(eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_GOTO_LEFT_LOCATION, location.getName()))));
    verify(context, atMost(1)).setTarget(eq(null));
  }

  @Test
  public void testIssue_NEGATIVE1() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(Location.generate(100, null, null, null, null));
    when(location.lookup(anyString())).thenReturn(TestHelper.MOB_MELEE);
    when(location.getWest()).thenReturn(location);
    final Player player = spy(TestHelper.PLAYER);
    when(player.getAbility(eq("test"))).thenReturn(Ability.HEROIC_STRIKE);
    doNothing().when(player).reduceHealth(anyInt());
    doNothing().when(player).reduceMana(anyInt());
    final Mob target = spy(TestHelper.MOB_MELEE);
    doNothing().when(target).reduceHealth(anyInt());
    doNothing().when(target).reduceMana(anyInt());
    when(world.getLocation()).thenReturn(location);
    when(context.getTarget()).thenReturn(target);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getPlayer()).thenReturn(player);
    when(context.getCommand()).thenReturn("go west");
    when(context.getScope()).thenReturn(Scope.COMBAT);
    new GoCommand().issue(context);
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_FLEE))));
    verify(player, atMost(1)).reduceHealth(anyInt());

  }

}
