package io.muzoo.ooc.ecosystems;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LifeFormHandler {

    private static Random rand = new Random();

    // The probabilities animals will be created in any given grid positions.
    private static final Map<Class, Double> CREATION_PROBABILITIES = new LinkedHashMap<Class, Double>() {{
        put(Rabbit.class, 1-0.2);
        put(Fox.class, 1-0.06);
        put(Tiger.class, 1-0.04);
        put(Hunter.class, 1-0.00035);
    }};

    public static void createAllLife(Field field, List<Animal> animals, List<Actor> actors, int row, int col){
        Double randDouble = rand.nextDouble();
        for(Map.Entry<Class, Double> entry: CREATION_PROBABILITIES.entrySet()){
            Class species = entry.getKey();
            Double creationProbability = entry.getValue();
            if(randDouble >= creationProbability){
                if(Animal.class.isAssignableFrom(species)) {
                    createAnimal(species, field, animals, new Location(row, col));
                }else if(Actor.class.isAssignableFrom(species)){
                    createActor(species, field, actors, new Location(row, col));
                }
            } // else leave the location empty
        }
    }

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