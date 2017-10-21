package com.ucd.comp41690.team21.zenze.Game;

import com.ucd.comp41690.team21.zenze.Game.Commands.Command;
import com.ucd.comp41690.team21.zenze.Game.Components.InputComponent;
import com.ucd.comp41690.team21.zenze.Game.Components.PhysicsComponent;

/**
 * Created by annalena on 19.10.17.
 */

public class GameObject {
    public static final String PLAYER_TAG = "Player";
    public static final String PLATTFORM_TAG = "Plattform";

    public InputComponent inputHandler;
    public PhysicsComponent physics;

    public float x_Pos;
    public float y_Pos;

    private String tag;

    public GameObject(InputComponent inputHandler, PhysicsComponent physics, int x, int y, String tag){
        this.physics = physics;
        this.inputHandler = inputHandler;
        x_Pos = x;
        y_Pos = y;
        this.tag = tag;
    }

    public void update(){
        if(inputHandler!=null) {
            Command cmd = inputHandler.handleInput();
            if (cmd != null) {
                cmd.execute(this);
            }
        }
        if(physics!=null) {
            physics.handlePhysics();
        }
    }

    public String getTag() {
        return tag;
    }
}
