package org.github.got;

/**
 * Type of interactive message. Color is picked depending on this.
 *
 * @author Maksim Chizhov
 */
public enum MessageType {
  /**
   * No decoration.
   */
  NONE,
  /**
   * System message.
   */
  SYSTEM,
  /**
   * Help message.
   */
  HELP,
  /**
   * Game message.
   */
  GAME,
  /**
   * Player input/output.
   */
  PLAYER,
  /**
   * Oracle talk.
   */
  ORACLE,
  /**
   * Hostile talk and actions.
   */
  HOSTILE,
  /**
   * Friendly talk and actions.
   */
  FRIENDLY,
  /**
   * Neutral talk and actions.
   */
  NEUTRAL,
  /**
   * Players amotion.
   */
  EMOTION,
  /**
   * Combat message.
   */
  COMBAT,
  /**
   * Ability/spell hit.
   */
  COMBAT_HIT,
  /**
   * Ability/spell miss.
   */
  COMBAT_MISS;

  public final String value = toString().toLowerCase();

  /**
   * Wrap specified string with xterm escape sequences. Applies specified
   * background, text color and font type. Background continues until end of
   * line. Supports sub messages.
   *
   * @param message
   * @return
   */
  public String wrap(final String message) {
    final String bkcolor = Resources.backgroundColor();
    final String colorFor = Resources.colorFor(this);
    return bkcolor + colorFor + message.replace(bkcolor, bkcolor + colorFor) + Resources.RESET_COLOR
        + Resources.ERASE_SEQUENCE + bkcolor;
  }

}
