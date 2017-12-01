package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.commands.Attack;

public class EnemyPhysics extends PhysicsComponent {
    private Attack attack = new Attack();
    private int healthTimer = 0;

    public EnemyPhysics(float x_Pos, float y_Pos, float scale){
        super(PhysicsComponent.SPHERE,x_Pos,y_Pos,scale);
        this.x_Vel = 0;
        this.y_Vel = 0;
        this.gravity = 0;
        this.acceleration = 0;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object,elapsedTime);
        for (GameObject o : Game.getInstance().getGameWorld().getEntities()) {
            if (o.getTag().equals(GameObject.ATTACK_TAG)) {
                if (o.physics != null && o.physics.boundingVolume != null) {
                    PhysicsComponent.Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
                    if (col != PhysicsComponent.Collision.NONE) {
                        o.isAlive = false;
                        if (healthTimer <= 0) {
                            object.health -= o.type.getDamage();
                            healthTimer = 10;
                        }
                    }
                }
            }
        }
        if (healthTimer > 0) {
            healthTimer--;
        }
        if(object.health==0){
            object.isAlive=false;
            attack.exit(object);
        }
    }
}
