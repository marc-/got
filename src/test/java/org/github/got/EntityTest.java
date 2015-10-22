package org.github.got;

import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.github.got.entity.Clazz;
import org.junit.Test;

public class EntityTest {

  public static Entity generateDummyEntity() {
    final Entity entity = new Entity(EMPTY, null, null, Clazz.WARRIOR, null, null) {
    };
    return entity;
  }

  @Test
  public final void testEntityStringStringStringClazzAttitudeListOfAbility() {
    new Entity(EMPTY, 0, null, null, null, null, null) {
    };
  }

  @Test
  public final void testEntityStringIntStringStringClazzAttitudeListOfAbility() {
    new Entity(EMPTY, 0, null, null, null, null, null, null, null, null) {
    };
  }

  @Test
  public final void testEntityStringStringStringClazzAttitudeStringStringString() {
    new Entity(EMPTY, null, null, null, null, null) {
    };
  }

  @Test
  public final void testEntityStringIntStringStringClazzAttitudeStringStringStringListOfAbility() {
    new Entity(EMPTY, null, null, null, null, null, null, null) {
    };
  }

  @Test
  public final void testGetNameLookup() {
    final Entity entity = new Entity("UPPERCASE", null, null, null, null, null) {
    };
    assertEquals("uppercase", entity.getNameLookup());
  }

  @Test
  public final void testAddIntellect() {
    final Entity entity = new Entity(EMPTY, null, null, Clazz.MAGE, null, null) {
    };
    final int expected = entity.getIntellect() + 1;
    entity.addIntellect(1);
    assertEquals(expected, entity.getIntellect());
  }

  @Test
  public final void testAddAgility() {
    final Entity entity = new Entity(EMPTY, null, null, Clazz.HUNTER, null, null) {
    };
    final int expected = entity.getAgility() + 1;
    entity.addAgility(1);
    assertEquals(expected, entity.getAgility());
  }

  @Test
  public final void testAddStrength() {
    final Entity entity = generateDummyEntity();
    final int expected = entity.getStrength() + 1;
    entity.addStrength(1);
    assertEquals(expected, entity.getStrength());
  }

  @Test
  public final void testAddStamina() {
    final Entity entity = generateDummyEntity();
    final int expected = entity.getStamina() + 1;
    entity.addStamina(1);
    assertEquals(expected, entity.getStamina());
  }

  @Test
  public final void testIncrementLevel() {
    final Entity entity = generateDummyEntity();
    final int expected = entity.getLevel() + 1;
    entity.incrementLevel(1);
    assertEquals(expected, entity.getLevel());
  }

  @Test
  public final void testDescribe() {
    final Entity entity = generateDummyEntity();
    assertNotNull(entity.describe());
  }

  @Test
  public final void testDescribeStats() {
    final Entity entity = generateDummyEntity();
    assertNotNull(entity.describeStats());
  }

  @Test
  public final void testDescribeAbilities() {
    final Entity entity = new Entity(EMPTY, null, null, Clazz.WARRIOR, null, Collections.emptyList()) {
    };
    assertNotNull(entity.describeAbilities());
  }

  @Test
  public final void testIsDead() {
    final Entity entity = generateDummyEntity();
    entity.setHealth(0);
    assertTrue(entity.isDead());
  }

}
