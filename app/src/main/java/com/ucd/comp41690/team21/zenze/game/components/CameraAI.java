package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.commands.MoveHorizontal;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * moves the camera horizontally according to players position and level borders
 */
public class CameraAI implements InputComponent {
    private final float movementWindow;
    private final GameObject focus;
    private final float viewFrustum;
    private final float minSpeed;
    private final float maxSpeed;
    private final int rightBorder;

    public CameraAI(int movementWindow, GameObject focus, float viewFrustum, float minSpeed, float maxSpeed, int rightBorder) {
        this.movementWindow = movementWindow;
        this.focus = focus;
        this.viewFrustum = viewFrustum;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.rightBorder = rightBorder;
    }

    @Override
    public void handleInput(GameObject object) {
        //lock borders of level
        if (focus.x_Pos <= viewFrustum && object.x_Pos <= viewFrustum) {
            object.x_Pos = viewFrustum;
        } else if (focus.x_Pos >= rightBorder - viewFrustum && object.x_Pos >= rightBorder - viewFrustum) {
            object.x_Pos = rightBorder - viewFrustum;
        } else {
            float distance = object.x_Pos - focus.x_Pos;
            float diff = Math.abs(movementWindow-Math.abs(distance));
            //linear interpolation for smooth movement
            float currentSpeed = (minSpeed*(movementWindow-diff)+maxSpeed*diff)/movementWindow;

            if (distance <= 0 && Math.abs(distance) >= movementWindow) {
                //return new MoveHorizontal(currentSpeed);
                object.x_Pos += currentSpeed;
            } else if (distance >= 0 && Math.abs(distance) >= movementWindow) {
                //return new MoveHorizontal(-currentSpeed);
                object.x_Pos -= currentSpeed;
            }
        }
    }
}
