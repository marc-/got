package org.github.got;

import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.github.got.entity.Player;
import org.github.got.item.ItemStack;
import org.github.got.item.Requirement;
import org.github.got.item.Requirement.Type;
import org.junit.Test;

public class QuestTest {

  @Test
  public final void testQuest() {
    new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
  }

  @Test
  public final void testMeetRequirement() {
    final Requirement requirement = new Requirement(Type.LOOT, "Test", 1);
    requirement.setProgress(1);
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, Collections.singletonList(requirement), null, 0);
    assertTrue(quest.meetRequirement());
  }

  @Test
  public final void testMeetRequirementTracked() {
    final Requirement requirement = new Requirement(Type.LOOT, "Test", 1);
    final Player player = new Player();
    player.addToInventory(new Item("Test", 0), 1);
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, Collections.singletonList(requirement), null, 0);
    player.accept(quest);
    assertTrue(quest.meetRequirement());
  }

  @Test
  public final void testMeetRequirementTracked1() {
    final Requirement requirement = new Requirement(Type.LOOT, "Test", 1);
    final Player player = new Player();
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, Collections.singletonList(requirement), null, 0);
    player.accept(quest);
    player.addToInventory(new Item("Test", 0), 1);
    assertTrue(quest.meetRequirement());
  }

  @Test
  public final void testMeetRequirement_NEGATIVE() {
    final Requirement requirement = new Requirement(Type.LOOT, "Test", 1);
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, Collections.singletonList(requirement), null, 0);
    assertFalse(quest.meetRequirement());
  }

  @Test
  public final void testDescribe() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertNotNull(quest.describe());
  }

  @Test
  public final void testDescribeRequrements() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertNotNull(quest.describeRequrements());
  }

  @Test
  public final void testDescribeRequrementsEmpty() {
    final Requirement requirement = new Requirement(Type.LOOT, "Test", 1);
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, Collections.singletonList(requirement), null, 0);
    assertNotNull(quest.describeRequrements());
  }

  @Test
  public final void testStatus() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertNotNull(quest.status());
  }

  @Test
  public final void testHasRewards() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null,
        Collections.singletonList(new ItemStack(new Item("TEST", 0), 1)), 0);
    assertTrue(quest.hasRewards());
  }

  @Test
  public final void testHasRewards_NEGATIVE() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertFalse(quest.hasRewards());
  }

  @Test
  public final void testDescribeRewards() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertNotNull(quest.describeRewards());
  }

  @Test
  public final void testRewardLookup() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null,
        Collections.singletonList(new ItemStack(new Item("TEST", 0), 1)), 0);
    assertNotNull(quest.rewardLookup("1"));
    assertNotNull(quest.rewardLookup("test"));
  }

  @Test
  public final void testRewardLookup_NEGATIVE() {
    final Quest quest = new Quest(EMPTY, 0, null, null, null, null, null, null, 0);
    assertNull(quest.rewardLookup("1"));
    assertNull(quest.rewardLookup("test"));
  }

}
