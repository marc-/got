package org.github.got;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * String resources. Provides access to string bundles.
 *
 * @author Maksim Chizhov
 *
 */
public final class Resources {
  public static final ResourceBundle COLORS = ResourceBundle.getBundle("colors", Locale.getDefault());
  public static final ResourceBundle GAME = ResourceBundle.getBundle("game", Locale.getDefault());
  public static final String ERASE_SEQUENCE = "\u001b[K";
  public static final String CLEAR_SCREEN = "\u001b[2J";
  public static final String RESET_COLOR = "\u001b[1m";
  public static final String GAME_SYSTEM_ERROR_ITEM_NOT_FOUND = "game.system.error.item_not_found";
  public static final String GAME_SYSTEM_ERROR_NOT_VENDOR = "game.system.error.not_vendor";
  public static final String GAME_SYSTEM_ERROR_ITEM_CANT_BE_USED = "game.system.error.item_cant_be_used";
  public static final String GAME_SYSTEM_ERROR_UNKNOWN_COMMAND = "game.system.error.unknown_command";
  public static final String GAME_SYSTEM_ERROR_WRONG_SCOPE = "game.system.error.wrong_scope";
  public static final String GAME_ACTIONS_ERROR_QUEST_NOT_FOUND = "game.actions.error.quest_not_found";
  public static final String GAME_SYSTEM_ERROR_NOT_QUEST_GIVER = "game.system.error.not_quest_giver";
  public static final String GAME_SYSTEM_ERROR_CANT_START = "game.system.error.cant_start_quest";
  public static final String GAME_TIP_GAME_LIST_QUESTS = "game.tip.game.list_quests";
  public static final String GAME_ACTIONS_QUEST_ACCEPTED = "game.actions.quest.accepted";
  public static final String GAME_SYSTEM_ERROR_NPC_NOT_FOUND = "game.system.error.npc_not_found";
  public static final String GAME_SYSTEM_ERROR_CANT_ATTACK_FRIENDLY = "game.system.error.cant_attack_friendly";
  public static final String GAME_ACTIONS_ATTACK = "game.actions.attack";
  public static final String GAME_ACTIONS_ERROR_NOT_ENOUGH_MONEY = "game.actions.error.not_enough_money";
  public static final String GAME_ACTIONS_BUY_PURCHASED = "game.actions.buy.purchased";
  public static final String GAME_SYSTEM_ERROR_UNKNOWN_ABILITY = "game.system.error.unknown_ability";
  public static final String GAME_EVENT_YOU_KILLED_MOD = "game.event.you_killed_mod";
  public static final String GAME_EVENT_YOU_ARE_DEAD = "game.event.you_are_dead";
  public static final String GAME_DESCRIBE_LOOT_MONEY = "game.describe.loot.money";
  public static final String GAME_DESCRIBE_ITEM_STACK = "game.describe.item_stack";
  public static final String GAME_ACTIONS_COMBAT_MISSED = "game.actions.combat.missed";
  public static final String GAME_ACTIONS_COMBAT_MOB_MISSED = "game.actions.combat.mob_missed";
  public static final String GAME_ACTIONS_GOTO_LEFT_LOCATION = "game.actions.goto.left_location";
  public static final String STORY_ORACLE_RESURRECT = "story.oracle.resurrect";
  public static final String GAME_ACTIONS_GOTO_ERROR_NOWAY = "game.actions.goto.error.noway";
  public static final String GAME_EVENT_YOU_BEEN_RESURRECTED = "game.event.you_been_resurrected";
  public static final String GAME_ACTIONS_COMPLETE_QUEST_NOT_COMPLETED = "game.actions.complete_quest.not_completed";
  public static final String GAME_EVENT_LEVELUP = "game.event.levelup";
  public static final String GAME_ACTIONS_COMPLETE_QUEST_COMPLETED = "game.actions.complete_quest.completed";
  public static final String GAME_ACTIONS_COMPLETE_QUEST_CHOOSE_REWARD = "game.actions.complete_quest.choose_reward";
  public static final String GAME_ACTIONS_COMPLETE_QUEST_ERROR_WRONG_NPC = "game.actions.complete_quest.error.wrong_npc";
  public static final String GAME_EVENT_FLED = "game.event.fled";
  public static final String GAME_ACTIONS_FLEE = "game.actions.flee";
  public static final String GAME_ACTIONS_GOTO_BEEN_ATTACKED_BY = "game.actions.goto.been_attacked_by";
  public static final String GAME_EVENT_SNEAK_FAILED = "game.event.sneak_failed";
  public static final String GAME_ACTIONS_GOTO_NO_ONE_ATTCKED = "game.actions.goto.no_one_attcked";
  public static final String GAME_CHARACTER_CREATE_CONFIRMATION = "game.character.create.confirmation";
  public static final String GAME_CHARACTER_CREATE_YOU_ARE = "game.character.create.you_are";
  public static final String GAME_CHARACTER_CREATE_ERROR_EMPTY_NAME = "game.character.create.error.empty_name";
  public static final String GAME_CHARACTER_CREATE_NAME = "game.character.create.name";
  public static final String GAME_CHARACTER_CREATE_ERROR_INVALID_CLASS = "game.character.create.error.invalid_class";
  public static final String GAME_CHARACTER_CREATE_CLASS = "game.character.create.class";
  public static final String GAME_CHARACTER_CREATE_GENDER = "game.character.create.gender";
  public static final String GAME_CHARACTER_CREATE_RACE = "game.character.create.race";
  public static final String GAME_SYSTEM_ERROR_RESUME = "game.system.error.resume";
  public static final String GOT_SAVE_FILE_PATH = "got.save";
  public static final String GAME_SYSTEM_RESUME_CONFIRMATION = "game.system.resume_confirmation";
  public static final String GAME_SYSTEM_ERROR_SAVE = "game.system.error.save";
  public static final String GAME_SYSTEM_SAVED = "game.system.saved";
  public static final String GAME_ACTIONS_SELL_SOLD = "game.actions.sell.sold";
  public static final String GAME_ACTIONS_ERROR_NOT_ENOUGH_ITEMS = "game.actions.error.not_enough_items";
  public static final String GAME_SYSTEM_ERROR_INVALID_COMMAND_FORMAT = "game.system.error.invalid_command_format";
  public static final String GAME_SYSTEM_ERROR_NPC_DONT_WANT_TO_TALK = "game.system.error.npc_dont_want_to_talk";
  public static final String GAME_TIP_GAME_TALK_NPC = "game.tip.game.talk.npc";
  public static final String GAME_TIP_GAME_TALK_NPC_VENDOR = "game.tip.game.talk.npc.vendor";
  public static final String GAME_SYSTEM_LOADED = "game.system.loaded";
  public static final String GAME_ACTIONS_USE_CONSUMED = "game.actions.use.consumed";
  public static final String GAME_DESCRIBE_VENDOR_ITEM = "game.describe.vendor_item";
  public static final String STORY_VENDOR_FAREWELL = "story.vendor.farewell";
  public static final String STORY_VENDOR_WELCOME = "story.vendor.welcome";
  public static final String STORY_VENDOR_DESCRIPTION = "story.vendor.description";
  public static final String GAME_DESCRIBE_PLAYER_INVENTORY_ITEM = "game.describe.player.inventory.item";
  public static final String GAME_DESCRIBE_PLAYER_INVENTORY_EMPTY = "game.describe.player.inventory.empty";
  public static final String GAME_DESCRIBE_PLAYER_INVENTORY = "game.describe.player.inventory";
  public static final String GAME_DESCRIBE_PLAYER_JOURNAL_ITEM = "game.describe.player.journal.item";
  public static final String GAME_DESCRIBE_PLAYER_JOURNAL_EMPTY = "game.describe.player.journal.empty";
  public static final String GAME_DESCRIBE_PLAYER_STATS = "game.describe.player.stats";
  public static final String STORY_ORACLE_FAREWELL = "story.oracle.farewell";
  public static final String STORY_ORACLE_WELCOME = "story.oracle.welcome";
  public static final String STORY_ORACLE_DESCRIPTION = "story.oracle.description";
  public static final String GAME_ACTIONS_ERROR_NO_QUESTS = "game.actions.error.no_quests";
  public static final String GAME_DESCRIBE_NPC_QUEST = "game.describe.npc_quest";
  public static final String GAME_DESCRIBE_MOB_MOOD = "game.describe.mob.mood";
  public static final String GAME_DESCRIBE_MOB = "game.describe.mob";
  public static final String GAME_MOOD_CALM = "game.mood.calm";
  public static final String GAME_MOOD_ANGRY = "game.mood.angry";
  public static final String GAME_DESCRIBE_QUEST_REWARDS_EXPERIENCE = "game.describe.quest.rewards.experience";
  public static final String GAME_DESCRIBE_QUEST = "game.describe.quest";
  public static final String GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM = "game.describe.quest.requirements.item";
  public static final String GAME_DESCRIBE_QUEST_REQUIREMENTS_ITEM_PROGRESS = "game.describe.quest.requirements.item.progress";
  public static final String GAME_QUEST_REQUIREMENT_MET = "game.quest.requirement.met";
  public static final String GAME_QUEST_REQUIREMENT_NOT_MET = "game.quest.requirement.not_met";
  public static final String GAME_DESCRIBE_QUEST_REWARDS_ITEM = "game.describe.quest.rewards.item";
  public static final String GAME_DESCRIBE_ABILITY = "game.describe.ability";
  public static final String GAME_TIP_GAME_USE_MANA_POTION = "game.tip.game.use.mana_potion";
  public static final String GAME_ACTION_CAST_OOM = "game.actions.combat.oom";
  public static final String GAME_ACTION_CAST_MOB_OOM = "game.actions.combat.mob_oom";
  public static final String GAME_DESCRIBE_ENTITY_ABILITIES_ITEM = "game.describe.entity.abilities.item";
  public static final String GAME_DESCRIBE_ENTITY_STATS = "game.describe.entity.stats";
  public static final String GAME_DESCRIBE_ENTITY = "game.describe.entity";
  public static final String RACE_ORC = "orc";
  public static final String ORACLE = "Oracle";
  public static final String VENDOR = "Goram";
  public static final String MALE = "game.gender.male";
  public static final String GAME_DESCRIBE_LOCATION_SHORT = "game.describe.location.short";
  public static final String GAME_DESCRIBE_LOCATION_SHORT_NO_PASSAGE = "game.describe.location.short.no_passage";
  public static final String GAME_DESCRIBE_LOCATION = "game.describe.location";

