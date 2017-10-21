package com.ucd.comp41690.team21.zenze.Game.Commands;

import com.ucd.comp41690.team21.zenze.Game.GameObject;

/**
 * Created by annalena on 21.10.17.
 */

public class MoveHorizontal implements Command {
    private float speed;
    public MoveHorizontal(float speed){
        this.speed = speed;
    }
    @Override
    public void execute(GameObject actor) {
        actor.x_Pos += speed;
    }
}
