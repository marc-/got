package org.github.got;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WorldTest {

  @Test
  public final void testGenerate() {
    final World world = World.generate();
    assertNotNull(world);
    assertNotNull(world.getHome());
    assertNotNull(world.getLocation());
    assertNotNull(world.getVisitedLocations());
    assertTrue(world.getVisitedLocations().isEmpty());
    assertEquals(world.getHome(), world.getLocation());
  }

  @Test
  public final void testDescribe() {
    final World world = World.generate();
    assertNotNull(world);
    assertNotNull(world.describe());
  }

  @Test
  public final void testVerifyConnected() {
    final Location location = Location.generate(0, Location.generate(0, null, null, null, null), null, null, null);
    assertEquals(location.getWest() != null || location.getEast() != null || location.getNorth() != null
        || location.getSouth() != null, World.verifyConnected(location));
  }

  @Test
  public final void testVerifyConnected_NEGATIVE() {
    final Location generate = Location.generate(0, null, null, null, null);
    assertFalse(World.verifyConnected(generate));
  }

}
