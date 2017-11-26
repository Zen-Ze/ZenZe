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
    private static final int MS_PER_UPDATE = 20;//30FPS

    private Renderer gameView;
    private GameWorld gameWorld;
    private List<Observer<InputEvent>> inputObserverList;

    volatile boolean running;
    private Thread gameThread;

    private static Game instance;
    private int gameWidth;
    private int gameHeight;
    public int UIHeight;
    public String log;

    public int normalItemCount = 0;
    public int sunnyItemCount = 0;
    public int snowyItemCount = 0;
    public int rainyItemCount = 0;
    public int sunnyAttackCount = 0;
    public int rainyAttackCount = 0;
    public int snowyAttackCount = 0;

    public Game(Context context, int width, int height, WeatherStatus status, boolean graphicsRenderer) {
        Game.instance = this;
        this.gameWidth = width;
        this.gameHeight = height;
        this.inputObserverList = new LinkedList<>();

        this.gameWorld = new GameWorld(context, status);

        if (graphicsRenderer) {
            this.gameView = new GraphicsRenderer(context, gameWorld);
        } else {
            this.gameView = new SimpleRenderer(context, gameWorld);
        }

        this.gameThread = null;
        this.log = "";
    }

    @Override
    public void run() {
        long sleepTime = 0;
        long beginTime = 0;
        long elapsedTime = 0;
        double deltaTime = 0;
        long prevUpdate = System.currentTimeMillis();

        while (running) {
            beginTime = System.currentTimeMillis();
            deltaTime = elapsedTime > 100 ? ((beginTime - prevUpdate) / 100d) : ((beginTime - prevUpdate) / 1000d);
            gameWorld.update(deltaTime);
            gameView.render(gameWorld);
            prevUpdate = System.currentTimeMillis();
            elapsedTime = prevUpdate - beginTime;
            sleepTime = elapsedTime > 100 ? 17 : MS_PER_UPDATE - elapsedTime;
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
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
