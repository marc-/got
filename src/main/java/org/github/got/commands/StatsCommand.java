package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;

/**
 * Display current player stats.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "stats", "st" })
public class StatsCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeStats());
  }
}
