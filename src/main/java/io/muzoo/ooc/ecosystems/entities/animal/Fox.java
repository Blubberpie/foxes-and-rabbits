package io.muzoo.ooc.ecosystems.entities.animal;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.entities.LifeFormHandler;

import java.util.*;

import static io.muzoo.ooc.ecosystems.entities.LifeFormMeta.FOX;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Fox extends Carnivore{

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with random age.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     */
    public Fox(boolean randomAge) {
        super(randomAge, FOX.getMaxAge(), rand,
                new LinkedHashMap<Class, Integer>(){{ put(Rabbit.class, 4); }});
    }

    /**
     * This is what the animal does most of the time
     *
     * @param currentField The field currently occupied.
     * @param updatedField The field to transfer to.
     * @param newAnimals A list to add newly born animals to.
     */
    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge(FOX.getMaxAge());
        incrementHunger();
        if (isAlive()) {
            // New foxes are born into adjacent locations.
            int births = breed(rand, FOX.getBreedingAge(), FOX.getBreedingProbability(), FOX.getMaxLitterSize());
            for (int b = 0; b < births; b++) {
                LifeFormHandler.createAnimal(FOX.getSpecies(), updatedField, newAnimals, getLocation());
            }
            eatOrDie(currentField, updatedField, this);
        }
    }
}