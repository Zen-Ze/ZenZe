package com.ucd.comp41690.team21.zenze.game.components;

import android.widget.Toast;

import com.ucd.comp41690.team21.zenze.activities.GameActivity;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PlayerPhysics extends PhysicsComponent {
    private final float ground;
    private final float rightBorder;

    private final float jumpVelocity;
    private final float earlyJumpVelocity;
    public boolean isJumping;

    private int healthTimer = 0;

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
            leapFrogIntegration(object, elapsedTime, 0.5f);
        } else {
            leapFrogIntegration(object, elapsedTime, 1);
        }

        //check for collisions with other game objects
        for (GameObject o : Game.getInstance().getGameWorld().getEntities()) {
            if (o.physics != null && o.physics.boundingVolume != null) {
                Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
                if (col != Collision.NONE) {
                    if (o.getTag().equals(GameObject.ITEM_TAG)) {
                        o.isAlive = false;
                        Game.getInstance().normalItemCount++;
                        ((GameActivity)(Game.getInstance().context)).onItemFound(o.type);
                    } else if (o.getTag().equals(GameObject.S_ITEM_TAG)) {
                        o.isAlive = false;
                        switch (Game.getInstance().getGameWorld().getState().getStatus()) {
                            case SNOWY:
                                Game.getInstance().snowyItemCount++;
                                break;
                            case RAINY:
                                Game.getInstance().rainyItemCount++;
                                break;
                            case SUNNY:
                                Game.getInstance().sunnyItemCount++;
                                break;
                        }
                        ((GameActivity)(Game.getInstance().context)).onItemFound(o.type);
                    } else if (o.getTag().equals(GameObject.ENEMY_TAG)) {
                        float[] newPos = getCollisionPoint(
                                (Sphere) this.boundingVolume, (Sphere) o.physics.boundingVolume);
                        if ((col == Collision.LEFT && x_Vel > 0) || (col == Collision.RIGHT && x_Vel < 0)) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                        } else if (col == Collision.TOP && y_Vel >= 0) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                            isJumping = false;
                            y_Vel = 0;
                        } else if (col == Collision.BOTTOM && y_Vel <= 0) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                            y_Vel = 0;
                        }
                        if (healthTimer <= 0) {
                            object.health -= o.type.getDamage();
                            if(object.health==0){
                                ((GameActivity)(Game.getInstance().context)).onGameOver();
                            }
                            healthTimer = 75;
                        }
                    }
                }
            }
        }
        if (healthTimer > 0) {
            healthTimer--;
        }
        //check for collisions with map
        for (GameObject o : Game.getInstance().getGameWorld().getMap()) {
            Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
            if (col != Collision.NONE) {
                if (o.getTag().equals(GameObject.PLATFORM_TAG)) {
                    if (y_Vel >= 0 &&
                            (col == Collision.CORNER_LEFT || col == Collision.CORNER_RIGHT)) {
                        if (col == Collision.CORNER_RIGHT) {
                            object.x_Pos += ((Sphere) boundingVolume).radius / 5;
                        } else {
                            object.x_Pos -= ((Sphere) boundingVolume).radius / 5;
                        }
                    } else if (y_Vel >= 0 && col == Collision.TOP) {
                        object.y_Pos = o.y_Pos
                                - ((AABB) o.physics.boundingVolume).height
                                - ((Sphere) boundingVolume).radius;
                        y_Vel = 0;
                        isJumping = false;
                    } else if (col == Collision.LEFT && x_Vel >= 0 && !isJumping) {
                        object.x_Pos = o.x_Pos
                                - ((AABB) o.physics.boundingVolume).width
                                - ((Sphere) boundingVolume).radius;
                    } else if (col == Collision.RIGHT && x_Vel <= 0 && !isJumping) {
                        object.x_Pos = o.x_Pos
                                + ((AABB) o.physics.boundingVolume).width
                                + ((Sphere) boundingVolume).radius;
                    }
                } else if (o.getTag().equals(GameObject.M_PLATFORM_TAG)) {
                    resolveSolidCollision(col, object, o);
                }
            }
        }
        //keep player inside the visible space
        if (object.y_Pos > ground) {
            object.y_Pos = ground;
            y_Vel = 0;
            isJumping = false;
            ((GameActivity)(Game.getInstance().context)).onGameOver();
        }
        if (object.y_Pos < 1) {
            object.y_Pos = 1;
            y_Vel = 0;
        }
        if (object.x_Pos < 0) {
            object.x_Pos = 0;
            y_Vel = y_Vel > 0 ? y_Vel : 0;
        }
        if (object.x_Pos > rightBorder) {
            object.x_Pos = rightBorder;
            y_Vel = y_Vel > 0 ? y_Vel : 0;
        }
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getEarlyJumpVelocity() {
        return earlyJumpVelocity;
    }
}
