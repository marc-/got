package org.github.got;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.github.got.location.Town;

public class World {
  private static int MAX_WIDTH = 10;
  private static int MIN_WIDTH = 3;
  private static int MAX_HEIGHT = 10;
  private static int MIN_HEIGHT = 3;
  private final Town home;
  private Location location;
  private final Queue<Location> visitedLocations = new LinkedList<>();
  private static final HashMap<String, Item> ITEMS = new LinkedHashMap<>();
  private static final HashMap<String, Quest> QUESTS = new LinkedHashMap<>();

  private World(final Town home) {
    location = this.home = home;
  }

  public Location getLocation() {
    return location;
  }

  public Town getHome() {
    return home;
  }

  public static World generate() {
    final int width = RandomUtil.nextInt(MAX_WIDTH - MIN_WIDTH) + MIN_WIDTH;
    final int height = RandomUtil.nextInt(MAX_HEIGHT - MIN_WIDTH) + MIN_HEIGHT;
    final List<Location> locations = new ArrayList<>(width + height);
    Town home = null;
    int lvl = 1;
    Location location = null;
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        final Location prev = location;
        final Location north = j == 0 ? null : prev;
        final Location east = i == 0 ? null : locations.get((i - 1) * height + j);
        if (i == width / 2 && j == height / 2) {
          location = home = new Town(null, east, north, null);
        } else {
          location = Location.generate(lvl, null, east, north, null);
          lvl = RandomUtil.nextInt(Math.abs(i + j - (width + height) / 2) + 1) + 1;
        }
        if (north != null && !verifyConnected(north)) {
          Location.connect(north, null, null, null, location);
        }
        if (east != null && !verifyConnected(east)) {
          Location.connect(east, location, null, null, null);
        }
        locations.add(location);
      }
    }
    return new World(home);
  }

  /* For testing. */ static boolean verifyConnected(final Location location) {
    return !(location.getWest() == null && location.getEast() == null && location.getNorth() == null
        && location.getSouth() == null);
  }

  public static void registerItem(final Item item) {
    ITEMS.put(item.getNameLookup(), item);
  }

  public static void registerQuest(final Quest quest) {
    QUESTS.put(quest.getNameLookup(), quest);
  }

  public String describe() {
    return Resources.getString("game.describe.world");
  }

  public Quest questLookup(final String name) {
    return QUESTS.get(name.trim().toLowerCase());
  }

  public Item itemLookup(final String name) {
    return ITEMS.get(name.trim().toLowerCase());
  }

  public Queue<Location> getVisitedLocations() {
    return visitedLocations;
  }

  public void setLocation(final Location location) {
    this.location = location;
  }

}
