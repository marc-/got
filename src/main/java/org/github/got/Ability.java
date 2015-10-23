package org.github.got;

import static org.github.got.Resources.GAME_DESCRIBE_ABILITY;

import java.util.Collections;
import java.util.List;

import org.github.got.entity.Clazz;

/**
 * Class describes combat abilities and spells.
 *
 * @author Maksim Chizhov
 */
public class Ability implements Searchable {
  /**
   * Ability/spell type.
   */
  public static enum Type {
    MELEE,
    RANGED,
    SPELL;

    public final String value = toString().toLowerCase();

    /**
     * Every game class can only use one of the type accordingly.
     *
     * @param clazz
     *          mob or player class
     * @return type
     */
    public static Type byClass(final Clazz clazz) {
      switch (clazz) {
      case HUNTER:
        return Type.RANGED;
      case MAGE:
        return Type.SPELL;
      default:
        return Type.MELEE;
      }
    }

  }

  public static final Ability HEROIC_STRIKE = newMeleeAbility("Heroic Strike", 4, 0.3f);
  public static final Ability AIMED_SHOT = newRangedAbility("Aimed Shot", 5, 0.4f);
  public static final Ability FIREBALL = newSpell("Fire Ball", 6, 0.4f, 10);

  private final String name;
  private final String nameLookup;
  private final Type type;
  /** Base damage(heal?) value. */
  private final int baseValue;
  /** Strength damage modificator value. */
  private final float damageModStrength;
  /** Agility damage modificator value. */
  private final float damageModAgility;
  /** Intellect damage modificator value. */
  private final float damageModIntellect;
  /** Spells spend mana. */
  private final int manaCost;

  public Ability(final String name, final int baseValue, final Type type, final float damageModStrength,
      final float damageModAgility, final float damageModIntellect, final int manaCost) {
    this.name = name;
    this.type = type;
    nameLookup = name.toLowerCase();
    this.baseValue = baseValue;
    this.damageModStrength = damageModStrength;
    this.damageModAgility = damageModAgility;
    this.damageModIntellect = damageModIntellect;
    this.manaCost = manaCost;
  }

  /**
   * Some predefined class based abilities.
   *
   * @param clazz
   *          player/mob class
   * @return list of available abilities
   */
  public static List<Ability> byClass(final Clazz clazz) {
    if (clazz == null) {
      return Collections.emptyList();
    }
    switch (clazz) {
    case HUNTER:
      return Collections.singletonList(AIMED_SHOT);
    case MAGE:
      return Collections.singletonList(FIREBALL);
    default:
      return Collections.singletonList(HEROIC_STRIKE);
    }

  }

  protected static Ability newMeleeAbility(final String name, final int baseValue, final float damageModStrength) {
    return new Ability(name, baseValue, Type.MELEE, damageModStrength, 0, 0, 0);
  }

  protected static Ability newRangedAbility(final String name, final int baseValue, final float damageModAgility) {
    return new Ability(name, baseValue, Type.RANGED, 0, damageModAgility, 0, 0);
  }

  protected static Ability newSpell(final String name, final int baseValue, final float damageModIntellect,
      final int cost) {
    return new Ability(name, baseValue, Type.SPELL, 0, 0, damageModIntellect, cost);
  }

  public String getName() {
    return name;
  }

  @Override
  public String getNameLookup() {
    return nameLookup;
  }

  public int getBaseValue() {
    return baseValue;
  }

  public float getDamageModificatorStrength() {
    return damageModStrength;
  }

  public float getDamageModificatorAgility() {
    return damageModAgility;
  }

  public float getDamageModificatorIntellect() {
    return damageModIntellect;
  }

  public Type getType() {
    return type;
  }

  public int getManaCost() {
    return manaCost;
  }

  /**
   * Describes ability/spell using template.
   *
   * @return formatted ability/spell info.
   */
  public String describe() {
    return Resources.getString(GAME_DESCRIBE_ABILITY, name, type.value, baseValue);
  }

}
