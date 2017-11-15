package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 21.10.17.
 */

public class Jump implements Command {
    @Override
    public void execute(GameObject actor) {
        actor.physics.setVelocityY(-10.666666f);
    }

    @Override
    public void exit(GameObject actor) {
        if(actor.physics.getVelocityY()<-5.333333){
            actor.physics.setVelocityY(-5.333333f);
        }
    }

    @Override
    public void enter(GameObject actor) {

    }
}
