package com.ucd.comp41690.team21.zenze.game.components;

import android.util.Log;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PlayerPhysics implements PhysicsComponent {
    boolean isOnGround;

    float velX;
    float velY;
    float gravity;
    float acceleration;

    public PlayerPhysics(float gravity){
        isOnGround = true;
        velX = 0;
        velY = 0;
        this.gravity = gravity;
        acceleration = 0;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        velX += acceleration*elapsedTime;
        velY += gravity*elapsedTime;

        object.x_Pos += velX*elapsedTime;
        object.y_Pos += velY*elapsedTime;

        if(object.y_Pos>8){
            object.y_Pos=8;
        }
    }

    @Override
    public void setVelocityY(float v) {
        this.velY = v;
    }

    @Override
    public float getVelocityY(){
        return velY;
    }

    @Override
    public void setVelocityX(float v) {
        this.velX = v;
    }

    @Override
    public float getVelocityX() {
        return velX;
    }
}
