package org.github.got.commands;

import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.github.got.Ability;
import org.github.got.Command;
import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Item;
import org.github.got.Quest;
import org.github.got.Searchable;
import org.github.got.TestHelper;
import org.github.got.item.ItemStack;
import org.junit.After;
import org.junit.Test;

public class EngineTest {

  @Test
  public final void testRegisterCommand() {
    @CommandA(value = "test")
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = new TestCommand();
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.process("test");
    verify(engine, times(1)).tryCommand(same(command), any());
  }

  @Test
  public final void testInit() {
    @CommandA(value = Command.INIT)
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = new TestCommand();
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.init();
    verify(engine, times(1)).tryCommand(same(command), any());
  }

  @Test
  public final void testRunOnce() {
    @CommandA(value = "test", runOnce = true)
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = new TestCommand();
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.process("test");
    engine.process("test");
    verify(engine, times(1)).tryCommand(same(command), any());
  }

  @Test
  public final void testProcess() {
    @CommandA(value = "test")
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = spy(new TestCommand());
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.process("test");
    verify(engine, times(1)).tryCommand(same(command), any());
    verify(command, times(1)).issue(any());
  }

  @Test
  public final void testProcess1() {
    @CommandA(value = "test")
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = spy(new TestCommand());
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.process("test1");
    verify(engine).tryCommand(same(command), any());
    verify(command, never()).issue(any());
  }

  @Test
  public final void testProcess2() {
    @CommandA(value = "test")
    class TestCommand implements Command {
      @Override
      public void issue(final Context context) {
      }
    }
    final TestCommand command = spy(new TestCommand());
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    engine.process(EMPTY);
    verify(engine).tryCommand(same(command), any());
    verify(command, never()).issue(any());
  }

  @Test
  public final void testLookup() {
    final Ability ability = new Ability("TEST_ABILITY", 0, null, 0, 0, 0, 0);
    final Item item = new Item("TEST_ITEM", 0);
    final ItemStack itemStack = new ItemStack(new Item("TEST_ITEM_STACK", 0), 0);
    final Quest quest = new Quest("TEST_QUEST", 0, null, null, null, null, null, null, 0);
    final List<Searchable> iteams = Arrays.asList(ability,
        item,
        itemStack,
        quest);
    assertEquals(ability, Engine.lookup("1", iteams));
    assertEquals(ability, Engine.lookup("test_ability", iteams));

    assertEquals(item, Engine.lookup("2", iteams));
    assertEquals(item, Engine.lookup("test_item", iteams));

    assertEquals(itemStack, Engine.lookup("3", iteams));
    assertEquals(itemStack, Engine.lookup("test_item_stack", iteams));

    assertEquals(quest, Engine.lookup("4", iteams));
    assertEquals(quest, Engine.lookup("test_quest", iteams));
  }

  @After
  public void tearDown() {
    Engine.cleanup();
  }

}
