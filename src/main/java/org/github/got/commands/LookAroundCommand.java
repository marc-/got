package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;

@CommandA(value = { "l(ook( around)?)?|ls$" }, scope = Scope.INGAME)
public class LookAroundCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getWorld().getLocation().describe());
  }
}
