package com.ucd.comp41690.team21.zenze.Game;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.Game.View.Renderer;
import com.ucd.comp41690.team21.zenze.Game.View.SimpleRenderer;

public class Game extends Subject implements Runnable {
    private static final double MS_PER_UPDATE = 50;

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
        gameView = new SimpleRenderer(context);
    }

    @Override
    public void run() {
        while (running) {
            double start = System.currentTimeMillis();
            gameWorld.update();
            gameView.render(gameWorld);
            try {
                long sleepTime = (long) (start + MS_PER_UPDATE - System.currentTimeMillis());
                if (sleepTime > 0) {
                    gameThread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
}
