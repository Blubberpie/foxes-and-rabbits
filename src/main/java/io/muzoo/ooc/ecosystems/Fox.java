package io.muzoo.ooc.ecosystems;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Fox extends Animal{
    // Characteristics shared by all foxes (static fields).

    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.09;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    // Individual characteristics (instance fields).

    // The fox's food level, which is increased by eating rabbits.
    private int foodLevel;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge) {
        super();
        if (randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(RABBIT_FOOD_VALUE));
        } else {
            // leave age at 0
            setFoodLevel(RABBIT_FOOD_VALUE);
        }
    }

    /**
     * This is what the animal does most of the time
     *
     * @param currentField The field currently occupied.
     * @param updatedField The field to transfer to.
     * @param newAnimals A list to add newly born animals to.
     */
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge(MAX_AGE);
        incrementHunger();
        if (isAlive()) {
            // New foxes are born into adjacent locations.
            int births = breed(rand, BREEDING_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE);
            for (int b = 0; b < births; b++) {
                AnimalFactory.createAnimal(Fox.class, updatedField, newAnimals, getLocation());
            }
            // Move towards the source of food if found.
            Location newLocation = findFood(currentField, getLocation());
            if (newLocation == null) {  // no food found - move randomly
                newLocation = updatedField.freeAdjacentLocation(getLocation());
            }
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                die();
            }
        }
    }

    /**
     * Tell the fox to look for rabbits adjacent to its current location.
     *
     * @param field    The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations =
                field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if (animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if (rabbit.isAlive()) {
                    rabbit.die();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }


    private void setFoodLevel(int foodLevel) { this.foodLevel = foodLevel; }

    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            die();
        }
    }
}
