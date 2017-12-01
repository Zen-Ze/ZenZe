package com.ucd.comp41690.team21.zenze.game.commands;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 21.10.17.
 */

public class MoveHorizontal implements Command {

    public static int DIRECTION_LEFT = -1;
    public static int DIRECTION_RIGHT = 1;
    private int direction;

    public MoveHorizontal(int direction){
        this.direction = direction;
    }

    @Override
    public void execute(GameObject actor) {
        actor.physics.x_Vel = actor.type.getSpeed()*direction;
    }

    @Override
    public void exit(GameObject actor) {
        actor.physics.x_Vel = 0;
    }

    @Override
    public void enter(GameObject actor) {

    }


}
