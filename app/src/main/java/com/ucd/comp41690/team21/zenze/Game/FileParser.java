package com.ucd.comp41690.team21.zenze.Game;

import android.content.Context;
import android.util.Log;

import com.ucd.comp41690.team21.zenze.Game.Components.PlattformPhysics;
import com.ucd.comp41690.team21.zenze.Game.Components.PlayerInputHandler;
import com.ucd.comp41690.team21.zenze.Game.Components.PlayerPhysics;
import com.ucd.comp41690.team21.zenze.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * loads the level from file and creates objects
 */
public class FileParser {
    public static void loadMap(Context context, GameWorld world){
        InputStream in = context.getResources().openRawResource(R.raw.test_level);
        BufferedReader d = new BufferedReader(new InputStreamReader(in));

        try {
            int width = Integer.parseInt(d.readLine());
            int height = Integer.parseInt(d.readLine());

            int x = 0;
            int y = 0;
            while(d.ready()) {
                char c = (char) d.read();
                switch(c){
                    case '\n':
                        x=0;
                        y++;
                        break;
                    case '1':
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler();
                        PlayerPhysics playerPhysics = new PlayerPhysics();
                        GameObject player = new GameObject(
                                playerInputHandler, playerPhysics, x, y, GameObject.PLAYER_TAG);
                        world.addObject(player);
                        break;
                    case '#':
                        PlattformPhysics plattformPhysics = new PlattformPhysics();
                        GameObject platform = new GameObject(
                                null, plattformPhysics, x, y, GameObject.PLATTFORM_TAG);
                        world.addObject(platform);
                        x++;
                        break;
                    default:
                        x++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
