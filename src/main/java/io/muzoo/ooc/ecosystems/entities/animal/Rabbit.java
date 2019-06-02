package io.muzoo.ooc.ecosystems.entities.animal;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.utilities.Location;
import io.muzoo.ooc.ecosystems.entities.LifeFormHandler;

import java.util.List;
import java.util.Random;

import static io.muzoo.ooc.ecosystems.entities.LifeFormMeta.RABBIT;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Rabbit extends Herbivore{

    // A shared random number generator to control breeding.
    private static final Random rand = new Random();

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     *
     * @param randomAge If true, the rabbit will have a random age.
     */
    public Rabbit(boolean randomAge) {
        super();
        if (randomAge) {
            setAge(rand.nextInt(RABBIT.getMaxAge()));
        }
    }

    public void act(Field currentField, Field updatedField, List<Animal> newAnimals) {
        incrementAge(RABBIT.getMaxAge());
        if (isAlive()) {
            int births = breed(rand, RABBIT.getBreedingAge(), RABBIT.getBreedingProbability(), RABBIT.getMaxLitterSize());
            for (int b = 0; b < births; b++) {
                LifeFormHandler.createAnimal(RABBIT.getSpecies(), updatedField, newAnimals, getLocation());
            }
            Location newLocation = updatedField.freeAdjacentLocation(getLocation());
            // Only transfer to the updated field if there was a free location
            if (newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            } else {
                // can neither move nor stay - overcrowding - all locations taken
                die();
            }
        }
    }
}
