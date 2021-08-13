package com.somnus.server.somnuslb.observer;

public interface Observable {
    public void addObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyAddition(Object o);
    public void notifyRemoval(Object o);
}
