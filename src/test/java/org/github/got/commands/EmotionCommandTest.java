package org.github.got.commands;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.junit.Test;

public class EmotionCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    when(context.getCommand()).thenReturn("/me jump");
    new EmotionCommand().issue(context);
    verify(printStream, times(1)).println(eq(MessageType.EMOTION.wrap(TestHelper.PLAYER.getName() + " jump")));
  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    when(context.getCommand()).thenReturn("/me ");
    new EmotionCommand().issue(context);
    verify(printStream, never()).println(anyString());
  }

}
