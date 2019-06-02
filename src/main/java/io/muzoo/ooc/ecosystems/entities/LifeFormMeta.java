package io.muzoo.ooc.ecosystems.entities;

import io.muzoo.ooc.ecosystems.entities.actor.Hunter;
import io.muzoo.ooc.ecosystems.entities.animal.Fox;
import io.muzoo.ooc.ecosystems.entities.animal.Rabbit;
import io.muzoo.ooc.ecosystems.entities.animal.Tiger;

import java.awt.*;

public enum LifeFormMeta {
    // Ordered DESCENDING by creation probability
    RABBIT(Rabbit.class,
            Color.orange,
            0.9,
            10,
            150,
            0.06,
            4),
    FOX(Fox.class,
            Color.cyan,
            0.03,
            5,
            50,
            0.03,
            5),
    TIGER(Tiger.class,
            Color.red,
            0.0298,
            15,
            300,
            0.025,
            4),
    HUNTER(Hunter.class,
            Color.black,
            0.0002,
            20,
            500,
            0.002,
            2);

    private Class species;
    private double creationProbability;
    private Color color;
    private int breedingAge;
    private int maxAge;
    private double breedingProbability;
    private int maxLitterSize;

    LifeFormMeta(Class species,
                 Color color,
                 double creationProbability,
                 int breedingAge,
                 int maxAge,
                 double breedingProbability,
                 int maxLitterSize){
        this.species = species;
        this.creationProbability = creationProbability;
        this.color = color;
        this.breedingAge = breedingAge;
        this.maxAge = maxAge;
        this.breedingProbability = breedingProbability;
        this.maxLitterSize = maxLitterSize;
    }

    @Override
    public String toString(){ return this.name().toLowerCase(); }

    public Class getSpecies() { return species; }
    public double getCreationProbability() { return creationProbability; }
    public Color getColor() { return color; }
    public int getBreedingAge() { return breedingAge; }
    public double getBreedingProbability() { return breedingProbability; }
    public int getMaxAge() { return maxAge; }
    public int getMaxLitterSize() { return maxLitterSize; }
}
