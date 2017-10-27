package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public interface PhysicsComponent {
    void handlePhysics(GameObject object);
    void setVelocityY(float v);
}
