package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Interface for Commands having to methods execute and exit
 * enhances reusability of code and allows all kind of objects to have the same behaviour
 */
public interface Command {
    void execute(GameObject actor);
    void exit(GameObject actor);
}
