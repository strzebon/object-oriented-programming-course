package agh.ics.oop;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public String toString() {
        return switch (this) {
            case EAST -> "Wschód";
            case WEST -> "Zachód";
            case NORTH -> "Północ";
            case SOUTH -> "Południe";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case EAST -> SOUTH;
            case WEST -> NORTH;
            case NORTH -> EAST;
            case SOUTH -> WEST;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case EAST -> NORTH;
            case WEST -> SOUTH;
            case NORTH -> WEST;
            case SOUTH -> EAST;
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1, 0);
            case NORTH -> new Vector2d(0, 1);
            case SOUTH -> new Vector2d(0, -1);
        };
    }
    public static void main(String[] args){
        MapDirection direction = MapDirection.NORTH;
        System.out.println(direction);
        System.out.println(direction.next());
        System.out.println(direction.previous());
        System.out.println(direction.toUnitVector());
    }

}
