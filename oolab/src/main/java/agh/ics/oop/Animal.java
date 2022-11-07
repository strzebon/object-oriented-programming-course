package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString(){
        return this.position.toString() + ", " + this.direction.toString();
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
        Vector2d oldPosition = this.position;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> this.position = this.position.add(this.direction.toUnitVector());
            case BACKWARD ->  this.position = this.position.subtract(this.direction.toUnitVector());
        }

        if (!(this.position.follows(new Vector2d(0,0)) && this.position.precedes(new Vector2d(4,4)))){
            this.position = oldPosition;
        }
    }
}

