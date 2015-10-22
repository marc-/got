package org.github.got.commands;

import static org.github.got.Resources.CLEAR_SCREEN;
import static org.github.got.Resources.ERASE_SEQUENCE;
import static org.github.got.Resources.GAME_SYSTEM_LOADED;
import static org.github.got.Resources.RESET_COLOR;
import static org.github.got.Resources.backgroundColor;
import static org.github.got.Resources.getString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import org.github.got.Context;
import org.github.got.Game;
import org.github.got.Item;
import org.github.got.MessageType;
import org.github.got.Quest;
import org.github.got.TestHelper;
import org.github.got.TestHelper.PlayerExt;
import org.github.got.World;
import org.github.got.entity.Clazz;
import org.github.got.entity.Player;
import org.github.got.item.ItemStack;
import org.junit.Before;
import org.junit.Test;

public class ResumeGameCommandTest {

  private static final String SAVE_FILE = "target/2.save";
  private PlayerExt player1;

  @Test
  public void testIssue() {

    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    doAnswer(invocation -> {
      final PlayerExt player2 = (PlayerExt) invocation.getArguments()[0];
      TestHelper.assertPlayers(player1, player2);
      return null;
    }).when(context).setPlayer(any(Player.class));
    final ResumeGameCommand resumeGameCommand = spy(new ResumeGameCommand());
    when(resumeGameCommand.saveFilePath()).thenReturn(SAVE_FILE);
    resumeGameCommand.issue(context);
    verify(printStream, times(1))
    .println(eq(backgroundColor() + CLEAR_SCREEN + RESET_COLOR + ERASE_SEQUENCE + backgroundColor()));
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_LOADED))));
    verify(context, times(1)).setWorld(any());

  }

  @Before
  public void before() throws FileNotFoundException, IOException {
    Game.setInProgress(false);
    final Engine engine = mock(Engine.class);
    final World generate = World.generate();
    when(engine.getWorld()).thenReturn(generate);
    TestHelper.setEngine(engine);

    player1 = new PlayerExt(TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(), Clazz.MAGE);
    player1.getQuests().add(new Quest(TestHelper.uuid(), 5, TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(),
        TestHelper.uuid(), null, null, 1));
    final String uuid = TestHelper.uuid();
    new Quest(uuid, 5, TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(), null, null, 1);
    player1.getInventory().add(new ItemStack(new Item(TestHelper.uuid(), 15), 10));

    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE));) {
      out.writeObject(player1);
    }
  }

}
