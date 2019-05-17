package io.muzoo.ooc.ecosystems;

import java.util.List;
import java.util.Random;

public abstract class Animal extends LifeForm {

    public Animal(){
        setAge(0);
        setAlive();
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     *
     * @param currentField The field currently occupied.
     * @param updatedField The field to transfer to.
     * @param newAnimals A list to add newly born animals to.
     */
    abstract public void act(Field currentField, Field updatedField, List<Animal> newAnimals);

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     *
     * @return The number of births (may be zero).
     */
    public int breed(Random rand, int breedingAge, double breedingProbability, int maxLitterSize) {
        int births = 0;
        if (canBreed(breedingAge) && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }

    /**
     * An animal can breed if it has reached the breeding age.
     *
     * @return true if the animal can breed, false otherwise.
     */
    private boolean canBreed(int breedingAge) {
        return getAge() >= breedingAge;
    }

}
