package org.github.got.commands;

import static org.github.got.TextUtil.SPACE;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.TextUtil;

@CommandA(value = { "/me .*", }, scope = Scope.INGAME)
public class EmotionCommand extends AbstractCommand {

  @Override
  public void issue(final Context context) {
    final String emotion = TextUtil.stripFirstPart(context.getCommand());
    if (!emotion.isEmpty()) {
      emotion(context, context.getPlayer().getName() + SPACE + emotion);
    }
  }

}
