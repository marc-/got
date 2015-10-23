package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;

/**
 * Displays quest log (only active items currently displayed only).
 *
 * @author Maksim Chizhov
 */
@CommandA(value = { "journal", "j" })
public class JournalCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeJournal());
  }
}
