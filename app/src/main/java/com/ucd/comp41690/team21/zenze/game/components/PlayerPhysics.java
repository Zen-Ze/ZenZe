package com.ucd.comp41690.team21.zenze.game.components;

import android.util.Log;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PlayerPhysics extends PhysicsComponent {
    private final float ground;
    private final float rightBorder;

    private final float boundingVolume;

    private final float jumpVelocity;
    private final float earlyJumpVelocity;
    public boolean isJumping;

    public PlayerPhysics(int minJumpHeight, int maxJumpHeight, float jumpTime,
                         float rightBorder, float ground, float scale) {
        x_Vel = 0;
        y_Vel = 0;
        acceleration = 0;
        gravity = 2 * maxJumpHeight / (jumpTime * jumpTime);

        this.jumpVelocity = (float) -Math.sqrt(2 * gravity * maxJumpHeight);
        this.earlyJumpVelocity = (float) -Math.sqrt(jumpVelocity * jumpVelocity
                - 2 * gravity * (maxJumpHeight - minJumpHeight));

        this.boundingVolume = scale / 2;
        this.ground = ground - 2 * boundingVolume;
        this.rightBorder = rightBorder - 4 * boundingVolume;
        isJumping = false;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        if(isJumping) {
            leapFrogIntegration(object, elapsedTime, 0.4f);
        }else{
            leapFrogIntegration(object, elapsedTime, 1);
        }

        if (object.y_Pos > ground) {
            object.y_Pos = ground;
            isJumping = false;
        }
        if (object.y_Pos < 0) {
            object.y_Pos = 0;
        }
        if (object.x_Pos < 0) {
            object.x_Pos = 0;
        }
        if (object.x_Pos > rightBorder) {
            object.x_Pos = rightBorder;
        }
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getEarlyJumpVelocity() {
        return earlyJumpVelocity;
    }
}
