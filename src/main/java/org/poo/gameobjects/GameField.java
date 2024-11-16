package org.poo.gameobjects;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.gameobjects.cards.GenericHero;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Getter;
import lombok.Setter;
import org.poo.utils.GameConstants;
import org.poo.utils.Point;

import java.util.ArrayList;

public class GameField {
    private final GenericCard[][] field =
            new GenericCard[GameConstants.TABLE_ROWS][GameConstants.TABLE_COLUMNS];
    @Getter @Setter
    private GenericHero player1Hero;
    @Getter @Setter
    private GenericHero player2Hero;

    // Constructor

    public GameField() {
        player1Hero = null;
        player2Hero = null;

        for (int i = 0; i < GameConstants.TABLE_ROWS; i++) {
            for (int j = 0; j < GameConstants.TABLE_COLUMNS; j++) {
                field[i][j] = null;
            }
        }
    }

    // Methods

    /**
     * Completely resets the field
     */
    public void resetField() {
        for (int i = 0; i < GameConstants.TABLE_ROWS; i++) {
            for (int j = 0; j < GameConstants.TABLE_COLUMNS; j++) {
                field[i][j] = null;
            }
        }
        player1Hero = null;
        player2Hero = null;
    }

    /**
     * Returns the hero of the current player
     * @param playerTurn The current active player
     * @return GenericHero instance with the hero requested
     */
    public GenericHero getPlayerHero(final int playerTurn) {
        if (playerTurn == 1) {
            return player1Hero;
        } else {
            return player2Hero;
        }
    }

    /**
     * Returns the opposite hero of the current player
     * @param playerTurn The current active player
     * @return GenericHero instance with the hero requested
     */
    public GenericHero getOppositePlayerHero(final int playerTurn) {
        if (playerTurn == 1) {
            return player2Hero;
        } else {
            return player1Hero;
        }
    }

    /**
     * Gets the cards on a row
     * @param row The row requested
     * @return An ArrayList instance with all the cards requested
     */
    public ArrayList<GenericCard> getRowCards(final int row) {
        ArrayList<GenericCard> cards = new ArrayList<>(GameConstants.TABLE_ROWS);
        for (int i = 0; i < GameConstants.TABLE_COLUMNS; i++) {
            if (field[row][i] != null) {
                cards.add(field[row][i]);
            }
        }

        return cards;
    }

    /**
     * Gets the number of cards on the row
     * @param row The row checked
     * @return Returns the number of cards on the row checked
     */
    public int getRowOccupancy(final int row) {
        return getRowCards(row).size();
    }

    /**
     * Adds a GenericCard to the playing field
     * @param genericCard The GenericCard that is placed down
     * @param rowNumber The index of the row where the card will be placed
     */
    public void addCard(final GenericCard genericCard, final int rowNumber) {
        for (int i = 0; i < GameConstants.TABLE_COLUMNS; i++) {
            if (field[rowNumber][i] == null) {
                field[rowNumber][i] = genericCard;
                break;
            }
        }
    }

    /**
     * Removes the card from the field
     * @param point The coordinates of the card on the field
     */
    public void removeCard(final Point point) {
        // Shifts every column on the row to the left
        for (int i = point.getColumn(); i < GameConstants.TABLE_COLUMNS - 1; i++) {
            field[point.getRow()][i] = field[point.getRow()][i + 1];
        }
        field[point.getRow()][GameConstants.TABLE_COLUMNS - 1] = null;
    }

    /**
     * Gets the card on the field
     * @param point The coordinates of the card on the field
     * @return Returns the GenericCard instance
     */
    public GenericCard getCard(final Point point) {
        return field[point.getRow()][point.getColumn()];
    }

