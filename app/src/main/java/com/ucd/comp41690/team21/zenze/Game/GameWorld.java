package com.ucd.comp41690.team21.zenze.Game;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

/**
 * contains the worlds objects and map
 */
public class GameWorld {
    private List<GameObject> entities;
    public GameObject player;
    private int numTilesH;

    public GameWorld(Context context){
        entities = new LinkedList<>();
        FileParser.init(context);
        FileParser.loadWorld(context, this);
        numTilesH = FileParser.getNumTilesH();
    }

    public void update(){
        for(GameObject o : entities){
            o.update();
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
}
