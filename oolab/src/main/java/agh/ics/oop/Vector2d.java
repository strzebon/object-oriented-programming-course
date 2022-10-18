package agh.ics.oop;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(int x, int y){
        return "(" + x + "," + y + ")";
    }

    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other){
        int x = this.x + other.x;
        int y = this.y + other.y;
        return new Vector2d(x, y);
    }

    public Vector2d subtract(Vector2d other){
        int x = this.x - other.x;
        int y = this.y - other.y;
        return new Vector2d(x, y);
    }

    public Vector2d upperRight(Vector2d other){
        int x = Math.max(this.x, other.x);
        int y = Math.max(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d lowerLeft(Vector2d other){
        int x = Math.min(this.x, other.x);
        int y = Math.min(this.y, other.y);
        return new Vector2d(x, y);
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }

}
