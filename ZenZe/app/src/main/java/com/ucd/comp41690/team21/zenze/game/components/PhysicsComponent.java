package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.GameObject;

public interface PhysicsComponent {
    void handlePhysics(GameObject object, double elapsedTime);
    void setVelocityY(float v);
    float getVelocityY();
    void setVelocityX(float v);
    float getVelocityX();

}
