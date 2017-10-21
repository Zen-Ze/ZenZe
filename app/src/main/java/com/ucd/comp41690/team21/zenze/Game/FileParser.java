package com.ucd.comp41690.team21.zenze.Game;

import android.content.Context;
import com.ucd.comp41690.team21.zenze.Game.Components.PlattformPhysics;
import com.ucd.comp41690.team21.zenze.Game.Components.PlayerInputHandler;
import com.ucd.comp41690.team21.zenze.Game.Components.PlayerPhysics;
import com.ucd.comp41690.team21.zenze.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * loads the level from a file and creates objects
 */
public class FileParser {
    private static float player_Speed = 0;

    public static void init(Context context){
        InputStream in = context.getResources().openRawResource(R.raw.player_stats);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject playerStats = new JSONObject(sb.toString());
            player_Speed = Float.parseFloat(playerStats.getString("speed"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void loadWorld(Context context, GameWorld world){
        InputStream in = context.getResources().openRawResource(R.raw.test_level);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            int width = Integer.parseInt(reader.readLine());
            int height = Integer.parseInt(reader.readLine());

            int x = 0;
            int y = 0;
            while(reader.ready()) {
                char c = (char) reader.read();
                switch(c){
                    case '\n':
                        x=0;
                        y++;
                        break;
                    case '1':
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler(player_Speed);
                        PlayerPhysics playerPhysics = new PlayerPhysics();
                        GameObject player = new GameObject(
                                playerInputHandler, playerPhysics, x, y, GameObject.PLAYER_TAG);
                        world.addObject(player);
                        world.player = player;
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
