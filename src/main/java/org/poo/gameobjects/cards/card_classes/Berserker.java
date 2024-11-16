package org.poo.gameobjects.cards.card_classes;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.fileio.CardInput;
import org.poo.utils.GameConstants;

public class Berserker extends GenericCard {
    public Berserker() {
        super();
        super.setTank(false);
    }

    public Berserker(final CardInput card) {
        super(card);
        super.setTank(false);
    }

    public Berserker(final GenericCard card) {
        super(card);
        super.setTank(false);
    }

    @Override
    public final int getRowPlacement(final int playerIndex) {
        return playerIndex == 1 ? GameConstants.PLAYER1_BACK_ROW : GameConstants.PLAYER2_BACK_ROW;
    }
}