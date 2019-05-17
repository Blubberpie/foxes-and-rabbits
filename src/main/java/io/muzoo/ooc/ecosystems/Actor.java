package io.muzoo.ooc.ecosystems;

public abstract class Actor extends LifeForm {

    // The actor's fullness which
    // gets
    private int fullness;

    // -1 = Hungry
    // 0 = Neutral
    // 1 = Full
    private int hungerStatus;

    public void incrementFullness(int hungerBoost, int HUNGER_THRESHOLD){
        fullness += hungerBoost;
        setHungerStatus(HUNGER_THRESHOLD);
    }

    public void decrementFullness(int MAX_DEPLETION_RATE, int HUNGER_THRESHOLD){
        fullness -= (MAX_DEPLETION_RATE * (1 - (getFullness() / HUNGER_THRESHOLD)));
        setHungerStatus(HUNGER_THRESHOLD);
    }

    public void setHungerStatus(int HUNGER_THRESHOLD){
        if (fullness > (HUNGER_THRESHOLD * 0.8)) {
            this.hungerStatus = 1;
//            this.fullness = (int) Math.round(HUNGER_THRESHOLD * 0.8);
            this.fullness = HUNGER_THRESHOLD;
        }
        else if (fullness < (HUNGER_THRESHOLD * 0.2)) {
            this.hungerStatus = -1;
//            this.fullness = (int) Math.round(HUNGER_THRESHOLD * 0.2);
            this.fullness = 0;
        }
        else this.hungerStatus = 0;
    }


    public int getFullness() { return fullness; }

    public int getHungerStatus() { return hungerStatus; }

    public void setFullness(int fullness) { this.fullness = fullness; }
}
