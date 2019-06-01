package io.muzoo.ooc.ecosystems.entities;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.utilities.Location;
import io.muzoo.ooc.ecosystems.entities.actor.Actor;
import io.muzoo.ooc.ecosystems.entities.animal.Animal;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

public class LifeFormHandler {

    private static Random rand = new Random();

    /**
     * Iterate through every life form and place each on the field,
     * given their probabilities of creation.
     *
     * @param field The field on which lives will be created
     * @param animals The list of animals that have been created
     * @param actors The list of actors that have been created
     * @param row The location (row)
     * @param col The location (column)
     */
    public static void createAllLife(Field field, List<Animal> animals, List<Actor> actors, int row, int col){
        Double randDouble = rand.nextDouble();
        for(LifeFormMeta lifeFormMeta : LifeFormMeta.values()){
            Class species = lifeFormMeta.getSpecies();
            Double creationProbability = lifeFormMeta.getCreationProbability();
            if(randDouble <= creationProbability){
                if(Animal.class.isAssignableFrom(species)) {
                    createAnimal(species, field, animals, new Location(row, col));
                }else if(Actor.class.isAssignableFrom(species)){
                    createActor(species, field, actors, new Location(row, col));
                }
            } // else leave the location empty
        }
    }

    /**
     * Creates an actor at a given location.
     *
     * @param species A subclass of Actor
     * @param field The field on which the actor will be created
     * @param actors The list of actors that have been created
     * @param currentLocation The current location
     */
    public static void createActor(Class species, Field field, List<Actor> actors, Location currentLocation){
        try {
            Actor actor = (Actor) species.getConstructor().newInstance();
            actors.add(actor);
            Location newLocation = field.randomAdjacentLocation(currentLocation);
            actor.setLocation(newLocation);
            field.place(actor, newLocation);
        }catch(NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates an animal at a given location.
     *
     * @param species A subclass of Animal
     * @param field The field on which the animal will be created
     * @param animals The list of animals that have been created
     * @param currentLocation The current location
     */
    public static void createAnimal(Class species, Field field, List<Animal> animals, Location currentLocation){
        try {
            Animal animal = (Animal) species.getConstructor(boolean.class).newInstance(false);
            animals.add(animal);
            Location newLocation = field.randomAdjacentLocation(currentLocation);
            animal.setLocation(newLocation);
            field.place(animal, newLocation);
        }catch(NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}