package io.muzoo.ooc.ecosystems;

import java.util.List;
import java.util.Random;

public abstract class Animal {

    // Individual characteristics (instance fields).

    // The fox's age.
    private int age;
    // Whether the fox is alive or not.
    private boolean alive;
    // The fox's position
    private Location location;

    public Animal(){
        setAge(0);
        this.alive = true;
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
     * Check whether the animal is alive or not.
     *
     * @return True if the animal is still alive.
     */
    public boolean isAlive() { return alive; }

    public void die() {
        alive = false;
    }

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
        return age >= breedingAge;
    }

    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    public void incrementAge(int maxAge) {
        setAge(getAge() + 1);
        if (getAge() > maxAge) { die(); }
    }

    public void setAge(int age){ this.age = age; }

    /**
     * Set the animal's location.
     *
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col) { this.location = new Location(row, col); }

    /**
     * Set the animal's location.
     *
     * @param location The fox's location.
     */
    public void setLocation(Location location) { this.location = location; }

    public int getAge() { return age; }

    public Location getLocation() { return location; }
}
