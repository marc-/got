package org.github.got.commands;

import static org.github.got.Resources.GAME_ACTIONS_COMBAT_MISSED;
import static org.github.got.Resources.GAME_ACTIONS_COMBAT_MOB_MISSED;
import static org.github.got.Resources.GAME_ACTIONS_GOTO_ERROR_NOWAY;
import static org.github.got.Resources.GAME_ACTIONS_GOTO_LEFT_LOCATION;
import static org.github.got.Resources.GAME_ACTION_CAST_MOB_OOM;
import static org.github.got.Resources.GAME_ACTION_CAST_OOM;
import static org.github.got.Resources.GAME_DESCRIBE_LOOT_MONEY;
import static org.github.got.Resources.GAME_EVENT_LEVELUP;
import static org.github.got.Resources.GAME_EVENT_YOU_ARE_DEAD;
import static org.github.got.Resources.GAME_EVENT_YOU_BEEN_RESURRECTED;
import static org.github.got.Resources.GAME_EVENT_YOU_KILLED_MOD;
import static org.github.got.Resources.GAME_TIP_GAME_USE_MANA_POTION;
import static org.github.got.Resources.STORY_ORACLE_RESURRECT;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.github.got.Ability;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.Formulas;
import org.github.got.Item;
import org.github.got.Location;
import org.github.got.World;
import org.github.got.entity.Player;
import org.github.got.item.ItemStack;
import org.github.got.item.Requirement;
import org.github.got.item.Requirement.Type;
import org.github.got.location.Town;

public abstract class CombatCommands extends AbstractCommand {

  protected void revive(final Context context) {
    context.getPlayer().restore();
    context.setScope(Scope.TOWN);
    sendSpiritToTombStone(context);
  }

  protected void sendSpiritToTombStone(final Context context) {
    final World world = context.getWorld();
    repopulateLocations(world);
    game(context, GAME_EVENT_YOU_BEEN_RESURRECTED);
    oracle(context, STORY_ORACLE_RESURRECT, context.getPlayer().getName());
    world.setLocation(world.getHome());
    gameRaw(context, world.getHome().describe());
  }

  protected boolean goTo(final Context context, final Location location) {
    if (location == null) {
      game(context, GAME_ACTIONS_GOTO_ERROR_NOWAY);
      return false;
    }
    final World world = context.getWorld();
    repopulateLocations(world);
    game(context, GAME_ACTIONS_GOTO_LEFT_LOCATION, world.getLocation().getName());
    world.setLocation(location);
    gameRaw(context, location.describe());
    if (location instanceof Town) {
      // Restore hp and mp once we get to town.
      context.getPlayer().restore();
    }
    return true;
  }

  private void repopulateLocations(final World world) {
    final Queue<Location> visitedLocations = world.getVisitedLocations();
    final Location currentLocation = world.getLocation();
    visitedLocations.add(currentLocation);
    while (visitedLocations.size() > 2) {
      final Location loc = visitedLocations.poll();
      loc.repopulate();
    }
  }

  protected void attack(final Context context, final Entity source, final Entity target, final Ability ability,
      final boolean forceCrit) {
    final Entity hostile = source instanceof Player ? target : source;
    if (org.github.got.Ability.Type.SPELL.equals(ability.getType()) && source.getMana() - ability.getManaCost() < 0) {
      if (source instanceof Player) {
        combat(context, GAME_ACTION_CAST_OOM);
        system(context, GAME_TIP_GAME_USE_MANA_POTION);
      } else {
        combat(context, GAME_ACTION_CAST_MOB_OOM);
      }
    } else {
      source.reduceMana(ability.getManaCost());
      if (Formulas.missed(source, target)) {
        final boolean crit = forceCrit || Formulas.crit(source, target);
        final int damage = Formulas.damage(source, target, ability, crit);
        final String msg = hitOrBeingHit(target, crit);
        combatHit(context, msg, hostile.getName(), ability.getName(), damage);
        target.reduceHealth(damage);
        if (target.isDead()) {
          final int killExperience = Formulas.killExperience(source, target);
          combat(context, killOrBeeingKilled(target), hostile.getName(), killExperience);
          if (target instanceof Player) {
            revive(context);
          } else {
            loot(context);
            final int level = source.getLevel();
            ((Player) source).addExperience(killExperience);
            if (source.getLevel() > level) {
              game(context, GAME_EVENT_LEVELUP, source.describeStats());
            }
            context.getWorld().getLocation().removeCreature(target);
          }
          context.setTarget(null);
        }
      } else {
        combatMiss(context, missOrBeingMissed(target), hostile.getName(), ability.getName());
      }
    }
  }

  private String missOrBeingMissed(final Entity target) {
    if (target instanceof Player) {
      return GAME_ACTIONS_COMBAT_MOB_MISSED;
    } else {
      return GAME_ACTIONS_COMBAT_MISSED;
    }
  }

  private void loot(final Context context) {
    gameRaw(context, "\t***YAY!LOOT!***");
    final List<ItemStack> items = generateLoot(context.getTarget(), context.getPlayer());
    items.stream().forEach(i -> {
      gameRaw(context, i.describe());
      context.getPlayer().addToInventory(i);
    });
    final int coins = Formulas.lootCoins(context.getTarget());
    context.getPlayer().addCoins(coins);
    game(context, GAME_DESCRIBE_LOOT_MONEY, coins);
  }

  private List<ItemStack> generateLoot(final Entity target, final Player player) {
    final Stream<List<Requirement>> requirements = player.getQuests().stream().map(q -> q.getRequirements());
    return requirements.flatMap(rs -> {
      final Stream<ItemStack> lootable = rs.stream().filter(r -> Type.LOOT.equals(r.getType()))
          .map(r -> new ItemStack(new Item(r.getName(), 0), Formulas.questItemsInLoot()));
      return lootable;
    }).collect(Collectors.toList());
  }

  private static String killOrBeeingKilled(final Entity target) {
    if (target instanceof Player) {
      return GAME_EVENT_YOU_ARE_DEAD;
    } else {
      return GAME_EVENT_YOU_KILLED_MOD;
    }
  }

  private static String hitOrBeingHit(final Entity target, final boolean crit) {
    final StringBuilder mgs = new StringBuilder("game.actions.combat.");
    if (target instanceof Player) {
      mgs.append("been_");
    }
    if (crit) {
      mgs.append("critically_");
    }
    mgs.append("hit");
    return mgs.toString();
  }

}
