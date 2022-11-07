package agh.ics.oop;

public class OptionsParser {
    public static MoveDirection[] parse(String[] args){
        int size = 0;
        for(String arg: args){
            if( arg.equals("f") || arg.equals("forward") || arg.equals("b") || arg.equals("backward") || arg.equals("r") || arg.equals("right") || arg.equals("l") || arg.equals("left")){
                size++;
            }
        }
        MoveDirection[] directions = new MoveDirection[size];
        int i = 0;
        for(String arg: args){
            switch (arg) {
                case "f", "forward" -> {
                    directions[i] = MoveDirection.FORWARD;
                    i++;
                }
                case "b", "backward" -> {
                    directions[i] = MoveDirection.BACKWARD;
                    i++;
                }
                case "r", "right" -> {
                    directions[i] = MoveDirection.RIGHT;
                    i++;
                }
                case "l", "left" -> {
                    directions[i] = MoveDirection.LEFT;
                    i++;
                }
            }
        }
        return directions;
    }
}
