package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;
import android.util.Log;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * contains the worlds objects and map
 */
public class GameWorld {
    private List<GameObject> entities;
    private GameObject player;
    private GameObject camera;
    private GameState state;

    private int numTilesH;
    private int numTilesV;

    public GameWorld(Context context, WeatherStatus status){
        entities = new LinkedList<>();
        FileParser.init(context);
        FileParser.loadWorld(context, this);
        numTilesH = FileParser.getNumTilesH();
        numTilesV = FileParser.getNumTilesV();
        state = FileParser.loadState(status);
    }

    public void update(double elapsedTime){
        for(Iterator<GameObject> it = entities.iterator(); it.hasNext();){
            GameObject obj = it.next();
            obj.update(elapsedTime);
            if(!obj.isAlive){
                it.remove();
            }
        }
    }

    public void addObject(GameObject obj){
        entities.add(obj);
    }

    public List<GameObject> getEntities(){
        return entities;
    }

    public int getNumTilesH() {
        return numTilesH;
    }

    public int getNumTilesV() {
        return numTilesV;
    }

    public GameObject getPlayer() {
        return player;
    }

    public void setPlayer(GameObject player) {
        this.player = player;
    }

    public GameObject getCamera() {
        return camera;
    }

    public void setCamera(GameObject camera) {
        this.camera = camera;
    }

    public GameState getState() {
        return state;
    }
}
