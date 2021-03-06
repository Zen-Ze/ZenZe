package com.ucd.comp41690.team21.zenze.game.commands;

import android.graphics.Color;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameState;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
import com.ucd.comp41690.team21.zenze.game.components.Type;
import com.ucd.comp41690.team21.zenze.game.util.FileParser;

/**
 * Created by annalena on 26.11.17.
 * The command to execute an attack and adding the dropdown if the attacks were successful
 */

public class Attack implements Command {
    /**
     * instantiates a new attack object at the players position by cloning the correct attack
     * @param actor the actor which executes the attack
     */
    @Override
    public void execute(GameObject actor) {
        Game.getInstance().getGameWorld().addNewObject(Game.getInstance().getGameWorld().getAttack());
    }

    /**
     * is called when an enemy was successfully defeated
     * instantiates a dropdown at the old enemies position depending on the game state
     * @param actor the enemy that was killed
     */
    @Override
    public void exit(GameObject actor) {
        GameState currentState = Game.getInstance().getGameWorld().getState();
        Type dropDownType = new Type(0, 0, 0.75f, 0, currentState.getSpecialAttackImage(),
                Color.DKGRAY, currentState.getStatus(), currentState.getSpecialAttackInfo(),
                FileParser.getDBIdSpecialAttack(), Type.A_ITEM_TAG);
        PhysicsComponent dropDownPhysics = new PhysicsComponent(PhysicsComponent.SPHERE, actor.x_Pos, actor.y_Pos, dropDownType.getScale());
        GameObject dropDown = new GameObject(null, dropDownPhysics, dropDownType, actor.x_Pos, actor.y_Pos);
        Game.getInstance().getGameWorld().addNewObject(dropDown);
    }
}
