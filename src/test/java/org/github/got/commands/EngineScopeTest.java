package org.github.got.commands;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.github.got.Command;
import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.TestHelper;
import org.junit.After;
import org.junit.Test;

public class EngineScopeTest {

  @Test
  public void testScopeNone() {
    final Command command = mock(ScopeNone.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_none");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_none");
    }
    verify(command, times(1)).issue(any());
  }

  @Test
  public void testScopeMenu() {
    final Command command = mock(ScopeMenu.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_menu");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_menu");
    }
    verify(command, times(1)).issue(any());
  }

  @Test
  public void testScopeGlobal() {
    final Command command = mock(ScopeGlobal.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_global");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_global");
    }
    verify(command, times(Scope.values().length)).issue(any());
  }

  @Test
  public void testScopeIngame() {
    final Command command = mock(ScopeIngame.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_ingame");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_ingame");
    }
    verify(command, times(5)).issue(any());
  }

  @Test
  public void testScopeLocation() {
    final Command command = mock(ScopeLocation.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_location");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_location");
    }
    verify(command, times(2)).issue(any());
  }

  @Test
  public void testScopeTalk() {
    final Command command = mock(ScopeTalk.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_talk");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_talk");
    }
    verify(command, times(1)).issue(any());
  }

  @Test
  public void testScopeCombat() {
    final Command command = mock(ScopeCombat.class);
    Engine.registerCommand(command);
    final Engine engine = TestHelper.engine();
    final Context context = engine.contextFor("test_combat");
    for (final Scope scope : Scope.values()) {
      context.setScope(scope);
      engine.process("TEST_combat");
    }
    verify(command, times(1)).issue(any());
  }

  @After
  public void tearDown() {
    Engine.cleanup();
  }

  abstract class Cmd implements Command {
    @Override
    public void issue(final Context context) {
    }
  }

  @CommandA(value = "test_none", scope = Scope.NONE)
  class ScopeNone extends Cmd {
  }

  @CommandA(value = "test_menu", scope = Scope.MENU)
  class ScopeMenu extends Cmd {
  }

  @CommandA(value = "test_global", scope = Scope.GLOBAL)
  class ScopeGlobal extends Cmd {
  }

  @CommandA(value = "test_ingame", scope = Scope.INGAME)
  class ScopeIngame extends Cmd {
  }

  @CommandA(value = "test_town", scope = Scope.TOWN)
  class ScopeTown extends Cmd {
  }

  @CommandA(value = "test_location", scope = Scope.LOCATION)
  class ScopeLocation extends Cmd {
  }

  @CommandA(value = "test_talk", scope = Scope.TALK)
  class ScopeTalk extends Cmd {
  }

  @CommandA(value = "test_combat", scope = Scope.COMBAT)
  class ScopeCombat extends Cmd {
  }

}
