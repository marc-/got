package org.github.got;

import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AbilityTest {

  @Test
  public void testDescribeMelee() {
    final Ability meleeAbilty = Ability.newMeleeAbility(EMPTY, 0, 0);
    assertNotNull(meleeAbilty);
    assertNotNull(meleeAbilty.describe());
  }

  @Test
  public void testDescribeRanged() {
    final Ability rangedAbilty = Ability.newRangedAbility(EMPTY, 0, 0);
    assertNotNull(rangedAbilty);
    assertNotNull(rangedAbilty.describe());
  }

  @Test
  public void testDescribeSpell() {
    final Ability spell = Ability.newSpell(EMPTY, 0, 0, 0);
    assertNotNull(spell);
    assertNotNull(spell.describe());

  }

}
