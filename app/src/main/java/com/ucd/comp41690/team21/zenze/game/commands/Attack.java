package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 26.11.17.
 */

public class Attack implements Command {
    @Override
    public void execute(GameObject actor) {
        Game.getInstance().getGameWorld().addNewObject(Game.getInstance().getGameWorld().getAttack());
    }

    @Override
    public void exit(GameObject actor) {

    }

    @Override
    public void enter(GameObject actor) {

    }
}
