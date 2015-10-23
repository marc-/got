package org.github.got.entity;

import static org.github.got.Resources.GAME_DESCRIBE_MOB;
import static org.github.got.Resources.GAME_DESCRIBE_MOB_MOOD;
import static org.github.got.Resources.GAME_MOOD_ANGRY;
import static org.github.got.Resources.GAME_MOOD_CALM;
import static org.github.got.TextUtil.capitalize;

import java.util.List;

import org.github.got.Ability;
import org.github.got.Entity;
import org.github.got.RandomUtil;
import org.github.got.Resources;

/**
 * Creature to fight with.
 *
 * @author Maksim Chizhov
 */
public abstract class Mob extends Entity {

  /**
   * Mob's mood. Depending on this, mob will attack player or ignore while
   * travel across the world.
   */
  public enum Mood {
    ANGRY(Resources.getString(GAME_MOOD_ANGRY)),
    CALM(Resources.getString(GAME_MOOD_CALM));

    public final String description;

    public final String mood = name().toLowerCase();

    private Mood(final String description) {
      this.description = description;
    }

    public static Mood random() {
      return RandomUtil.randomObject(Mood.values());
    }

  }

  private final Mood mood;

  public Mob(final String name, final int level, final String race, final String gender, final Clazz clazz,
      final Attitude attitude, final Mood mood, final List<Ability> abilities) {
    super(name, level, race, gender, clazz, attitude, abilities);
    this.mood = mood;
  }

  @Override
  public String describe() {
    return Resources.getString(GAME_DESCRIBE_MOB, super.describe(), getLevel(),
        Attitude.HOSTILE.equals(getAttitude())
        ? Resources.getString(GAME_DESCRIBE_MOB_MOOD, getMood().mood, getMood().description)
            : capitalize(getAttitude().toString()));
  }

  public String describeBreifly() {
    return Resources.getString(GAME_DESCRIBE_MOB, super.describe(), getLevel(), capitalize(getMood().mood));
  }

  public Ability getAbility() {
    if (getAbilities().isEmpty()) {
      return null;
    }
    return getAbilities().iterator().next();
  }

  public Mood getMood() {
    return mood;
  }

  /**
   * Animal mob. Don't gave gender.
   *
   */
  public static class Animal extends Mob {

    public Animal(final String name, final int level, final Clazz clazz, final Attitude attitude, final Mood mood,
        final List<Ability> abilities) {
      super(name, level, null, null, clazz, attitude, mood, abilities);
    }

  }

  public static class Humanoid extends Mob {

    public Humanoid(final String name, final int level, final String gender, final Clazz clazz, final Attitude attitude,
        final Mood mood, final List<Ability> abilities) {
      super(name, level, null, gender, clazz, attitude, mood, abilities);
    }

  }
}
