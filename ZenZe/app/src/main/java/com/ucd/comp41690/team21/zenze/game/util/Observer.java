package com.ucd.comp41690.team21.zenze.game.util;


/**
 * basic observer interface for an input event
*/
public interface Observer<T> {
    void onNotify(T event);
}
