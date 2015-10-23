package org.github.got;

import static org.github.got.TextUtil.EMPTY;
import static org.github.got.TextUtil.NL;
import static org.github.got.TextUtil.collapseSpaces;
import static org.github.got.TextUtil.toEmpty;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.github.got.commands.Engine;
import org.github.got.entity.Clazz;
import org.github.got.entity.NonPlayerCharacter;

/**
 * Represent base game entity -- player, NPC, mobs.
 *
 * @author Maksim Chizhov
 */
public abstract class Entity implements Searchable {

  public static final Logger LOGGER = Logger.getLogger(NonPlayerCharacter.class.getPackage().getName());

  /**
   * Entity attitude towards player.
   */
  public enum Attitude {
    /**
     * Special attitude.
     */
    ORACLE,
    /**
     * Used for NPCs, like vendors.
     */
    FRIENDLY,
    /**
     * Neutral mobs, won't attack player.
     */
    NEUTRAL,
    /**
     * With some chance will attack player who tries to leave location.
     */
    HOSTILE;

    /**
     * Get associated {@link org.github.got.MessageType} to colorize output.
     *
     * @return messageType
     */
    public MessageType messageType() {
      return MessageType.valueOf(name());
    }
  }

  /**
   * Player or mob level. Used for various calculations (like chance to hit in
   * battle).
   */
  private int level;
  private String name;
  /**
   * Entity alias to make user input processing case insensitive.
   */
  private String nameLookup;
  /**
   * Just a fancy text near npc or player name in output. Does not affect
   * gameplay.
   */
  private String race;
  /**
   * Players are not limited on gender to be chosen. Feel free to put anything
   * in here. Does not affect gameplay.
   */
  private String gender;
  /**
   * In case character clazz. Stats, abilities and damage are calculated based
   * on it.
   */
  private Clazz clazz;
  private Attitude attitude;
  private final String description;
  /**
   * Displayed when conversation starts.
   */
  private final String welcomeMessage;
  /**
   * Displayed when conversation end.
   */
  private final String farewellMessage;

  private int stamina;
  private int strength;
  private int agility;
  private int intellect;

  /**
   * Calculated based on stamina value.
   */
  private int health;
  /**
   * Calculated based on intellect value.
   */
  private int mana;
  /**
   * Combat spells and abilities.
   */
  private List<Ability> abilities;

  public Entity(final String name, final String race, final String gender, final Clazz clazz, final Attitude affection,
      final List<Ability> abilities) {
    this(name, 1, race, gender, clazz, affection, EMPTY, EMPTY, EMPTY, abilities);
  }

  public Entity(final String name, final int level, final String race, final String gender, final Clazz clazz,
      final Attitude affection, final List<Ability> abilities) {
    this(name, level, race, gender, clazz, affection, EMPTY, EMPTY, EMPTY, abilities);
  }

  public Entity(final String name, final String race, final String gender, final Clazz clazz, final Attitude affection,
      final String description, final String welcomeMessage, final String farewellMessage) {
    this(name, 1, race, gender, clazz, affection, description, welcomeMessage, farewellMessage,
        Collections.emptyList());
  }

