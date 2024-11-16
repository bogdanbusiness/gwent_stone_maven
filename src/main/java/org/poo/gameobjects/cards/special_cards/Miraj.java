package org.poo.gameobjects.cards.special_cards;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.fileio.CardInput;
import org.poo.utils.GameConstants;

import java.util.ArrayList;

public class Miraj extends GenericCard {
    public Miraj() {
        super();
        super.setTank(false);
    }

    public Miraj(final CardInput card) {
        super(card);
        super.setTank(false);
    }

    public Miraj(final GenericCard card) {
        super(card);
        super.setTank(false);
    }

    @Override
    public final int getRowPlacement(final int playerIndex) {
        return playerIndex == 1 ? GameConstants.PLAYER1_FRONT_ROW : GameConstants.PLAYER2_FRONT_ROW;
    }

    @Override
    public final GenericCard useAbility(final ArrayList<GenericCard> cards) {
        if (cards.isEmpty()) {
            return null;
        }

        GenericCard enemy = cards.get(0);
        int enenmyHP = enemy.getHealth();
        int selfHP = super.getHealth();

        enemy.setHealth(selfHP);
        super.setHealth(enenmyHP);
        super.setHasAttacked(true);

        return null;
    }
}
