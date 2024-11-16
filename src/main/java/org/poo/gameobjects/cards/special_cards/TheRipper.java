package org.poo.gameobjects.cards.special_cards;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.fileio.CardInput;
import org.poo.utils.GameConstants;

import java.util.ArrayList;

public class TheRipper extends GenericCard {
    public TheRipper(final CardInput card) {
        super(card);
        super.setTank(false);
    }

    public TheRipper(final GenericCard card) {
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

        GenericCard enemy = cards.getFirst();
        if (enemy.getAttackDamage() < 2) {
            enemy.setAttackDamage(0);
            return null;
        }

        enemy.setAttackDamage(enemy.getAttackDamage() - GameConstants.THE_RIPPER_REDUCE_ATTACK);
        super.setHasAttacked(true);

        return null;
    }
}

