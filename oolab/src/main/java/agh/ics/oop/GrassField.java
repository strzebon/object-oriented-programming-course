package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    private final int quantity;
    private final Map<Vector2d, Grass> grasses;


    public GrassField(int quantity){
        this.quantity = quantity;
        this.grasses = new HashMap<>();
        this.lowerLeft = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.upperRight = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.visualizer = new MapVisualizer(this);

        int range = (int) Math.sqrt(quantity * 10) + 1;
        Random generator = new Random();
        int counter = 0;
        while(counter < quantity){
            int x = generator.nextInt(range);
            int y = generator.nextInt(range);
            Vector2d position = new Vector2d(x, y);
            if(!isOccupied(position)){
                this.grasses.put(position, new Grass(position));
                counter++;
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if(this.grasses.get(position) != null) return true;
        return super.isOccupied(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(super.objectAt(position) == null) return this.grasses.get(position);
        return super.objectAt(position);
    }

    @Override
    Vector2d checkLowerLeft() {
        Vector2d realLowerLeft = this.upperRight;
        for(Vector2d position: this.animals.keySet()){
            realLowerLeft = realLowerLeft.lowerLeft(position);
        }
        for(Vector2d position: this.grasses.keySet()){
            realLowerLeft = realLowerLeft.lowerLeft(position);
        }
        return realLowerLeft;
    }

    @Override
    Vector2d checkUpperRight() {
        Vector2d realUpperRight = this.lowerLeft;
        for(Vector2d position: this.animals.keySet()){
            realUpperRight = realUpperRight.upperRight(position);
        }
        for(Vector2d position: this.grasses.keySet()){
            realUpperRight = realUpperRight.upperRight(position);
        }
        return realUpperRight;
    }
}
