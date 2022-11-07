package agh.ics.oop;

public class World {
    public static void main(String[] args){
        Animal animal = new Animal();
        System.out.println(animal);
//        System.out.println(animal.getPosition());
//        System.out.println(animal.getDirection());
//        System.out.println(animal);
//
//        animal.move(MoveDirection.RIGHT);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        animal.move(MoveDirection.FORWARD);
//        System.out.println(animal);


        for(MoveDirection arg: OptionsParser.parse(args)){
            animal.move(arg);
        }
        System.out.println(animal);
    }
}