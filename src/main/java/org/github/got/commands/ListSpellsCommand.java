package org.github.got.commands;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;

/**
 * List user's spells and abilities.
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "list (spells|abilities)", "ls (s|a)" }, scope = Scope.INGAME)
public class ListSpellsCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    gameRaw(context, context.getPlayer().describeAbilities());
  }

}
