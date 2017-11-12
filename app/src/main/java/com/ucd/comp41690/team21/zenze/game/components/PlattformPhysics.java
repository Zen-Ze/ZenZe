package com.ucd.comp41690.team21.zenze.game.components;


import com.ucd.comp41690.team21.zenze.game.GameObject;

public  class PlattformPhysics extends PhysicsComponent {
    public PlattformPhysics(float x_Pos, float y_Pos, float scale){
        this.x_Vel = 0;
        this.y_Vel = 0;
        this.gravity = 0;
        this.acceleration = 0;
        this.boundingVolume = new AABB(x_Pos, y_Pos, scale);
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {

    }
}
