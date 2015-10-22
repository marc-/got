package org.github.got;

import static org.github.got.TextUtil.center;

import java.util.Scanner;

import org.github.got.commands.AbstractCommand;
import org.github.got.commands.AcceptQuestCommand;
import org.github.got.commands.AttackCommand;
import org.github.got.commands.BuyCommand;
import org.github.got.commands.ByeCommand;
import org.github.got.commands.CastCommand;
import org.github.got.commands.CompleteQuestCommand;
import org.github.got.commands.EmotionCommand;
import org.github.got.commands.Engine;
import org.github.got.commands.GoCommand;
import org.github.got.commands.HelpCommand;
import org.github.got.commands.InventoryCommand;
import org.github.got.commands.JournalCommand;
import org.github.got.commands.ListItemsCommand;
import org.github.got.commands.ListQuestsCommand;
import org.github.got.commands.ListSpellsCommand;
import org.github.got.commands.LookAroundCommand;
import org.github.got.commands.NewGameCommand;
import org.github.got.commands.ResumeGameCommand;
import org.github.got.commands.SaveGameCommand;
import org.github.got.commands.SellCommand;
import org.github.got.commands.StatsCommand;
import org.github.got.commands.TalkCommand;
import org.github.got.commands.UseCommand;
import org.github.got.commands.ViewItemCommand;
import org.github.got.commands.ViewQuestCommand;

public final class Game {

  private static boolean inProgress = false;
  private boolean quit = false;
  private Scanner scanner;

  private static Engine ENGINE;

  public static void main(final String[] args) {
    final Game game = new Game();
    game.start();
  }

  public Game() {
    Engine.registerCommand(new InitCommand());
    Engine.registerCommand(new ForceQuitCommand());
    Engine.registerCommand(new QuitCommand());
    Engine.registerCommand(new AcceptQuestCommand());
    Engine.registerCommand(new AttackCommand());
    Engine.registerCommand(new BuyCommand());
    Engine.registerCommand(new ByeCommand());
    Engine.registerCommand(new CastCommand());
    Engine.registerCommand(new CompleteQuestCommand());
    Engine.registerCommand(new EmotionCommand());
    Engine.registerCommand(new GoCommand());
    Engine.registerCommand(new HelpCommand());
    Engine.registerCommand(new InventoryCommand());
    Engine.registerCommand(new JournalCommand());
    Engine.registerCommand(new ListItemsCommand());
    Engine.registerCommand(new ListQuestsCommand());
    Engine.registerCommand(new ListSpellsCommand());
    Engine.registerCommand(new LookAroundCommand());
    Engine.registerCommand(new NewGameCommand());
    Engine.registerCommand(new ResumeGameCommand());
    Engine.registerCommand(new SaveGameCommand());
    Engine.registerCommand(new SellCommand());
    Engine.registerCommand(new StatsCommand());
    Engine.registerCommand(new TalkCommand());
    Engine.registerCommand(new UseCommand());
    Engine.registerCommand(new ViewItemCommand());
    Engine.registerCommand(new ViewQuestCommand());
  }

  private void start() {
    try (final Scanner s = new Scanner(System.in, "utf8")) {
      scanner = s;
      ENGINE = new Engine(System.out, scanner);
      ENGINE.init();
      while (!quit) {
        ENGINE.process(scanner.nextLine());
      }
    }
  }

  @CommandA(value = { Command.INIT }, runOnce = true)
  private static class InitCommand extends AbstractCommand {

    @Override
    public void issue(final Context context) {
      clearScreen(context);
      printCaption(context);
      systemRaw(context, center(Resources.gameName()));
      systemRaw(context, center(Resources.gameDescription()));
      system(context, "game.tip.system.new");
      system(context, "game.tip.system.resume");
      system(context, "game.tip.system.quit");
      system(context, "game.tip.system.help");
    }

  }

  @CommandA(value = { "quit", "q" })
  private class QuitCommand extends AbstractCommand {

    @Override
    public void issue(final Context context) {
      quit = yesno(context, "game.system.quit_confirmation");
    }

  }

  @CommandA({ "q!" })
  private class ForceQuitCommand implements Command {

    @Override
    public void issue(final Context context) {
      quit = true;
    }

  }

  public static World getWorld() {
    return ENGINE.getWorld();
  }

  /* For testing. */ static void setEngine(final Engine engine) {
    ENGINE = engine;
  }

  public static boolean isInProgress() {
    return inProgress;
  }

  public static void setInProgress(final boolean inProgress) {
    Game.inProgress = inProgress;
  }

}
