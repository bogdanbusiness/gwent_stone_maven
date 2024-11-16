package org.poo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Point {
    private int row;
    private int column;

    public Point() {
        this.row = 0;
        this.column = 0;
    }

    public Point(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Turns the point into a JSON format
     * @return ObjectNode instance with the format
     */
    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode pointNode = mapper.createObjectNode();

        pointNode.put("x", this.row);
        pointNode.put("y", this.column);

        return pointNode;
    }
}
