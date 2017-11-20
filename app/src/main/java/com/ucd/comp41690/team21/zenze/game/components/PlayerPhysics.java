package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PlayerPhysics extends PhysicsComponent {
    private final float ground;
    private final float rightBorder;

    private final float jumpVelocity;
    private final float earlyJumpVelocity;
    public boolean isJumping;

    public PlayerPhysics(int minJumpHeight, int maxJumpHeight, float jumpTime,
                         float rightBorder, float ground, float x_Pos, float y_Pos, float scale) {
        super(PhysicsComponent.SPHERE, x_Pos, y_Pos, scale);

        x_Vel = 0;
        y_Vel = 0;
        acceleration = 0;
        gravity = 2 * maxJumpHeight / (jumpTime * jumpTime);

        this.jumpVelocity = (float) -Math.sqrt(2 * gravity * maxJumpHeight);
        this.earlyJumpVelocity = (float) -Math.sqrt(jumpVelocity * jumpVelocity
                - 2 * gravity * (maxJumpHeight - minJumpHeight));
        isJumping = false;

        this.ground = ground - 2 * ((Sphere) boundingVolume).radius;
        this.rightBorder = rightBorder - 4 * ((Sphere) boundingVolume).radius;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object, elapsedTime);

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
                    if (y_Vel >= 0 &&
                            (col == Collision.CORNER_LEFT || col == Collision.CORNER_RIGHT)) {
                        if(col == Collision.CORNER_RIGHT){
                            object.x_Pos += ((Sphere) boundingVolume).radius/5;
                        } else {
                            object.x_Pos -= ((Sphere) boundingVolume).radius/5;
                        }
                    }
                    if (y_Vel >= 0 && col == Collision.TOP) {
                        object.y_Pos = o.y_Pos
                                - ((AABB) o.physics.boundingVolume).height
                                - ((Sphere) boundingVolume).radius;
                        y_Vel = 0;
                        isJumping = false;
                    }
                    if(col == Collision.LEFT && x_Vel>=0 && !isJumping){
                        object.x_Pos = o.x_Pos
                                - ((AABB) o.physics.boundingVolume).width
                                - ((Sphere) boundingVolume).radius;
                    }
                    if(col == Collision.RIGHT && x_Vel<=0 && !isJumping){
                        object.x_Pos = o.x_Pos
                                + ((AABB) o.physics.boundingVolume).width
                                + ((Sphere) boundingVolume).radius;
                    }
                }
            }
        }
        //check for collisions with other game objects
        for(GameObject o : Game.getInstance().getGameWorld().getEntities()){
            if(o.getTag().equals(GameObject.ITEM_TAG)){
                if(intersects(this.boundingVolume, o.physics.boundingVolume) != Collision.NONE) {
                    o.isAlive = false;
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
