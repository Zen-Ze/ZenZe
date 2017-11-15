package com.ucd.comp41690.team21.zenze.game.components;

<<<<<<< HEAD
import android.view.InputEvent;
import android.view.MotionEvent;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
=======
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
import com.ucd.comp41690.team21.zenze.game.commands.Jump;
import com.ucd.comp41690.team21.zenze.game.commands.MoveHorizontal;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
<<<<<<< HEAD
=======
import com.ucd.comp41690.team21.zenze.game.util.InputEvent;
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
import com.ucd.comp41690.team21.zenze.game.util.Observer;

/**
 * receives input from the user
 * defines characters reaction
 */
<<<<<<< HEAD
public class PlayerInputHandler implements InputComponent, Observer<InputEvent>{

    private MotionEvent inputEvent;
=======
public class PlayerInputHandler implements InputComponent, Observer<InputEvent> {

    private InputEvent inputEvent;
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1

    private MoveHorizontal moveLeft;
    private MoveHorizontal moveRight;
    private Jump jumpUp;

    public PlayerInputHandler() {
        Game.getInstance().addObserver(this);
<<<<<<< HEAD
        moveLeft = new MoveHorizontal(MoveHorizontal.DIRECTION_LEFT);
        moveRight = new MoveHorizontal(MoveHorizontal.DIRECTION_RIGHT);
        jumpUp = new Jump();
=======

        moveLeft = new MoveHorizontal(MoveHorizontal.DIRECTION_LEFT);
        moveRight = new MoveHorizontal(MoveHorizontal.DIRECTION_RIGHT);
        jumpUp = new Jump();

        inputEvent = InputEvent.NULL;
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
    }

    @Override
    public void handleInput(GameObject object) {
<<<<<<< HEAD
        if(inputEvent!=null) {
            switch (inputEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (inputEvent.getX() < Game.getInstance().getWidth() / 4) {
                        moveLeft.execute(object);
                    } else if  (inputEvent.getX() > Game.getInstance().getWidth()*3/4){
                        moveRight.execute(object);
                    } else {
                        jumpUp.execute(object);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (inputEvent.getX() < Game.getInstance().getWidth() / 4) {
                        moveLeft.exit(object);
                    } else if  (inputEvent.getX() > Game.getInstance().getWidth()*3/4){
                        moveRight.exit(object);
                    } else {
                        jumpUp.exit(object);
                    }
                    break;
            }
        }
=======
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
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
    }

    @Override
    public void onNotify(InputEvent event) {
<<<<<<< HEAD
        try {
            this.inputEvent = (MotionEvent) event;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
=======
        this.inputEvent = event;
>>>>>>> 9af233b6a60eb06f62cfe07927a2926b4f0eeda1
    }
}
