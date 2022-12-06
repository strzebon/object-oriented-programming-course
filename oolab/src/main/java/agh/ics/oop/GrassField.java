package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> grasses;
    private final MapBoundary grassBoundary = new MapBoundary();


    public GrassField(int quantity){
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
                grassBoundary.add(position);
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
    public Vector2d checkLowerLeft() {
        Vector2d animalLowerLeft = animalBoundary.getLowerLeft();
        Vector2d grassLowerLeft = grassBoundary.getLowerLeft();
        return new Vector2d(Math.min(animalLowerLeft.x, grassLowerLeft.x), Math.min(animalLowerLeft.y, grassLowerLeft.y));
    }

    @Override
    public Vector2d checkUpperRight() {
        Vector2d animalUpperRight = animalBoundary.getUpperRight();
        Vector2d grassUpperRight = grassBoundary.getUpperRight();
        return new Vector2d(Math.max(animalUpperRight.x, grassUpperRight.x), Math.max(animalUpperRight.y, grassUpperRight.y));
    }
}
