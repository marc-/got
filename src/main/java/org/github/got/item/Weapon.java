package org.github.got.item;

import org.github.got.Entity;
import org.github.got.Item;
import org.github.got.entity.Clazz;

public class Weapon extends Item {

  private final Clazz clazz;
  private final int damage;
  private final int stamina;
  private final int strength;
  private final int agility;
  private final int intellect;

  public Weapon(final String name, final int price, final Clazz clazz, final int damage, final int stamina,
      final int strength, final int agility, final int intellect) {
    super(name, price);
    this.clazz = clazz;
    this.damage = damage;
    this.stamina = stamina;
    this.strength = strength;
    this.agility = agility;
    this.intellect = intellect;
  }

  public Clazz getClazz() {
    return clazz;
  }

  public int getDamage() {
    return damage;
  }

  public int getStamina() {
    return stamina;
  }

  public int getStrength() {
    return strength;
  }

  public int getAgility() {
    return agility;
  }

  public int getIntellect() {
    return intellect;
  }

  public boolean canWear(final Entity player) {
    return clazz.equals(player.getClazz());
  }
}
