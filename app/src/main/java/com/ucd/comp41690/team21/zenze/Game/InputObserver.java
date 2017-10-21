package com.ucd.comp41690.team21.zenze.Game;

import android.view.InputEvent;

/**
 * basic observer interface for an input event
*/
public abstract class InputObserver {
    public abstract void onNotify(InputEvent event);
}
