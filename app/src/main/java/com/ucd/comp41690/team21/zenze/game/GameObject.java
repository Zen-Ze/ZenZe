package com.ucd.comp41690.team21.zenze.game;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.components.InputComponent;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
import com.ucd.comp41690.team21.zenze.game.components.Type;

/**
 * The Base class for every object in the world
 * the behaviour of the different objects is defined by the components
 */
public class GameObject {
    //Tags to identify different game Objects
    public static final String PLAYER_TAG = "Player";
    public static final String PLATTFORM_TAG = "Plattform";
    public static final String CAMERA_TAG = "Camera";

    //Components
    public InputComponent inputHandler;
    public PhysicsComponent physics;
    public Type type;

    //2D Position
    public float x_Pos;
    public float y_Pos;
    public float scale;

    //Tag to identify objects
    private String tag;

    //Live related stuff
    public int health;
    public boolean isAlive;

    /**
     * Creates a new Game Object
     * @param inputHandler The AI or User issued Commands to controll this object
     * @param physics The component handling the physics and collisions of a object
     * @param type Defines the type of the object
     * @param x The position along the x axis
     * @param y The position along the y axis
     * @param tag An optional tag to easly identify the object
     */
    public GameObject(InputComponent inputHandler, PhysicsComponent physics, Type type, float x, float y, String tag){
        this.physics = physics;
        this.inputHandler = inputHandler;
        this.type = type;
        this.x_Pos = x;
        this.y_Pos = y;
        if (type == null){
            scale = 1;
            health = 100;
        }else {
            this.scale = type.scale;
            this.health = type.health;
        }
        this.tag = tag;
        this.isAlive = true;
    }

    /**
     * updates an object by processing input and handling physics
     * @param elapsedTime the time gone since the last update
     */
    public void update(double elapsedTime){
        if(inputHandler!=null) {
            inputHandler.handleInput(this);
        }
        if(physics!=null) {
            physics.handlePhysics(this, elapsedTime);
        }
    }

    public String getTag() {
        return tag;
    }
}
