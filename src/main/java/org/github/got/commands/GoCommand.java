package org.github.got.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.github.got.CommandA;
import org.github.got.Context;
import org.github.got.Context.Scope;
import org.github.got.Entity;
import org.github.got.Entity.Attitude;
import org.github.got.Formulas;
import org.github.got.Location;
import org.github.got.Resources;
import org.github.got.entity.Mob;
import org.github.got.entity.Player;

/**
 * Move character to specified location if possible. If current location is
 * populated with hostiles with some chance attempt to leave could fail and
 * player receives critical hit. Allows to flee the battle (same rules as for
 * regular move -- fail flee -- get hit hard).
 *
 * @author Maksim Chizhov
 *
 */
@CommandA(value = { "go (west|east|north|south)", "(west|east|north|south)", "gw|ge|gn|gs" }, scope = { Scope.TALK,
    Scope.LOCATION, Scope.COMBAT }, starts = Scope.LOCATION)
public class GoCommand extends CombatCommands {

  private static final Map<String, Handler> HANDLERS = new HashMap<>(12);

  public GoCommand() {
    if (HANDLERS.isEmpty()) {
      final Handler west = context -> goWest(context);
      final Handler east = context -> goEast(context);
      final Handler north = context -> goNorth(context);
      final Handler south = context -> goSouth(context);
      HANDLERS.put("go west", west);
      HANDLERS.put("west", west);
      HANDLERS.put("gw", west);
      HANDLERS.put("go east", east);
      HANDLERS.put("east", east);
      HANDLERS.put("ge", east);
      HANDLERS.put("go north", north);
      HANDLERS.put("north", north);
      HANDLERS.put("gn", north);
      HANDLERS.put("go south", south);
      HANDLERS.put("south", south);
      HANDLERS.put("gs", south);
    }
  }

  @Override
  public void issue(final Context context) {
    if (Scope.COMBAT.equals(context.getScope())) {
      attemptToFlee(context);
    } else {
      context.setTarget(null);
      final Location location = context.getWorld().getLocation();
      if (!Location.Type.TOWN.equals(location.getType())) {
        final List<Entity> hostiles = location.getCreatures().stream()
            .filter(c -> c instanceof Mob && Attitude.HOSTILE.equals(c.getAttitude())).collect(Collectors.toList());
        if (!hostiles.isEmpty()) {
          attemptToSneak(context, hostiles);
        }
      }
    }
    context.setResult(context.getTarget() == null && go(context));
  }

  private boolean go(final Context context) {
    return HANDLERS.get(context.getCommand()).handle(context);
  }

  /**
   * Attempts to sneak behind hostiles. With 70% angry mobs will spot you adn
   * attack, 20% for calm mobs. Doesn't correlate to level.
   *
   * @param context
   * @param hostiles
   */
  private void attemptToSneak(final Context context, final List<Entity> hostiles) {
    // 70% that angry hostile mob will attack player
    Mob hostile = (Mob) hostiles.stream()
        .filter(h -> Mob.Mood.ANGRY.equals(((Mob) h).getMood()) && Formulas.angryModAttack()).findAny().orElse(null);
    if (hostile == null) {
      // 20% that calm hostile mob will attack player
      hostile = (Mob) hostiles.stream()
          .filter(h -> Mob.Mood.CALM.equals(((Mob) h).getMood()) && Formulas.calmMobAttack()).findAny().orElse(null);
    }
    if (hostile == null) {
      game(context, Resources.GAME_ACTIONS_GOTO_NO_ONE_ATTCKED);
    } else {
      game(context, Resources.GAME_EVENT_SNEAK_FAILED);
      combat(context, Resources.GAME_ACTIONS_GOTO_BEEN_ATTACKED_BY, hostile.describeBreifly());
      context.setTarget(hostile);
      context.setScope(Scope.COMBAT);
    }
  }

  /**
   * Attempt to flee battle.
   *
   * @param context
   */
  private void attemptToFlee(final Context context) {
    game(context, Resources.GAME_ACTIONS_FLEE);
    final Entity target = context.getTarget();
    final Player player = context.getPlayer();
    // Chance to flee ((player.level - target.level)*10 + 50)%, if attempt
    // fail, mob will critically hit you.
    if (Formulas.fled(player, target)) {
      game(context, Resources.GAME_EVENT_FLED);
      context.setTarget(null);
    } else {
      attack(context, target, player, ((Mob) target).getAbility(), true);
    }
  }

  private interface Handler {
    public boolean handle(Context context);
  }

  private boolean goWest(final Context context) {
    return goTo(context, context.getWorld().getLocation().getWest());
  }

  private boolean goEast(final Context context) {
    return goTo(context, context.getWorld().getLocation().getEast());
  }

  private boolean goNorth(final Context context) {
    return goTo(context, context.getWorld().getLocation().getNorth());
  }

  private boolean goSouth(final Context context) {
    return goTo(context, context.getWorld().getLocation().getSouth());
  }
}
