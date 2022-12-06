package agh.ics.oop;

import java.util.Comparator;

public class Vector2dYComparator implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d u, Vector2d v) {
        if (u.y != v.y) return u.y - v.y;
        return u.x - v.x;
    }
}
