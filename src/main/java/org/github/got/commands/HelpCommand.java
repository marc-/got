package org.github.got.commands;

import org.github.got.Command;
import org.github.got.CommandA;
import org.github.got.Context;

@CommandA({ "help", "h" })
public class HelpCommand extends AbstractCommand implements Command {

  static {
    Engine.registerCommand(new GameHelpCommand());
    Engine.registerCommand(new ConversationHelpCommand());
    Engine.registerCommand(new CombatHelpCommand());
  }

  @Override
  public void issue(final Context context) {
    system(context, "game.help.system.caption");
    system(context, "game.help.system.new");
    system(context, "game.help.system.resume");
    system(context, "game.help.system.quit");
    system(context, "game.help.system.help");
    system(context, "game.help.system.help_game");
    system(context, "game.help.system.help_conversation");
  }

  @CommandA({ "help game", "hg" })
  /* For testing. */static class GameHelpCommand extends AbstractCommand {
    @Override
    public void issue(final Context context) {
      system(context, "game.help.actions.game.caption");
      system(context, "game.help.actions.game.west");
      system(context, "game.help.actions.game.east");
      system(context, "game.help.actions.game.north");
      system(context, "game.help.actions.game.south");
      system(context, "game.help.actions.game.look");
      system(context, "game.help.actions.game.inventory");
      system(context, "game.help.actions.game.journal");
      system(context, "game.help.actions.game.stats");
      system(context, "game.help.actions.game.talk");
      system(context, "game.help.actions.game.view_quest");
      system(context, "game.help.actions.game.view_item");
      system(context, "game.help.actions.game.emotion");
      system(context, "game.help.actions.game.attack");
      system(context, "game.help.actions.game.use");
    }
  }

  @CommandA({ "help conversation", "help conv", "hc" })
  /* For testing. */static class ConversationHelpCommand extends AbstractCommand {
    @Override
    public void issue(final Context context) {
      system(context, "game.help.actions.conversation.caption");
      system(context, "game.help.actions.conversation.list_quests");
      system(context, "game.help.actions.conversation.list_items");
      system(context, "game.help.actions.conversation.accept_quest");
      system(context, "game.help.actions.conversation.buy_item");
      system(context, "game.help.actions.conversation.sell_item");
      system(context, "game.help.actions.conversation.bye");
    }
  }

  @CommandA({ "help battle", "hb" })
  /* For testing. */static class CombatHelpCommand extends AbstractCommand {
    @Override
    public void issue(final Context context) {
      system(context, "game.help.actions.combat.caption");
      system(context, "game.help.actions.combat.cast");
    }
  }

}
