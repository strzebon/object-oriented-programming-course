package agh.ics.oop;

import java.util.ArrayList;

public class Animal implements IMapElement {
    private final ArrayList<IPositionChangeObserver> observers;
    private MapDirection direction;
    private Vector2d position;
    private final IWorldMap map;

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.direction = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
        this.observers = new ArrayList<>();
    }

    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
    }

    public String toString(){
        return switch (this.direction){
            case NORTH -> "N";
            case EAST -> "E";
            case SOUTH -> "S";
            case WEST -> "W";
        };
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String getImage() {
        return switch (this.direction){
            case NORTH -> "src/main/resources/up.png";
            case EAST -> "src/main/resources/right.png";
            case SOUTH -> "src/main/resources/down.png";
            case WEST -> "src/main/resources/left.png";
        };
    }

    @Override
    public String toCaption() {
        return null;
    }

    public MapDirection getDirection(){
        return this.direction;
    }

    public IWorldMap getMap(){
        return this.map;
    }

    public void move(MoveDirection direction){
        Vector2d oldPosition;
        Vector2d newPosition;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                oldPosition = this.position;
                newPosition = this.position.add(this.direction.toUnitVector());
                if(map.canMoveTo(newPosition)){
                    this.position = newPosition;
                    this.positionChanged(oldPosition, newPosition);
                }
            }
            case BACKWARD -> {
                oldPosition = this.position;
                newPosition = this.position.subtract(this.direction.toUnitVector());
                if(map.canMoveTo(newPosition)) {
                    this.position = newPosition;
                    this.positionChanged(oldPosition, newPosition);
                }
            }
        }
    }

    void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : this.observers) {
            observer.positionChanged(oldPosition, newPosition);
        }
    }
}


