package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * controlls the physical behaviour of the game's objects
 */
public class PhysicsComponent {
    public final static int SPHERE = 0;
    public final static int RECTANGULAR = 1;

    public float x_Vel;
    public float y_Vel;
    public float gravity;
    public float acceleration;
    public BoundingVolume boundingVolume;

    /**
     * creates a new physics component
     * @param boundingVolume indicates the objects bounding volume, either spherical or rectangular
     * @param x the x position of the bounding volume, should match the objects one
     * @param y the y position of the bounding volume, should match the objects one
     * @param scale the scale of the bounding volume
     */
    public PhysicsComponent(int boundingVolume, float x, float y, float scale) {
        switch (boundingVolume) {
            case SPHERE:
                this.boundingVolume = new Sphere(x, y, scale / 2);
                break;
            case RECTANGULAR:
                this.boundingVolume = new AABB(x, y, scale / 2);
                break;
            default:
                this.boundingVolume = null;
        }
    }

    /**
     * updates an objects bounding volume, position and reactes to collisions
     * @param object the object to update
     * @param elapsedTime the elapsed time since the last update
     */
    public void handlePhysics(GameObject object, double elapsedTime) {
        boundingVolume.update(object);
    }

    /**
     * updates an objects position using leap frog integration for more stable results than euler
     * @param object the object whose position should be updated
     * @param elapsedTime the elapsed time since the last update
     * @param damping a damping factor that can be used to slow down the object eg. incase of jumping
     */
    public void leapFrogIntegration(GameObject object, double elapsedTime, float damping) {
        x_Vel += acceleration * elapsedTime;
        y_Vel += gravity * elapsedTime;

        object.x_Pos += x_Vel * elapsedTime * damping;
        object.y_Pos += y_Vel * elapsedTime;
    }

    /**
     * resolves a collision with a solid object and a spherical one
     * @param col the type of collision to resolve
     * @param player the object with a spherical bounding volume
     * @param o the solid object, can be spherical or rectangular
     */
    public void resolveSolidCollision(Collision col, GameObject player, GameObject o) {
        float playerOffset = ((Sphere) player.physics.boundingVolume).radius;
        float objectHeight = 0, objectWidth = 0;
        if (o.physics.boundingVolume.getClass().equals(Sphere.class)) {
            objectWidth = objectHeight = ((Sphere) (o.physics.boundingVolume)).radius;
        } else if (o.physics.boundingVolume.getClass().equals(AABB.class)) {
            objectWidth = ((AABB) (o.physics.boundingVolume)).width;
            objectHeight = ((AABB) (o.physics.boundingVolume)).height;
        }

        switch (col) {
            case TOP:
                player.y_Pos = o.y_Pos
                        - objectHeight
                        - playerOffset;
                y_Vel = 0;
                ((PlayerPhysics) (player.physics)).isJumping = false;
                break;
            case BOTTOM:
                player.y_Pos = o.y_Pos
                        + objectHeight
                        + playerOffset;
                y_Vel = 0;
                ((PlayerPhysics) (player.physics)).isJumping = false;
                break;
            case LEFT:
                player.x_Pos = o.x_Pos
                        - objectWidth
                        - playerOffset;
                y_Vel = y_Vel > 0 ? y_Vel : 0;
                break;
            case RIGHT:
                player.x_Pos = o.x_Pos
                        + objectWidth
                        + playerOffset;
                y_Vel = y_Vel > 0 ? y_Vel : 0;
                break;
            case CORNER_RIGHT:
                player.x_Pos += playerOffset / 5;
                break;
            case CORNER_LEFT:
                player.x_Pos -= playerOffset / 5;
                break;
        }
    }

