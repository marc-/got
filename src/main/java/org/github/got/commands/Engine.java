package org.github.got.commands;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import org.github.got.Command;
import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.Searchable;
import org.github.got.World;
import org.github.got.entity.Player;
import org.github.got.location.Town;

public class Engine {
  private static final Collection<Command> COMMANDS = new LinkedList<>();
  private static final Collection<Command> RUN_ONCE = new LinkedList<>();
  private static final AbstractCommand COMMAND = new AbstractCommand() {
    @Override
    public void issue(final Context context) {
      printUnknownCommand(context);
    }
  };
  private final PrintStream printStream;
  private final Scanner scanner;
  private World world;
  private Player player;
  private Scope currentScope = Scope.MENU;
  private Entity target;

  public Engine(final PrintStream printStream, final Scanner scanner) {
    this.printStream = printStream;
    this.scanner = scanner;
  }

  public static void registerCommand(final Command command) {
    final CommandA annotation = command.getClass().getAnnotation(CommandA.class);
    if (annotation == null) {
      return;
    }
    if (annotation.runOnce()) {
      if (!RUN_ONCE.contains(command)) {
        RUN_ONCE.add(command);
      }
    } else {
      if (!COMMANDS.contains(command)) {
        COMMANDS.add(command);
      }
    }
  }

  /* For testing. */ static void cleanup() {
    COMMANDS.clear();
  }

  public void init() {
    process(Command.INIT);
  }

  public void process(final String cmd) {
    final Context ctx = contextFor(cmd);
    if (!RUN_ONCE.isEmpty()) {
      for (final Iterator<Command> it = RUN_ONCE.iterator(); it.hasNext();) {
        if (tryCommand(it.next(), ctx)) {
          it.remove();
        }
      }
    }
    _process(ctx);
    COMMAND.prompt(ctx);
  }

  private void _process(final Context ctx) {
    for (final Command command : COMMANDS) {
      if (tryCommand(command, ctx)) {
        return;
      }
    }
    if (!ctx.getCommand().isEmpty() && !Command.INIT.equals(ctx.getCommand())) {
      AbstractCommand.printUnknownCommand(ctx);
    }
  }

  /* For testing. */ boolean tryCommand(final Command command, final Context ctx) {
    if (respond(command, ctx.getCommand())) {
      if (checkScope(command)) {
        command.issue(ctx);
        if (ctx.succeed()) {
          adjustScope(command);
        }
      } else {
        AbstractCommand.printCantPerformActionAtThisMoment(ctx);
      }
      return true;
    }
    return false;
  }

  private boolean respond(final Command command, final String cmd) {
    final CommandA annotation = command.getClass().getAnnotation(CommandA.class);
    if (annotation == null) {
      return false;
    }
    final String[] value = annotation.value();
    return value.length == 0 || Arrays.stream(value).anyMatch(c -> cmd.matches(c));
  }

  private void adjustScope(final Command command) {
    final CommandA annotation = command.getClass().getAnnotation(CommandA.class);
    if (!Scope.NONE.equals(annotation.starts()) && annotation.starts() != null) {
      if (Scope.LOCATION.equals(annotation.starts()) && world.getLocation() instanceof Town) {
        currentScope = Scope.TOWN;
      }else{
        currentScope = annotation.starts();
      }
    }
  }

  /* For testing. */ boolean checkScope(final Command command) {
    final CommandA annotation = command.getClass().getAnnotation(CommandA.class);
    if (annotation == null) {
      return false;
    }
    final Scope[] scopes = annotation.scope();
    if (scopes == null) {
      return false;
    }
    if (Arrays.stream(scopes).anyMatch(s -> Scope.GLOBAL.equals(s))) {
      return true;
    }
    if (Arrays.stream(scopes).anyMatch(s -> currentScope.equals(s))) {
      return true;
    }
    if (Arrays.stream(scopes)
        .anyMatch(s -> Scope.INGAME.equals(s) && currentScope.ordinal() >= Scope.INGAME.ordinal())) {
      return true;
    }
    if (Arrays.stream(scopes).anyMatch(s -> Scope.LOCATION.equals(s) && Scope.TOWN.equals(currentScope))) {
      return true;
    }
    return false;
  }

  /* For testing. */ Context contextFor(final String cmd) {
    final String lcmd = cmd.trim().toLowerCase();
    return new Context() {

      private boolean succeed = true;

      @Override
      public World getWorld() {
        return world;
      }

      @Override
      public PrintStream getPrintStream() {
        return printStream;
      }

      @Override
      public Player getPlayer() {
        return player;
      }

      @Override
      public String getCommand() {
        return lcmd;
      }

      @Override
      public void setWorld(final World world) {
        Engine.this.world = world;
      }

      @Override
      public void setPlayer(final Player player) {
        Engine.this.player = player;

      }

      @Override
      public Entity getTarget() {
        return target;
      }

      @Override
      public void setTarget(final Entity entity) {
        target = entity;
      }

      @Override
      public void setResult(final boolean succeed) {
        this.succeed = succeed;

      }

      @Override
      public boolean succeed() {
        return succeed;
      }

      @Override
      public Scanner getScanner() {
        return scanner;
      }

      @Override
      public Scope getScope() {
        return currentScope;
      }

      @Override
      public void setScope(final Scope scope) {
        currentScope = scope;
      }


    };
  }

  public World getWorld() {
    return world;
  }

  /**
   * Looking for specified item.
   *
   * @param nameOrIndex
   *          lower case name or index of the item (start with 1)
   * @param items
   *          list of items to look for
   * @return first matching item
   */
  public static <T extends Searchable> T lookup(final String nameOrIndex, final List<T> items) {
    final String name = nameOrIndex;
    int index = -1;
    try {
      index = Integer.parseInt(nameOrIndex) - 1;
    } catch (final NumberFormatException e) {
      Entity.LOGGER.log(Level.FINE, e.getMessage(), e);
    }
    if (index >= 0 && index < items.size()) {
      return items.get(index);
    }

    return items.stream().filter(i -> i.getNameLookup().equals(name)).findFirst().orElse(null);
  }

}
