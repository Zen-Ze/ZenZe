package com.ucd.comp41690.team21.zenze.game.util;

/**
 * basic subject class for the observer pattern
 */
public interface Subject<T> {

    void addObserver(Observer<T> observer);

    void removeObserver(Observer<T> observer);

    void notify(T event);
}
