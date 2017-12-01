package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.components.PlayerPhysics;

/**
 * Created by annalena on 21.10.17.
 */

public class Jump implements Command {
    @Override
    public void execute(GameObject actor) {
        if(!((PlayerPhysics) actor.physics).isJumping) {
            actor.physics.y_Vel = ((PlayerPhysics) actor.physics).getJumpVelocity();
            ((PlayerPhysics) actor.physics).isJumping = true;
        }
    }

    @Override
    public void exit(GameObject actor) {
        if(actor.physics.y_Vel < ((PlayerPhysics)actor.physics).getEarlyJumpVelocity()){
            actor.physics.y_Vel = ((PlayerPhysics)actor.physics).getEarlyJumpVelocity();
        }
    }

    @Override
    public void enter(GameObject actor) {

    }
}
