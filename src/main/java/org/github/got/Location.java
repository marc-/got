package org.github.got;

import static org.github.got.TextUtil.NL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.github.got.Entity.Attitude;
import org.github.got.commands.Engine;
import org.github.got.entity.Clazz;
import org.github.got.entity.Mob;
import org.github.got.entity.Mob.Animal;
import org.github.got.entity.Mob.Humanoid;
import org.github.got.entity.Mob.Mood;

public class Location {

  static final int MAX_MONSTERS_PER_LOCATION = 7;

  public static enum Type {
    TOWN(Resources.getString("game.location.town.name"), Resources.getString("game.location.town.description")),
    DUNGEON(Resources.getString("game.location.dungeon.name"),
        Resources.getString("game.location.dungeon.description")),
    FOREST(Resources.getString("game.location.forest.name"), Resources.getString("game.location.forest.description")),
    FIELD(Resources.getString("game.location.field.name"), Resources.getString("game.location.field.description"));
    public final String value;
    public final String description;

    private Type(final String name, final String description) {
      value = name;
      this.description = description;
    }
  }

  protected String description;
  protected String name;
  protected int level = 0;
  private Location west;
  private Location east;
  private Location north;
  private Location south;
  private final Type type;

  protected List<Entity> creatures;

  public List<Entity> getCreatures() {
    return creatures;
  }

  protected Location(final Type type) {
    this.type = type;
    name = type.value;
    description = type.description;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public Type getType() {
    return type;
  }

  public Location getWest() {
    return west;
  }

  public Location getEast() {
    return east;
  }

  public Location getNorth() {
    return north;
  }

  public Location getSouth() {
    return south;
  }

  public static Location generate(final int level, final Location west, final Location east, final Location north,
      final Location south) {
    final Location location = new Location(Type.values()[RandomUtil.nextInt(1, Type.values().length)]);
    location.level = level;
    connect(location, RandomUtil.nextBoolean() ? null : west, RandomUtil.nextBoolean() ? null : east,
        RandomUtil.nextBoolean() ? null : north, RandomUtil.nextBoolean() ? null : south);
    populateLocation(location);
    return location;
  }

  public static void populateLocation(final Location location) {
    final int monsters_num = RandomUtil.nextInt(MAX_MONSTERS_PER_LOCATION);
    location.creatures = new ArrayList<>(monsters_num);
    for (int i = 0; i < monsters_num; i++) {
      Attitude affection = Attitude.NEUTRAL;
      Clazz clazz = Clazz.WARRIOR;
      int level = 1;
      if (location.level > 1) {
        affection = RandomUtil.nextBoolean() ? Attitude.HOSTILE : affection;
        clazz = Clazz.random();
        level = RandomUtil.nextInt(location.level - 1, location.level + 1);
      }
      location.creatures.add(randomMod(level, clazz, affection));
    }
  }

  private static Mob randomMod(final int level, final Clazz clazz, final Attitude affection) {
    if (RandomUtil.nextBoolean()) {
      return new Humanoid(TextUtil.mobHumanoidName(), level, TextUtil.gender(), clazz, affection, Mood.random(),
          Ability.byClass(clazz));
    } else {
      return new Animal(TextUtil.mobAnimalName(), level, clazz, affection, Mood.random(), Ability.byClass(clazz));
    }
  }

  public String describe() {
    return Resources.getString(Resources.GAME_DESCRIBE_LOCATION, describe(this),
        creatures.stream().map(c -> c.getAttitude().messageType().wrap(c.describe())).collect(Collectors.joining(NL)),
        describe(west), describe(east), describe(north), describe(south));
  }

  public Entity lookup(final String string) {
    return Engine.lookup(string, creatures);
  }

  private static String describe(final Location location) {
    if (location == null) {
      return Resources.getString(Resources.GAME_DESCRIBE_LOCATION_SHORT_NO_PASSAGE);
    }
    return Resources.getString(Resources.GAME_DESCRIBE_LOCATION_SHORT, TextUtil.capitalize(location.name),
        location.description, location.level);
  }

  public void repopulate() {
    populateLocation(this);
  }

  public static void connect(final Location location, final Location west, final Location east, final Location north,
      final Location south) {
    if (west != null) {
      west.east = location;
      location.west = west;
    }
    if (east != null) {
      east.west = location;
      location.east = east;
    }
    if (north != null) {
      north.south = location;
      location.north = north;
    }
    if (south != null) {
      south.north = location;
      location.south = south;
    }
  }

  public void removeCreature(final Entity entity) {
    creatures.remove(entity);
  }

}
