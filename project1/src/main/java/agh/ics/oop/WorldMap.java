package agh.ics.oop;

import java.util.*;

public class WorldMap implements IPositionChangeObserver{
    private final int width;
    private final int height;
    private final int mapVariant;
    private final int grassProfit;
    private final int requiredEnergy;
    private final int reproductionCost;
    private final int minMutation;
    private final int maxMutation;
    private final int mutationVariant;
    private final Vector2d lowerLeft = new Vector2d(0, 0);
    private final Vector2d upperRight;
    private final AnimalComparator animalComparator = new AnimalComparator();
    private final Map<Vector2d, TreeSet<Animal>> animalsMap = new HashMap<>();
    private final LinkedList<Animal> animals = new LinkedList<>();
    private final LinkedList<Grass> grasses = new LinkedList<>();
    private final ToxicityComparator toxicityComparator = new ToxicityComparator();
    public static int[][] deathMap;
    private final TreeSet<Vector2d> uncoveredJungle  = new TreeSet<>(toxicityComparator);
    private final TreeSet<Vector2d> uncoveredSteppes  = new TreeSet<>(toxicityComparator);
    private Vector2d jungleLowerLeft;
    private Vector2d jungleUpperRight;
    private int sumOfLifetimesOfDeadAnimals = 0;
    private int numberOfDeadAnimals = 0;
    private int day = 0;

    public WorldMap(int width, int height, int mapVariant, int grassProfit, int requiredEnergy, int reproductionCost, int minMutation, int maxMutation, int mutationVariant){
        this.width = width;
        this.height = height;
        this.mapVariant =  mapVariant;
        this.grassProfit =  grassProfit;
        this.requiredEnergy = requiredEnergy;
        this.reproductionCost = reproductionCost;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.mutationVariant = mutationVariant;
        this.upperRight = new Vector2d(width - 1, height - 1);
        deathMap = new int[width][height];
        initJungleAndSteps();
    }

    private boolean belongsToJungle(Vector2d position){
        return position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight);
    }

    private void initJungleAndSteps() {
        jungleLowerLeft = new Vector2d(width /4, height /4);
        jungleUpperRight = upperRight.subtract(jungleLowerLeft);
        for (int x=0; x<width; x++) for (int y=0; y<height; y++) {
            Vector2d position = new Vector2d(x, y);
            if (belongsToJungle(position)) uncoveredJungle.add(position);
            else uncoveredSteppes.add(position);
        }
    }


    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) && position.precedes(this.upperRight);
    }

    private void place(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.add(animal);
        TreeSet<Animal> treeSet = animalsMap.computeIfAbsent(position, k -> new TreeSet<>(animalComparator));
        treeSet.add(animal);
        animal.addObserver(this);
    }

    public void removeDeadAnimals() {
        LinkedList<Animal> animalsToRemove = new LinkedList<>();
        for (Animal animal: animals){
            if (animal.getEnergy() <= 0) {
                Vector2d position = animal.getPosition();
                deathMap[position.x][position.y] ++;
                animalsToRemove.add(animal);
                animalsMap.get(animal.getPosition()).remove(animal);
                sumOfLifetimesOfDeadAnimals += animal.getAge();
                numberOfDeadAnimals ++;
            }
        }
        for (Animal animal : animalsToRemove){
            animals.remove(animal);
        }
    }

    public void moveAnimals() {
        for (Animal animal: animals){
            animal.move();
        }
    }