  private static final String GAME_DESCRIPTION = "game.description";
  private static final String GAME_NAME = "game.name";
  private static final String BACKGROUND = "background";
  public static final String BKSPACE = "\u001b[1D";
  static final String GAME_DESCRIBE_ITEM = "game.describe.item";

  private Resources() {
  }

  public static String loadCaption() {
    final byte[] buff = new byte[1024];
    try (final BufferedInputStream is = new BufferedInputStream(Resources.class.getResourceAsStream("/caption.ansi"))) {
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int c = 0;
      while ((c = is.read(buff)) > 0) {
        baos.write(buff, 0, c);
      }
      return new String(baos.toByteArray(), "utf8");
    } catch (final IOException e) {
      throw new Error("Can not load caption. Terminating.");
    }
  }

  public static String colorFor(final MessageType messageType) {
    if (messageType == MessageType.NONE) {
      return TextUtil.EMPTY;
    }
    return COLORS.getString(messageType.value);
  }

  public static String backgroundColor() {
    return COLORS.getString(BACKGROUND);
  }

  public static String gameName() {
    return GAME.getString(GAME_NAME);
  }

  public static String gameDescription() {
    return GAME.getString(GAME_DESCRIPTION);
  }

  public static String getString(final String name, final Object... args) {
    return String.format(GAME.getString(name), args);
  }
}
