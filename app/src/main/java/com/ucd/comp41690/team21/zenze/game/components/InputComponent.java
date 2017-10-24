package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.GameObject;

public interface InputComponent {
    Command handleInput(GameObject object);
}
