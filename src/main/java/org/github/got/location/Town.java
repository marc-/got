package org.github.got.location;

import java.util.Arrays;

import org.github.got.Location;
import org.github.got.entity.NonPlayerCharacter;
import org.github.got.entity.Oracle;
import org.github.got.entity.Vendor;

public class Town extends Location {

  public Town(final Location west, final Location east, final Location north, final Location south) {
    super(Type.TOWN);
    connect(this, west, east, north, south);
    creatures = Arrays.asList(new Oracle(), Vendor.defautVendor());
  }

  public NonPlayerCharacter getOracle() {
    return (NonPlayerCharacter) creatures.get(0);
  }

  public NonPlayerCharacter getVendor() {
    return (NonPlayerCharacter) creatures.get(1);
  }

  @Override
  public void repopulate() {
  }
}