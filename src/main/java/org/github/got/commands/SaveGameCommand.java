package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_ERROR_SAVE;
import static org.github.got.Resources.GAME_SYSTEM_SAVED;
import static org.github.got.Resources.GOT_SAVE_FILE_PATH;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;

@CommandA(value = { "save", "s" }, scope = Scope.TOWN)
public class SaveGameCommand extends AbstractCommand {

  private static final Logger LOGGER = Logger.getLogger(SaveGameCommand.class.getPackage().getName());

  @Override
  public void issue(final Context context) {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveFilePath()));) {
      out.writeObject(context.getPlayer());
      system(context, GAME_SYSTEM_SAVED);
    } catch (final IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
      system(context, GAME_SYSTEM_ERROR_SAVE);
    }
  }

  /* For testing purposes. */ String saveFilePath() {
    return GOT_SAVE_FILE_PATH;
  }
}
