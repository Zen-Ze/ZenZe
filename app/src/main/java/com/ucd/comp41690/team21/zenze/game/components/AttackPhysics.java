package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 26.11.17.
 */

public class AttackPhysics extends PhysicsComponent {

    public AttackPhysics(float x, float y, Type type) {
        super(PhysicsComponent.RECTANGULAR, x, y, type.getScale());
        x_Vel = type.getSpeed();
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object,elapsedTime);
        leapFrogIntegration(object, elapsedTime, 1);
        object.health--;
    }
}
