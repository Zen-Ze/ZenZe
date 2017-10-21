package com.ucd.comp41690.team21.zenze.Game;

import android.view.InputEvent;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

/**
 * basic subject class for the observer pattern
 */
public abstract class Subject {
    private List<InputObserver> inputObserverList;

    public Subject(){
        inputObserverList = new LinkedList<>();
    }

    public void addInputObserver(InputObserver observer){
        inputObserverList.add(observer);
    }

    public void removeInputObserver(InputObserver observer){
        inputObserverList.remove(observer);
    }

    public void notify(InputEvent event){
        for (InputObserver observer: inputObserverList) {
            observer.onNotify(event);
        }
    }
}
