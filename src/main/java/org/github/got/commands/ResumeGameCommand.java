package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_RESUME;
import static org.github.got.Resources.GAME_SYSTEM_LOADED;
import static org.github.got.Resources.GAME_SYSTEM_RESUME_CONFIRMATION;
import static org.github.got.Resources.GOT_SAVE_FILE_PATH;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Game;
import org.github.got.World;
import org.github.got.entity.Player;

@CommandA(value = { "resume", "r" }, starts = Scope.TOWN)
public class ResumeGameCommand extends AbstractCommand {

  private static final Logger LOGGER = Logger.getLogger(ResumeGameCommand.class.getPackage().getName());

  @Override
  public void issue(final Context context) {
    clearScreen(context);
    if (!Game.isInProgress() || yesno(context, GAME_SYSTEM_RESUME_CONFIRMATION)) {
      try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(saveFilePath()));) {
        context.setWorld(World.generate());
        context.setPlayer((Player) out.readObject());
        Game.setInProgress(true);
        system(context, GAME_SYSTEM_LOADED);
      } catch (final IOException | ClassNotFoundException e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
        system(context, GAME_SYSTEM_ERROR_RESUME);
      }
    }
  }

  /* For testing purposes. */ String saveFilePath() {
    return GOT_SAVE_FILE_PATH;
  }

}
