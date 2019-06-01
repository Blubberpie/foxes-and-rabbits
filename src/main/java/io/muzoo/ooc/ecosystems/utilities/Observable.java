package io.muzoo.ooc.ecosystems.utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<S extends Observable<S, O, A, B>,
                                 O extends Observer<S, O, A, B>,
                                 A, B>{

    protected List<O> observers;
    private boolean changed;

    public Observable() { this.observers = new ArrayList<>(); }

    public void addObserver (O observer){ this.observers.add(observer); }
    public void removeObserver (O observer){ this.observers.remove(observer); }

    @SuppressWarnings("unchecked")
    public void notifyObservers(A argument, B argument2){
        for (O observer: observers){
            observer.update((S) this, argument, argument2);
        }
    }

    public void setChanged() { this.changed = true; }
    public boolean isChanged() { return changed; }
}
