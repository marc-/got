package org.github.got.commands;

import static org.github.got.Resources.GAME_CHARACTER_CREATE_CLASS;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_CONFIRMATION;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_ERROR_EMPTY_NAME;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_ERROR_INVALID_CLASS;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_GENDER;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_NAME;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_RACE;
import static org.github.got.Resources.GAME_CHARACTER_CREATE_YOU_ARE;
import static org.github.got.TextUtil.EMPTY;
import static org.github.got.TextUtil.SPACE;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Game;
import org.github.got.Resources;
import org.github.got.World;
import org.github.got.entity.Clazz;
import org.github.got.entity.Player;

/**
 * Start a new game. All unsaved progress will be lost.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "new", "n" }, starts = Scope.TOWN)
public class NewGameCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    clearScreen(context);
    final World world = World.generate();
    context.setWorld(world);
    createCharacter(context);
    Game.setInProgress(true);
    clearScreen(context);
    gameRaw(context, world.describe() + SPACE + world.getLocation().describe());
    oracle(context, world.getHome().getOracle().getWelcomeMessage());
    oracle(context, "story.oracle.phrase1");
    system(context, "story.phrase1");
    system(context, "story.phrase2");
  }

  private void createCharacter(final Context context) {
    boolean done = false;
    while (!done) {
      system(context, GAME_CHARACTER_CREATE_RACE);
      final String race = context.getScanner().nextLine().trim();
      // For testing purposes.
      if (race.equals("stub")) {
        final Player player = new Player("Qwe", "orc", "male", Clazz.WARRIOR);
        context.setPlayer(player);
        system(context, Resources.GAME_CHARACTER_CREATE_YOU_ARE);
        gameRaw(context, player.describe());
        gameRaw(context, player.describeStats());
        systemRaw(context, "Press enter to continue.");
        context.getScanner().nextLine();
        break;
      }
      system(context, GAME_CHARACTER_CREATE_GENDER);
      final String gender = context.getScanner().nextLine().trim();
      Clazz clazz = null;
      while (clazz == null) {
        system(context, GAME_CHARACTER_CREATE_CLASS, Clazz.available());
        clazz = Clazz.fromString(context.getScanner().nextLine().trim());
        if (clazz == null) {
          system(context, GAME_CHARACTER_CREATE_ERROR_INVALID_CLASS);
        }
      }
      String name = EMPTY;
      while (name.isEmpty()) {
        system(context, GAME_CHARACTER_CREATE_NAME);
        name = context.getScanner().nextLine().trim();
        if (name.isEmpty()) {
          system(context, GAME_CHARACTER_CREATE_ERROR_EMPTY_NAME);
        }
      }
      final Player player = new Player(name, race, gender, clazz);
      context.setPlayer(player);
      systemRaw(context, GAME_CHARACTER_CREATE_YOU_ARE);
      gameRaw(context, player.describe());
      gameRaw(context, player.describeStats());
      done = yesno(context, GAME_CHARACTER_CREATE_CONFIRMATION);
    }
  }

}
