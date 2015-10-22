package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_COMPLETE_QUEST_COMPLETED;
import static org.github.got.Resources.GAME_ACTIONS_ERROR_QUEST_NOT_FOUND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_NOT_QUEST_GIVER;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.github.got.entity.Player;
import org.junit.Test;

public class CompleteQuestCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).complete(same(TestHelper.QUEST), any());
    when(player.questLookup(eq("test"))).thenReturn(TestHelper.QUEST);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(TestHelper.VENDOR);
    when(context.getCommand()).thenReturn("complete test");
    new CompleteQuestCommand().issue(context);
    verify(printStream, times(1)).println(
        eq(MessageType.GAME.wrap(getString(GAME_ACTIONS_COMPLETE_QUEST_COMPLETED, TestHelper.QUEST.describe()))));
    verify(player, times(1)).complete(same(TestHelper.QUEST), any());

  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).complete(same(TestHelper.QUEST), any());
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(TestHelper.VENDOR);
    when(context.getCommand()).thenReturn("complete test");
    new CompleteQuestCommand().issue(context);
    verify(printStream, times(1))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_ACTIONS_ERROR_QUEST_NOT_FOUND, "Test"))));

  }

  @Test
  public void testIssue_NEGATIVE1() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    final Player player = spy(TestHelper.PLAYER);
    doNothing().when(player).complete(same(TestHelper.QUEST), any());
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(player);
    when(context.getTarget()).thenReturn(TestHelper.MOB_CASTER);
    when(context.getCommand()).thenReturn("complete test");
    new CompleteQuestCommand().issue(context);
    verify(printStream, times(1)).println(
        eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_NOT_QUEST_GIVER, TestHelper.MOB_CASTER.getName()))));

  }

}
