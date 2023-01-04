package agh.ics.oop;
import agh.ics.oop.gui.*;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;

public class SimulationEngine implements Runnable{
    private final App app;
    private final WorldMap map;
    private final int delay;
    private final int dailyGrass;
    private final List<IRefreshObserver> observers = new ArrayList<>();
    private boolean stopped = false;

    public SimulationEngine(WorldMap map, App app, int delay, int startGrass, int dailyGrass, int startAnimals, int startEnergy, int genomeLength) {
        this.app = app;
        this.map = map;
        this.delay = delay;
        this.dailyGrass = dailyGrass;
        this.map.spawnGrass(startGrass);
        this.map.spawnAnimals(startAnimals, startEnergy, genomeLength);

    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        stopped = false;
        while (true) {
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            if (!stopped) {
                map.removeDeadAnimals();
                map.moveAnimals();
                map.eatGrass();
                map.reproduce();
                map.spawnGrass(dailyGrass);
                map.nextDay();
                notifyObservers();
            }
        }
    }

    public void stop(){
        stopped = true;
    }
    public void start() { stopped = false; }
    public void startThread() { stopped = false; }

    public void addObserver(IRefreshObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IRefreshObserver observer){
        observers.remove(observer);
    }

    private void notifyObservers(){
        for (IRefreshObserver observer : this.observers) {
            observer.refresh();
        }
    }
}
