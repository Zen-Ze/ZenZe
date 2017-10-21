package com.ucd.comp41690.team21.zenze.Game.Components;

import android.view.InputEvent;

import com.ucd.comp41690.team21.zenze.Game.Command;

/**
 * Created by annalena on 19.10.17.
 */

public interface InputComponent {
    Command handleInput(InputEvent event);
}
