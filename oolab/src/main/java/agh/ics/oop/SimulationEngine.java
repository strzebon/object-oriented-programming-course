package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    private final List<MoveDirection> directions;
    private final IWorldMap map;
    private final List<Animal> animals;

    public SimulationEngine(MoveDirection[] directions, IWorldMap map, Vector2d[] positions){
        this.directions = List.of(directions);
        this.map = map;
        this.animals = new ArrayList<>();
        for(Vector2d position: positions){
            Animal animal = new Animal(map, position);
            if(map.place(animal)) animals.add(animal);
        }
    }


    @Override
    public void run() {
        int i = 0;
        for(MoveDirection direction: directions){
            animals.get(i).move(direction);
            i = (i + 1) % animals.size();
            System.out.println(map);
        }
    }
}
