package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public class EnemyPhyiscs extends PhysicsComponent {

    public EnemyPhyiscs(float x_Pos, float y_Pos, float scale){
        super(PhysicsComponent.SPHERE,x_Pos,y_Pos,scale);
        this.x_Vel = 0;
        this.y_Vel = 0;
        this.gravity = 0;
        this.acceleration = 0;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {

    }
}
