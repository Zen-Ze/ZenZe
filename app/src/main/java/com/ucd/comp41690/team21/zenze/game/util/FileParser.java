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
import com.ucd.comp41690.team21.zenze.game.components.Type;

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
    //Player stats
    private static int playerSpeed = 0;
    private static int playerMinJumpHeight = 0;
    private static int playerMaxJumpHeight = 0;
    private static float playerJumpTime = 0;
    private static float playerScale = 0;
    private static int playerHealth = 0;

    //Camera stats
    private static int cameraMovementWindow = 0;
    private static float cameraMinSpeed = 0;

    //Tile Map stats
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
            playerSpeed = gameConfig.getInt("Player_Speed");
            playerMaxJumpHeight = gameConfig.getInt("Player_MaxJumpHeight");
            playerMinJumpHeight = gameConfig.getInt("Player_MinJumpHeight");
            playerJumpTime = (float) gameConfig.getDouble("Player_JumpTime");
            playerScale = (float) gameConfig.getDouble("Player_Scale");
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
            numTilesV = Integer.parseInt(reader.readLine());
            int x = 0;
            int y = 0;
            while (reader.ready()) {
                char c = (char) reader.read();
                switch (c) {
                    case '\n':
                        x = 0;
                        y++;
                        break;
                    case '1'://Player
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler();
                        PlayerPhysics playerPhysics = new PlayerPhysics(playerMinJumpHeight,
                                playerMaxJumpHeight, playerJumpTime, numTilesV, numTilesH, playerScale);
                        Type type = new Type(playerHealth, playerSpeed, playerScale);
                        GameObject player = new GameObject(
                                playerInputHandler, playerPhysics, type, x, y, GameObject.PLAYER_TAG);
                        world.addObject(player);
                        world.setPlayer(player);
                        //initialise the camera
                        float viewFrustum = (Game.getInstance().getWidth() /
                                (Game.getInstance().getHeight() / numTilesH + 1) + 2) / 2;
                        GameObject simpleCamera = new GameObject(
                                new CameraAI(cameraMovementWindow, player, viewFrustum,
                                        cameraMinSpeed, 0.5f, numTilesV),
                                null, null, 0, 0, GameObject.CAMERA_TAG);
                        world.addObject(simpleCamera);
                        world.setCamera(simpleCamera);
                        break;
                    case '#': //Platform
                        PlattformPhysics plattformPhysics = new PlattformPhysics();
                        GameObject platform = new GameObject(
                                null, plattformPhysics, null, x, y, GameObject.PLATTFORM_TAG);
                        world.addPlatform(platform);
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

    public static GameState loadState(WeatherStatus status) {
        switch (status) {
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
