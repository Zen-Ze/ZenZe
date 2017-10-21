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

    public GameWorld(Context context){
        entities = new LinkedList<>();
        FileParser.loadMap(context, this);
    }

    public void update(){

    }

    public void addObject(GameObject obj){
        entities.add(obj);
    }

    public List<GameObject> getEntities(){
        return entities;
    }

}
