package org.github.got.commands;

import static org.github.got.Resources.GAME_CHARACTER_CREATE_CLASS;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_CONFIRMATION;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_GENDER;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_NAME;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_RACE;
import static org.github.got.Resources.getString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.Scanner;

import org.github.got.Context;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.github.got.entity.Clazz;
import org.github.got.entity.Player;
import org.junit.Test;

public class NewGameCommandTest {

  private static final String NAME = "TEsT";
  private static final String GENDER = "MaLe";
  private static final String RACE = "UnDead";

  @Test
  public void testIssue() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getTarget()).thenReturn(TestHelper.ENTITY);
    final String source = RACE + "\n" + GENDER + "\nHunter\n" + NAME;
    when(context.getScanner()).thenReturn(new Scanner(source + "\nno\n" + source + "\nyes"));
    doAnswer(invocation -> {
      final Player player = (Player) invocation.getArguments()[0];
      assertEquals(RACE, player.getRace());
      assertEquals(GENDER, player.getGender());
      assertEquals(Clazz.HUNTER, player.getClazz());
      assertEquals(NAME, player.getName());
      return null;
    }).when(context).setPlayer(any());
    new NewGameCommand().issue(context);
    verify(printStream, times(2)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_RACE))));
    verify(printStream, times(2)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_GENDER))));
    verify(printStream, times(2))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_CLASS, Clazz.available()))));
    verify(printStream, times(2))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_NAME, Clazz.available()))));
    verify(printStream, times(2))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_NAME, Clazz.available()))));
    verify(printStream, times(2)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_CONFIRMATION))));

  }

  @Test
  public void testIssue_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getTarget()).thenReturn(TestHelper.ENTITY);
    when(context.getScanner()).thenReturn(new Scanner(RACE + "\n" + GENDER + "\nDruid\nMaGe\n" + NAME + "\nyes"));
    doAnswer(invocation -> {
      final Player player = (Player) invocation.getArguments()[0];
      assertEquals(RACE, player.getRace());
      assertEquals(GENDER, player.getGender());
      assertEquals(Clazz.MAGE, player.getClazz());
      assertEquals(NAME, player.getName());
      return null;
    }).when(context).setPlayer(any());
    new NewGameCommand().issue(context);
    verify(printStream, times(2))
    .println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_CLASS, Clazz.available()))));
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_CHARACTER_CREATE_CONFIRMATION))));

  }

}
