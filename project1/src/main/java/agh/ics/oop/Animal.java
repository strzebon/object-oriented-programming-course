package agh.ics.oop;
import java.util.ArrayList;
import java.util.Random;

public class Animal implements IMapElement{
    private WorldMap map;
    private int energy;
    public MapDirection direction;
    public ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private Genes genes;
    private int startingEnergy;
    private final int lengthOfGenes;
    private Vector2d position;
    private int age;
    private int children;
    private int eatenGrass;
    Random rnd = new Random();

    public Animal(WorldMap map, Animal strongerParent, Animal weakerParent, int energy, int minMutation, int maxMutation, int mutationVariant) {
        this.direction = MapDirection.NORTH.rotate(rnd.nextInt(8));
        this.rnd = new Random();
        this.map = map;
        this.startingEnergy = energy;
        this.position = strongerParent.getPosition();
        this.age = 0;
        this.children = 0;
        this.lengthOfGenes = strongerParent.getLengthOfGenes();
        this.eatenGrass = 0;
        this.energy = energy;

        int proportion = this.proportion(strongerParent.getEnergy(), weakerParent.getEnergy(), strongerParent.getLengthOfGenes());
        int side = rnd.nextInt(2);

        this.genes = new Genes(strongerParent.getGenotype(), weakerParent.getGenotype(), side, lengthOfGenes, proportion);
        if (mutationVariant == 0) this.genes.fullRandomness(minMutation, maxMutation);
        else this.genes.slightCorrection(minMutation, maxMutation);
    }

    public Animal(WorldMap map, Vector2d position, int startEnergy, int genomeLength) {
        this.direction = MapDirection.NORTH.rotate(rnd.nextInt(8));
        this.rnd = new Random();
        this.lengthOfGenes = genomeLength;
        this.startingEnergy = energy;
        this.genes = new Genes(genomeLength);
        this.position = position;
        this.age = 0;
        this.children = 0;
        this.energy = startEnergy;
        this.eatenGrass = 0;
        this.map = map;
    }


//    public Animal(Vector2d position, int startEnergy, int genomeLength, WorldMap map){
//        this(position, startEnergy, genomeLength);
//        this.map = map;
//    }

    public boolean isDead(){ // return true if this animal is dead
        return energy <= 0;
    }
    public int getLengthOfGenes(){
        return this.lengthOfGenes;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public int getEnergy() {
        return energy;
    }

    public void turnBack() {
        this.direction = this.direction.rotate(4);
    }

    public void changeEnergy(int i) {
        this.energy = this.energy + i;
    }

    public int getAge() {
        return age;
    }

    public int getChildren() {
        return children;
    }

    public void move() {
        int gene = genes.nextGene();
        MapDirection geneDirection = direction.rotate(gene);
        this.direction = geneDirection;

        Vector2d toMove = position.add(geneDirection.toUnitVector());


        Vector2d positionToDelete = new Vector2d(position.x, position.y);
//        this.position = this.position.add(geneDirection.toUnitVector());
        this.positionChanged(this, positionToDelete, toMove);
    }

    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
        for (IPositionChangeObserver observer : observers) {
            observer.positionChanged(animal, oldPosition, newPosition);
        }
    }
    public void changePosition(Vector2d newPosition) {
        this.position = newPosition;
    }
    public int[] getGenotype() {
        return genes.getGenes();
    }
    public void increaseAge() {
        this.age += 1;
    }
    public void increaseEatenGrasses() {
        this.eatenGrass += 1;
    }
    public void increaseNumberOfChildren() {
        this.children += 1;
    }
    public int proportion(int energy1, int energy2, int length){
        return (int) (energy1 / (energy1 + energy2)) * length;
    }
    @Override
    public String getImagePath() {
        if (this.energy >= 0.75 * this.startingEnergy)
            return "src/main/resources/green_rabbit.png";
        else if (this.energy >= 0.5 * this.startingEnergy)
            return "src/main/resources/light_green_rabbit.png";
        else if (this.energy >= 0.25 * this.startingEnergy)
            return "src/main/resources/yellow_rabbit.png";
        else
            return "src/main/resources/red_rabbit.png";
    }
    public String getColorImage(){
        if (this.energy >= 0.75 * this.startingEnergy)
            return "-fx-background-color: #1C130F";
        else if (this.energy >= 0.5 * this.startingEnergy)
            return "-fx-background-color: #DE6F0D";
        else if (this.energy >= 0.25 * this.startingEnergy)
            return "-fx-background-color: #C7C706";
        else
            return "-fx-background-color: #BA1E47";
    }
    public int getActiveGene(){
        return this.genes.getActiveGene();
    }
    public int getEatenGrass(){
        return this.eatenGrass;
    }
}