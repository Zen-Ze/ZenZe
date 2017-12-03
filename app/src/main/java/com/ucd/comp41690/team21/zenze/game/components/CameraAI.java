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

    /**
     * initialises a new camera object
     * @param movementWindow the players movement window for which the camera should not change its position
     * @param focus the object to follow, probably the player in most cases
     * @param viewFrustum used to lock the camera at the end/beginning of the level
     * @param minSpeed the minimum acceleration of the camera
     * @param maxSpeed the max speed, should optimally equal the players speed
     * @param rightBorder the right border of the level
     */
    public CameraAI(int movementWindow, GameObject focus, float viewFrustum, float minSpeed, float maxSpeed, int rightBorder) {
        this.movementWindow = movementWindow;
        this.focus = focus;
        this.viewFrustum = viewFrustum;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.rightBorder = rightBorder;
    }

    /**
     * updates the cameras position
     * @param object the camera to update
     */
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
