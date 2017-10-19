package com.ucd.comp41690.team21.zenze.Game;

import com.ucd.comp41690.team21.zenze.Game.Components.InputComponent;
import com.ucd.comp41690.team21.zenze.Game.Components.PhysicsComponent;

/**
 * Created by annalena on 19.10.17.
 */

public class GameObject {
    private InputComponent input;
    private PhysicsComponent physics;

    private int x_Pos;
    private int y_Pos;

    public GameObject(InputComponent input, PhysicsComponent physics, int x, int y){
        this.physics = physics;
        this.input = input;
        x_Pos = x;
        y_Pos = y;
    }


    public int getX_Pos() {
        return x_Pos;
    }

    public int getY_Pos() {
        return y_Pos;
    }
}
