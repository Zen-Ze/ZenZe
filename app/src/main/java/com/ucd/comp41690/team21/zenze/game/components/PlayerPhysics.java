package com.ucd.comp41690.team21.zenze.game.components;

import android.arch.persistence.room.Room;
import android.widget.Toast;

import com.ucd.comp41690.team21.zenze.activities.GameActivity;
import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

import java.util.List;

public class PlayerPhysics extends PhysicsComponent {
    private final float ground;
    private final float rightBorder;

    private final float jumpVelocity;
    private final float earlyJumpVelocity;
    public boolean isJumping;

    private int healthTimer = 0;

    public PlayerPhysics(int minJumpHeight, int maxJumpHeight, float jumpTime,
                         float rightBorder, float ground, float x_Pos, float y_Pos, float scale) {
        super(PhysicsComponent.SPHERE, x_Pos, y_Pos, scale);

        x_Vel = 0;
        y_Vel = 0;
        acceleration = 0;
        gravity = 2 * maxJumpHeight / (jumpTime * jumpTime);

        this.jumpVelocity = (float) -Math.sqrt(2 * gravity * maxJumpHeight);
        this.earlyJumpVelocity = (float) -Math.sqrt(jumpVelocity * jumpVelocity
                - 2 * gravity * (maxJumpHeight - minJumpHeight));
        isJumping = false;

        this.ground = ground - 2 * ((Sphere) boundingVolume).radius;
        this.rightBorder = rightBorder - 4 * ((Sphere) boundingVolume).radius;
    }

