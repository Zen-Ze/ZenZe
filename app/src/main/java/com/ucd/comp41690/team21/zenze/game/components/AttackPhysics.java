package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * Created by annalena on 26.11.17.
 * moves an attack object without checking for collisions with tiles
 * the object dies after a certain time to keep if from taking to much computational time
 */

public class AttackPhysics extends PhysicsComponent {

    /**
     * creates a new attack with a rectangular bounding volume and the types speed
     * @param x the x coordinate of the object this component belongs to
     * @param y the y coordinate of the object this component belongs to
     * @param type the objects type
     */
    public AttackPhysics(float x, float y, Type type) {
        super(PhysicsComponent.RECTANGULAR, x, y, type.getScale());
        x_Vel = type.getSpeed();
    }

    /**
     * updates the attacks position and decreasing its health to make it disappear after a certain time
     * @param object the object to update
     * @param elapsedTime the time elapsed since the last update
     */
    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object,elapsedTime);
        leapFrogIntegration(object, elapsedTime, 1);
        object.health--;
    }
}
