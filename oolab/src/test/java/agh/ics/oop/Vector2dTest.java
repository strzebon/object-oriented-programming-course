package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {
    Vector2d v1 = new Vector2d(-1, 1);
    Vector2d v2 = new Vector2d(-1, 2);
    Vector2d v3 = new Vector2d(0, 2);



    @Test
    void testToString() {
        assertEquals("(-1,1)", v1.toString());
    }

    @Test
    void precedes() {
        assertTrue(v1.precedes(v3));
        assertFalse(v2.precedes(v1));
    }

    @Test
    void follows() {
        assertTrue(v3.follows(v2));
        assertFalse(v1.follows(v3));
    }

    @Test
    void add() {
        assertEquals(new Vector2d(-2,3),v1.add(v2));
    }

    @Test
    void subtract() {
        assertEquals(new Vector2d(0,-1), v1.subtract(v2));
    }

    @Test
    void upperRight() {
        assertEquals(new Vector2d(0,2), v3.upperRight(v1));
    }

    @Test
    void lowerLeft() {
        assertEquals(new Vector2d(-1, 1), v1.lowerLeft(v2));
    }

    @Test
    void opposite() {
        assertEquals(new Vector2d(1,-1), v1.opposite());
    }

    @Test
    void testEquals() {
        assertTrue(v1.equals(v1));
        assertTrue(v1.equals(new Vector2d(-1,1)));
        assertFalse(v1.equals(v2));
        assertFalse(v1.equals(null));

    }
}