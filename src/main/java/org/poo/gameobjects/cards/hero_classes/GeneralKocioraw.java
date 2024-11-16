package org.poo.gameobjects.cards.hero_classes;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.gameobjects.cards.GenericHero;

import java.util.ArrayList;

public class GeneralKocioraw extends GenericHero {
    public GeneralKocioraw(final int mana, final String name,
                           final String description, final ArrayList<String> colors) {
        super(mana, name, description, colors);
    }

    /**
     * Increases the attack damage to all the affected cards
     * @param cards An array list with all the affected cards by the ability
     * @return Null
     */
    @Override
    public GenericCard useAbility(final ArrayList<GenericCard> cards) {
        if (cards.isEmpty()) {
            return null;
        }

        // Freeze all the cards
        for (GenericCard ally : cards) {
            ally.setAttackDamage(ally.getAttackDamage() + 1);
        }

        super.setHasAttacked(true);
        return null;
    }
}
