package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void test() {
        Animal animal = new Animal();
        assertTrue(animal.isAt(new Vector2d(2,2)));
        assertEquals(animal.getDirection(), MapDirection.NORTH);

        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(), MapDirection.EAST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(), MapDirection.SOUTH);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(), MapDirection.WEST);
        animal.move(MoveDirection.RIGHT);
        assertEquals(animal.getDirection(), MapDirection.NORTH);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getDirection(), MapDirection.WEST);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getDirection(), MapDirection.SOUTH);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getDirection(), MapDirection.EAST);
        animal.move(MoveDirection.LEFT);
        assertEquals(animal.getDirection(), MapDirection.NORTH);

        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(2,3)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(2,4)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(2,4)));
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(3,4)));
        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(4,4)));
        animal.move(MoveDirection.BACKWARD);
        assertTrue(animal.isAt(new Vector2d(4,4)));
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4,3)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4,2)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4,1)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4,0)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(4,0)));
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(3,0)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(2,0)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(1,0)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(0,0)));
        animal.move(MoveDirection.FORWARD);
        assertTrue(animal.isAt(new Vector2d(0,0)));
    }

}