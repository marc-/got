package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_UNKNOWN_COMMAND;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.junit.Test;

public class ByeCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getTarget()).thenReturn(TestHelper.ENTITY);
    new ByeCommand().issue(context);
    verify(printStream, times(1)).println(eq(MessageType.HOSTILE.wrap(getString(GAME_SYSTEM_ERROR_UNKNOWN_COMMAND))));
    verify(context, times(1)).setTarget(eq(null));
  }
}
