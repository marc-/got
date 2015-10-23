package org.github.got;

import java.io.PrintStream;
import java.util.Scanner;

import org.github.got.entity.Player;

/**
 * Interface provide read/write access to current game state.
 *
 * @author Maksim Chizhov
 *
 */
public interface Context {

  /**
   * Scope, where command can be executed.
   *
   */
  public enum Scope {
    /**
     * Dummy scope, used usually with {@link org.github.got.CommandA#starts()}
     */
    NONE,
    /**
     * Game starts with this scope.
     */
    MENU,
    /**
     * Command can be used anywhere in the game.
     */
    GLOBAL,
    /**
     * All scopes, except {@link org.github.got.Context.Scope#NONE},
     * {@link org.github.got.Context.Scope#MENU},
     * {@link org.github.got.Context.Scope#GLOBAL}.
     */
    INGAME,
    /**
     * Command can be used only in Town.
     */
    TOWN,
    /**
     * Command can be used in Town or any other location.
     */
    LOCATION,
    /**
     * Command can be used during conversation.
     */
    TALK,
    /**
     * Command can be used during battle.
     */
    COMBAT,
  }

  /**
   * Game output. Used for interaction with user.
   *
   * @return printStream
   */
  public PrintStream getPrintStream();

  public World getWorld();

  public Player getPlayer();

  /**
   * Current user input to be processed.
   *
   * @return user input
   */
  public String getCommand();

  public void setWorld(World world);

  public void setPlayer(Player player);

  /**
   * Current user target for interaction.
   *
   * @return target
   */
  public Entity getTarget();

  public void setTarget(Entity entity);

  /**
   * Command execution result.
   *
   * @param result
   */
  public void setResult(boolean result);

  /**
   * Indicates command execution status.
   *
   * @return true if command was executed successfully
   */
  public boolean succeed();

  /**
   * Provides user interactive input.
   *
   * @return scanner
   */
  public Scanner getScanner();

  public Scope getScope();

  public void setScope(Scope scope);

}
