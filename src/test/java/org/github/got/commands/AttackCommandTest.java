package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_NPC_NOT_FOUND;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.Location;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.github.got.World;
import org.junit.Test;

public class AttackCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    final Location location = spy(world.getLocation());
    when(location.lookup(anyString())).thenReturn(TestHelper.MOB_CASTER);
    when(world.getLocation()).thenReturn(location);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("attack test");
    new AttackCommand().issue(context);
    verify(printStream, times(1)).println(contains(TestHelper.MOB_CASTER.getName()));
    verify(context, times(1)).setTarget(same(TestHelper.MOB_CASTER));
  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final World world = spy(World.generate());
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(world);
    when(context.getCommand()).thenReturn("attack test");
    new AttackCommand().issue(context);
    verify(printStream, times(1))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_NPC_NOT_FOUND, "Test"))));
  }

}
