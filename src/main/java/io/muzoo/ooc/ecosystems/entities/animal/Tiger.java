package io.muzoo.ooc.ecosystems.entities.animal;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.utilities.Location;
import io.muzoo.ooc.ecosystems.entities.LifeFormHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * A simple model of a tiger.
 * Tigers age, move, eat rabbits and foxes, and die.
 *
 * @author Zwel Pai
 */
public class Tiger extends Carnivore {

    // The age at which a tiger can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a tiger can live.
    private static final int MAX_AGE = 300;
    // The likelihood of a tiger breeding.
    private static final double BREEDING_PROBABILITY = 0.04;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     */
    public Tiger(boolean randomAge) {
        super(randomAge, MAX_AGE, rand,
                new LinkedHashMap<Class, Integer>(){{
                    put(Rabbit.class, 4);
                    put(Fox.class, 8);
        }});
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
            // New tigers are born into adjacent locations.
            int births = breed(rand, BREEDING_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE);
            for (int b = 0; b < births; b++) {
                LifeFormHandler.createAnimal(Tiger.class, updatedField, newAnimals, getLocation());
            }
            eatOrDie(currentField, updatedField, this);
        }
    }
}