    /**
     * checking for intersection between two bounding volumes
     * @param a the first bounding volume
     * @param b the second bounding volume
     * @return the type of collision
     */
    public Collision intersects(BoundingVolume a, BoundingVolume b) {
        if (a.getClass().equals(Sphere.class) && b.getClass().equals(AABB.class)) {
            return intersects((Sphere) a, (AABB) b);
        } else if (a.getClass().equals(Sphere.class) && b.getClass().equals(Sphere.class)) {
            return intersects((Sphere) a, (Sphere) b);
        }
        return Collision.NONE;
    }

    /**
     * collision detection between a sphere and an axis aligned rectangle
     *
     * @param sphere a spherical bounding volume
     * @param rect an axis aligned rectangular bounding volume
     * @return the type of collision between the two volumes
     */
    public Collision intersects(Sphere sphere, AABB rect) {

        //compute the distance between the two centers
        float x_distance = Math.abs(sphere.x_Pos - rect.x_Pos);
        float y_distance = Math.abs(sphere.y_Pos - rect.y_Pos);
        float old_xDistance = Math.abs(sphere.old_xPos - rect.x_Pos);
        float old_yDistance = Math.abs(sphere.old_yPos - rect.y_Pos);

        //check for collision between frames
        if (rect.getTop() >= sphere.old_yPos && rect.getTop() <= sphere.y_Pos
                && x_distance <= rect.width && old_xDistance <= rect.width) {
            return Collision.TOP;
        }
        if (rect.getBottom() >= sphere.old_yPos && rect.getBottom() <= sphere.y_Pos
                && x_distance <= rect.width && old_xDistance <= rect.width) {
            return Collision.BOTTOM;
        }
        if (rect.getLeft() >= sphere.old_xPos && rect.getLeft() <= sphere.x_Pos
                && y_distance <= rect.height && old_yDistance <= rect.height) {
            return Collision.LEFT;
        }
        if (rect.getRight() >= sphere.old_xPos && rect.getRight() <= sphere.x_Pos
                && y_distance <= rect.height && old_yDistance <= rect.height) {
            return Collision.RIGHT;
        }

        //If distance greater than volumes they do not intersect
        if (x_distance > (sphere.radius + rect.width)) {
            return Collision.NONE;
        }
        if (y_distance > (sphere.radius + rect.height)) {
            return Collision.NONE;
        }
        //Sphere inside rectangle
        if (y_distance <= (sphere.radius + rect.height) && x_distance <= rect.width) {
            if (sphere.y_Pos <= rect.y_Pos) {
                return Collision.TOP;
            } else {
                return Collision.BOTTOM;
            }
        }
        if (x_distance <= (sphere.radius + rect.width) && y_distance <= rect.height) {
            if (sphere.x_Pos <= rect.x_Pos) {
                return Collision.LEFT;
            } else {
                return Collision.RIGHT;
            }
        }
        //corner case
        double cornerDist_sq = Math.pow(x_distance - rect.width, 2) + Math.pow(y_distance - rect.height, 2);
        if (cornerDist_sq <= Math.pow(sphere.radius, 2)) {
            if (sphere.x_Pos < rect.x_Pos) {
                return Collision.CORNER_LEFT;
            } else {
                return Collision.CORNER_RIGHT;
            }
        }
        return Collision.NONE;
    }

    /**
     * checks for collsions between two spherical bounding volumes
     * @param a the first sphere
     * @param b the second sphere
     * @return the type of collision between the two volumes
     */
    public Collision intersects(Sphere a, Sphere b) {
        //check for intersection based on radius
        if ((Math.pow(b.x_Pos - a.x_Pos, 2) + Math.pow(b.y_Pos - a.y_Pos, 2))
                <= Math.pow(a.radius + b.radius, 2)) {
            //return type depending on positions
            if(a.y_Pos<b.y_Pos-b.radius/2) {
                return Collision.TOP;
            } else if(a.y_Pos>b.y_Pos+b.radius/2) {
                return Collision.BOTTOM;
            } else if(a.x_Pos<b.x_Pos-b.radius/2) {
                return Collision.LEFT;
            } else if(a.x_Pos>b.x_Pos+b.radius/2) {
                return Collision.RIGHT;
            } else {
                if (a.x_Pos<b.x_Pos){
                    return Collision.LEFT;
                } else{
                    return Collision.RIGHT;
                }
            }
        }
        return Collision.NONE;
    }

