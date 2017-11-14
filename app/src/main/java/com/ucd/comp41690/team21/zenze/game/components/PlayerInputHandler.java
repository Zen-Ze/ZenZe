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

    private MoveHorizontal moveLeft;
    private MoveHorizontal moveRight;
    private Jump jumpUp;

    public PlayerInputHandler() {
        Game.getInstance().addObserver(this);

        moveLeft = new MoveHorizontal(MoveHorizontal.DIRECTION_LEFT);
        moveRight = new MoveHorizontal(MoveHorizontal.DIRECTION_RIGHT);
        jumpUp = new Jump();

        inputEvent = InputEvent.NULL;
    }

    @Override
    public void handleInput(GameObject object) {
            switch (inputEvent) {
                case TILT_LEFT:
                    moveLeft.execute(object);
                    break;
                case TILT_RIGHT:
                    moveRight.execute(object);
                    break;
                case TILT_NONE:
                    moveLeft.exit(object);
                    moveRight.exit(object);
                    break;
                case TOUCH_DOWN:
                    jumpUp.execute(object);
                    break;
                case TOUCH_UP:
                    jumpUp.exit(object);
                    break;
            }
    }

    @Override
    public void onNotify(InputEvent event) {
        this.inputEvent = event;
    }
}
