package org.github.got;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.github.got.Location.Type;
import org.github.got.entity.Mob;
import org.junit.Test;

public class LocationTest {

  @Test
  public final void testGenerate() {
    final Location location = Location.generate(0, null, null, null, null);
    assertNotNull(location);
    assertNotNull(location.getType());
    assertEquals(0, location.level);
    assertNull(location.getWest());
    assertNull(location.getEast());
    assertNull(location.getNorth());
    assertNull(location.getSouth());
  }

  @Test
  public final void testPopulateLocation() {
    final Location location = new Location(Type.TOWN);
    location.level = 5;
    Location.populateLocation(location);
    assertNotNull(location.creatures);
    assertTrue(location.creatures.size() < Formulas.MAX_MONSTERS_PER_LOCATION);
    location.creatures.forEach(c -> assertTrue(c.getLevel() >= 4 && c.getLevel() <= 6));
  }

  @Test
  public final void testDescribe() {
    final Location location = Location.generate(0, null, null, null, null);
    assertNotNull(location);
    assertNotNull(location.describe());
  }

  @Test
  public final void testLookup() {
    final Location location = new Location(Type.TOWN);
    final Mob mob = new Mob("MOB", 0, null, null, null, null, null, null) {
    };
    location.creatures = Collections.singletonList(mob);
    assertEquals(mob, location.lookup("1"));
    assertEquals(mob, location.lookup("mob"));
  }

  @Test
  public final void testRepopulate() {
    final Location location = new Location(Type.TOWN);
    location.repopulate();
    assertNotNull(location.creatures);
    assertTrue(location.creatures.size() < Formulas.MAX_MONSTERS_PER_LOCATION);
  }

  @Test
  public final void testConnect() {
    final Location location = new Location(Type.TOWN);
    Location.connect(location, location, location, location, location);
    assertEquals(location, location.getWest());
    assertEquals(location, location.getEast());
    assertEquals(location, location.getNorth());
    assertEquals(location, location.getSouth());

  }

}
