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

/**
 * Describes worlds location. User can move between location, fight mobs, talk
 * to NPC.
 *
 * @author Maksim Chizhov
 */
public class Location {

  /**
   * Location types. Currently there are only two type of location Town and
   * everything around. This structure just give some diversity for the game
   * world.
   *
   * @author Maksim Chizhov
   */
  public static enum Type {
    /**
     * Start point for every new and saved game. Has no hostiles. Oracle and
     * vendor are located here.
     */
    TOWN(Resources.getString("game.location.town.name"), Resources.getString("game.location.town.description")),
    DUNGEON(Resources.getString("game.location.dungeon.name"),
        Resources.getString("game.location.dungeon.description")),
    FOREST(Resources.getString("game.location.forest.name"), Resources.getString("game.location.forest.description")),
    FIELD(Resources.getString("game.location.field.name"), Resources.getString("game.location.field.description"));
    /**
     * Used in interactive output.
     */
    public final String value;
    public final String description;

    private Type(final String name, final String description) {
      value = name;
      this.description = description;
    }
  }

  protected String description;
  protected String name;
  /**
   * Location level. Used for minimum and maximum mobs level occupying location.
   * Town always has 0.
   */
  protected int level = 0;
  /**
   * Location to west of current.
   */
  private Location west;
  /**
   * Location to east of current.
   */
  private Location east;
  /**
   * Location to north of current.
   */
  private Location north;
  /**
   * Location to south of current.
   */
  private Location south;
  private final Type type;

  /**
   * Creatures populating location.
   */
  protected List<Entity> creatures;

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

  /**
   * Generates location with random level, creatures and connections to other
   * locations.
   *
   * @param level
   * @param west
   * @param east
   * @param north
   * @param south
   * @return
   */
  public static Location generate(final int level, final Location west, final Location east, final Location north,
      final Location south) {
    final Location location = new Location(Type.values()[RandomUtil.nextInt(1, Type.values().length)]);
    location.level = level;
    connect(location, RandomUtil.nextBoolean() ? null : west, RandomUtil.nextBoolean() ? null : east,
        RandomUtil.nextBoolean() ? null : north, RandomUtil.nextBoolean() ? null : south);
    populateLocation(location);
    return location;
  }

  /**
   * Randomize population. Location level is considered as minumum and maximum
   * edge for mobs levels. Location with level 1 never has hostiles. It safe to
   * travel there.
   *
   * @param location
   */
  public static void populateLocation(final Location location) {
    final int monsters_num = RandomUtil.nextInt(Formulas.MAX_MONSTERS_PER_LOCATION);
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

  /**
   * Generate random mob using provided level, clazz and affection.
   *
   * @param level
   * @param clazz
   * @param affection
   * @return
   */
  private static Mob randomMod(final int level, final Clazz clazz, final Attitude affection) {
    if (RandomUtil.nextBoolean()) {
      return new Humanoid(TextUtil.mobHumanoidName(), level, TextUtil.gender(), clazz, affection, Mood.random(),
          Ability.byClass(clazz));
    } else {
      return new Animal(TextUtil.mobAnimalName(), level, clazz, affection, Mood.random(), Ability.byClass(clazz));
    }
  }

  /**
   * Describes location including connected locations and occupants.
   *
   * @return
   */
  public String describe() {
    return Resources.getString(Resources.GAME_DESCRIBE_LOCATION, describe(this),
        creatures.stream().map(c -> c.getAttitude().messageType().wrap(c.describe())).collect(Collectors.joining(NL)),
        describe(west), describe(east), describe(north), describe(south));
  }

  /**
   * Looks up for creature in location.
   *
   * @param nameOrIndex
   *          name or index (starting from 1)
   * @return
   */
  public Entity lookup(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, creatures);
  }

  /**
   * Give brief location description. Handles null as dead end.
   *
   * @param location
   * @return
   */
  private static String describe(final Location location) {
    if (location == null) {
      return Resources.getString(Resources.GAME_DESCRIBE_LOCATION_SHORT_NO_PASSAGE);
    }
    return Resources.getString(Resources.GAME_DESCRIBE_LOCATION_SHORT, TextUtil.capitalize(location.name),
        location.description, location.level);
  }

  /**
   * Resets population..
   */
  public void repopulate() {
    populateLocation(this);
  }

  /**
   * Connects locations to each other.
   *
   * @param location
   * @param west
   * @param east
   * @param north
   * @param south
   */
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

  public List<Entity> getCreatures() {
    return creatures;
  }

  public void removeCreature(final Entity entity) {
    creatures.remove(entity);
  }

}
