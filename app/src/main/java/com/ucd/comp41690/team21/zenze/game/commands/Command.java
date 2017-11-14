package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 *
 */
public interface Command {
    void execute(GameObject actor);
    void exit(GameObject actor);
    void enter(GameObject actor);
}
