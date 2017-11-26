package com.ucd.comp41690.team21.zenze.game.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameState;
import com.ucd.comp41690.team21.zenze.game.GameWorld;
import com.ucd.comp41690.team21.zenze.game.components.CameraAI;
import com.ucd.comp41690.team21.zenze.game.components.EnemyAI;
import com.ucd.comp41690.team21.zenze.game.components.EnemyPhyiscs;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
import com.ucd.comp41690.team21.zenze.game.components.PlatformPhysics;
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

    //Enemy stats
    private static float enemySize = 0;
    private static int enemyHealth = 0;

    //Item stats
    private static float itemSize = 0;
    private static float specialItemSize = 0;
    private static String itemString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis pharetra, tellus quis varius euismod...";

    //Tile Map stats
    private static int numTilesV = 0;
    private static int numTilesH = 0;
    private static float platformSize = 0;

    //Images
    private static Bitmap tileImage, tileMiddleImage, backgroundImage, playerImage, enemyImage,
            itemImage, specialItemImage, enemyAttackImage, attackImage;

    /**
     * Initialises the game from the game config file
     *
     * @param context the android context to get access to the resource files
     */
    public static GameState init(Context context, WeatherStatus status) {
        InputStream in = context.getResources().openRawResource(R.raw.game_config);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject gameConfig = new JSONObject(sb.toString());
            //Player
            playerSpeed = gameConfig.getInt("Player_Speed");
            playerMaxJumpHeight = gameConfig.getInt("Player_MaxJumpHeight");
            playerMinJumpHeight = gameConfig.getInt("Player_MinJumpHeight");
            playerJumpTime = (float) gameConfig.getDouble("Player_JumpTime");
            playerScale = (float) gameConfig.getDouble("Player_Scale");
            playerHealth = gameConfig.getInt("Player_Health");
            //camera
            cameraMovementWindow = gameConfig.getInt("Camera_MovementWindow");
            cameraMinSpeed = gameConfig.getInt("Camera_MinSpeed");
            //Items
            itemSize = (float) gameConfig.getDouble("Item_Scale");
            specialItemSize = (float) gameConfig.getDouble("SpecialItem_Scale");
            //Platforms
            platformSize = (float) gameConfig.getDouble("Platform_Scale");
            //Enemies
            enemyHealth = gameConfig.getInt("Enemy_Health");
            enemySize = (float) gameConfig.getDouble("Enemy_Scale");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.character);
        itemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_normal);
        switch (status) {
            case SUNNY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sunny);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_sunny);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_sunny);
                enemyAttackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_sunny);
                attackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.RED, backgroundImage, enemyImage, status);
            case RAINY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_rainy);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_rainy2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_rainy);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_rainy);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_rainy);
                enemyAttackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_rainy);
                attackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.BLUE, backgroundImage, enemyImage, status);
            case SNOWY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_snowy);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_snowy2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_snowy);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_snowy);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_snowy);
                enemyAttackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_snowy);
                attackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.LTGRAY, backgroundImage, enemyImage, status);
            default:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sunny);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_sunny);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_sunny);
                enemyAttackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_sunny);
                attackImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.GRAY, backgroundImage, enemyImage, status);
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
                        continue;
                    case 'P'://Player
                        PlayerInputHandler playerInputHandler = new PlayerInputHandler();
                        PlayerPhysics playerPhysics = new PlayerPhysics(playerMinJumpHeight,
                                playerMaxJumpHeight, playerJumpTime, numTilesV, numTilesH, x, y, playerScale);
                        Type type = new Type(playerHealth, playerSpeed, playerScale, playerImage, Color.WHITE, null, "");
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
                        PlatformPhysics platformPhysics =
                                new PlatformPhysics(x, y, platformSize);
                        Type platformType = new Type(0, 0, platformSize, tileImage, Color.BLACK, world.getState().getStatus(), "");
                        GameObject platform = new GameObject(
                                null, platformPhysics, platformType, x, y, GameObject.PLATFORM_TAG);
                        world.addPlatform(platform);
                        break;
                    case '*':
                        PlatformPhysics platformMiddlePhysics =
                                new PlatformPhysics(x, y, platformSize);
                        Type platformMiddleType = new Type(0, 0, platformSize, tileMiddleImage, Color.BLACK, world.getState().getStatus(), "");
                        GameObject platformMiddle = new GameObject(
                                null, platformMiddlePhysics, platformMiddleType, x, y, GameObject.M_PLATFORM_TAG);
                        world.addPlatform(platformMiddle);
                        break;
                    case 'I':
                        PhysicsComponent itemPhysics =
                                new PhysicsComponent(PhysicsComponent.SPHERE, x, y, itemSize);
                        Type itemType = new Type(0, 0, itemSize, itemImage, Color.YELLOW, null, itemString);
                        GameObject item = new GameObject(null, itemPhysics, itemType, x, y, GameObject.ITEM_TAG);
                        world.addObject(item);
                        break;
                    case 'S':
                        PhysicsComponent specialItemPhysics =
                                new PhysicsComponent(PhysicsComponent.SPHERE, x, y, itemSize);
                        Type specialItemType = new Type(0, 0, specialItemSize, specialItemImage,
                                Color.GREEN, world.getState().getStatus(), itemString);
                        GameObject specialItem = new GameObject(
                                null, specialItemPhysics, specialItemType, x, y, GameObject.S_ITEM_TAG);
                        world.addObject(specialItem);
                        break;
                    case 'E':
                        EnemyPhyiscs enemyPhysics = new EnemyPhyiscs(x,y,enemySize);
                        EnemyAI enemyAI = new EnemyAI();
                        Type enemyType = new Type(enemyHealth, 0, enemySize, enemyImage, Color.CYAN,
                                world.getState().getStatus(), itemString);
                        GameObject enemy = new GameObject(enemyAI, enemyPhysics, enemyType, x, y, GameObject.ENEMY_TAG);
                        world.addObject(enemy);
                        break;
                }
                x++;
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
