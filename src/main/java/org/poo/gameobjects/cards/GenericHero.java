package org.poo.gameobjects.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.utils.GameConstants;

import java.util.ArrayList;

public class GenericHero extends GenericCard {
    public GenericHero(final int mana, final String name,
                       final String description, final ArrayList<String> colors) {
        super(mana, GameConstants.HERO_HEALTH, 0, name, description, colors);
    }

    /**
     * Transforms the held hero into JSON format
     * @return ArrayNode class with the formated output
     */
    public ObjectNode toJsonHero() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode cardNode = mapper.createObjectNode();
        cardNode.put("mana", getMana());
        cardNode.put("description", getDescription());

        // Create a new mapper for colors
        ObjectMapper colorMapper = new ObjectMapper();
        ArrayNode colorsArray = colorMapper.createArrayNode();
        for (int j = 0; j < getColors().size(); j++) {
            colorsArray.add(getColors().get(j));
        }

        cardNode.set("colors", colorsArray);

        cardNode.put("name", getName());
        cardNode.put("health", getHealth());

        return cardNode;
    }
}
