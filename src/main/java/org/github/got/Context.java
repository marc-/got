package org.github.got;

import java.io.PrintStream;
import java.util.Scanner;

import org.github.got.entity.Player;

public interface Context {

  public enum Scope {
    NONE,
    MENU,
    GLOBAL,
    INGAME,
    TOWN,
    LOCATION,
    TALK,
    COMBAT,
  }

  public PrintStream getPrintStream();

  public World getWorld();

  public Player getPlayer();

  public String getCommand();

  public void setWorld(World world);

  public void setPlayer(Player player);

  public Entity getTarget();

  public void setTarget(Entity entity);

  public void setResult(boolean result);

  public boolean succeed();

  public Scanner getScanner();

  public Scope getScope();

  public void setScope(Scope scope);


}
