package io.muzoo.ooc.ecosystems.utilities;

import io.muzoo.ooc.ecosystems.entities.*;
import io.muzoo.ooc.ecosystems.entities.actor.Actor;
import io.muzoo.ooc.ecosystems.entities.animal.Animal;

import java.util.*;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kolling
 * @version 2002.10.28
 */
public class Simulator extends Observable{

    // The private static final variables represent 
    // configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 50;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 50;

    // The list of animals in the field
    private List<Animal> animals;
    // The list of animals just born
    private List<Animal> newAnimals;
    // The list of actors in the field
    private List<Actor> actors;
    // The list of actors just created
    private List<Actor> newActors;
    // The current state of the field.
    private Field field;
    // A second field, used to build the next stage of the simulation.
    private Field updatedField;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        animals = new ArrayList<>();
        newAnimals = new ArrayList<>();
        actors = new ArrayList<>();
        newActors = new ArrayList<>();
        field = new Field(depth, width);
        updatedField = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width, this);
        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     */
    public void runLongSimulation() {
        simulate(500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        newAnimals.clear();
        newActors.clear();

        // let all animals act
        for (Iterator<Animal> iter = animals.iterator(); iter.hasNext(); ) {
            Animal animal = iter.next();
            animal.act(field, updatedField, newAnimals);
            if(!animal.isAlive()){
                iter.remove();
            }
        }
        // let all actors act
        for (Iterator<Actor> iter = actors.iterator(); iter.hasNext(); ){
            Actor actor = iter.next();
            actor.act(field, updatedField, newActors);
        }
        // add new born animals to the list of animals
        animals.addAll(newAnimals);

        // Swap the field and updatedField at the end of the step.
        Field temp = field;
        field = updatedField;
        updatedField = temp;
        updatedField.clear();

        // display the new field on screen
        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        animals.clear();
        actors.clear();
        field.clear();
        updatedField.clear();
        populate(field);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Populate a field with foxes and rabbits.
     *
     * @param field The field to be populated.
     */
    private void populate(Field field) {
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                LifeFormHandler.createAllLife(field, animals, actors, row, col);
            }
        }
        Collections.shuffle(animals);
        Collections.shuffle(actors);
    }

    public void notifyObservers(){
        setChanged();
        super.notifyObservers();
    }
}
