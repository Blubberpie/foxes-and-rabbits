package io.muzoo.ooc.ecosystems.utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable{

    protected List<Observer> observers;
    private boolean changed;

    public Observable() { this.observers = new ArrayList<>(); }

    public void addObserver (Observer observer){ this.observers.add(observer); }
    public void removeObserver (Observer observer){ this.observers.remove(observer); }

    public void notifyObservers(){
        for (Observer observer: observers){
            observer.update(this);
        }
    }

    public void setChanged() { this.changed = true; }
    public boolean isChanged() { return changed; }
}
