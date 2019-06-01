package io.muzoo.ooc.ecosystems.entities;

import io.muzoo.ooc.ecosystems.entities.actor.Hunter;
import io.muzoo.ooc.ecosystems.entities.animal.Fox;
import io.muzoo.ooc.ecosystems.entities.animal.Rabbit;
import io.muzoo.ooc.ecosystems.entities.animal.Tiger;

import java.awt.*;

public enum LifeFormMeta {
    // Ordered DESCENDING by creation probability
    RABBIT(Rabbit.class, 0.8, Color.orange),
    FOX(Fox.class, 0.14, Color.cyan),
    TIGER(Tiger.class, 0.05977, Color.red),
    HUNTER(Hunter.class, 0.00023, Color.black);

    private Class species;
    private double creationProbability;
    private Color color;

    LifeFormMeta(Class species, double creationProbability, Color color){
        this.species = species;
        this.creationProbability = creationProbability;
        this.color = color;
    }

    @Override
    public String toString(){ return this.name().toLowerCase(); }

    public Class getSpecies() { return species; }
    public double getCreationProbability() { return creationProbability; }
    public Color getColor() { return color; }
}
