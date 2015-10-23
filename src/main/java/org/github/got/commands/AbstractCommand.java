package org.github.got.commands;

import static org.github.got.TextUtil.AMOUNT;
import static org.github.got.TextUtil.AT;
import static org.github.got.TextUtil.COLON;
import static org.github.got.TextUtil.DOLLAR_SPACE;
import static org.github.got.TextUtil.NAME;
import static org.github.got.TextUtil.NO;
import static org.github.got.TextUtil.PROMPT;
import static org.github.got.TextUtil.YES;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.got.Command;
import org.github.got.Context;
import org.github.got.Entity;
import org.github.got.MessageType;
import org.github.got.PrintContext;
import org.github.got.Printer;
import org.github.got.Resources;

/**
 * Basic command functionality. Grant access to user input and console output.
 *
 * @author Maksim Chizhov
 */
public abstract class AbstractCommand implements Command {

  protected static final Printer PRINTER = new SimplePrinter();

  public static void printUnknownCommand(final Context context) {
    PRINTER.print(PrintContext.system(context, Resources.getString(Resources.GAME_SYSTEM_ERROR_UNKNOWN_COMMAND)));
  }

  public static void printCantPerformActionAtThisMoment(final Context context) {
    PRINTER.print(PrintContext.system(context, Resources.getString(Resources.GAME_SYSTEM_ERROR_WRONG_SCOPE)));
  }

  protected void message(final Context context, final MessageType messageType, final String bundle,
      final Object... args) {
    messageRaw(context, messageType, Resources.getString(bundle, args));
  }

  protected void messageRaw(final Context context, final MessageType messageType, final String message) {
    PRINTER.print(PrintContext.create(context, messageType, message));
  }

  protected void system(final Context context, final String bundle, final Object... args) {
    systemRaw(context, Resources.getString(bundle, args));
  }

  protected void systemRaw(final Context context, final String message) {
    PRINTER.print(PrintContext.system(context, message));
  }

  protected void game(final Context context, final String bundle, final Object... args) {
    gameRaw(context, Resources.getString(bundle, args));
  }

  protected void gameRaw(final Context context, final String message) {
    PRINTER.print(PrintContext.game(context, message));
  }

  protected void oracle(final Context context, final String bundle, final Object... args) {
    PRINTER.print(PrintContext.oracle(context, Resources.getString(bundle, args)));
  }

  protected void player(final Context context, final String bundle, final Object... args) {
    playerRaw(context, Resources.getString(bundle, args));
  }

  protected void playerRaw(final Context context, final String message) {
    PRINTER.print(PrintContext.player(context, message));
  }

  protected void emotion(final Context context, final String message) {
    PRINTER.print(PrintContext.emotion(context, message));
  }

  protected void combat(final Context context, final String bundle, final Object... args) {
    PRINTER.print(PrintContext.combat(context, Resources.getString(bundle, args)));
  }

  protected void combatHit(final Context context, final String bundle, final Object... args) {
    PRINTER.print(PrintContext.combatHit(context, Resources.getString(bundle, args)));
  }

  protected void combatMiss(final Context context, final String bundle, final Object... args) {
    PRINTER.print(PrintContext.combatMiss(context, Resources.getString(bundle, args)));
  }

  protected void hostile(final Context context, final String bundle, final Object... args) {
    PRINTER.print(PrintContext.hostile(context, Resources.getString(bundle, args)));
  }

  protected void clearScreen(final Context context) {
    PRINTER.print(PrintContext.none(context, Resources.CLEAR_SCREEN));
  }

  protected void printCaption(final Context context) {
    PRINTER.print(PrintContext.none(context, Resources.loadCaption()));
  }

  public void prompt(final Context context) {
    String str = PROMPT;
    if (context.getPlayer() != null) {
      str = context.getPlayer().getName() + AT + context.getWorld().getLocation().getType().value
          + (context.getTarget() != null ? COLON + context.getTarget().getName() + DOLLAR_SPACE : PROMPT);
    }
    playerRaw(context, str);
  }

  public boolean yesno(final Context context, final String bundle, final Object... args) {
    final String message = Resources.getString(bundle, args);
    while (true) {
      systemRaw(context, message);
      final String yesno = context.getScanner().nextLine().toLowerCase();
      if (yesno.isEmpty()) {
        continue;
      }
      if (yesno.charAt(0) == YES) {
        return true;
      }
      if (yesno.charAt(0) == NO) {
        return false;
      }
    }
  }

  protected static Entity findTarget(final Context context, final String name) {
    return context.getWorld().getLocation().lookup(name);
  }

  @Override
  public int hashCode() {
    return getClass().getCanonicalName().hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (super.equals(obj)) {
      return true;
    }
    return getClass().equals(obj.getClass());
  }

  protected static String extractName(final String command, final Pattern pattern) {
    final Matcher matcher = pattern.matcher(command);
    if (!matcher.find()) {
      return null;
    }
    return matcher.group(NAME).trim();
  }

  protected static int extractAmount(final String command, final Pattern pattern, final int def) {
    int amount = def;
    final Matcher matcher = pattern.matcher(command);
    if (matcher.find()) {
      try {
        amount = Integer.parseInt(matcher.group(AMOUNT));
      } catch (final IllegalArgumentException e) {
        // Ok.
      }
    }
    return amount;
  }

  private static class SimplePrinter implements Printer {

    @Override
    public void print(final PrintContext printContext) {
      final MessageType messageType = printContext.getMessageType();
      final String message = messageType.wrap(printContext.getMessage());
      if (MessageType.PLAYER.equals(messageType)) {
        printContext.getPrintStream().print(message);
      } else {
        printContext.getPrintStream().println(message);
      }
    }

  }

}
