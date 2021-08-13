package com.somnus.server.somnuslb.mirrors;

import com.somnus.server.somnuslb.observer.Observable;
import com.somnus.server.somnuslb.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class MirrorObservable implements Observable {

    private List<Observer> observers;

    public MirrorObservable() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyAddition(Object o) {
        for(Observer observer : observers)
            observer.add(o);
    }

    @Override
    public void notifyRemoval(Object o) {
        for(Observer observer : observers)
            observer.remove(o);
    }
}
