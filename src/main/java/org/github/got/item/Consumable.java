package org.github.got.item;

import org.github.got.Entity;
import org.github.got.Item;
import org.github.got.Resources;

public class Consumable extends Item {

  public enum Effect {
    RESTORES_HEALH(Resources.getString("game.resource.health")),
    RESTORES_MANA(Resources.getString("game.resource.mana"));

    public void apply(final Entity entity, final Consumable consumable) {
      switch (this) {
      case RESTORES_MANA:
        entity.retoreMana(consumable.getValue());
        break;
      default:
        entity.retoreHealth(consumable.getValue());
        break;
      }
    }

    public final String value;

    private Effect(final String resource) {
      value = resource;
    }

  }

  private final Effect effect;

  public Effect getEffect() {
    return effect;
  }

  private final int value;

  public Consumable(final String name, final Effect effect, final int value, final int price) {
    super(name, price);
    this.effect = effect;
    this.value = value;
  }

  protected int getValue() {
    return value;
  }

}
