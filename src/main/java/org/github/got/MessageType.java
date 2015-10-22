package org.github.got;

public enum MessageType {
  NONE,
  SYSTEM,
  HELP,
  GAME,
  PLAYER,
  ORACLE,
  HOSTILE,
  FRIENDLY,
  NEUTRAL,
  EMOTION,
  COMBAT,
  COMBAT_HIT,
  COMBAT_MISS;

  public final String value = toString().toLowerCase();

  public String wrap(final String message) {
    final String bkcolor = Resources.backgroundColor();
    final String colorFor = Resources.colorFor(this);
    return bkcolor + colorFor + message.replace(bkcolor, bkcolor + colorFor) + Resources.RESET_COLOR
        + Resources.ERASE_SEQUENCE + bkcolor;
  }

}
