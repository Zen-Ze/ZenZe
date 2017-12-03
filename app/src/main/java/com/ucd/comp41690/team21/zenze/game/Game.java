package com.ucd.comp41690.team21.zenze.game;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;
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
    public Context context;
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
        this.context = context;
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
        Looper.prepare();
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
            sleepTime = elapsedTime > MS_PER_UPDATE ? 17 : MS_PER_UPDATE - elapsedTime;
            try {
                Thread.sleep(sleepTime);
            } catch (Exception e) {
            }
        }
    }

    public void pause() {
        //save data to database
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
        Player player = db.playerDao().getAll().get(0);
        player.setLastCoordX((int)gameWorld.getPlayer().x_Pos);
        player.setLastCoordY((int)gameWorld.getPlayer().y_Pos);
        player.setSavedHealth(gameWorld.getPlayer().health);
        db.playerDao().update(player);
        AttackList al = db.attackListDao().findById(db.playerDao().getAll().get(0).getAttackListId());
        ItemList il = db.itemListDao().findById(db.playerDao().getAll().get(0).getItemListId());
        List<AttackListLine> attacks = db.attackListLineDao().getByAttackListId(al.getId());
        List<ItemListLine> items = db.itemListLineDao().getByItemListId(il.getId());
        for(AttackListLine attack : attacks){
            if(attack.getAttackId()== FileParser.DBIdNormalAttack){
                attack.setAmount(normalItemCount);
                db.attackListLineDao().update(attack);
            }
            if(attack.getAttackId()== FileParser.DBIdSunnyAttack){
                attack.setAmount(sunnyAttackCount);
                db.attackListLineDao().update(attack);
            }
            if(attack.getAttackId()== FileParser.DBIdRainyAttack){
                attack.setAmount(rainyAttackCount);
                db.attackListLineDao().update(attack);
            }
            if(attack.getAttackId()== FileParser.DBIdSnowyAttack){
                attack.setAmount(snowyAttackCount);
                db.attackListLineDao().update(attack);
            }
        }
        for(ItemListLine item : items){
            if(item.getItemId()== FileParser.DBIdNormalAttack){
                item.setAmount(normalItemCount);
                db.itemListLineDao().update(item);
            }
            if(item.getItemId()== FileParser.DBIdSunnyAttack){
                item.setAmount(sunnyAttackCount);
                db.itemListLineDao().update(item);
            }
            if(item.getItemId()== FileParser.DBIdRainyAttack){
                item.setAmount(rainyAttackCount);
            }
            if(item.getItemId()== FileParser.DBIdSnowyAttack){
                item.setAmount(snowyAttackCount);
                db.itemListLineDao().update(item);
            }
        }
        db.close();
        //stop game thread
        if(gameThread!=null) {
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
    }

    public void resume() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public SurfaceView getView() {
        return gameView.getView();
    }

    public void setRunning(boolean running){
        this.running = running;
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

    public boolean isRunning(){
        return running;
    }
}
