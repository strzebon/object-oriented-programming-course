package agh.ics.oop;
public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.direction = MapDirection.NORTH;
        this.position = initialPosition;
        this.map = map;
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

    public MapDirection getDirection(){
        return this.direction;
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                if(map.canMoveTo(this.position.add(this.direction.toUnitVector()))){
                    this.position = this.position.add(this.direction.toUnitVector());
                }
            }
            case BACKWARD -> {
                if(map.canMoveTo(this.position.subtract(this.direction.toUnitVector()))){
                    this.position = this.position.subtract(this.direction.toUnitVector());
                }
            }
        }
    }
}


