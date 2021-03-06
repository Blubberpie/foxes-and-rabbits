package io.muzoo.ooc.ecosystems.entities.animal;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.utilities.Location;

import java.util.*;

public abstract class Carnivore extends Animal{

    // The animal's food level, which is increased by eating prey.
    private int foodLevel;

    // Mapping of an animal's prey to food value (the number
    // of steps the animal can go before it has to eat again).
    private static Map<Class, Integer> PREY_FOOD_VALUES;

    public Carnivore(boolean randomAge, int MAX_AGE, Random rand, Map<Class, Integer> preyFoodValues){
        super();
        setPreyFoodValues(preyFoodValues);
        if (randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            setFoodLevel(rand.nextInt(getMaxFoodLevel()));
        } else {
            // leave age at 0
            // todo: max or random?
            setFoodLevel(getMaxFoodLevel());
        }
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    public void incrementHunger() {
        setFoodLevel(getFoodLevel() - 1);
        if (getFoodLevel() <= 0) die();
    }

    public void eatOrDie(Field currentField, Field updatedField, Animal animal){
        // Move towards the source of food if found.
        Location newLocation = findFood(currentField, getLocation());
        if (newLocation == null) {  // no food found - move randomly
            newLocation = updatedField.freeAdjacentLocation(getLocation());
        }
        if (newLocation != null) {
            setLocation(newLocation);
            updatedField.place(animal, newLocation);
        } else {
            // can neither move nor stay - overcrowding - all locations taken
            animal.die();
        }
    }

    /**
     * Tell the animal to look for prey adjacent to its current location.
     *
     * @param field    The field in which it must look.
     * @param location Where in the field it is located.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object tmp = field.getObjectAt(where);
            if(!(tmp == null)) {
                if (Animal.class.isAssignableFrom(tmp.getClass())) {
                    Animal animal = (Animal) tmp;
                    if (devour(animal)) return where;
                }
            }
        }
        return null;
    }

    /**
     * Eat its prey. Don't eat if not prey.
     *
     * @param potentialPrey The animal
     * @return True if a prey has been eaten. False otherwise.
     */
    private boolean devour(Animal potentialPrey){
        for(Map.Entry<Class, Integer> entry: getPreyFoodValues().entrySet()){
            Class species = entry.getKey();
            Integer foodValue = entry.getValue();
            if(species.isInstance(potentialPrey)){
                if (potentialPrey.isAlive()){
                    potentialPrey.die();
                    setFoodLevel(foodValue);
                    return true;
                }
            }
        }
        return false;
    }

    private int getMaxFoodLevel(){
        return Collections.max(getPreyFoodValues().values());
    }

    public static Map<Class, Integer> getPreyFoodValues() { return PREY_FOOD_VALUES; }

    public int getFoodLevel() { return foodLevel; }

    public static void setPreyFoodValues(Map<Class, Integer> preyFoodValues) { PREY_FOOD_VALUES = preyFoodValues; }

    public void setFoodLevel(int foodLevel) { this.foodLevel = foodLevel; }
}
