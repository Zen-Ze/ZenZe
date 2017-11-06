package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public abstract class PhysicsComponent {
    public float x_Vel;
    public float y_Vel;
    public float gravity;
    public float acceleration;

    public abstract void handlePhysics(GameObject object, double elapsedTime);

    public void leapFrogIntegration(GameObject object, double elapsedTime, float damping){
        x_Vel += acceleration*elapsedTime;
        y_Vel += gravity*elapsedTime;

        object.x_Pos += x_Vel *elapsedTime *damping;
        object.y_Pos += y_Vel *elapsedTime;
    }
}
