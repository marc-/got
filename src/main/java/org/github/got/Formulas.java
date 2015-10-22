package org.github.got;

public final class Formulas {
  public static final int HUNDRED_PERCENT = 100;
  public static final int CRIT_CHANCE = 5;
  public static final int CHANCE_TO_BE_ATTACKED_BY_CALM_MOB = 20;
  public static final int CHANCE_TO_BE_ATTACKED_BY_ANGRY_MOB = 70;
  public static final int MIN_EXP = 20;
  private static final float DEFAULT_DMG_MOD = 1.5f;
  private static final float CRIT_MOD = 1.5f;

  private Formulas() {
  }

  public static int fleeChance(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel()) * 10 + 50;
  }

  public static boolean fled(final Entity target, final Entity player) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) >= fleeChance(player, target);
  }

  public static int missChance(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel()) * 15 + 20;
  }

  public static boolean missed(final Entity source, final Entity target) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) >= missChance(source, target);
  }

  public static boolean angryModAttack() {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= CHANCE_TO_BE_ATTACKED_BY_ANGRY_MOB;
  }

  public static boolean calmMobAttack() {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= HUNDRED_PERCENT - CHANCE_TO_BE_ATTACKED_BY_CALM_MOB;
  }

  public static int questItemsInLoot() {
    return RandomUtil.nextInt(4) + 1;
  }

  public static int lootCoins(final Entity entity) {
    return (int) (entity.getLevel() * 1.5f);
  }

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

  public static int critChance(final Entity source, final Entity target) {
    return 5 + (target.getLevel() - source.getLevel()) * 10;
  }

  public static boolean crit(final Entity source, final Entity target) {
    return RandomUtil.nextInt(HUNDRED_PERCENT) <= critChance(source, target);
  }

  public static int killExperience(final Entity source, final Entity target) {
    return (target.getLevel() - source.getLevel() + 2) * 10;
  }

  public static int maxMana(final int intellect) {
    return intellect * 5;
  }

  public static int maxHealth(final int stamina) {
    return stamina * 5;
  }

  public static int experienceToLevelup(final int level) {
    return MIN_EXP * level;
  }

}
