package com.ucd.comp41690.team21.zenze.Game.Components;

import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;

import com.ucd.comp41690.team21.zenze.Game.Commands.Command;
import com.ucd.comp41690.team21.zenze.Game.Commands.RunCommand;
import com.ucd.comp41690.team21.zenze.Game.Game;
import com.ucd.comp41690.team21.zenze.Game.InputObserver;

/**
 * receives input from the user
 * defines characters reaction
 */
public class PlayerInputHandler extends InputObserver implements InputComponent{

    private float playerSpeed;
    private MotionEvent inputEvent;
    private Command returnCommand;

    public PlayerInputHandler(float playerSpeed) {
        Game.getInstance().addInputObserver(this);
        this.playerSpeed = playerSpeed;
        returnCommand = null;
    }

    @Override
    public Command handleInput() {
        if(inputEvent!=null) {
            switch (inputEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    if (inputEvent.getX() < Game.getInstance().getWidth() / 2) {
                        Log.d("down", "left");
                        returnCommand = new RunCommand(playerSpeed * -1);
                    } else {
                        Log.d("down", "right");
                        returnCommand = new RunCommand(playerSpeed);
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
