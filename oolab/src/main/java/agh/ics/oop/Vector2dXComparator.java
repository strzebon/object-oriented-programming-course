package agh.ics.oop;

import java.util.Comparator;

public class Vector2dXComparator implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d u, Vector2d v) {
        if (u.x != v.x) return u.x - v.x;
        return u.y - v.y;
    }
}
