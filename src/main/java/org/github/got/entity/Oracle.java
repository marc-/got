package org.github.got.entity;

import static org.github.got.Resources.MALE;
import static org.github.got.Resources.ORACLE;
import static org.github.got.Resources.STORY_ORACLE_DESCRIPTION;
import static org.github.got.Resources.STORY_ORACLE_FAREWELL;
import static org.github.got.Resources.STORY_ORACLE_WELCOME;
import static org.github.got.Resources.VENDOR;

import java.util.Arrays;
import java.util.Collections;

import org.github.got.Formulas;
import org.github.got.Quest;
import org.github.got.Resources;
import org.github.got.item.Consumable;
import org.github.got.item.Consumable.Effect;
import org.github.got.item.ItemStack;

public class Oracle extends NonPlayerCharacter {

  public Oracle() {
    super(Resources.ORACLE, "human?", Resources.getString(MALE), null, Attitude.ORACLE,
        Resources.getString(STORY_ORACLE_DESCRIPTION), STORY_ORACLE_WELCOME, STORY_ORACLE_FAREWELL,
        Collections
        .singletonList(new Quest(Resources.getString("story.quest1.name", VENDOR), 0, ORACLE, VENDOR,
            Resources.getString("story.quest1.start", VENDOR), "story.quest1.end", Collections.emptyList(),
            Arrays.asList(new ItemStack(new Consumable("Apple", Effect.RESTORES_HEALH, 10, 0), 10),
                new ItemStack(new Consumable("Battle of water", Effect.RESTORES_MANA, 10, 0), 10)),
            Formulas.MIN_EXP)));
  }

}
