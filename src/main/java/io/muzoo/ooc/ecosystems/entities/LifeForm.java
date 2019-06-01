package io.muzoo.ooc.ecosystems.entities;

import io.muzoo.ooc.ecosystems.utilities.Location;
import io.muzoo.ooc.ecosystems.utilities.Observer;

public abstract class LifeForm {

    // Individual characteristics (instance fields).

    // The life form's age.
    private int age;
    // Whether the life form is alive or not.
    private boolean alive;
    // The life form's position
    private Location location;

    /**
     * Check whether the animal is alive or not.
     *
     * @return True if the animal is still alive.
     */
    public boolean isAlive() { return alive; }

    /**
     * Kill this life form.
     */
    public void die() {
        alive = false;
    }


    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    public void incrementAge(int maxAge) {
        setAge(getAge() + 1);
        if (getAge() > maxAge) { die(); }
    }

    public void setAge(int age){ this.age = age; }

    /**
     * Set the animal's location.
     *
     * @param row The vertical coordinate of the location.
     * @param col The horizontal coordinate of the location.
     */
    public void setLocation(int row, int col) { this.location = new Location(row, col); }

    /**
     * Set the animal's location.
     *
     * @param location The fox's location.
     */
    public void setLocation(Location location) { this.location = location; }

    public void setAlive() { this.alive = true; }

    public int getAge() { return age; }

    public Location getLocation() { return location; }
}
