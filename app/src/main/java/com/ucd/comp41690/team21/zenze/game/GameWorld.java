package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;
import android.util.Log;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.components.AttackPhyiscs;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
import com.ucd.comp41690.team21.zenze.game.components.Type;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * contains the worlds objects and map
 */
public class GameWorld {
    private List<GameObject> entities;
    private List<GameObject> tileMap;
    private List<GameObject> newEntities;
    private GameObject player;
    private GameObject camera;
    private GameObject attackNormal;
    private GameObject attackSpecial;
    private GameState state;

    private int numTilesH;
    private int numTilesV;

    public GameWorld(Context context, WeatherStatus status){
        entities = new LinkedList<>();
        tileMap = new LinkedList<>();
        newEntities = new LinkedList<>();
        state = FileParser.init(context, status);
        FileParser.loadWorld(context, this);
        numTilesH = FileParser.getNumTilesH();
        numTilesV = FileParser.getNumTilesV();
    }

    public void update(double elapsedTime){
        for(Iterator<GameObject> it = entities.iterator(); it.hasNext();){
            GameObject obj = it.next();
            obj.update(elapsedTime);
            if(!obj.isAlive){
                it.remove();
            }
        }
        for(GameObject o : newEntities){
            entities.add(0,o);
        }
        newEntities.clear();
        Game.getInstance().log = entities.size()+"";
    }

    public void addObject(GameObject obj){
        entities.add(obj);
    }
    public void addNewObject(GameObject obj) {newEntities.add(obj);}
    public void addPlatform(GameObject obj){
        tileMap.add(obj);
    }

    public List<GameObject> getEntities(){
        return entities;
    }
    public List<GameObject> getMap(){return tileMap;}

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

    public GameObject getAttack(){
        switch(state.getStatus()){
            case RAINY:
                if(Game.getInstance().sunnyAttackCount != 0){
                    return cloneAttack(attackSpecial);
                }
                break;
            case SUNNY:
                if(Game.getInstance().snowyAttackCount != 0){
                    return cloneAttack(attackSpecial);
                }
                break;
            case SNOWY:
                if(Game.getInstance().rainyAttackCount != 0){
                    return cloneAttack(attackSpecial);
                }
                break;
        }
        return cloneAttack(attackNormal);
    }

    private GameObject cloneAttack(GameObject attack){
        float xPos = player.x_Pos+player.scale/2;
        float yPos = player.y_Pos;
        Type attackType = new Type(attack.type.getHealth(), attack.type.getSpeed(),
                attack.type.getScale(), attack.type.getDamage(),
                attack.type.getImage(), attack.type.getColour(),
                attack.type.getState(), attack.type.getInfo());
        AttackPhyiscs attackPhysics = new AttackPhyiscs(xPos, yPos, attackType);
        return new GameObject(null, attackPhysics, attackType, xPos, yPos, GameObject.ATTACK_TAG);
    }


    public void setAttackNormal(GameObject attackNormal) {
        this.attackNormal = attackNormal;
    }

    public void setAttackSpecial(GameObject attackSpecial) {
        this.attackSpecial = attackSpecial;
    }
}
