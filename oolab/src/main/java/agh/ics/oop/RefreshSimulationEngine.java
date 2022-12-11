package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RefreshSimulationEngine implements Runnable{

    private List<MoveDirection> directions;
    private final IWorldMap map;
    private final List<Animal> animals;
    private final List<IRefreshObserver> observers;
    private final int moveDelay;

    public RefreshSimulationEngine(IWorldMap map, Vector2d[] positions, int moveDelay){
        this.map = map;
        this.animals = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.moveDelay = moveDelay;
        for(Vector2d position: positions){
            Animal animal = new Animal(map, position);
            animals.add(animal);
            map.place(animal);
        }
    }

    @Override
    public void run() {
        int i = 0;
        for(MoveDirection direction: directions){
            animals.get(i).move(direction);
            i = (i + 1) % animals.size();
            System.out.println(map);
            notifyObservers();
            try{
                Thread.sleep(moveDelay);
            }
            catch (InterruptedException ex){
                throw new RuntimeException(ex);
            }
        }
    }

    public void addObserver(IRefreshObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IRefreshObserver observer){
        observers.remove(observer);
    }

    void notifyObservers(){
        for (IRefreshObserver observer : this.observers) {
            observer.refresh();
        }
    }

    public void setDirections(MoveDirection[] directions) {
        this.directions = List.of(directions);
    }
}
