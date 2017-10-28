package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.commands.MoveHorizontal;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;

/**
 * moves the camera horizontally according to players position and level borders
 */
public class CameraAI implements InputComponent {
    private float movementWindow;
    private GameObject focus;
    private float viewFrustum;
    private float minSpeed;
    private float maxSpeed;

    public CameraAI(int movementWindow, GameObject focus, float viewFrustum, float minSpeed, float maxSpeed) {
        this.movementWindow = movementWindow;
        this.focus = focus;
        this.viewFrustum = viewFrustum;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void handleInput(GameObject object) {
        int numTilesV = Game.getInstance().getGameWorld().getNumTilesV();
        //lock borders of level
        if (focus.x_Pos <= viewFrustum && object.x_Pos <= viewFrustum) {
            object.x_Pos = viewFrustum;
        } else if (focus.x_Pos >= numTilesV - viewFrustum && object.x_Pos >= numTilesV - viewFrustum) {
            object.x_Pos = numTilesV - viewFrustum;
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
