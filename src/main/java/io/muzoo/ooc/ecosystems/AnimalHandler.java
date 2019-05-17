package io.muzoo.ooc.ecosystems;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnimalHandler {

    private static Random rand = new Random();

    // The probabilities animals will be created in any given grid positions.
    private static final Map<Class, Double> CREATION_PROBABILITIES = new LinkedHashMap<Class, Double>() {{
        put(Fox.class, 0.02);
        put(Rabbit.class, 0.08);
    }};

    public static void createAllAnimals(Field field, List<Animal> animals, int row, int col){
        for(Map.Entry<Class, Double> entry: CREATION_PROBABILITIES.entrySet()){
            Class species = entry.getKey();
            Double creationProbability = entry.getValue();
            if(rand.nextDouble() <= creationProbability){
                try {
                    Animal animal = (Animal) species.getConstructor(boolean.class).newInstance(true);
                    animals.add(animal);
                    animal.setLocation(row, col);
                    field.place(animal, row, col);
                }catch(NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            } // else leave the location empty
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