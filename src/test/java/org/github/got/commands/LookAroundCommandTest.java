package org.github.got.commands;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.World;
import org.junit.Test;

public class LookAroundCommandTest {

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getWorld()).thenReturn(World.generate());
    new LookAroundCommand().issue(context);
    verify(printStream, times(1)).println(anyString());
  }

}
