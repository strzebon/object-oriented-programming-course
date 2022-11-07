package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse() {
        String[] args = {"d", "f", "f", "forward", "l", "ssd", "right", "backward", "b", "back","r", "left"};
        MoveDirection[] directions = OptionsParser.parse(args);
        assertEquals(directions.length, 9);
        assertEquals(directions[0], MoveDirection.FORWARD);
        assertEquals(directions[1], MoveDirection.FORWARD);
        assertEquals(directions[2], MoveDirection.FORWARD);
        assertEquals(directions[3], MoveDirection.LEFT);
        assertEquals(directions[4], MoveDirection.RIGHT);
        assertEquals(directions[5], MoveDirection.BACKWARD);
        assertEquals(directions[6], MoveDirection.BACKWARD);
        assertEquals(directions[7], MoveDirection.RIGHT);
        assertEquals(directions[8], MoveDirection.LEFT);

    }
}