//    public void eatGrass() {
//        for (Grass grass: grasses){
//            Vector2d position = grass.getPosition();
//            TreeSet<Animal> competitors = animalsMap.get(position);
//            if (competitors != null && competitors.size() > 0){
//                competitors.first().changeEnergy(grassProfit);
//                competitors.first().increaseEatenGrasses();
//                grasses.remove(grass);
//                if (belongsToJungle(position)) uncoveredJungle.add(position);
//                else uncoveredSteppes.add(position);
//            }
//        }
//    }
    public void eatGrass() {
        LinkedList<Grass> grassesToRemove = new LinkedList<>();
        for (Grass grass: grasses){
            Vector2d position = grass.getPosition();
            TreeSet<Animal> competitors = animalsMap.get(position);
            if (competitors != null && !competitors.isEmpty()){
                competitors.first().changeEnergy(grassProfit);
                competitors.first().increaseEatenGrasses();
                grassesToRemove.add(grass);
                if (belongsToJungle(position)) uncoveredJungle.add(position);
                else uncoveredSteppes.add(position);
            }
        }

        for (Grass grass: grassesToRemove){
            grasses.remove(grass);
        }
    }
    public void reproduce() {
        for (Map.Entry<Vector2d, TreeSet<Animal>> set : animalsMap.entrySet()){
            TreeSet<Animal> competitors = set.getValue();
            if (competitors.size() >= 2){
                Iterator<Animal> it = competitors.iterator();
                Animal strongerParent = it.next();
                Animal weakerParent = it.next();
                if (weakerParent.getEnergy() >= requiredEnergy){
                    Animal child = new Animal(this, strongerParent, weakerParent, 2*reproductionCost, minMutation, maxMutation, mutationVariant);
                    strongerParent.changeEnergy(-reproductionCost);
                    weakerParent.changeEnergy(-reproductionCost);
                    strongerParent.increaseNumberOfChildren();
                    weakerParent.increaseNumberOfChildren();
                    place(child);
                }
            }
        }
    }

    public void spawnAnimals(int number, int startEnergy, int genomeLength){
        Random generator = new Random();
        for (int i=0; i<number; i++) {
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            Vector2d position = new Vector2d(x, y);
            Animal animal = new Animal(this, position, startEnergy, genomeLength);
            place(animal);
        }
    }

    public void spawnGrass(int number) {
        Random generator = new Random();
        for (int i=0; i<number; i++) {
            int n = generator.nextInt(4);
            if (n == 0) spawnGrassInField(uncoveredSteppes);
            else spawnGrassInField(uncoveredJungle);
        }
    }

    private void spawnGrassInField(TreeSet<Vector2d> uncoveredField){
        if (uncoveredField.isEmpty()) return;
        int k = 0;
        int min = deathMap[uncoveredField.first().x][uncoveredField.first().y];
        for (Vector2d pos : uncoveredField){
            if (deathMap[pos.x][pos.y] > min) break;
            k++;
        }

        Random generator = new Random();
        int index = generator.nextInt(k);
        Iterator<Vector2d> it = uncoveredField.iterator();
        for (int i=0; i<index; i++) it.next();
        Vector2d position = it.next();
        Grass grass = new Grass(position);
        grasses.add(grass);
        uncoveredField.remove(position);
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d potentialPosition) {
        Vector2d newPosition;
        if (canMoveTo(potentialPosition)) {
            newPosition = potentialPosition;
        } else if (mapVariant == 0) {
            if (potentialPosition.y > upperRight.y || potentialPosition.y < lowerLeft.y) {
                animal.turnBack();
                newPosition = oldPosition;
            } else {
                if (potentialPosition.x < 0) newPosition = new Vector2d(upperRight.x, potentialPosition.y);
                else newPosition = new Vector2d(0, potentialPosition.y);
            }
        } else {
            animal.changeEnergy(-reproductionCost);
            Random generator = new Random();
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            newPosition = new Vector2d(x, y);
        }

        animal.changePosition(newPosition);
        animalsMap.get(oldPosition).remove(animal);
        TreeSet<Animal> treeSet = animalsMap.computeIfAbsent(newPosition, k -> new TreeSet<>(animalComparator));
        treeSet.add(animal);

    }

    public int getNumberOfAnimals(){
        return animals.size();
    }

    public int getNumberOfGrasses(){
        return grasses.size();
    }

    public int getNumberOfUncoveredSquares(){
        return uncoveredJungle.size() + uncoveredSteppes.size();
    }

    public int[] getMostPopularGenotype(){
        int max = 0;
        int[] mostPopular = {};
        for (Animal animal: animals){
            int x = 0;
            int[] genotype = animal.getGenotype();
            for (Animal other: animals){
                if (Arrays.equals(genotype, other.getGenotype())) x++;
            }
            if (x > max){
                max = x;
                mostPopular = genotype;
            }
        }
        return mostPopular;
    }

    public int getAverageEnergyOfAliveAnimals(){
        int s = 0;
        for (Animal animal : animals) s += animal.getEnergy();
        if (animals.size() == 0) return 0;
        return s / animals.size();
    }

    public int getAverageLifetimeOfDeadAnimals(){
        if (numberOfDeadAnimals == 0){return 0;}
        return sumOfLifetimesOfDeadAnimals / numberOfDeadAnimals;
    }

    public LinkedList<Animal> getAnimalsHavingMostPopularGenotype(){
        int[] genotype = getMostPopularGenotype();
        LinkedList<Animal> mostPopularAnimals = new LinkedList<>();
        for (Animal animal : animals) {
            if (Arrays.equals(genotype, animal.getGenotype())) mostPopularAnimals.add(animal);
        }
        return mostPopularAnimals;
    }

    public void nextDay(){
        day ++;
        for (Animal animal : animals) {
            animal.increaseAge();
            animal.changeEnergy(-1);
        }
    }

    public int getDay() {
        return day;
    }

    public Vector2d getJungleLowerLeft() {
        return this.jungleLowerLeft;
    }
    public Vector2d getJungleUpperRight(){
        return this.jungleUpperRight;
    }
    public Vector2d getUpperRight(){
        return this.upperRight;
    }
    public LinkedList<Animal> getAnimals(){
        LinkedList<Animal> copyAnimals = new LinkedList<>();
        for (Map.Entry<Vector2d, TreeSet<Animal>> set : animalsMap.entrySet()){
            TreeSet<Animal> competitors = set.getValue();
            if (competitors.size() > 0){
                copyAnimals.add(competitors.first());
            }
        }
        return copyAnimals;
    }
    public LinkedList<Grass> getGrasses(){
        return this.grasses;
    }
    public Object objectAt(Vector2d position) {
        TreeSet<Animal> positions = this.animalsMap.get(position);
        if (positions == null || positions.size() == 0) {
            return getGrassFromPosition(position);
        } else
            return positions.first();
    }

    public Object getGrassFromPosition(Vector2d position){
        for (Grass grass : grasses){
            if (grass.getPosition().equals(position)){
                return grass;
            }
        }
        return null;
    }
}
