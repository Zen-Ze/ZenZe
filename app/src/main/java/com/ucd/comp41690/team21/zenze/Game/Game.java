package com.ucd.comp41690.team21.zenze.Game;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.Game.View.Renderer;
import com.ucd.comp41690.team21.zenze.Game.View.SimpleRenderer;

public class Game implements Runnable{
    private static final double MS_PER_UPDATE = 50;

    private Renderer gameView;
    private GameWorld gameWorld;

    volatile boolean running;
    private Thread gameThread = null;

    private static int gameWidth;
    private static int gameHeight;

    public Game(Context context, int width, int height){
        Game.gameWidth = width;
        Game.gameHeight = height;

        gameWorld = new GameWorld(context);
        gameView = new SimpleRenderer(context);
    }

    @Override
    public void run() {
        while(running){
            double start = System.currentTimeMillis();
            gameWorld.update();
            gameView.render(gameWorld);
            try {
                long sleepTime = (long)(start + MS_PER_UPDATE - System.currentTimeMillis());
                if(sleepTime>0) {
                    gameThread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause(){
        running = false;
        try{
            gameThread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public SurfaceView getView() {
        return gameView.getView();
    }

    public void onTouchEvent(MotionEvent event) {
        gameWorld.player.inputHandler.handleInput(event);
    }

    public static int getWidth(){
        return Game.gameWidth;
    }
    public static int getHeight(){
        return Game.gameHeight;
    }
}
