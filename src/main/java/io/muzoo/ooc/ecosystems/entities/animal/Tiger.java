package io.muzoo.ooc.ecosystems.entities.animal;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.entities.LifeFormHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static io.muzoo.ooc.ecosystems.entities.LifeFormMeta.*;

/**
 * A simple model of a tiger.
 * Tigers age, move, eat rabbits and foxes, and die.
 *
 * @author Zwel Pai
 */
public class Tiger extends Carnivore {

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a tiger. A tiger can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the tiger will have random age and hunger level.
     */
    public Tiger(boolean randomAge) {
        super(randomAge, TIGER.getMaxAge(), rand,
                new LinkedHashMap<Class, Integer>(){{
                    put(RABBIT.getSpecies(), 4);
                    put(RABBIT.getSpecies(), 8);
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
        incrementAge(TIGER.getMaxAge());
        incrementHunger();
        if (isAlive()) {
            // New tigers are born into adjacent locations.
            int births = breed(rand, TIGER.getBreedingAge(), TIGER.getBreedingProbability(), TIGER.getMaxLitterSize());
            for (int b = 0; b < births; b++) {
                LifeFormHandler.createAnimal(TIGER.getSpecies(), updatedField, newAnimals, getLocation());
            }
            eatOrDie(currentField, updatedField, this);
        }
    }
}