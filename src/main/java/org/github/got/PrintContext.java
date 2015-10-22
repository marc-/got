package org.github.got;

import java.io.PrintStream;

public class PrintContext {

  private final PrintStream printStream;
  private final MessageType messageType;
  private final String message;

  protected PrintContext(final PrintStream printStream, final MessageType messageType, final String message) {
    this.printStream = printStream;
    this.messageType = messageType;
    this.message = message;
  }

  public PrintStream getPrintStream() {
    return printStream;
  }

  public MessageType getMessageType() {
    return messageType;
  }

  public String getMessage() {
    return message;
  }

  public static PrintContext create(final Context context, final MessageType messageType) {
    return new PrintContext(context.getPrintStream(), messageType, null);
  }

  public static PrintContext create(final Context context, final MessageType messageType, final String message) {
    return new PrintContext(context.getPrintStream(), messageType, message);
  }

  public static PrintContext create(final Context context) {
    return new PrintContext(context.getPrintStream(), MessageType.NONE, null);
  }

  public PrintContext message(final String message) {
    return new PrintContext(printStream, messageType, message);
  }

  public static PrintContext system(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.SYSTEM, message);
  }

  public static PrintContext game(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.GAME, message);
  }

  public static PrintContext oracle(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.ORACLE, message);
  }

  public static PrintContext player(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.PLAYER, message);
  }

  public static PrintContext emotion(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.EMOTION, message);
  }

  public static PrintContext combat(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.COMBAT, message);
  }

  public static PrintContext combatHit(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.COMBAT_HIT, message);

  }

  public static PrintContext combatMiss(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.COMBAT_MISS, message);

  }

  public static PrintContext hostile(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.HOSTILE, message);
  }

  public static PrintContext none(final Context context, final String message) {
    return new PrintContext(context.getPrintStream(), MessageType.NONE, message);
  }

}
