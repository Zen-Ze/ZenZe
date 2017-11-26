package com.ucd.comp41690.team21.zenze.game.components;

import com.ucd.comp41690.team21.zenze.game.commands.Jump;
import com.ucd.comp41690.team21.zenze.game.commands.MoveHorizontal;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.util.InputEvent;
import com.ucd.comp41690.team21.zenze.game.util.Observer;

/**
 * receives input from the user
 * defines characters reaction
 */
public class PlayerInputHandler implements InputComponent, Observer<InputEvent> {

    private InputEvent inputEvent;
    private boolean moving;

    private MoveHorizontal moveLeft;
    private MoveHorizontal moveRight;
    private Jump jumpUp;

    public PlayerInputHandler() {
        Game.getInstance().addObserver(this);

        moveLeft = new MoveHorizontal(MoveHorizontal.DIRECTION_LEFT);
        moveRight = new MoveHorizontal(MoveHorizontal.DIRECTION_RIGHT);
        jumpUp = new Jump();

        inputEvent = InputEvent.NULL;
        moving = false;
    }

    @Override
    public void handleInput(GameObject object) {
            switch (inputEvent) {
                case TILT_LEFT:
                    moveLeft.execute(object);
                    moving = true;
                    break;
                case TILT_RIGHT:
                    moveRight.execute(object);
                    moving = true;
                    break;
                case TILT_NONE:
                    moveLeft.exit(object);
                    moveRight.exit(object);
                    moving = false;
                    break;
                case TOUCH_DOWN_LEFT:
                    jumpUp.execute(object);
                    break;
                case TOUCH_UP:
                    jumpUp.exit(object);
                    break;
            }
    }

    @Override
    public void onNotify(InputEvent event) {
        if(event == InputEvent.TILT_NONE && moving){
            this.inputEvent = event;
        } else if (event == InputEvent.TOUCH_DOWN_LEFT || event == InputEvent.TOUCH_UP || !moving){
            this.inputEvent = event;
        }
    }
}
