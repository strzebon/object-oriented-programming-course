package agh.ics.oop;

public interface IWorldMap {

    boolean canMoveTo(Vector2d position);

    boolean place(Animal animal);

    void removeDeadAnimals();

    void moveAnimals();

    void eatGrass();

    void reproduce();

    void spawnGrass();
}
