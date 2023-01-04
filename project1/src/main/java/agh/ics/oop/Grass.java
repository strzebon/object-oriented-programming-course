package agh.ics.oop;

public class Grass implements IMapElement{
    private Vector2d position;
    private static int energy;
    public Grass(Vector2d position) {
        this.position = position;
    }
    public void setEnergy(int energy){
        Grass.energy = energy;
    }
    public int getEnergy(){
        return Grass.energy;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    @Override
    public String toString(){
        return "Grass: " + this.position.toString();
    }
    @Override
    public String getImagePath() {
        return "src/main/resources/carrot.png";
    }
}
