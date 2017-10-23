package com.ucd.comp41690.team21.zenze.game;

import android.content.Context;

import com.ucd.comp41690.team21.zenze.game.components.CameraAI;
import com.ucd.comp41690.team21.zenze.game.components.PlattformPhysics;
import com.ucd.comp41690.team21.zenze.game.components.PlayerInputHandler;
import com.ucd.comp41690.team21.zenze.game.components.PlayerPhysics;
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
    private static float playerSpeed = 0;
    private static int cameraMovementWindow = 0;
    private static float cameraMinSpeed = 0;
    private static int numTilesV = 0;
    private static int numTilesH = 0;

    /**
     * Initialises the game from the game config file
     *
     * @param context the android context to get access to the resource files
     */
    public static void init(Context context) {
        InputStream in = context.getResources().openRawResource(R.raw.game_config);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject playerStats = new JSONObject(sb.toString());
            playerSpeed = Float.parseFloat(playerStats.getString("Player_Speed"));
            cameraMovementWindow = playerStats.getInt("Camera_MovementWindow");
            cameraMinSpeed = playerStats.getInt("Camera_MinSpeed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the game world specified in test level
     *
     * @param context the android context to get access to the resource files
     * @param world   the world to fill with objects
     */
    public static void loadWorld(Context context, GameWorld world) {
        //read in all the objects
        InputStream in = context.getResources().openRawResource(R.raw.test_level);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            numTilesV = Integer.parseInt(reader.readLine());
            numTilesH = Integer.parseInt(reader.readLine());

            int x = 0;
            int y = 0;
            while (reader.ready()) {
                char c = (char) reader.read();
                switch (c) {
                    case '\n':
                        x = 0;
                        y++;
                        break;
                    case '1':
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler(playerSpeed);
                        PlayerPhysics playerPhysics = new PlayerPhysics();
                        GameObject player = new GameObject(
                                playerInputHandler, playerPhysics, x, y, GameObject.PLAYER_TAG);
                        world.addObject(player);
                        world.setPlayer(player);
                        //initialise the camera
                        float viewFrustum = (Game.getInstance().getWidth() /
                                (Game.getInstance().getHeight() / numTilesH + 1) + 2) / 2;
                        GameObject simpleCamera = new GameObject(
                                new CameraAI(cameraMovementWindow, player, viewFrustum,
                                        cameraMinSpeed, playerSpeed),
                                null, 0, 0, GameObject.CAMERA_TAG);
                        world.addObject(simpleCamera);
                        world.setCamera(simpleCamera);
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

    public static int getNumTilesH() {
        return numTilesH;
    }

    public static int getNumTilesV() {
        return numTilesV;
    }
}
