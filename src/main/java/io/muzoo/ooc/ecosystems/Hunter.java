package io.muzoo.ooc.ecosystems;

import java.util.Map;

public abstract class Hunter extends Actor{

    // Mapping of a hunter's possible targets
    // and their hunger-filling value.
    private static Map<Class, Integer> VICTIMS;
}
