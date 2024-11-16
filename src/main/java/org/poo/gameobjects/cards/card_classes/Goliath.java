package org.poo.gameobjects.cards.card_classes;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.fileio.CardInput;
import org.poo.utils.GameConstants;

public class Goliath extends GenericCard {
    public Goliath() {
        super();
        super.setTank(true);
    }

    public Goliath(final CardInput card) {
        super(card);
        super.setTank(true);
    }

    public Goliath(final GenericCard card) {
        super(card);
        super.setTank(true);
    }

    @Override
    public final int getRowPlacement(final int playerIndex) {
        return playerIndex == 1 ? GameConstants.PLAYER1_FRONT_ROW : GameConstants.PLAYER2_FRONT_ROW;
    }
}