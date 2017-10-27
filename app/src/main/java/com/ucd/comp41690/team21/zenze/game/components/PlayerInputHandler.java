package com.ucd.comp41690.team21.zenze.game.components;

import android.view.InputEvent;
import android.view.MotionEvent;

import com.ucd.comp41690.team21.zenze.game.commands.Command;
import com.ucd.comp41690.team21.zenze.game.commands.Jump;
import com.ucd.comp41690.team21.zenze.game.commands.MoveHorizontal;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.util.Observer;

/**
 * receives input from the user
 * defines characters reaction
 */
public class PlayerInputHandler implements InputComponent, Observer<InputEvent>{

    private MotionEvent inputEvent;

    private MoveHorizontal moveLeft;
    private MoveHorizontal moveRight;
    private Jump jumpUp;

    private Command returnCommand;

    public PlayerInputHandler(float playerSpeed) {
        Game.getInstance().addObserver(this);
        moveLeft = new MoveHorizontal(-playerSpeed);
        moveRight = new MoveHorizontal(playerSpeed);
        jumpUp = new Jump();
        returnCommand = null;
    }

    @Override
    public Command handleInput(GameObject object) {
        if(inputEvent!=null) {
            switch (inputEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (inputEvent.getX() < Game.getInstance().getWidth() / 4) {
                        returnCommand = moveLeft;
                    } else if  (inputEvent.getX() > Game.getInstance().getWidth()*3/4){
                        returnCommand = moveRight;
                    } else {
                        returnCommand = jumpUp;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    returnCommand = null;
                    break;
            }
        }
        return returnCommand;
    }

    @Override
    public void onNotify(InputEvent event) {
        try {
            this.inputEvent = (MotionEvent) event;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
