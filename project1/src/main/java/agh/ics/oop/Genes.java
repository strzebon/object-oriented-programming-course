package agh.ics.oop;

import java.util.LinkedList;
import java.util.Random;

public class Genes {
    public int[] genes;
    private int iterator;
    private final int size;
    Random rnd = new Random();
    public Genes(int size){
        this.size = size;
        this.genes = new int[size];
        this.iterator = this.randomIterator();

        for(int i = 0; i<size; i++){
            int genotype = this.randomGene();
            this.genes[i] = genotype;
        }
    }
    public Genes(int[] genes1, int[] genes2, int side, int size, int amountOfStrongerGene) { // genes1 - stronger genes
        this.genes = new int[size];
        this.size = size;
        this.iterator = this.randomIterator();
        if (side == 0) { // left side of genes are from stronger animal
            if (amountOfStrongerGene >= 0) System.arraycopy(genes1, 0, this.genes, 0, amountOfStrongerGene);

            for (int i = amountOfStrongerGene; i < size; i++) {
                this.genes[i] = genes2[size - i + amountOfStrongerGene - 1];
            }
        }
        else {
            if (size - amountOfStrongerGene >= 0)
                System.arraycopy(genes2, 0, this.genes, 0, size - amountOfStrongerGene);

            for (int i = size - amountOfStrongerGene; i < size; i++) {
                this.genes[i] = genes1[2 * size - amountOfStrongerGene - i - 1];
            }
        }
    }

    public int[] getGenes(){
        return genes;
    }

    public int nextGene(){
        this.iterator = (iterator + 1) % size;
        return genes[iterator];
    }
    public int getActiveGene(){
        return genes[iterator];
    }

    private int randomGene(){
        return rnd.nextInt(8);
    }
    private int randomIterator(){
        return rnd.nextInt(size);
    }
    public void fullRandomness(int minMutation, int maxMutation){
        int amountOfMutation = maxMutation - minMutation;
        LinkedList<Integer> rndArray = new LinkedList<>();

        for(int i = 0; i < size; i++){
            rndArray.add(i);
        }

        if (amountOfMutation > 0) {
            int rndAmount = rnd.nextInt(amountOfMutation + 1) + minMutation;

            for(int i = 0; i < rndAmount; i++){
                int rndPosition = rnd.nextInt(rndArray.size());

                this.genes[rndArray.get(rndPosition)] = this.randomGene();

                rndArray.remove(rndPosition);
            }
        }
    }
    public void slightCorrection(int minMutation, int maxMutation){
        int amountOfMutation = maxMutation - minMutation;
        LinkedList<Integer> rndArray = new LinkedList<>();

        for(int i = 0; i < size; i++){
            rndArray.add(i);
        }

        if (amountOfMutation > 0) {
            int rndAmount = rnd.nextInt(amountOfMutation + 1) + minMutation;

            for(int i = 0; i < rndAmount; i++){
                int rndPosition = rnd.nextInt(rndArray.size());
                int tmp = rnd.nextInt(2);

                if (tmp == 0) this.genes[rndArray.get(rndPosition)] = (this.genes[rndArray.get(rndPosition)] + 1) % 8;
                else this.genes[rndArray.get(rndPosition)] = (this.genes[rndArray.get(rndPosition)] - 1) % 8;

                rndArray.remove(rndPosition);
            }
        }
    }
}
