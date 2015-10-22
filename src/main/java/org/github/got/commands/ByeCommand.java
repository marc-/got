package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;

@CommandA(value = { "bye", "bb" }, scope = Scope.TALK, starts = Scope.TOWN)
public class ByeCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    final Entity target = context.getTarget();
    message(context, target.getAttitude().messageType(), target.getFarewellMessage());
    context.setTarget(null);
  }
}
