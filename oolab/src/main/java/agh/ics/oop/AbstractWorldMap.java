package agh.ics.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected MapVisualizer visualizer;
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    abstract Vector2d checkLowerLeft();
    abstract Vector2d checkUpperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight) && this.animals.get(position) == null;
    }

    @Override
    public boolean place(Animal animal) {
        if(this.animals.get(animal.getPosition()) == null){
            this.animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is occupied");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.animals.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.animals.get(position);
    }

    public String toString(){
        return this.visualizer.draw(checkLowerLeft(), checkUpperRight());
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        animals.put(newPosition, animals.get(oldPosition));
        animals.remove(oldPosition);
    }
}
