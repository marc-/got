package org.github.got.commands;

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

public class JournalCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    new JournalCommand().issue(context);
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(TestHelper.PLAYER.describeJournal())));
  }

}
