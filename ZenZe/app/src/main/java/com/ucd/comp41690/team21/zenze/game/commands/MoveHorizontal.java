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
        actor.physics.setVelocityX(actor.type.speed*direction);
    }

    @Override
    public void exit(GameObject actor) {
        actor.physics.setVelocityX(0);
    }

    @Override
    public void enter(GameObject actor) {

    }


}
