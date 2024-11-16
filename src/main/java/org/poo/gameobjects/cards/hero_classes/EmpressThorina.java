package org.poo.gameobjects.cards.hero_classes;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.gameobjects.cards.GenericHero;

import java.util.ArrayList;

public class EmpressThorina extends GenericHero {
    public EmpressThorina(final int mana, final String name,
                          final String description, final ArrayList<String> colors) {
        super(mana, name, description, colors);
    }

    /**
     * Searches for the highest health card in the affected cards and returns it to be destroyed
     * @param cards An array list with all the affected cards by the ability
     * @return The card that will be destroyed
     */
    @Override
    public final GenericCard useAbility(final ArrayList<GenericCard> cards) {
        if (cards.isEmpty()) {
            return null;
        }

        // Find the card with the most health
        GenericCard enemyMostHP = cards.get(0);
        for (GenericCard enemy : cards) {
            if (enemyMostHP.getHealth() < enemy.getHealth()) {
                enemyMostHP = enemy;
            }
        }

        super.setHasAttacked(true);
        return enemyMostHP;
    }
}
