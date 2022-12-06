package agh.ics.oop;

import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private final Vector2dXComparator xComparator = new Vector2dXComparator();
    private final Vector2dYComparator yComparator = new Vector2dYComparator();
    private final TreeSet<Vector2d> xSet = new TreeSet<>(xComparator);
    private final TreeSet<Vector2d> ySet = new TreeSet<>(yComparator);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        this.remove(oldPosition);
        this.add(newPosition);
    }

    public void add(Vector2d position) {
        xSet.add(position);
        ySet.add(position);
    }

    public void remove(Vector2d position){
        xSet.remove(position);
        ySet.remove(position);
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(xSet.first().x, ySet.first().y);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(xSet.last().x, ySet.last().y);
    }
}
