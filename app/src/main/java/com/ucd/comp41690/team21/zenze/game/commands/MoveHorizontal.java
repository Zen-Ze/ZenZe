package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 21.10.17.
 * makes an object move in a horizontal direction according to the objects speed
 */

public class MoveHorizontal implements Command {

    public static int DIRECTION_LEFT = -1;
    public static int DIRECTION_RIGHT = 1;
    private int direction;

    /**
     * creates a new command for moving horizontally in the indicated direction
     * @param direction the direction to move in, use DIRECTION_LEFT and DIRECTION_RIGHT
     */
    public MoveHorizontal(int direction){
        this.direction = direction;
    }

    /**
     * make the object move in a direction according to its speed
     * @param actor the object to move
     */
    @Override
    public void execute(GameObject actor) {
        actor.physics.x_Vel = actor.type.getSpeed()*direction;
    }

    /**
     * make an object stop to move by setting its velocity to zero
     * @param actor the object to stop
     */
    @Override
    public void exit(GameObject actor) {
        actor.physics.x_Vel = 0;
    }
}
