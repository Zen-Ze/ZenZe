package com.ucd.comp41690.team21.zenze.Game.Components;

import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;

import com.ucd.comp41690.team21.zenze.Game.Command;
import com.ucd.comp41690.team21.zenze.Game.Game;

/**
 * receives input from the user
 * defines characters reaction
 */
public class PlayerInputHandler implements InputComponent{
    @Override
    public Command handleInput(InputEvent event) {
        try {
            MotionEvent motionEvent = (MotionEvent) event;
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    Log.d("down", motionEvent.getX() + ", " + motionEvent.getY());
                    if(motionEvent.getX() < Game.getWidth()/2){
                        Log.d("down", "left");
                    }
                    else{
                        Log.d("down", "right");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d("up", motionEvent.getX() + ", " + motionEvent.getY());
            }
            return null;
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }
}
