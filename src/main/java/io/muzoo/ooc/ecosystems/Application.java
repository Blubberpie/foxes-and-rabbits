package io.muzoo.ooc.ecosystems;

import io.muzoo.ooc.ecosystems.utilities.Simulator;

public class Application {
    public static void main(String[] args) {
        Simulator sim = new Simulator(100, 180);
        sim.simulate(1000);
        System.exit(0);
    }

}
