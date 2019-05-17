package io.muzoo.ooc.ecosystems;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Hunter extends Actor{

    private static final int MAX_DEPLETION_RATE = 5;
    private static final int HUNGER_THRESHOLD = 50;

    // Mapping of a hunter's possible targets
    // and their hunger-filling value.
    private static Map<Class, Integer> VICTIMS;

    public Hunter() {
        setFullness(0);
        setHungerStatus(HUNGER_THRESHOLD);
        VICTIMS = new LinkedHashMap<Class, Integer>(){{
            put(Tiger.class, 15);
            put(Fox.class, 8);
            put(Rabbit.class, 4);
        }};
    }

    public void act(Field currentField, Field updatedField, List<Actor> actors){
        decrementFullness(MAX_DEPLETION_RATE, HUNGER_THRESHOLD);
//        if(getHungerStatus() == -1){
//
//        }else if(getHungerStatus() == 0){
//
//        }else if(getHungerStatus() == 1){
//
//        }
        Location loc = hunt(currentField, getLocation());
        if (loc == null) {  // no food found - move randomly
            loc = updatedField.freeAdjacentLocation(getLocation());
        }
        if (loc != null) {
            setLocation(loc);
            updatedField.place(this, loc);
        } else {
            // can neither move nor stay - overcrowding - all locations taken
            die(); // todo: change
        }
    }

    private Location hunt(Field field, Location location){
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object tmp = field.getObjectAt(where);
            if(!(tmp == null)) {
                if (Animal.class.isAssignableFrom(tmp.getClass())) {
                    Animal animal = (Animal) tmp;
                    for (Map.Entry<Class, Integer> entry : VICTIMS.entrySet()) {
                        Class species = entry.getKey();
                        if (species.isInstance(animal)) {
                            if (animal.isAlive()) {
                                animal.die();
                                return where;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private int commitFrenzy(Field field, Location location){
        return 0;
    }
}
