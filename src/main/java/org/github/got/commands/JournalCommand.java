package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;

@CommandA(value = { "journal", "j" })
public class JournalCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeJournal());
  }
}
