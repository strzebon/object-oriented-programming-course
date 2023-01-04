package agh.ics.oop;

import java.util.Comparator;

public class ToxicityComparator implements Comparator<Vector2d> {

    @Override
    public int compare(Vector2d u, Vector2d v) {
        if (WorldMap.deathMap[u.x][u.y] != WorldMap.deathMap[v.x][v.y]){
            return WorldMap.deathMap[u.x][u.y] - WorldMap.deathMap[v.x][v.y];
        }
        if (u.x != v.x) return u.x - v.x;
        return u.y - v.y;
    }
}
