package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public abstract class PhysicsComponent {
    public float x_Vel;
    public float y_Vel;
    public float gravity;
    public float acceleration;
    public BoundingVolume boundingVolume;

    public abstract void handlePhysics(GameObject object, double elapsedTime);

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
    public boolean intersects(Sphere sphere, AABB rect){
        //compute the distance between the two centers
        float x_distance = Math.abs(sphere.x_Pos - rect.x_Pos);
        float y_distance = Math.abs(sphere.y_Pos - rect.y_Pos);

        //If distance greater than volumes they do not intersect
        if(x_distance > (sphere.radius + rect.width)){
            return false;
        }
        if(y_distance > (sphere.radius + rect.height)){
            return false;
        }
        //Sphere inside rectangle
        if(x_distance <= rect.width){
            return true;
        }
        if(y_distance <= rect.height){
            return true;
        }
        //corner case
        double cornerDist_sq = Math.pow(x_distance-rect.width,2)+Math.pow(y_distance-rect.height,2);
        return (cornerDist_sq <= Math.pow(sphere.radius,2));
    }

    public boolean intersects(BoundingVolume a, BoundingVolume b){
        if(a.getClass().equals(Sphere.class)&&b.getClass().equals(AABB.class)){
            return intersects((Sphere)a, (AABB)b);
        }
        return false;
    }

    public class BoundingVolume{
        public float x_Pos;
        public float y_Pos;

        public BoundingVolume(float x_Pos, float y_Pos){
            this.x_Pos = x_Pos;
            this.y_Pos = y_Pos;
        }

        public void update(GameObject o){
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
    }
}
