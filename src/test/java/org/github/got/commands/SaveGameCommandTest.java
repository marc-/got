package org.github.got.commands;

import static org.github.got.Resources.GAME_SYSTEM_SAVED;
import static org.github.got.Resources.getString;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.github.got.Context;
import org.github.got.Item;
import org.github.got.MessageType;
import org.github.got.Quest;
import org.github.got.TestHelper;
import org.github.got.World;
import org.github.got.entity.Clazz;
import org.github.got.item.ItemStack;
import org.junit.Before;
import org.junit.Test;

public class SaveGameCommandTest {

  private static final String SAVE_FILE = "target/1.save";

  @Test
  public void testIssue() throws FileNotFoundException, IOException, ClassNotFoundException {
    final PrintStream printStream = mock(PrintStream.class);
    final Context context = mock(Context.class);
    when(context.getPrintStream()).thenReturn(printStream);
    final TestHelper.PlayerExt player1 = new TestHelper.PlayerExt(TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(),
        Clazz.HUNTER);
    player1.getQuests().add(new Quest(TestHelper.uuid(), 5, TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(),
        TestHelper.uuid(), null, null, 1));
    final String uuid = TestHelper.uuid();
    new Quest(uuid, 5, TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(), TestHelper.uuid(), null, null, 1);
    player1.getInventory().add(new ItemStack(new Item(TestHelper.uuid(), 15), 10));
    player1.getCompletedQuests().add(uuid);
    when(context.getPlayer()).thenReturn(player1);
    final SaveGameCommand saveGameCommand = spy(new SaveGameCommand());
    when(saveGameCommand.saveFilePath()).thenReturn(SAVE_FILE);
    saveGameCommand.issue(context);
    verify(printStream, times(1)).println(eq(MessageType.SYSTEM.wrap(getString(GAME_SYSTEM_SAVED))));
    final Path path = Paths.get(SAVE_FILE);
    assertTrue(Files.exists(path));

    try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(path.toFile()));) {
      final TestHelper.PlayerExt player2 = (TestHelper.PlayerExt) out.readObject();
      assertNotNull(player2);
      TestHelper.assertPlayers(player1, player2);
    }
  }

  @Before
  public void before() {
    final Engine mock = mock(Engine.class);
    final World generate = World.generate();
    when(mock.getWorld()).thenReturn(generate);
    TestHelper.setEngine(mock);
  }
}
