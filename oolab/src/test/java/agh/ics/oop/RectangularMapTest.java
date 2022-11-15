package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangularMapTest {
    @Test
    void test(){
        IWorldMap map = new RectangularMap(2,2);
        Animal animal1 = new Animal(map, new Vector2d(0,0));
        Animal animal2 = new Animal(map, new Vector2d(1,1));
        assertTrue(map.place(animal1));
        assertTrue(map.place(animal2));
        assertFalse(map.place(animal1));
        assertEquals(map.objectAt(new Vector2d(0,0)), animal1);
        assertEquals(map.objectAt(new Vector2d(1,1)), animal2);
        assertTrue(map.isOccupied(new Vector2d(1,1)));
        assertFalse((map.isOccupied(new Vector2d(0,1))));
        assertTrue(map.canMoveTo(new Vector2d(1,0)));
        assertFalse(map.canMoveTo(new Vector2d(2,2)));
        assertFalse(map.canMoveTo(new Vector2d(0,-1)));
        assertFalse(map.canMoveTo(new Vector2d(3,3)));
    }
}