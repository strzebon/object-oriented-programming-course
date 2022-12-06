package agh.ics.oop;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected MapVisualizer visualizer;
    protected Map<Vector2d, Animal> animals = new HashMap<>();
    protected MapBoundary animalBoundary = new MapBoundary();

    public abstract Vector2d checkLowerLeft();
    public abstract Vector2d checkUpperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight) && this.animals.get(position) == null;
    }

    @Override
    public boolean place(Animal animal) {
        Vector2d position = animal.getPosition();
        if(this.animals.get(position) == null && position.follows(this.lowerLeft) && position.precedes(this.upperRight)){
            this.animals.put(position, animal);
            animalBoundary.add(position);
            animal.addObserver(this);
            animal.addObserver(animalBoundary);
            return true;
        }
        else if(this.animals.get(position) != null){
            throw new IllegalArgumentException(position + " is occupied");
        }
        else throw new IllegalArgumentException(position + " is outside the map");
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
