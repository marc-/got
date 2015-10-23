package org.github.got;

/**
 * Utility class to calculate chances and entties parameters.
 *
 * @author Maksim Chizhov
 */
public final class Formulas {
  public static final int HUNDRED_PERCENT = 100;
  public static final int CRIT_CHANCE = 5;
  public static final int CHANCE_TO_BE_ATTACKED_BY_CALM_MOB = 20;
  public static final int CHANCE_TO_BE_ATTACKED_BY_ANGRY_MOB = 70;
  /**
   * Used to calculate amount of experience required to get next level.
   */
  public static final int MIN_EXP = 20;
  /**
   * Default stat modificator. Applies on raw str, agi, int to get base
   * character damage.
   */
  private static final float DEFAULT_DMG_MOD = 1.5f;
  /**
   * Critical damage bonus.
   */
  private static final float CRIT_MOD = 1.5f;
  /**
   * 
   */
  static final int MAX_MONSTERS_PER_LOCATION = 7;
  static int WORLD_MAX_WIDTH = 10;
  static int WORLD_MIN_WIDTH = 3;
  static int WORLD_MAX_HEIGHT = 10;
  static int WORLD_MIN_HEIGHT = 3;

  private Formulas() {
  }

  /**
   * Chance to leave combat before it ends.
   *
   * @param source
   *          player
   * @param target
   *          hostile
   * @return
   */
  public static int fleeChance(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel()) * 10 + 50;
  }

  /**
   * Roll for flee from battle.
   *
   * @param player
   * @param target
   *
   * @return
   */
  public static boolean fled(final Entity player, final Entity target) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) >= fleeChance(player, target);
  }

  /**
   * Chance to miss with spell or ability.
   *
   * @param source
   * @param target
   * @return
   */
  public static int missChance(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel()) * 15 + 20;
  }

  /**
   * Roll for miss.
   *
   * @param source
   * @param target
   * @return
   */
  public static boolean missed(final Entity source, final Entity target) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) >= missChance(source, target);
  }

  /**
   * Chance to hostile angry mob to attack player who attempts to leave
   * location.
   *
   * @return
   */
  public static boolean angryModAttack() {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= CHANCE_TO_BE_ATTACKED_BY_ANGRY_MOB;
  }

  /**
   * Chance to hostile calm mob to attack player who attempts to leave location.
   *
   * @return
   */
  public static boolean calmMobAttack() {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= HUNDRED_PERCENT - CHANCE_TO_BE_ATTACKED_BY_CALM_MOB;
  }

  /**
   * Chance to loot a quest item when kill enemy.
   *
   * @return
   */
  public static int questItemsInLoot() {
    return RandomUtil.nextInt(4) + 1;
  }

  /**
   * Amount of coins in mobs drop.
   *
   * @param entity
   * @return
   */
  public static int lootCoins(final Entity entity) {
    return (int) (entity.getLevel() * 1.5f);
  }

  /**
   * Calculates damage base on character characteristics. Assumes, that only one
   * stat (str, agi, int) can have non zero value.
   *
   * @param source
   * @param target
   * @param ability
   * @param crit
   * @return
   */
  public static int damage(final Entity source, final Entity target, final Ability ability, final boolean crit) {
    float critMod = 1f;
    if (crit) {
      critMod = CRIT_MOD;
    }
    return (int) (((float) source.getLevel() / (float) target.getLevel() * ability.getBaseValue()
        + (int) (ability.getDamageModificatorStrength() * source.getStrength())
        + (int) (ability.getDamageModificatorAgility() * source.getAgility())
        + (int) (ability.getDamageModificatorIntellect() * source.getIntellect())) * DEFAULT_DMG_MOD * critMod);
  }

  /**
   * Chance to critically hit target.
   *
   * @param source
   * @param target
   * @return
   */
  public static int critChance(final Entity source, final Entity target) {
    return 5 + (target.getLevel() - source.getLevel()) * 10;
  }

  /**
   * Roll for crit.
   *
   * @param source
   * @param target
   * @return
   */
  public static boolean crit(final Entity source, final Entity target) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= critChance(source, target);
  }

  /**
   * Experience given for mob kill. Depends on player and mob level difference.
   * Lower level mob awards less experience.
   *
   * @param source
   * @param target
   * @return
   */
  public static int killExperience(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel() + 2) * 10;
  }

  /**
   * Maximum mana point based on intellect.
   *
   * @param intellect
   * @return
   */
  public static int maxMana(final int intellect) {
    return intellect * 5;
  }

  /**
   * Maximum health points base on stamina.
   *
   * @param stamina
   * @return
   */
  public static int maxHealth(final int stamina) {
    return stamina * 5;
  }

  /**
   * Calculates total experience required to gain next level.
   *
   * @param level
   * @return
   */
  public static int experienceToLevelup(final int level) {
    return MIN_EXP * level;
  }

}