    /**
     * returns the point to which to reset the first object in case of collision
     * @param a the first colliding sphere which should be moved out of the second object
     * @param b the second bounding volume which remains in place
     * @return an array containing x and y coordinates for the new object position
     */
    public float[] getCollisionPoint(Sphere a, Sphere b){
        //compute vector between centers
        float[] vector = new float[2];
        vector[0] = a.x_Pos-b.x_Pos;
        vector[1] = a.y_Pos-b.y_Pos;
        //normalise
        double length = Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);
        vector[0] /= length;
        vector[1] /= length;
        //collision point
        float[] col = new float[]{b.x_Pos+vector[0]*b.radius, b.y_Pos+vector[1]*b.radius};
        return new float[]{col[0]+vector[0]*a.radius, col[1] + vector[1]*a.radius};
    }

    /**
     * a super class for bounding volumes
     */
    public abstract class BoundingVolume {
        public float x_Pos;
        public float y_Pos;

        public float old_xPos;
        public float old_yPos;

        public BoundingVolume(float x_Pos, float y_Pos) {
            this.x_Pos = x_Pos;
            this.y_Pos = y_Pos;
            this.old_xPos = x_Pos;
            this.old_yPos = y_Pos;
        }

        public void update(GameObject o) {
            this.old_xPos = this.x_Pos;
            this.old_yPos = this.y_Pos;
            this.x_Pos = o.x_Pos;
            this.y_Pos = o.y_Pos;
        }
    }

    /**
     * a spherical bounding volume
     */
    public class Sphere extends BoundingVolume {
        public float radius;

        public Sphere(float x_Pos, float y_Pos, float radius) {
            super(x_Pos, y_Pos);
            this.radius = radius;
        }
    }

    /**
     * a rectangular bounding volume
     */
    public class AABB extends BoundingVolume {
        //stores width/2 and height/2
        public float width;
        public float height;

        /**
         * used to initialise a new AABB with arbitrary rectangular shape
         * @param x_Pos the x position of the bounding volume
         * @param y_Pos the y position of the bounding volume
         * @param width half of the width of the rectangle
         * @param height half of the height of the rectangle
         */
        public AABB(float x_Pos, float y_Pos, float width, float height) {
            super(x_Pos, y_Pos);
            this.width = width;
            this.height = height;
        }

        /**
         * used to initialise a new AABB with the shape of a square
         * @param x_Pos the bounding volumes x position
         * @param y_Pos the y position of the bounding volume
         * @param dimension the half of the width and height of the square shape
         */
        public AABB(float x_Pos, float y_Pos, float dimension) {
            super(x_Pos, y_Pos);
            this.width = dimension;
            this.height = dimension;
        }

        /**
         * used to get the top coordinate along the y axis of the bounding volume
         * @return the upper y coordinate of the volume
         */
        public float getTop() {
            return this.y_Pos - height;
        }

        /**
         * used to get the bottom coordinate along the y axis of the bounding volume
         * @return the lower y coordinate of the volume
         */
        public float getBottom() {
            return this.y_Pos + height;
        }

        /**
         * used to get the left side coordinate along the x axis of the bounding volume
         * @return the leftmost x coordinate of the volume
         */
        public float getLeft() {
            return this.x_Pos - width;
        }

        /**
         * used to get the right side coordinate along the x axis of the bounding volume
         * @return the rightmost x coordinate of the volume
         */
        public float getRight() {
            return this.x_Pos + width;
        }
    }

    /**
     * reflects the type or position of a collision
     */
    public enum Collision {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        CORNER_RIGHT,
        CORNER_LEFT,
        NONE;
    }
}
