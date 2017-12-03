package com.ucd.comp41690.team21.zenze.game.components;

import android.arch.persistence.room.Room;

import com.ucd.comp41690.team21.zenze.activities.GameActivity;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyListLine;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.commands.Attack;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.List;

public class EnemyPhysics extends PhysicsComponent {
    private Attack attack = new Attack();
    private int healthTimer = 0;

    public EnemyPhysics(float x_Pos, float y_Pos, float scale){
        super(PhysicsComponent.SPHERE,x_Pos,y_Pos,scale);
        this.x_Vel = 0;
        this.y_Vel = 0;
        this.gravity = 0;
        this.acceleration = 0;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object,elapsedTime);
        for (GameObject o : Game.getInstance().getGameWorld().getEntities()) {
            if (o.type!=null && o.type.getTag().equals(Type.ATTACK_TAG)) {
                if (o.physics != null && o.physics.boundingVolume != null) {
                    PhysicsComponent.Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
                    if (col != PhysicsComponent.Collision.NONE) {
                        o.isAlive = false;
                        if (healthTimer <= 0) {
                            object.health -= o.type.getDamage();
                            healthTimer = 10;
                        }
                    }
                }
            }
        }
        if (healthTimer > 0) {
            healthTimer--;
        }
        if(object.health==0){
            if(object.isAlive) {
                object.isAlive = false;
                attack.exit(object);
                AppDatabase database = Room.databaseBuilder(Game.getInstance().context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
                EnemyList el = database.enemyListDao().findById(database.playerDao().getAll().get(0).getEnemyListId());
                List<EnemyListLine> enemies = database.enemyListLineDao().getByEnemyListId(el.getId());
                for (EnemyListLine enemy : enemies) {
                    if (enemy.getEnemyId() == FileParser.DBIdSunnyEnemy && object.type.getState() == WeatherStatus.SUNNY) {
                        enemy.setAmount(enemy.getAmount() + 1);
                        database = null;
                        return;
                    }
                    if (enemy.getEnemyId() == FileParser.DBIdRainyEnemy && object.type.getState() == WeatherStatus.RAINY) {
                        enemy.setAmount(enemy.getAmount() + 1);
                        database = null;
                        return;
                    }
                    if (enemy.getEnemyId() == FileParser.DBIdSnowyEnemy && object.type.getState() == WeatherStatus.SNOWY) {
                        enemy.setAmount(enemy.getAmount() + 1);
                        database = null;
                        return;
                    }
                }
                ((GameActivity) (Game.getInstance().context)).onEnemyDefeated(object.type);
            }
        }
    }
}
