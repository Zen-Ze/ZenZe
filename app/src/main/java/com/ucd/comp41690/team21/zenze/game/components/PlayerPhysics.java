package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PlayerPhysics extends PhysicsComponent {
    private final float ground;
    private final float rightBorder;

    private final float jumpVelocity;
    private final float earlyJumpVelocity;
    public boolean isJumping;

    private float oldPosX;
    private float oldPosY;

    public PlayerPhysics(int minJumpHeight, int maxJumpHeight, float jumpTime,
                         float rightBorder, float ground, float x_Pos, float y_Pos, float scale) {
        x_Vel = 0;
        y_Vel = 0;
        acceleration = 0;
        gravity = 2 * maxJumpHeight / (jumpTime * jumpTime);

        this.jumpVelocity = (float) -Math.sqrt(2 * gravity * maxJumpHeight);
        this.earlyJumpVelocity = (float) -Math.sqrt(jumpVelocity * jumpVelocity
                - 2 * gravity * (maxJumpHeight - minJumpHeight));

        this.boundingVolume = new Sphere(x_Pos, y_Pos, scale / 2);
        this.ground = ground - 2 * ((Sphere) boundingVolume).radius;
        this.rightBorder = rightBorder - 4 * ((Sphere) boundingVolume).radius;
        isJumping = false;

        oldPosX = 0;
        oldPosY = 0;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object, elapsedTime);
        oldPosX = object.x_Pos;
        oldPosY = object.y_Pos;

        //update position
        if (isJumping) {
            leapFrogIntegration(object, elapsedTime, 0.4f);
        } else {
            leapFrogIntegration(object, elapsedTime, 1);
        }
        //check for collisions with map
        for (GameObject o : Game.getInstance().getGameWorld().getMap()) {
            Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
            if (col != Collision.NONE) {
                if (o.getTag().equals(GameObject.PLATTFORM_TAG)) {
                    Game.getInstance().log = col.toString();
                    if (y_Vel >= 0 &&
                            (col == Collision.CORNER_LEFT || col == Collision.CORNER_RIGHT)) {
                        if(col == Collision.CORNER_RIGHT){
                            object.x_Pos += ((Sphere) boundingVolume).radius/10;
                        } else {
                            object.x_Pos -= ((Sphere) boundingVolume).radius/10;
                        }
                    }
                    if (y_Vel >= 0 && col == Collision.TOP) {
                        object.y_Pos = o.y_Pos
                                - ((AABB) o.physics.boundingVolume).height
                                - ((Sphere) boundingVolume).radius;
                        y_Vel = 0;
                        isJumping = false;
                    }
                    if(col == Collision.LEFT && x_Vel>=0){
                        object.x_Pos = o.x_Pos
                                - ((AABB) o.physics.boundingVolume).width
                                - ((Sphere) boundingVolume).radius;
                    }
                    if(col == Collision.RIGHT && x_Vel<=0){
                        object.x_Pos = o.x_Pos
                                + ((AABB) o.physics.boundingVolume).width
                                + ((Sphere) boundingVolume).radius;
                    }
                }
            }
        }
        //keep player inside the visible space
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
