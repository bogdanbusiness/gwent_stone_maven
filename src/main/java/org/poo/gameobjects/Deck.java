package org.poo.gameobjects;

import org.poo.gameobjects.cards.GenericCard;
import org.poo.gameobjects.cards.card_classes.Berserker;
import org.poo.gameobjects.cards.card_classes.Goliath;
import org.poo.gameobjects.cards.card_classes.Sentinel;
import org.poo.gameobjects.cards.card_classes.Warden;
import org.poo.gameobjects.cards.special_cards.Disciple;
import org.poo.gameobjects.cards.special_cards.Miraj;
import org.poo.gameobjects.cards.special_cards.TheCursedOne;
import org.poo.gameobjects.cards.special_cards.TheRipper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.poo.fileio.CardInput;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Getter @Setter
public class Deck {
    private int numCards;
    private ArrayList<GenericCard> deck;

    // Constructors

    public Deck() {
        numCards = 0;
        deck = new ArrayList<>();
    }

    public Deck(final Deck copyDeck) {
        numCards = 0;
        this.deck = new ArrayList<>();
        for (int i = 0; i < copyDeck.numCards; i++) {
            this.addToEndOfDeck(copyDeck.deck.get(i));
        }
    }

    // Methods

    /**
     * Adds a card to the deck
     * ATTENTION: This method is responsible for the creation of classes for each card type
     * @param card The CardInput instance that will be added
     */
    public void addToEndOfDeck(final CardInput card) {
        GenericCard newGenericCard;
        switch (card.getName()) {
            case "Sentinel":
                newGenericCard = new Sentinel(card);
                break;
            case "Berserker":
                newGenericCard = new Berserker(card);
                break;
            case "Goliath":
                newGenericCard = new Goliath(card);
                break;
            case "Warden":
                newGenericCard = new Warden(card);
                break;
            case "Miraj":
                newGenericCard = new Miraj(card);
                break;
            case "The Ripper":
                newGenericCard = new TheRipper(card);
                break;
            case "The Cursed One":
                newGenericCard = new TheCursedOne(card);
                break;
            case "Disciple":
                newGenericCard = new Disciple(card);
                break;
            default:
                newGenericCard = new GenericCard(card);
                System.out.println("Card not recognized.");
                System.out.println(card.getName());
        }
        deck.add(newGenericCard);
        numCards++;
    }

    /**
     * Adds a card to the deck
     * ATTENTION: This method is responsible for the creation of classes for each card type
     * @param card The CardInput instance that will be added
     */
    public void addToEndOfDeck(final GenericCard card) {
        GenericCard newGenericCard;
        switch (card.getName()) {
            case "Sentinel":
                newGenericCard = new Sentinel(card);
                break;
            case "Berserker":
                newGenericCard = new Berserker(card);
                break;
            case "Goliath":
                newGenericCard = new Goliath(card);
                break;
            case "Warden":
                newGenericCard = new Warden(card);
                break;
            case "Miraj":
                newGenericCard = new Miraj(card);
                break;
            case "The Ripper":
                newGenericCard = new TheRipper(card);
                break;
            case "The Cursed One":
                newGenericCard = new TheCursedOne(card);
                break;
            case "Disciple":
                newGenericCard = new Disciple(card);
                break;
            default:
                newGenericCard = new GenericCard(card);
                System.out.println("Card not recognized.");
                System.out.println(card.getName());
        }
        deck.add(newGenericCard);
        numCards++;
    }

    /**
     * Adds a card to the specified index
     * @param genericCard The GenericCard instance that will be added
     * @param handIndex The index where the card will be placed
     */
    public void addToIndexInDeck(final GenericCard genericCard, final int handIndex) {
        deck.add(handIndex, genericCard);
        numCards++;
    }

    /**
     * Finds the card in the deck according to an index
     * @param cardIndex The index of the card
     * @return The GenericCard instance of the found card or null on failure
     */
    public final GenericCard findCardInDeck(final int cardIndex) {
        if (deck.isEmpty() || cardIndex >= deck.size()) {
            return null;
        }
        return deck.get(cardIndex);
    }

    /**
     * Removes a card from the deck
     * @param cardIndex The index of the minion removed
     * @return Returns the minion
     */
    public final GenericCard removeFromDeck(final int cardIndex) {
        if (deck.isEmpty() || cardIndex >= deck.size()) {
            return null;
        }
        GenericCard ret = deck.remove(cardIndex);
        numCards--;
        return ret;
    }

    /**
     * Removes the first card from the deck
     * @return The GenericCard instance that was removed, or null if the deck is empty
     */
    public GenericCard removeFirstFromDeck() {
        return removeFromDeck(0);
    }

    /**
     * Shuffles the deck using a set seed
     * @param seed The seed that will be used to shuffle the deck
     */
    public void shuffleDeck(final int seed) {

        Collections.shuffle(deck, new Random(seed));
    }

    /**
     * Transforms the held deck into JSON format
     * @return ArrayNode class with the formated output
     */
    public ArrayNode toJsonDeck() {
        // Creates the output
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode output = mapper.createArrayNode();

        // Iterate over the deck
        for (int i = 0; i < numCards; i++) {
            // Create the card node and format the output
            ObjectNode cardNode = deck.get(i).printCard();
            output.add(cardNode);
        }

        return output;
    }
}
