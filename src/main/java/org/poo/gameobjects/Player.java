package org.poo.gameobjects;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.gameobjects.cards.GenericHero;
import org.poo.gameobjects.cards.hero_classes.EmpressThorina;
import org.poo.gameobjects.cards.hero_classes.GeneralKocioraw;
import org.poo.gameobjects.cards.hero_classes.KingMudface;
import org.poo.gameobjects.cards.hero_classes.LordRoyce;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CardInput;
import org.poo.fileio.DecksInput;

import lombok.Getter;
import lombok.Setter;
import org.poo.utils.GameConstants;

import static java.lang.Math.min;

@Getter @Setter
public class Player {
    private ArrayList<Deck> possibleDecks;
    private Deck deck;
    private Deck hand;
    private GenericHero genericHero;
    private int  mana;

    private int wonGames;
    private int totalGames;

    // Constructor
    public Player(final DecksInput decksInput) {
        // Transfer the deck from the input to the current class
        possibleDecks = new ArrayList<>();
        ArrayList<CardInput> inputDeck;
        Deck newDeck;

        // Transfer the decks to the available decks
        for (int i = 0; i < decksInput.getNrDecks(); i++) {
            newDeck = new Deck();

            inputDeck = decksInput.getDecks().get(i);
            for (CardInput cardInput : inputDeck) {
                newDeck.addToEndOfDeck(cardInput);
            }

            possibleDecks.add(newDeck);
        }

        // Initialises the rest of the class
        deck = null;
        hand = new Deck();
        genericHero = null;

        mana = 0;
        wonGames = 0;
        totalGames = 0;
    }

    /**
     * Resets the player without deleting the possible decks they can have
     */
    public void resetPlayer() {
        deck = null;
        hand = new Deck();
        genericHero = null;

        mana = 0;
    }

    /**
     * GenericHero setter that creates the specific class of the GenericHero
     * @param hero The GenericHero input card
     */
    public void setGenericHero(final CardInput hero) {
        switch (hero.getName()) {
            case "Empress Thorina":
                this.genericHero = new EmpressThorina(hero.getMana(), hero.getName(),
                        hero.getDescription(), hero.getColors());
                break;
            case "General Kocioraw":
                this.genericHero = new GeneralKocioraw(hero.getMana(), hero.getName(),
                        hero.getDescription(), hero.getColors());
                break;
            case "King Mudface":
                this.genericHero = new KingMudface(hero.getMana(), hero.getName(),
                        hero.getDescription(), hero.getColors());
                break;
            case "Lord Royce":
                this.genericHero = new LordRoyce(hero.getMana(), hero.getName(),
                        hero.getDescription(), hero.getColors());
                break;
            default:
                this.genericHero = new GenericHero(hero.getMana(), hero.getName(),
                        hero.getDescription(), hero.getColors());
                System.out.println("Hero not recognized.");
                System.out.println(hero.getName());
        }
    }

    // Methods

    /**
     * Chooses a deck from the pile of decks
     * @param deckIndex The index of the deck chosen
     */
    public void chooseDeck(final int deckIndex) {
        deck = new Deck(possibleDecks.get(deckIndex));
    }

    /**
     * Shuffles the deck of the player using a set seed
     * @param seed The seed that will be used to shuffle the deck
     */
    public void shufflePlayerDeck(final int seed) {
        deck.shuffleDeck(seed);
    }

    /**
     * Draws a card from deck and adds it into the hand
     */
    public void drawCardFromDeck() {
        GenericCard card = deck.removeFirstFromDeck();
        if (card != null) {
            hand.addToIndexInDeck(card, hand.getNumCards());
        }
    }

    /**
     * Handles removing a card from the hand while managing the mana of the player
     * @param handIndex The card that will be removed
     * @return The instance of the card removed
     */
    public GenericCard placeCardFromHand(final int handIndex) {
        // Find the card and check if it exists or if we have enough mana to cast it
        GenericCard card = hand.findCardInDeck(handIndex);
        if (card == null || card.getMana() > mana) {
            return null;
        }

        expendMana(card.getMana());
        return hand.removeFromDeck(handIndex);
    }

    /**
     * Returns a card to the start of the hand
     * @param card The card that will be reintroduced
     */
    public void returnCardToHand(final GenericCard card, final int handIndex) {
        deck.addToIndexInDeck(card, handIndex);
    }


    /**
     * Adds mana to the player's pool proportional to the number of rounds
     * @param turnCounter The number of ended turns since the start of the match
     */
    public void receiveMana(final int turnCounter) {
        mana += min(turnCounter / 2 + 1, GameConstants.MAX_MANA);
    }

    /**
     * Spends the mana of the player
     * @param manaExpended The amount of mana used
     */
    public void expendMana(final int manaExpended) {
        mana -= manaExpended;
    }

    /**
     * Changes statistics for the winning player
     */
    public void winGame() {
        wonGames++;
        totalGames++;
    }

    /**
     * Changes statistics for the losing player
     */
    public void loseGame() {
        totalGames++;
    }

    /**
     * Returns in JSON format the held deck of the player
     * @return ArrayNode with the formatted JSON
     */
    public ArrayNode toJsonPlayerDeck() {
        return deck.toJsonDeck();
    }

    /**
     * Returns in JSON format the held hand of the player
     * @return ArrayNode with the formatted JSON
     */
    public ArrayNode toJsonPlayerHand() {
        return hand.toJsonDeck();
    }

    /**
     * Returns in JSON format the held hero of the player
     * @return ArrayNode with the formatted JSON
     */
    public ObjectNode toJsonPlayerHero() {
        return genericHero.toJsonHero();
    }
}
