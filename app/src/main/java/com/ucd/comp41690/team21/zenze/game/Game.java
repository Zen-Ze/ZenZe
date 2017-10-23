package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.game.view.Renderer;
import com.ucd.comp41690.team21.zenze.game.view.SimpleRenderer;

public class Game extends Subject implements Runnable {
    //controlls for frame rate
    private static final int MS_PER_UPDATE = 30;//30FPS
    private static final int MAX_FRAME_SKIPS = 5;

    private Renderer gameView;
    private GameWorld gameWorld;

    volatile boolean running;
    private Thread gameThread = null;

    private static Game instance;
    private int gameWidth;
    private int gameHeight;

    public Game(Context context, int width, int height) {
        Game.instance = this;
        this.gameWidth = width;
        this.gameHeight = height;

        gameWorld = new GameWorld(context);
        gameView = new SimpleRenderer(context, gameWorld);
    }

    @Override
    public void run() {
        long sleepTime = 0;
        double beginTime = 0;
        double elapsedTime = 0;
        double framesSkipped = 0;

        while (running) {
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;

            gameWorld.update();
            gameView.render(gameWorld);

            elapsedTime = System.currentTimeMillis() - beginTime;
            sleepTime = (long)(MS_PER_UPDATE-elapsedTime);

            if(sleepTime>0){ //time left so sleep
                try{
                    Thread.sleep(sleepTime);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            while(sleepTime<0&&framesSkipped<MAX_FRAME_SKIPS){
                //catch up on updates, leave out rendering step
                gameWorld.update();
                sleepTime+=MS_PER_UPDATE;
                framesSkipped++;
            }
        }
    }

    public void pause() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public SurfaceView getView() {
        return gameView.getView();
    }

    public void onTouchEvent(MotionEvent event) {
        notify(event);
    }

    public int getWidth() {
        return gameWidth;
    }

    public int getHeight() {
        return gameHeight;
    }

    public static Game getInstance() {
        return instance;
    }

    public GameWorld getGameWorld(){
        return gameWorld;
    }

    public Renderer getGameView(){
        return gameView;
    }
}
