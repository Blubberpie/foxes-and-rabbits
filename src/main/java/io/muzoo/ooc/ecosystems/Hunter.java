package io.muzoo.ooc.ecosystems;

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

    public void act(Field currentField, Field updatedField){
        decrementFullness(MAX_DEPLETION_RATE, HUNGER_THRESHOLD);
        if(getHungerStatus() == -1){

        }else if(getHungerStatus() == 0){

        }else if(getHungerStatus() == 1){

        }
    }
}
