package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.components.PlayerPhysics;

/**
 * Created by annalena on 21.10.17.
 * makes an object jump
 */

public class Jump implements Command {
    /**
     * allows no double jumps, increases an objects y velocity according to its desired jump height
     * @param actor the object which should jump
     */
    @Override
    public void execute(GameObject actor) {
        if(!((PlayerPhysics) actor.physics).isJumping) {
            actor.physics.y_Vel = ((PlayerPhysics) actor.physics).getJumpVelocity();
            ((PlayerPhysics) actor.physics).isJumping = true;
        }
    }

    /**
     * is called if the user ceases to touch the screen before reaching the maximum jump height
     * decreases the velocity to make the object fall down again
     * @param actor the object which should stop jumping
     */
    @Override
    public void exit(GameObject actor) {
        if(actor.physics.y_Vel < ((PlayerPhysics)actor.physics).getEarlyJumpVelocity()){
            actor.physics.y_Vel = ((PlayerPhysics)actor.physics).getEarlyJumpVelocity();
        }
    }
}
