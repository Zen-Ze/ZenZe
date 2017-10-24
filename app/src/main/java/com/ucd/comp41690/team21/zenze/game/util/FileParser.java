package com.ucd.comp41690.team21.zenze.game.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameState;
import com.ucd.comp41690.team21.zenze.game.GameWorld;
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
            JSONObject gameConfig = new JSONObject(sb.toString());
            playerSpeed = (float)gameConfig.getJSONObject("Player").getDouble("Speed");
            cameraMovementWindow = gameConfig.getInt("Camera_MovementWindow");
            cameraMinSpeed = gameConfig.getInt("Camera_MinSpeed");
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
            numTilesH = Integer.parseInt(reader.readLine());
            int x = 0;
            int y = 0;
            while (reader.ready()) {
                char c = (char) reader.read();
                switch (c) {
                    case '\n':
                        numTilesV = x;
                        x = 0;
                        y++;
                        break;
                    case '1'://Player
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler(playerSpeed);
                        PlayerPhysics playerPhysics = new PlayerPhysics();
                        GameObject player = new GameObject(
                                playerInputHandler, playerPhysics, null, x, y, 1, GameObject.PLAYER_TAG);
                        world.addObject(player);
                        world.setPlayer(player);
                        //initialise the camera
                        float viewFrustum = (Game.getInstance().getWidth() /
                                (Game.getInstance().getHeight() / numTilesH + 1) + 2) / 2;
                        GameObject simpleCamera = new GameObject(
                                new CameraAI(cameraMovementWindow, player, viewFrustum,
                                        cameraMinSpeed, playerSpeed),
                                null, null, 0, 0, 1, GameObject.CAMERA_TAG);
                        world.addObject(simpleCamera);
                        world.setCamera(simpleCamera);
                        break;
                    case '#': //Platform
                        PlattformPhysics plattformPhysics = new PlattformPhysics();
                        GameObject platform = new GameObject(
                                null, plattformPhysics, null, x, y, 1, GameObject.PLATTFORM_TAG);
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

    public static GameState loadState(WeatherStatus status){
        switch (status){
            case SUNNY:
                return new GameState(Color.RED);
            case RAINY:
                return new GameState(Color.BLUE);
            case SNOWY:
                return new GameState(Color.WHITE);
            default:
                return new GameState(Color.GRAY);
        }
    }

    public static int getNumTilesH() {
        return numTilesH;
    }

    public static int getNumTilesV() {
        return numTilesV;
    }
}
