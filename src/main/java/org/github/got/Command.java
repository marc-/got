package org.github.got;

/**
 * Base interface for all commands.
 *
 * @author Maksim Chizhov
 *
 */
public interface Command {

  /**
   * Will be executed during game initialization, if used as value for
   * {@link org.github.got.CommandA}.
   */
  static String INIT = "__init__";

  /**
   * Process command.
   *
   * @param context
   *          grants access to current game state and certain shared objects
   */
  void issue(Context context);

}
