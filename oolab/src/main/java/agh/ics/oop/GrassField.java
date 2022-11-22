package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrassField extends AbstractWorldMap{
    private final int quantity;
    private final List<Grass> grasses;

    public GrassField(int quantity){
        this.quantity = quantity;
        this.grasses = new ArrayList<>();
        this.lowerLeft = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.animals = new ArrayList<>();
        this.visualizer = new MapVisualizer(this);

        int range = (int) Math.sqrt(quantity * 10) + 1;
        Random generator = new Random();
        int counter = 0;
        while(counter < quantity){
            int x = generator.nextInt(range);
            int y = generator.nextInt(range);
            Vector2d position = new Vector2d(x, y);
            boolean placed = false;
            for(Grass grass: this.grasses){
                if(grass.getPosition().equals(position)){
                    placed = true;
                    break;
                }
            }
            if(!placed){
                grasses.add(new Grass(position));
                counter ++;
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for(Grass grass: this.grasses) {
            if (grass.getPosition().equals(position)) return true;
        }
        return super.isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        for(Grass grass: this.grasses) {
            if (grass.getPosition().equals(position)) return grass;
        }
        return super.objectAt(position);
    }

    @Override
    Vector2d checkLowerLeft() {
        Vector2d realLowerLeft = this.upperRight;
        for(Animal animal: this.animals){
            realLowerLeft = realLowerLeft.lowerLeft(animal.getPosition());
        }
        for(Grass grass: this.grasses){
            realLowerLeft = realLowerLeft.lowerLeft(grass.getPosition());
        }
        return realLowerLeft;
    }

    @Override
    Vector2d checkUpperRight() {
        Vector2d realUpperRight = this.lowerLeft;
        for(Animal animal: this.animals){
            realUpperRight = realUpperRight.upperRight(animal.getPosition());
        }
        for(Grass grass: this.grasses){
            realUpperRight = realUpperRight.upperRight(grass.getPosition());
        }
        return realUpperRight;
    }
}
