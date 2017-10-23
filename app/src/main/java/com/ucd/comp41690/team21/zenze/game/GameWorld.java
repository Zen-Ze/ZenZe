package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;
import android.util.Log;

import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.LinkedList;
import java.util.List;

/**
 * contains the worlds objects and map
 */
public class GameWorld {
    private List<GameObject> entities;
    private GameObject player;
    private GameObject camera;

    private int numTilesH;
    private int numTilesV;

    public GameWorld(Context context){
        entities = new LinkedList<>();
        FileParser.init(context);
        FileParser.loadWorld(context, this);
        numTilesH = FileParser.getNumTilesH();
        numTilesV = FileParser.getNumTilesV();
    }

    public void update(double elapsedTime){
        for(GameObject o : entities){
            o.update(elapsedTime);
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
}
