package com.ucd.comp41690.team21.zenze.game;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.components.InputComponent;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;

/**
 * The Base class for every object in the world
 * the behaviour of the different objects is defined by the components
 */
public class GameObject {
    //Tags to identify different game Objects
    public static final String PLAYER_TAG = "Player";
    public static final String PLATTFORM_TAG = "Plattform";
    public static final String CAMERA_TAG = "Camera";

    public InputComponent inputHandler;
    public PhysicsComponent physics;

    //2D Position
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
            Command cmd = inputHandler.handleInput(this);
            if(cmd!=null){
                cmd.execute(this);
            }
        }
        if(physics!=null) {
            physics.handlePhysics(this);
        }
    }

    public String getTag() {
        return tag;
    }
}
