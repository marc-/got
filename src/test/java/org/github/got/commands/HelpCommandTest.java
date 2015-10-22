package org.github.got.commands;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.commands.HelpCommand.CombatHelpCommand;
import org.github.got.commands.HelpCommand.ConversationHelpCommand;
import org.github.got.commands.HelpCommand.GameHelpCommand;
import org.junit.After;
import org.junit.Test;

public class HelpCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    new HelpCommand().issue(context);
    verify(printStream, atLeast(7)).println(anyString());
  }

  @Test
  public void testIssue_GAME() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    new GameHelpCommand().issue(context);
    verify(printStream, atLeast(15)).println(anyString());
  }

  @Test
  public void testIssue_CONVERSATION() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    new ConversationHelpCommand().issue(context);
    verify(printStream, atLeast(7)).println(anyString());
  }

  @Test
  public void testIssue_COMBAT() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    new CombatHelpCommand().issue(context);
    verify(printStream, atLeast(2)).println(anyString());
  }

  @After
  public void tearDown() throws Exception {
    Engine.cleanup();
  }

}
