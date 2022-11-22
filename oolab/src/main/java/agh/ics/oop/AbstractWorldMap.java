package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap {

    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected MapVisualizer visualizer;
    protected List<Animal> animals = new ArrayList<>();

    abstract Vector2d checkLowerLeft();
    abstract Vector2d checkUpperRight();

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight) && !(objectAt(position) instanceof Animal);
    }

    @Override
    public boolean place(Animal animal) {
        if(!(objectAt(animal.getPosition()) instanceof Animal)){
            this.animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Animal animal: this.animals){
            if(animal.isAt(position)) return true;
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Animal animal: this.animals){
            if(animal.isAt(position)) return animal;
        }
        return null;
    }

    public String toString(){
        return this.visualizer.draw(checkLowerLeft(), checkUpperRight());
    }

}
