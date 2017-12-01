package com.ucd.comp41690.team21.zenze.game.commands;

import android.graphics.Color;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
import com.ucd.comp41690.team21.zenze.game.components.Type;

/**
 * Created by annalena on 26.11.17.
 */

public class Attack implements Command {
    @Override
    public void execute(GameObject actor) {
        Game.getInstance().getGameWorld().addNewObject(Game.getInstance().getGameWorld().getAttack());
    }

    @Override
    public void exit(GameObject actor) {
        Type dropDownType = new Type(0,0,0.75f,0,
                Game.getInstance().getGameWorld().getState().getSpecialAttackImage(), Color.DKGRAY,
                Game.getInstance().getGameWorld().getState().getStatus(), new String[]{"attack","shortInfoShortInfo", "Lorem ipsum dolor sit amet something"});
        PhysicsComponent dropDownPhysics = new PhysicsComponent(PhysicsComponent.SPHERE, actor.x_Pos, actor.y_Pos, dropDownType.getScale());
        GameObject dropDown = new GameObject(null, dropDownPhysics, dropDownType, actor.x_Pos, actor.y_Pos, GameObject.A_ITEM_TAG);
        Game.getInstance().getGameWorld().addNewObject(dropDown);
    }

    @Override
    public void enter(GameObject actor) {

    }
}