    /**
     * Gets the coordinates of a card on the field
     * @param card The card on the field
     * @param playerTurn The turn of a
     * @return Returns the Point instance with the card position, null on error
     */
    public Point getPlayerCardPosition(final GenericCard card, final int playerTurn) {
        int startingRow = playerTurn == 1 ? 0 : 2;
        Point returnPoint = new Point();
        for (int i = startingRow; i < startingRow + GameConstants.TABLE_HALF_ROWS; i++) {
            for (int j = 0; j < GameConstants.TABLE_COLUMNS; j++) {
                if (field[i][j] != null && field[i][j].equals(card)) {
                    returnPoint.setRow(i);
                    returnPoint.setColumn(j);
                    return returnPoint;
                }
            }
        }
        return null;
    }

    /**
     * Checks if a card is an enemy
     * @param cardCoordinates The coordinates of the card checked
     * @param playerTurn The turn of the player who attacks
     * @return Returns whether a card is an enemy or not
     */
    public boolean isEnemy(final Point cardCoordinates, final int playerTurn) {
        int cardRow = cardCoordinates.getRow();
        // Check the respective rows associated with a player
        if (playerTurn == 1
                && (cardRow == GameConstants.PLAYER1_FRONT_ROW
                || cardRow == GameConstants.PLAYER1_BACK_ROW)) {
            return false;
        } else if (playerTurn == 2
                && (cardRow == GameConstants.PLAYER2_FRONT_ROW
                || cardRow == GameConstants.PLAYER2_BACK_ROW)) {
            return false;
        }
        return true;
    }

    /**
     * Gets the number of tanks on the front row of the other player
     * @param playerTurn The turn of the player
     * @return The number of tanks on the row
     */
    public int getTanksOnRow(final int playerTurn) {
        int tankNumber = 0;
        ArrayList<GenericCard> cards;

        if (playerTurn == 1) {
            cards = getRowCards(GameConstants.PLAYER2_FRONT_ROW);
        } else {
            cards = getRowCards(GameConstants.PLAYER1_FRONT_ROW);
        }

        for (GenericCard card : cards) {
            if (card.isTank()) {
                tankNumber++;
            }
        }

        return tankNumber;
    }

    /**
     * Enables cards to attack again and unfreezes them
     */
    public void resetAttackForCards() {
        for (int i = 0; i < GameConstants.TABLE_ROWS; i++) {
            ArrayList<GenericCard> cards = getRowCards(i);
            for (GenericCard card : cards) {
                card.setHasAttacked(false);
            }
        }
    }

    /**
     * Unfreezes all the cards that belong to a player
     * @param playerTurn The player to which the cards belong to
     */
    public void unfreezePlayerCards(final int playerTurn) {
        int startingRow = playerTurn == 1 ? 2 : 0;

        for (int i = startingRow; i < startingRow + GameConstants.TABLE_HALF_ROWS; i++) {
            ArrayList<GenericCard> cards = getRowCards(i);
            for (GenericCard card : cards) {
                card.setFrozen(false);
            }
        }
    }

    /**
     * Returns in JSON format the entire game field
     * @return ArrayNode with the formatted JSON
     */
    public ArrayNode printAllCardsOnTable() {
        // Creates the output
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate through the entire field
        for (int i = 0; i < GameConstants.TABLE_ROWS; i++) {
            ArrayList<GenericCard> cards = getRowCards(i);
            ArrayNode row = mapper.createArrayNode();
            for (GenericCard card : cards) {
                row.add(card.printCard());
            }
            output.add(row);
        }

        return output;
    }

    /**
     * Returns in JSON format the frozen cards on the game field
     * @return ArrayNode with the formatted JSON
     */
    public ArrayNode printAllFrozenCardsOnTable() {
        // Creates the output
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate through the entire field
        for (int i = 0; i < GameConstants.TABLE_ROWS; i++) {
            ArrayList<GenericCard> cards = getRowCards(i);
            for (GenericCard card : cards) {
                if (card.isFrozen()) {
                    output.add(card.printCard());
                }
            }
        }

        return output;
    }
}
