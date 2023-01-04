package agh.ics.oop;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal a, Animal b) {
        if (a.getEnergy() != b.getEnergy()) return b.getEnergy() - a.getEnergy();
        if (a.getAge() != b.getAge()) return b.getAge() - a.getAge();
        return b.getChildren() - a.getChildren();
    }
}
