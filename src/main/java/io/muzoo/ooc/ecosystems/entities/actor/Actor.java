package io.muzoo.ooc.ecosystems.entities.actor;

import io.muzoo.ooc.ecosystems.utilities.Field;
import io.muzoo.ooc.ecosystems.entities.LifeForm;

import java.util.List;

public abstract class Actor extends LifeForm {

    // The actor's fullness which
    // gets
    private int fullness;

    // -1 = Hungry
    // 0 = Neutral
    // 1 = Full
    private int hungerStatus;

    abstract public void act(Field currentField, Field updatedField, List<Actor> actors);

    /**
     * Make the actor more full.
     *
     * @param hungerBoost A value with which to increment fullness
     * @param HUNGER_THRESHOLD The fullest an actor can be
     */
    public void incrementFullness(int hungerBoost, int HUNGER_THRESHOLD){
        fullness += hungerBoost;
        setHungerStatus(HUNGER_THRESHOLD);
    }

    /**
     * Make the actor more hungry.
     *
     * @param MAX_DEPLETION_RATE The rate at which to deplete fullness
     * @param HUNGER_THRESHOLD The fullest an actor can be
     */
    public void decrementFullness(int MAX_DEPLETION_RATE, int HUNGER_THRESHOLD){
        fullness -= MAX_DEPLETION_RATE;
        setHungerStatus(HUNGER_THRESHOLD);
    }

    public void setHungerStatus(int HUNGER_THRESHOLD){
        if (fullness > (HUNGER_THRESHOLD * 0.8)) {
            this.hungerStatus = 1;
            this.fullness = (int) Math.round(HUNGER_THRESHOLD * 0.8);
//            this.fullness = HUNGER_THRESHOLD;
        }
        else if (fullness < (HUNGER_THRESHOLD * 0.2)) {
            this.hungerStatus = -1;
            this.fullness = (int) Math.round(HUNGER_THRESHOLD * 0.2);
//            this.fullness = 0;
        }
        else this.hungerStatus = 0;
    }


    public int getFullness() { return fullness; }

    public int getHungerStatus() { return hungerStatus; }

    public void setFullness(int fullness) { this.fullness = fullness; }
}
