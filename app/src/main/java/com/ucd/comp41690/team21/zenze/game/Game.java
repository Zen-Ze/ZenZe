package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.util.InputEvent;
import com.ucd.comp41690.team21.zenze.game.util.Observer;
import com.ucd.comp41690.team21.zenze.game.util.Subject;
import com.ucd.comp41690.team21.zenze.game.view.GraphicsRenderer;
import com.ucd.comp41690.team21.zenze.game.view.Renderer;
import com.ucd.comp41690.team21.zenze.game.view.SimpleRenderer;

import java.util.LinkedList;
import java.util.List;

public class Game implements Runnable, Subject<InputEvent> {
    //controlls for frame rate
    private static final int MS_PER_UPDATE = 30;//30FPS
    private static final int MAX_FRAME_SKIPS = 5;

    private Renderer gameView;
    private GameWorld gameWorld;
    private List<Observer<InputEvent>> inputObserverList;

    volatile boolean running;
    private Thread gameThread;

    private static Game instance;
    private int gameWidth;
    private int gameHeight;
    public String log;

    public Game(Context context, int width, int height, WeatherStatus status) {
        Game.instance = this;
        this.gameWidth = width;
        this.gameHeight = height;
        this.inputObserverList = new LinkedList<>();

        this.gameWorld = new GameWorld(context, status);
        //this.gameView = new SimpleRenderer(context, gameWorld);
        this.gameView = new GraphicsRenderer(context, gameWorld);
        this.gameThread  = null;
        this.log = "";
    }

    @Override
    public void run() {
        long sleepTime = 0;
        double beginTime = 0;
        double elapsedTime = 0;
        double framesSkipped = 0;
        long prevUpdate = System.currentTimeMillis();

        while (running) {
            beginTime = System.currentTimeMillis();
            framesSkipped = 0;

            double updateTime = System.currentTimeMillis();
            gameWorld.update(Math.max((updateTime - prevUpdate),0.0001) / 1000);
            prevUpdate = System.currentTimeMillis();
            gameView.render(gameWorld);

            elapsedTime = System.currentTimeMillis() - beginTime;
            sleepTime = (long) (MS_PER_UPDATE - elapsedTime);

            if (sleepTime > 0) { //time left, so sleep
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                //catch up on updates, leave out rendering step
                gameWorld.update(Math.max((System.currentTimeMillis() - prevUpdate),0.0001) / 1000);
                prevUpdate = System.currentTimeMillis();
                sleepTime += MS_PER_UPDATE;
                framesSkipped++;
            }
        }
    }

    public void pause() {
        running = false;
        while (true) {
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        gameThread = null;
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public SurfaceView getView() {
        return gameView.getView();
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

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void onInputEvent(InputEvent event) {
        notify(event);
    }

    public void addObserver(Observer<InputEvent> observer) {
        inputObserverList.add(observer);
    }

    public void removeObserver(Observer<InputEvent> observer) {
        inputObserverList.remove(observer);
    }

    public void notify(InputEvent event) {
        for (Observer observer : inputObserverList) {
            observer.onNotify(event);
        }
    }
}