    @Override
    public void handlePhysics(GameObject object, double elapsedTime) {
        super.handlePhysics(object, elapsedTime);
        //update position
        if (isJumping) {
            leapFrogIntegration(object, elapsedTime, 0.5f);
        } else {
            leapFrogIntegration(object, elapsedTime, 1);
        }

        //check for collisions with other game objects
        for (GameObject o : Game.getInstance().getGameWorld().getEntities()) {
            if (o.physics != null && o.physics.boundingVolume != null) {
                Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
                if (col != Collision.NONE) {
                    if (o.type!=null && o.type.getTag().equals(Type.ITEM_TAG)) {
                        o.isAlive = false;
                        Game.getInstance().normalItemCount++;
                        AppDatabase db = Room.databaseBuilder(Game.getInstance().context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
                        List<ItemListLine> items = db.itemListLineDao().getByItemListId(db.itemListDao().findById(db.playerDao().getAll().get(0).getItemListId()).getId());
                        for(ItemListLine line : items){
                            if(line.getItemId() == FileParser.DBIdNormalItem){
                                line.setAmount(line.getAmount()+1);
                                db.itemListLineDao().update(line);
                            }
                        }
                        db.close();
                        ((GameActivity)(Game.getInstance().context)).onItemFound(o.type);
                    } else if (o.type!=null && o.type.getTag().equals(Type.S_ITEM_TAG)) {
                        o.isAlive = false;
                        AppDatabase db = Room.databaseBuilder(Game.getInstance().context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
                        List<ItemListLine> items = db.itemListLineDao().getByItemListId(db.itemListDao().findById(db.playerDao().getAll().get(0).getItemListId()).getId());

                        switch (Game.getInstance().getGameWorld().getState().getStatus()) {
                            case SNOWY:
                                Game.getInstance().snowyItemCount++;
                                Game.getInstance().normalItemCount++;
                                for(ItemListLine line : items){
                                    if(line.getItemId() == FileParser.DBIdSnowyItem){
                                        line.setAmount(line.getAmount()+1);
                                        db.itemListLineDao().update(line);
                                    }
                                }
                                break;
                            case RAINY:
                                Game.getInstance().normalItemCount++;
                                for(ItemListLine line : items){
                                    if(line.getItemId() == FileParser.DBIdRainyItem){
                                        line.setAmount(line.getAmount()+1);
                                        db.itemListLineDao().update(line);
                                    }
                                }
                                Game.getInstance().rainyItemCount++;
                                break;
                            case SUNNY:
                                Game.getInstance().sunnyItemCount++;
                                for(ItemListLine line : items){
                                    if(line.getItemId() == FileParser.DBIdSunnyItem){
                                        line.setAmount(line.getAmount()+1);
                                        db.itemListLineDao().update(line);
                                    }
                                }
                                break;
                        }
                        db.close();
                        ((GameActivity)(Game.getInstance().context)).onItemFound(o.type);
                    } else if (o.type!=null && o.type.getTag().equals(Type.A_ITEM_TAG)) {
                        o.isAlive = false;
                        AppDatabase db = Room.databaseBuilder(Game.getInstance().context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
                        List<AttackListLine> attacks = db.attackListLineDao().getByAttackListId(db.attackListDao().findById(db.playerDao().getAll().get(0).getAttackListId()).getId());

                        switch (Game.getInstance().getGameWorld().getState().getStatus()) {
                            case SNOWY:
                                Game.getInstance().snowyAttackCount++;
                                for(AttackListLine line : attacks){
                                    if(line.getAttackId() == FileParser.DBIdSnowyAttack){
                                        line.setAmount(line.getAmount()+1);
                                        db.attackListLineDao().update(line);
                                    }
                                }
                                break;
                            case RAINY:
                                Game.getInstance().rainyAttackCount++;
                                for(AttackListLine line : attacks){
                                    if(line.getAttackId() == FileParser.DBIdRainyAttack){
                                        line.setAmount(line.getAmount()+1);
                                        db.attackListLineDao().update(line);
                                    }
                                }
                                break;
                            case SUNNY:
                                Game.getInstance().sunnyAttackCount++;
                                for(AttackListLine line : attacks){
                                    if(line.getAttackId() == FileParser.DBIdSunnyAttack){
                                        line.setAmount(line.getAmount()+1);
                                        db.attackListLineDao().update(line);
                                    }
                                }
                                break;
                        }
                        db.close();
                        ((GameActivity)(Game.getInstance().context)).onItemFound(o.type);
                    } else if (o.type!=null && o.type.getTag().equals(Type.ENEMY_TAG)) {
                        float[] newPos = getCollisionPoint(
                                (Sphere) this.boundingVolume, (Sphere) o.physics.boundingVolume);
                        if ((col == Collision.LEFT && x_Vel > 0) || (col == Collision.RIGHT && x_Vel < 0)) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                        } else if (col == Collision.TOP && y_Vel >= 0) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                            isJumping = false;
                            y_Vel = 0;
                        } else if (col == Collision.BOTTOM && y_Vel <= 0) {
                            object.x_Pos = newPos[0];
                            object.y_Pos = newPos[1];
                            y_Vel = 0;
                        }
                        if (healthTimer <= 0) {
                            object.health -= o.type.getDamage();
                            if(object.health==0){
                                ((GameActivity)(Game.getInstance().context)).onGameOver();
                            }
                            healthTimer = 75;
                        }
                    }
                }
            }
        }
        if (healthTimer > 0) {
            healthTimer--;
        }
        //check for collisions with map
        for (GameObject o : Game.getInstance().getGameWorld().getMap()) {
            Collision col = intersects(this.boundingVolume, o.physics.boundingVolume);
            if (col != Collision.NONE) {
                if (o.type!=null && o.type.getTag().equals(Type.PLATFORM_TAG)) {
                    if (y_Vel >= 0 &&
                            (col == Collision.CORNER_LEFT || col == Collision.CORNER_RIGHT)) {
                        if (col == Collision.CORNER_RIGHT) {
                            object.x_Pos += ((Sphere) boundingVolume).radius / 5;
                        } else {
                            object.x_Pos -= ((Sphere) boundingVolume).radius / 5;
                        }
                    } else if (y_Vel >= 0 && col == Collision.TOP) {
                        object.y_Pos = o.y_Pos
                                - ((AABB) o.physics.boundingVolume).height
                                - ((Sphere) boundingVolume).radius;
                        y_Vel = 0;
                        isJumping = false;
                    } else if (col == Collision.LEFT && x_Vel >= 0 && !isJumping) {
                        object.x_Pos = o.x_Pos
                                - ((AABB) o.physics.boundingVolume).width
                                - ((Sphere) boundingVolume).radius;
                    } else if (col == Collision.RIGHT && x_Vel <= 0 && !isJumping) {
                        object.x_Pos = o.x_Pos
                                + ((AABB) o.physics.boundingVolume).width
                                + ((Sphere) boundingVolume).radius;
                    }
                } else if (o.type!=null && o.type.getTag().equals(Type.M_PLATFORM_TAG)) {
                    resolveSolidCollision(col, object, o);
                }
            }
        }
        //keep player inside the visible space
        if (object.y_Pos > ground) {
            object.y_Pos = ground;
            y_Vel = 0;
            isJumping = false;
            ((GameActivity)(Game.getInstance().context)).onGameOver();
        }
        if (object.y_Pos < 1) {
            object.y_Pos = 1;
            y_Vel = 0;
        }
        if (object.x_Pos < 0) {
            object.x_Pos = 0;
            y_Vel = y_Vel > 0 ? y_Vel : 0;
        }
        if (object.x_Pos > rightBorder) {
            object.x_Pos = rightBorder;
            y_Vel = y_Vel > 0 ? y_Vel : 0;
        }
    }

    public float getJumpVelocity() {
        return jumpVelocity;
    }

    public float getEarlyJumpVelocity() {
        return earlyJumpVelocity;
    }
}
