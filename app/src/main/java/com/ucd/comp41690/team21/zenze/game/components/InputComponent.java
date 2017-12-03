package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * the input component reads the users input or generates one itself using an AI
 * and issues commands to execute predefined behaviour on a game object
 */
public interface InputComponent {
    /**
     * reads the user input or generates own input
     * @param object the object that should execute some behaviour
     */
    void handleInput(GameObject object);
}