  public Entity(final String name, final int level, final String race, final String gender, final Clazz clazz,
      final Attitude attitude, final String description, final String welcomeMessage, final String farewellMessage,
      final List<Ability> abilities) {
    this.name = name;
    this.level = level;
    this.abilities = abilities;
    nameLookup = name.toLowerCase();
    this.race = race;
    this.gender = gender;
    this.clazz = clazz;
    if (clazz != null) {
      stamina = clazz.stamina * level;
      strength = clazz.strength * level;
      agility = clazz.agility * level;
      intellect = clazz.intellect * level;
      health = maxHealth();
      mana = maxMana();
    }
    this.description = description;
    this.welcomeMessage = welcomeMessage;
    this.farewellMessage = farewellMessage;
    this.attitude = attitude;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(final int level) {
    this.level = level;
  }

  public int getStamina() {
    return stamina;
  }

  public void setStamina(final int stamina) {
    this.stamina = stamina;
  }

  public int getStrength() {
    return strength;
  }

  public void setStrength(final int strength) {
    this.strength = strength;
  }

  public int getAgility() {
    return agility;
  }

  public void setAgility(final int agility) {
    this.agility = agility;
  }

  public int getIntellect() {
    return intellect;
  }

  public void setIntellect(final int intellect) {
    this.intellect = intellect;
  }

  public int getHealth() {
    return health;
  }

  public void setHealth(final int health) {
    this.health = health;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(final int mana) {
    this.mana = mana;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
    nameLookup = name.toLowerCase();
  }

  public String getRace() {
    return race;
  }

  public void setRace(final String race) {
    this.race = race;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(final String gender) {
    this.gender = gender;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public void setClazz(final Clazz clazz) {
    this.clazz = clazz;
  }

  public Attitude getAttitude() {
    return attitude;
  }

  public void setAttitude(final Attitude attitude) {
    this.attitude = attitude;
  }

  public String getDescription() {
    return description;
  }

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public String getFarewellMessage() {
    return farewellMessage;
  }

  @Override
  public String getNameLookup() {
    return nameLookup;
  }

  protected void addIntellect(final int intellect) {
    this.intellect += intellect;
  }

  protected void addAgility(final int agility) {
    this.agility += agility;
  }

  protected void addStrength(final int strength) {
    this.strength += strength;
  }

  protected void addStamina(final int stamina) {
    this.stamina += stamina;
  }

  protected void incrementLevel(final int i) {
    level += i;
  }

  public void reduceHealth(final int damage) {
    health = Math.max(health - damage, 0);
  }

  public void retoreHealth(final int amount) {
    health = Math.min(health + amount, maxHealth());
  }

  public void reduceMana(final int amount) {
    mana = Math.max(mana - amount, 0);
  }

  public void retoreMana(final int amount) {
    mana = Math.min(mana + amount, maxMana());
  }

  public String describe() {
    return collapseSpaces(Resources.getString(Resources.GAME_DESCRIBE_ENTITY, getName(), toEmpty(getRace()),
        toEmpty(getGender()), toEmpty(getClazz()), toEmpty(description)));
  }

  public String describeStats() {
    return Resources.getString(Resources.GAME_DESCRIBE_ENTITY_STATS, getLevel(), getStamina(), getStrength(),
        getAgility(), getIntellect(), getHealth(), getMana());
  }

  public String describeAbilities() {
    final String a = IntStream.range(0, abilities.size()).mapToObj(i -> {
      final Ability ability = abilities.get(i);
      return Resources.getString(Resources.GAME_DESCRIBE_ENTITY_ABILITIES_ITEM, i + 1, ability.describe());
    }).collect(Collectors.joining(NL));
    return a;
  }

  protected List<Ability> getAbilities() {
    return abilities;
  }

  /**
   * Looking for ability by lower case name or position in list from user input.
   *
   * @param nameOrIndex
   *          lower case spell/ability name or index (starting from 1) in list
   * @return null if noting is found
   */
  public Ability getAbility(final String nameOrIndex) {
    return Engine.lookup(nameOrIndex, abilities);
  }

  /**
   * Gets maximum mana point based on intellect value.
   *
   * @return maximum mana
   */
  protected int maxMana() {
    return Formulas.maxMana(intellect);
  }

  /**
   * Gets maximum health point based on stamina value.
   *
   * @return maximum mana
   */
  protected int maxHealth() {
    return Formulas.maxHealth(stamina);
  }

  public boolean isDead() {
    return getHealth() == 0;
  }

  /**
   * Used for user deserialization.
   *
   * @param abilities
   */
  protected void setAbilities(final List<Ability> abilities) {
    this.abilities = abilities;
  }

}
