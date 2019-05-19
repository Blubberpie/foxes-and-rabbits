package io.muzoo.ooc.ecosystems.entities.actor;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.utilities.Location;
import io.muzoo.ooc.ecosystems.entities.animal.Animal;
import io.muzoo.ooc.ecosystems.entities.animal.Fox;
import io.muzoo.ooc.ecosystems.entities.animal.Rabbit;
import io.muzoo.ooc.ecosystems.entities.animal.Tiger;

import java.util.*;

/**
 * The hunter does four main things:
 *
 * If starved, it will go on a frenzy and devour
 * everything around a certain radius.
 *
 * If full, it will wait until it gets hungry again.
 *
 * If neither of the above, it will eat whatever
 * is closest to it, and keep going.
 *
 * If it is trapped, it will destroy an entire column's
 * worth of life forms, with no fullness gain.
 *
 * Hunter is immortal as of this version
 *
 * @author Zwel Pai (5981061)
 */
public class Hunter extends Actor{

    private static final int MAX_DEPLETION_RATE = 3;
    private static final int HUNGER_THRESHOLD = 50;
    private static final int FRENZY_ZONE_DIAMETER = 20;
    private static final int FRENZY_ZONE_RADIUS = FRENZY_ZONE_DIAMETER/2;
    private static final int VOLLEY_ZONE_DIAMETER = 20;
    private static final int VOLLEY_ZONE_RADIUS = VOLLEY_ZONE_DIAMETER/2;
    private final Random rand = new Random();

    // Mapping of a hunter's possible targets
    // and their hunger-filling value.
    private static Map<Class, Integer> VICTIMS;

    public Hunter() {
        setFullness(rand.nextInt(HUNGER_THRESHOLD + 1));
        setHungerStatus(HUNGER_THRESHOLD);
        VICTIMS = new LinkedHashMap<Class, Integer>(){{
            put(Tiger.class, 15);
            put(Fox.class, 8);
            put(Rabbit.class, 4);
        }};
    }

    public void act(Field currentField, Field updatedField, List<Actor> actors){
        decrementFullness(MAX_DEPLETION_RATE, HUNGER_THRESHOLD);
        Location loc = getLocation();
        if(getHungerStatus() == -1){
            int kills = commitFrenzy(currentField, getLocation());
            incrementFullness(kills/2, HUNGER_THRESHOLD);
            loc = updatedField.freeAdjacentLocation(getLocation());
        }else if(getHungerStatus() == 0){
            loc = hunt(currentField, getLocation());
        }else if(getHungerStatus() == 1){
            decrementFullness(1, HUNGER_THRESHOLD);
        }

        if (loc == null) {  // no food found - move randomly
            loc = updatedField.freeAdjacentLocation(getLocation());
        }

        if (loc != null) {
            setLocation(loc);
            updatedField.place(this, loc);
        } else {
            // can neither move nor stay - overcrowding - all locations taken
//            die(); // todo: change
            commitVolley(currentField, getLocation());
        }


    }

    /**
     * Search and eat normally.
     *
     * @param field The field on which to search
     * @param location The current location
     * @return The location of the life form it ate
     */
    private Location hunt(Field field, Location location){
        Iterator adjacentLocations = field.adjacentLocations(location);
        while (adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            if(kill(field, where)){
                return where;
            }
        }
        return null;
    }

    /**
     * When starved, the Hunter will devour everything in its
     * immediate vicinity, and fill up its Hunger dramatically.
     *
     * @param field The field on which to search
     * @param location The current location
     * @return The number of kills
     */
    private int commitFrenzy(Field field, Location location){
        int successfulKills = 0;
        Location from = new Location(Math.max(0, location.getRow() - FRENZY_ZONE_RADIUS),
                                     Math.max(0, location.getCol() - FRENZY_ZONE_RADIUS));
        Iterator frenzyZone = field.getFrenzyZone(from, FRENZY_ZONE_DIAMETER);
        while (frenzyZone.hasNext()){
            if(kill(field, (Location) frenzyZone.next())){
                successfulKills++;
            }
        }
        return successfulKills;
    }

    /**
     * When trapped, the Hunter will destroy everything in a
     * column of death, for no gains in fullness.
     *
     * @param field The field on which to search
     * @param location The current location
     */
    private void commitVolley(Field field, Location location){
        Location from = new Location(0,
                Math.max(0, location.getCol() - VOLLEY_ZONE_RADIUS));
        Iterator volleyZone = field.getVolleyZone(from, VOLLEY_ZONE_DIAMETER);
        while (volleyZone.hasNext()){
            kill(field, (Location) volleyZone.next());
        }
    }

    /**
     * Kill a life form at a given location.
     *
     * @param field The field on which to search
     * @param where The location of the life form
     * @return True if a kill has been made. False otherwise.
     */
    private boolean kill(Field field, Location where){
        Object tmp = field.getObjectAt(where);
        if(!(tmp == null)){
            if (Animal.class.isAssignableFrom(tmp.getClass())){
                Animal animal = (Animal) tmp;
                if (animal.isAlive()){
                    animal.die();
                    return true;
                }
            }
        }
        return false;
    }
}
