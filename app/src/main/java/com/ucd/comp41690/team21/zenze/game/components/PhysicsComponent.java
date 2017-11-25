package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

public class PhysicsComponent {
    public final static int SPHERE = 0;
    public final static int RECTANGULAR = 1;

    public float x_Vel;
    public float y_Vel;
    public float gravity;
    public float acceleration;
    public BoundingVolume boundingVolume;

    public PhysicsComponent(int boundingVolume, float x, float y, float scale){
        switch (boundingVolume){
            case SPHERE:
                this.boundingVolume = new Sphere(x,y,scale/2);
                break;
            case RECTANGULAR:
                this.boundingVolume = new AABB(x,y,scale/2);
                break;
            default:
                this.boundingVolume = null;
        }
    }

    public void handlePhysics(GameObject object, double elapsedTime){
        boundingVolume.update(object);
    }

    public void leapFrogIntegration(GameObject object, double elapsedTime, float damping){
        x_Vel += acceleration*elapsedTime;
        y_Vel += gravity*elapsedTime;

        object.x_Pos += x_Vel *elapsedTime *damping;
        object.y_Pos += y_Vel *elapsedTime;
    }

    /**
     * collision detection between a sphere and an axis aligned rectangle
     * @param sphere
     * @param rect
     * @return true if the two of them intersect
     */
    public Collision intersects(Sphere sphere, AABB rect){

        //compute the distance between the two centers
        float x_distance = Math.abs(sphere.x_Pos - rect.x_Pos);
        float y_distance = Math.abs(sphere.y_Pos - rect.y_Pos);
        float old_xDistance = Math.abs(sphere.old_xPos - rect.x_Pos);
        float old_yDistance = Math.abs(sphere.old_yPos - rect.y_Pos);

        //check for collision between frames
        if(rect.getTop()>=sphere.old_yPos && rect.getTop()<=sphere.y_Pos
                && x_distance<=rect.width && old_xDistance<=rect.width){
            return Collision.TOP;
        }
        if(rect.getBottom()>=sphere.old_yPos && rect.getBottom()<=sphere.y_Pos
                && x_distance<=rect.width && old_xDistance<=rect.width){
            return Collision.BOTTOM;
        }
        if(rect.getLeft()>=sphere.old_xPos && rect.getLeft()<=sphere.x_Pos
                && y_distance<=rect.height && old_yDistance<=rect.height){
            return Collision.LEFT;
        }
        if(rect.getRight()>=sphere.old_xPos && rect.getRight()<=sphere.x_Pos
                && y_distance<=rect.height && old_yDistance<=rect.height){
            return Collision.RIGHT;
        }

        //If distance greater than volumes they do not intersect
        if(x_distance > (sphere.radius + rect.width)){
            return Collision.NONE;
        }
        if(y_distance > (sphere.radius + rect.height)){
            return Collision.NONE;
        }
        //Sphere inside rectangle
        if(y_distance <= (sphere.radius + rect.height) && x_distance <= rect.width){
            if(sphere.y_Pos <= rect.y_Pos){
                return Collision.TOP;
            } else {
                return Collision.BOTTOM;
            }
        }
        if(x_distance <= (sphere.radius + rect.width) && y_distance<=rect.height){
            if(sphere.x_Pos <= rect.x_Pos){
                return Collision.LEFT;
            } else {
                return Collision.RIGHT;
            }
        }
        //corner case
        double cornerDist_sq = Math.pow(x_distance-rect.width,2)+Math.pow(y_distance-rect.height,2);
        if(cornerDist_sq <= Math.pow(sphere.radius,2)){
            if(sphere.x_Pos < rect.x_Pos){
                return Collision.CORNER_LEFT;
            } else {
                return Collision.CORNER_RIGHT;
            }
        }
        return Collision.NONE;
    }

    public Collision intersects(Sphere a, Sphere b){
        if ((Math.pow(b.x_Pos-a.x_Pos,2) + Math.pow(b.y_Pos-a.y_Pos,2))
                < Math.pow(a.radius+b.radius,2)){
            return Collision.TOP;
        }
        return Collision.NONE;
    }

    public Collision intersects(BoundingVolume a, BoundingVolume b){
        if(a.getClass().equals(Sphere.class)&&b.getClass().equals(AABB.class)){
            return intersects((Sphere)a, (AABB)b);
        }else if(a.getClass().equals(Sphere.class)&&b.getClass().equals(Sphere.class)){
            return intersects((Sphere)a, (Sphere)b);
        }
        return Collision.NONE;
    }

    public class BoundingVolume{
        public float x_Pos;
        public float y_Pos;

        public float old_xPos;
        public float old_yPos;

        public BoundingVolume(float x_Pos, float y_Pos){
            this.x_Pos = x_Pos;
            this.y_Pos = y_Pos;
            this.old_xPos = x_Pos;
            this.old_yPos = y_Pos;
        }

        public void update(GameObject o){
            this.old_xPos = this.x_Pos;
            this.old_yPos = this.y_Pos;
            this.x_Pos = o.x_Pos;
            this.y_Pos = o.y_Pos;
        }
    }

    public class Sphere extends BoundingVolume{
        public float radius;

        public Sphere(float x_Pos, float y_Pos, float radius){
            super(x_Pos,y_Pos);
            this.radius = radius;
        }
    }

    public class AABB extends BoundingVolume{
        //stores width/2 and height/2
        public float width;
        public float height;

        public AABB(float x_Pos, float y_Pos, float width, float height){
            super(x_Pos, y_Pos);
            this.width = width;
            this.height = height;
        }
        public AABB(float x_Pos, float y_Pos, float dimension){
            super(x_Pos, y_Pos);
            this.width = dimension;
            this.height = dimension;
        }
        public float getTop(){
            return this.y_Pos-height;
        }
        public float getBottom(){
            return this.y_Pos+height;
        }
        public float getLeft(){
            return this.x_Pos-width;
        }
        public float getRight(){
            return this.x_Pos+width;
        }
    }

    public enum Collision{
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        CORNER_RIGHT,
        CORNER_LEFT,
        NONE;
    }
}
