package org.github.got.commands;

import static org.github.got.Resources.CLEAR_SCREEN;
import static org.github.got.Resources.ERASE_SEQUENCE;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_UNKNOWN_COMMAND;
import static org.github.got.Resources.GAME_SYSTEM_ERROR_WRONG_SCOPE;
import static org.github.got.Resources.ORACLE;
import static org.github.got.Resources.RESET_COLOR;
import static org.github.got.Resources.backgroundColor;
import static org.github.got.Resources.getString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.github.got.Context;
import org.github.got.MessageType;
import org.github.got.TestHelper;
import org.github.got.World;
import org.junit.After;
import org.junit.Test;

public class AbstractCommandTest {

  @Test
  public final void testPrintUnknownCommand() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    AbstractCommand.printUnknownCommand(context);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_UNKNOWN_COMMAND))));
  }

  @Test
  public final void testPrintCantPerformActionAtThisMoment() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    AbstractCommand.printCantPerformActionAtThisMoment(context);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testMessage() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.message(context, MessageType.NONE, GAME_SYSTEM_ERROR_UNKNOWN_COMMAND);
    verify(printStream, times(1)).println(eq(MessageType.NONE.wrap(getString(GAME_SYSTEM_ERROR_UNKNOWN_COMMAND))));
  }

  @Test
  public final void testMessageRaw() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.messageRaw(context, MessageType.NONE, GAME_SYSTEM_ERROR_UNKNOWN_COMMAND);
    verify(printStream, times(1)).println(eq(MessageType.NONE.wrap(GAME_SYSTEM_ERROR_UNKNOWN_COMMAND)));
  }

  @Test
  public final void testSystem() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.system(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testSystemRaw() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.systemRaw(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(GAME_SYSTEM_ERROR_WRONG_SCOPE)));
  }

  @Test
  public final void testGame() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.game(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testGameRaw() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.gameRaw(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.GAME.wrap(GAME_SYSTEM_ERROR_WRONG_SCOPE)));
  }

  @Test
  public final void testOracle() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.oracle(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.ORACLE.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testPlayer() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.player(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).print(eq(MessageType.PLAYER.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testPlayerRaw() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.playerRaw(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).print(eq(MessageType.PLAYER.wrap(GAME_SYSTEM_ERROR_WRONG_SCOPE)));
  }

  @Test
  public final void testEmotion() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.emotion(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.EMOTION.wrap(GAME_SYSTEM_ERROR_WRONG_SCOPE)));
  }

  @Test
  public final void testCombat() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.combat(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.COMBAT.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testCombatHit() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.combatHit(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.COMBAT_HIT.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testCombatMiss() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.combatMiss(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.COMBAT_MISS.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testHostile() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.hostile(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    verify(printStream, times(1)).println(eq(MessageType.HOSTILE.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testClearScreen() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.clearScreen(context);
    verify(printStream, times(1))
    .println(eq(backgroundColor() + CLEAR_SCREEN + RESET_COLOR + ERASE_SEQUENCE + backgroundColor()));
  }

  @Test
  public final void testPrintCaption() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.printCaption(context);
    verify(printStream, times(1)).println(anyString());
  }

  @Test
  public final void testPrompt() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    ABSTRACT_COMMAND_INST.prompt(context);
    verify(printStream, times(1)).print(eq(MessageType.PLAYER.wrap(":$ ")));
  }

  @Test
  public final void testPrompt1() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    when(context.getWorld()).thenReturn(World.generate());
    ABSTRACT_COMMAND_INST.prompt(context);
    verify(printStream, times(1)).print(eq(MessageType.PLAYER.wrap("TEST@town:$ ")));
  }

  @Test
  public final void testPrompt2() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getPlayer()).thenReturn(TestHelper.PLAYER);
    when(context.getWorld()).thenReturn(World.generate());
    when(context.getTarget()).thenReturn(TestHelper.ENTITY);
    ABSTRACT_COMMAND_INST.prompt(context);
    verify(printStream, times(1)).print(eq(MessageType.PLAYER.wrap("TEST@town:TEST$ ")));
  }

  @Test
  public final void testYesno_YES() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getScanner()).thenReturn(new Scanner("y"));
    final boolean yes = ABSTRACT_COMMAND_INST.yesno(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    assertTrue(yes);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testYesno_NO() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getScanner()).thenReturn(new Scanner("n"));
    final boolean no = ABSTRACT_COMMAND_INST.yesno(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    assertFalse(no);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testYesno_NEGATIVE() {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    when(context.getScanner()).thenReturn(new Scanner("asdasd\nqweqeqwe\n\nyes"));
    final boolean yes = ABSTRACT_COMMAND_INST.yesno(context, GAME_SYSTEM_ERROR_WRONG_SCOPE);
    assertTrue(yes);
    verify(printStream, times(4)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_ERROR_WRONG_SCOPE))));
  }

  @Test
  public final void testFindTarget() {
    final Context context = mock(Context.class);
    when(context.getWorld()).thenReturn(World.generate());
    assertNotNull(AbstractCommand.findTarget(context, ORACLE.toLowerCase()));
  }

  @Test
  public final void testFindTarget_NEGATIVE() {
    final Context context = mock(Context.class);
    when(context.getWorld()).thenReturn(World.generate());
    assertNull(AbstractCommand.findTarget(context, ORACLE));
  }

  @Test
  public final void testExtractName() {
    assertEquals("name qwe", AbstractCommand.extractName("have name qwe",
        Pattern.compile("have (?<name>([a-z- ]|[0-9])+)( (?<amount>[0-9]+))?$")));
  }

  @Test
  public final void testExtractName1() {
    assertEquals("1",
        AbstractCommand.extractName("have 1", Pattern.compile("have (?<name>([a-z- ]|[0-9])+)( (?<amount>[0-9]+))?$")));
  }

  @Test
  public final void testExtractName2() {
    assertEquals("name", AbstractCommand.extractName("have name 1", TestHelper.PATTERN));
  }

  @Test
  public final void testExtractName_NEGATIVE() {
    assertNull(AbstractCommand.extractName("have name 1 2", TestHelper.PATTERN));
  }

  @Test
  public final void testExtractName_NEGATIVE1() {
    assertNull(AbstractCommand.extractName("have ", TestHelper.PATTERN));
  }

  @Test
  public final void testExtractAmount() {
    assertEquals(1, AbstractCommand.extractAmount("have name 1", TestHelper.PATTERN, 0));
  }

  @Test
  public final void testExtractAmount2() {
    assertEquals(3, AbstractCommand.extractAmount("have name", TestHelper.PATTERN, 3));
  }

  @Test
  public final void testExtractAmount_NEGATIVE() {
    assertEquals(4, AbstractCommand.extractAmount("have name 1 2", TestHelper.PATTERN, 4));
  }

  @Test
  public final void testExtractAmount_NEGATIVE1() {
    assertEquals(5, AbstractCommand.extractAmount("have ", TestHelper.PATTERN, 5));
  }

  @After
  public void tearDown() throws Exception {
    Engine.cleanup();
  }

  private static final AbstractCommand ABSTRACT_COMMAND_INST = new AbstractCommand() {
    @Override
    public void issue(final Context context) {
    }
  };

}
