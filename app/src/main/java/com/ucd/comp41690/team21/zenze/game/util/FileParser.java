package com.ucd.comp41690.team21.zenze.game.util;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import com.ucd.comp41690.team21.zenze.backend.database.AppDatabase;
import com.ucd.comp41690.team21.zenze.backend.database.models.Attack;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackList;
import com.ucd.comp41690.team21.zenze.backend.database.models.AttackListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Enemy;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyList;
import com.ucd.comp41690.team21.zenze.backend.database.models.EnemyListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Item;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemList;
import com.ucd.comp41690.team21.zenze.backend.database.models.ItemListLine;
import com.ucd.comp41690.team21.zenze.backend.database.models.Player;
import com.ucd.comp41690.team21.zenze.backend.weather.WeatherStatus;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameState;
import com.ucd.comp41690.team21.zenze.game.GameWorld;
import com.ucd.comp41690.team21.zenze.game.components.AttackPhysics;
import com.ucd.comp41690.team21.zenze.game.components.CameraAI;
import com.ucd.comp41690.team21.zenze.game.components.EnemyAI;
import com.ucd.comp41690.team21.zenze.game.components.EnemyPhysics;
import com.ucd.comp41690.team21.zenze.game.components.PhysicsComponent;
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
import java.util.List;

/**
 * reads in the games config, fills and reads from the database
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
    private static int playerDamage = 0;
    private static int playerX = -1, playerY = -1;

    //Camera stats
    private static int cameraMovementWindow = 0;
    private static float cameraMinSpeed = 0;

    //Enemy stats
    private static float enemySize = 0;
    private static int enemyHealth = 0;
    private static int enemyDamage = 0;

    //Item stats
    private static float itemSize = 0;
    private static float specialItemSize = 0;

    //Tile Map stats
    private static int numTilesV = 0;
    private static int numTilesH = 0;
    private static float platformSize = 0;

    //Attack Stats
    private static int attackNormalHealth = 0;
    private static float attackNormalSpeed = 0;
    private static float attackNormalScale = 0;
    private static int attackNormalDamage = 0;

    private static int attackSpecialHealth = 0;
    private static float attackSpecialSpeed = 0;
    private static float attackSpecialScale = 0;
    private static int attackSpecialDamage = 0;

    //Images
    private static Bitmap tileImage, tileMiddleImage, backgroundImage, playerImage, enemyImage,
            itemImage, specialItemImage, attackSpecialImage, attackNormalImage;

    //attack and item info
    //item normal/sunny/rainy/snowy then attacks
    private static String[] infoTexts = new String[11];
    private static String[] infoNames = new String[11];

    //Weather state
    private static WeatherStatus weatherStatus;

    //Database Ids
    public static int DBIdNormalAttack = 0, DBIdSunnyAttack = 1, DBIdRainyAttack = 2, DBIdSnowyAttack = 3,
            DBIdPlayer = 4, DBIdNormalItem = 5, DBIdSunnyItem = 6, DBIdRainyItem = 7, DBIdSnowyItem=8, DBIdSunnyEnemy=9,
            DBIdRainyEnemy=10, DBIdSnowyEnemy=11;


    /**
     * used to fill the database and read the game config file
     * should be called before the level is created
     * @param context the games context
     * @param status the weatherstatus according to which the objects are initialised
     * @return returns a state for the game
     */
    public static GameState init(Context context, WeatherStatus status){
        weatherStatus = status;
        AppDatabase database = Room.databaseBuilder(context, AppDatabase.class, "zenze-db").allowMainThreadQueries().build();
        GameState state = initFromJSON(context);

        if(database.playerDao().countPlayers()==0) {

            //Add Attacks to Database
            Attack normalAttack = new Attack(infoNames[7], attackNormalDamage, "attack_normal",
                    (int)attackNormalScale, infoTexts[7], 3);
            normalAttack.setId(DBIdNormalAttack);
            database.attackDao().insertAll(normalAttack);
            Attack sunnyAttack = new Attack(infoNames[WeatherStatus.SUNNY.getValue()+4], attackNormalDamage, "attack_sunny",
                    (int)attackNormalScale, infoTexts[WeatherStatus.SUNNY.getValue()+4], WeatherStatus.SUNNY.getValue());
            sunnyAttack.setId(DBIdSunnyAttack);
            database.attackDao().insertAll(sunnyAttack);
            Attack rainyAttack = new Attack(infoNames[WeatherStatus.RAINY.getValue()+4], attackNormalDamage, "attack_rainy",
                    (int)attackNormalScale, infoTexts[WeatherStatus.RAINY.getValue()+4], WeatherStatus.RAINY.getValue());
            rainyAttack.setId(DBIdRainyAttack);
            database.attackDao().insertAll(rainyAttack);
            Attack snowyAttack = new Attack(infoNames[WeatherStatus.SNOWY.getValue()+4], attackNormalDamage, "attack_snowy",
                    (int)attackNormalScale, infoTexts[WeatherStatus.SNOWY.getValue()+4], WeatherStatus.SNOWY.getValue());
            snowyAttack.setId(DBIdSnowyAttack);
            database.attackDao().insertAll(snowyAttack);

            //Add Items to Database
            Item normalItem = new Item(infoNames[4], infoTexts[4], "item_normal", weatherStatus);
            normalItem.setId(DBIdNormalItem);
            database.itemDao().insertAll(normalItem);
            Item sunnyItem = new Item(infoNames[WeatherStatus.SUNNY.getValue()],
                    infoTexts[WeatherStatus.SUNNY.getValue()], "item_sunny", weatherStatus);
            sunnyItem.setId(DBIdSunnyItem);
            database.itemDao().insertAll(sunnyItem);
            Item rainyItem = new Item(infoNames[WeatherStatus.RAINY.getValue()],
                    infoTexts[WeatherStatus.RAINY.getValue()], "item_rainy", weatherStatus);
            rainyItem.setId(DBIdRainyItem);
            database.itemDao().insertAll(rainyItem);
            Item snowyItem = new Item(infoNames[WeatherStatus.SNOWY.getValue()],
                    infoTexts[WeatherStatus.SNOWY.getValue()], "item_snowy", weatherStatus);
            snowyItem.setId(DBIdSnowyItem);
            database.itemDao().insertAll(snowyItem);

            //Add Enemies to Database
            Enemy sunnyEnemy = new Enemy(infoNames[WeatherStatus.SUNNY.getValue()+8], enemyDamage, (int)enemySize,
                    infoTexts[WeatherStatus.SUNNY.getValue()+8], "enemy_sunny", 0, weatherStatus.getValue());
            sunnyEnemy.setId(DBIdSunnyEnemy);
            database.enemyDao().insertAll(sunnyEnemy);
            Enemy rainyEnemy = new Enemy(infoNames[WeatherStatus.RAINY.getValue()+8], enemyDamage, (int)enemySize,
                    infoTexts[WeatherStatus.RAINY.getValue()+8], "enemy_sunny", 0, weatherStatus.getValue());
            rainyEnemy.setId(DBIdRainyEnemy);
            database.enemyDao().insertAll(rainyEnemy);
            Enemy snowyEnemy = new Enemy(infoNames[WeatherStatus.SNOWY.getValue()+8], enemyDamage, (int)enemySize,
                    infoTexts[WeatherStatus.SNOWY.getValue()+8], "enemy_sunny", 0, weatherStatus.getValue());
            snowyEnemy.setId(DBIdSnowyEnemy);
            database.enemyDao().insertAll(snowyEnemy);

            //Add Player
            AttackList playerAttackList = new AttackList();
            playerAttackList.setId(24);
            database.attackListDao().insertAll(playerAttackList);
            EnemyList playerEnemyList = new EnemyList();
            playerEnemyList.setId(25);
            database.enemyListDao().insertAll(playerEnemyList);
            ItemList playerItemList = new ItemList();
            playerItemList.setId(26);
            database.itemListDao().insertAll(playerItemList);

            EnemyList el = database.enemyListDao().getAll().get(0);
            AttackList al = database.attackListDao().getAll().get(0);
            ItemList il = database.itemListDao().getAll().get(0);

            //Add the items/attacks/enemy lines to the player object
            EnemyListLine sunnyEnemyLine = new EnemyListLine(0,sunnyEnemy.getId(),el.getId());
            sunnyEnemyLine.setId(12);
            EnemyListLine rainyEnemyLine = new EnemyListLine(0,rainyEnemy.getId(),el.getId());
            rainyEnemyLine.setId(13);
            EnemyListLine snowyEnemyLine = new EnemyListLine(0,snowyEnemy.getId(),el.getId());
            snowyEnemyLine.setId(14);
            database.enemyListLineDao().insertAll(sunnyEnemyLine, snowyEnemyLine,rainyEnemyLine);
            AttackListLine normalAttackLine = new AttackListLine(0,normalAttack.getId(),al.getId());
            normalAttackLine.setId(1234);
            AttackListLine sunnyAttackLine = new AttackListLine(0,sunnyAttack.getId(),al.getId());
            sunnyAttackLine.setId(2345);
            AttackListLine rainyAttackLine = new AttackListLine(0,rainyAttack.getId(),al.getId());
            rainyAttackLine.setId(3456);
            AttackListLine snowyAttackLine = new AttackListLine(0,snowyAttack.getId(),al.getId());
            snowyAttackLine.setId(4567);
            //database.attackListLineDao().insertAll(normalAttackLine, sunnyAttackLine, rainyAttackLine, snowyAttackLine);
            ItemListLine normalItemLine = new ItemListLine(0,normalItem.getId(),il.getId());
            normalItemLine.setId(19);
            ItemListLine sunnyItemLine = new ItemListLine(0,sunnyItem.getId(),il.getId());
            sunnyItemLine.setId(20);
            ItemListLine snowyItemLine = new ItemListLine(0,snowyItem.getId(),il.getId());
            snowyItemLine.setId(21);
            ItemListLine rainyItemLine = new ItemListLine(0,rainyItem.getId(),il.getId());
            rainyItemLine.setId(22);
            database.itemListLineDao().insertAll(normalItemLine, sunnyItemLine, rainyItemLine, snowyItemLine);

            Player p = new Player(playerX, playerY, playerHealth, "toto", 1, il.getId(), al.getId(), el.getId());
            DBIdPlayer = p.getId();
            database.playerDao().insertAll(p);

        }else {
            //eg. load level, load coordinates, set game counters
            Player player = database.playerDao().getAll().get(0);
            playerX = player.getLastCoordX();
            playerY = player.getLastCoordY();
            playerHealth = player.getSavedHealth()==-1?playerHealth:player.getSavedHealth();

            AttackList al = database.attackListDao().findById(player.getAttackListId());
            ItemList il = database.itemListDao().findById(player.getItemListId());

            List<AttackListLine> attacks = database.attackListLineDao().getByAttackListId(al.getId());
            for (AttackListLine attack : attacks){
                if(attack.getAttackId() == DBIdSunnyAttack){
                    Game.getInstance().sunnyAttackCount = attack.getAmount();
                }
                if(attack.getAttackId() == DBIdRainyAttack){
                    Game.getInstance().rainyAttackCount = attack.getAmount();
                }
                if(attack.getAttackId() == DBIdSnowyAttack){
                    Game.getInstance().snowyAttackCount = attack.getAmount();
                }
            }
            List<ItemListLine> items = database.itemListLineDao().getByItemListId(il.getId());
            for (ItemListLine item : items){
                if(item.getItemId() == DBIdSunnyItem){
                    Game.getInstance().sunnyItemCount = item.getAmount();
                }
                if(item.getItemId() == DBIdRainyItem){
                    Game.getInstance().rainyItemCount = item.getAmount();
                }
                if(item.getItemId() == DBIdSnowyItem){
                    Game.getInstance().snowyItemCount = item.getAmount();
                }
                if(item.getItemId() == DBIdNormalItem){
                    Game.getInstance().normalItemCount = item.getAmount();
                }
            }
        }
        database.close();
        return state;
    }

    /**
     * Initialises the game from the game config file
     *
     * @param context the android context to get access to the resource files
     */
    public static GameState initFromJSON(Context context) {
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
            playerDamage = gameConfig.getInt("Player_Damage");
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
            enemyDamage = gameConfig.getInt("Enemy_Damage");
            //Attacks
            attackNormalHealth = gameConfig.getInt("AttackNormal_Health");
            attackNormalScale = (float) gameConfig.getDouble("AttackNormal_Scale");
            attackNormalSpeed = (float) gameConfig.getDouble("AttackNormal_Speed");
            attackNormalDamage = gameConfig.getInt("AttackNormal_Damage");

            attackSpecialHealth = gameConfig.getInt("AttackSpecial_Health");
            attackSpecialScale = (float) gameConfig.getDouble("AttackSpecial_Scale");
            attackSpecialSpeed = (float) gameConfig.getDouble("AttackSpecial_Speed");
            attackSpecialDamage = gameConfig.getInt("AttackSpecial_Damage");

            //Infos
            infoNames[3] = gameConfig.getString("ItemNormal_Name");
            infoNames[WeatherStatus.SUNNY.getValue()] = gameConfig.getString("ItemSunny_Name");
            infoNames[WeatherStatus.RAINY.getValue()] = gameConfig.getString("ItemRainy_Name");
            infoNames[WeatherStatus.SNOWY.getValue()] = gameConfig.getString("ItemSnowy_Name");
            infoNames[7] = gameConfig.getString("AttackNormal_Name");
            infoNames[WeatherStatus.SUNNY.getValue()+4] = gameConfig.getString("AttackSunny_Name");
            infoNames[WeatherStatus.RAINY.getValue()+4] = gameConfig.getString("AttackRainy_Name");
            infoNames[WeatherStatus.SNOWY.getValue()+4] = gameConfig.getString("AttackSnowy_Name");
            infoNames[WeatherStatus.SUNNY.getValue()+8] = gameConfig.getString("EnemySunny_Name");
            infoNames[WeatherStatus.RAINY.getValue()+8] = gameConfig.getString("EnemyRainy_Name");
            infoNames[WeatherStatus.SNOWY.getValue()+8] = gameConfig.getString("EnemySnowy_Name");

            infoTexts[3] = gameConfig.getString("ItemNormal_Text");
            infoTexts[WeatherStatus.SUNNY.getValue()] = gameConfig.getString("ItemSunny_Text");
            infoTexts[WeatherStatus.RAINY.getValue()] = gameConfig.getString("ItemRainy_Text");
            infoTexts[WeatherStatus.SNOWY.getValue()] = gameConfig.getString("ItemSnowy_Text");
            infoTexts[7] = gameConfig.getString("AttackNormal_Text");
            infoTexts[WeatherStatus.SUNNY.getValue()+4] = gameConfig.getString("AttackSunny_Text");
            infoTexts[WeatherStatus.RAINY.getValue()+4] = gameConfig.getString("AttackRainy_Text");
            infoTexts[WeatherStatus.SNOWY.getValue()+4] = gameConfig.getString("AttackSnowy_Text");
            infoTexts[WeatherStatus.SUNNY.getValue()+8] = gameConfig.getString("EnemySunny_Text");
            infoTexts[WeatherStatus.RAINY.getValue()+8] = gameConfig.getString("EnemyRainy_Text");
            infoTexts[WeatherStatus.SNOWY.getValue()+8] = gameConfig.getString("EnemySnowy_Text");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        playerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.character);
        itemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_normal);
        String[] specialAttackInfo = new String[]{
                infoNames[weatherStatus.getValue()+4],
                infoTexts[weatherStatus.getValue()+4].substring(0,Math.min(40, infoTexts[weatherStatus.getValue()+4].length()))+"...",
                infoTexts[weatherStatus.getValue()+4]};
        switch (weatherStatus) {
            case SUNNY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sunny);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_sunny);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_sunny);
                attackSpecialImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_sunny);
                attackNormalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.RED, backgroundImage, enemyImage, attackNormalImage,
                        attackSpecialImage, specialAttackInfo, weatherStatus);
            case RAINY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_rainy);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_rainy2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_rainy);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_rainy);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_rainy);
                attackSpecialImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_rainy);
                attackNormalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.BLUE, backgroundImage, enemyImage, attackNormalImage,
                        attackSpecialImage, specialAttackInfo, weatherStatus);
            case SNOWY:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_snowy);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_snowy2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_snowy);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_snowy);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_snowy);
                attackSpecialImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_snowy);
                attackNormalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.LTGRAY, backgroundImage, enemyImage, attackNormalImage,
                        attackSpecialImage, specialAttackInfo, weatherStatus);
            default:
                tileImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny);
                tileMiddleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_sunny2);
                backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_sunny);
                enemyImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_sunny);
                specialItemImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_sunny);
                attackSpecialImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_sunny);
                attackNormalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_normal);
                return new GameState(Color.GRAY, backgroundImage, enemyImage, attackNormalImage,
                        attackSpecialImage, specialAttackInfo, weatherStatus);
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
                        Type type = new Type(playerHealth, playerSpeed, playerScale, playerDamage,
                                playerImage, Color.WHITE, null, new String[]{}, DBIdPlayer, Type.PLAYER_TAG);
                        GameObject player;
                        if(playerX != -1 && playerY != -1) {
                            player = new GameObject(
                                    playerInputHandler, playerPhysics, type, playerX, playerY);
                        } else {
                            player = new GameObject(
                                    playerInputHandler, playerPhysics, type, x, y);
                        }
                        world.addObject(player);
                        world.setPlayer(player);
                        //initialise the camera
                        float viewFrustum = (Game.getInstance().getWidth() /
                                (Game.getInstance().getHeight() / numTilesH + 1) + 2) / 2;
                        GameObject simpleCamera = new GameObject(
                                new CameraAI(cameraMovementWindow, player, viewFrustum,
                                        cameraMinSpeed, 0.5f, numTilesV),
                                null, null, 0, 0);
                        world.addObject(simpleCamera);
                        world.setCamera(simpleCamera);
                        break;
                    case '#': //Platform
                        PhysicsComponent platformPhysics =
                                new PhysicsComponent(PhysicsComponent.RECTANGULAR, x, y, platformSize);
                        Type platformType = new Type(0, 0, platformSize, 0, tileImage, Color.BLACK,
                                weatherStatus, new String[]{}, -1, Type.PLATFORM_TAG);
                        GameObject platform = new GameObject(
                                null, platformPhysics, platformType, x, y);
                        world.addPlatform(platform);
                        break;
                    case '*': //solid platform
                        PhysicsComponent platformMiddlePhysics =
                                new PhysicsComponent(PhysicsComponent.RECTANGULAR, x, y, platformSize);
                        Type platformMiddleType = new Type(0, 0, platformSize, 0, tileMiddleImage,
                                Color.BLACK, weatherStatus, new String[]{}, -1, Type.M_PLATFORM_TAG);
                        GameObject platformMiddle = new GameObject(
                                null, platformMiddlePhysics, platformMiddleType, x, y);
                        world.addPlatform(platformMiddle);
                        break;
                    case 'I': //Item
                        PhysicsComponent itemPhysics =
                                new PhysicsComponent(PhysicsComponent.SPHERE, x, y, itemSize);
                        Type itemType = new Type(0, 0, itemSize, 0, itemImage, Color.YELLOW, null,
                                new String[]{infoNames[3], infoTexts[3].substring(0,Math.min(40, infoTexts[3].length())) + "...", infoTexts[3]},
                                DBIdNormalItem, Type.ITEM_TAG);
                        GameObject item = new GameObject(null, itemPhysics, itemType, x, y);
                        world.addObject(item);
                        break;
                    case 'S': //Special Item
                        PhysicsComponent specialItemPhysics =
                                new PhysicsComponent(PhysicsComponent.SPHERE, x, y, itemSize);
                        Type specialItemType = new Type(0, 0, specialItemSize, 0, specialItemImage,
                                Color.GREEN, weatherStatus,
                                new String[]{infoNames[weatherStatus.getValue()],
                                        infoTexts[weatherStatus.getValue()].substring(0,Math.min(40, infoTexts[weatherStatus.getValue()].length())) + "...",
                                        infoTexts[weatherStatus.getValue()]},
                                getDBIdSpecialItem(), Type.S_ITEM_TAG);
                        GameObject specialItem = new GameObject(
                                null, specialItemPhysics, specialItemType, x, y);
                        world.addObject(specialItem);
                        break;
                    case 'E': //Enemy
                        EnemyPhysics enemyPhysics = new EnemyPhysics(x,y,enemySize);
                        EnemyAI enemyAI = new EnemyAI();
                        Type enemyType = new Type(enemyHealth, 0, enemySize, enemyDamage, enemyImage, Color.CYAN,
                                weatherStatus, new String[]{infoNames[weatherStatus.getValue()+8],
                                infoTexts[weatherStatus.getValue()+8].substring(0,Math.min(40, infoTexts[weatherStatus.getValue()+8].length())) + "...",
                                infoTexts[weatherStatus.getValue()+8]}, getDBIdEnemy(), Type.ENEMY_TAG);
                        GameObject enemy = new GameObject(enemyAI, enemyPhysics, enemyType, x, y);
                        world.addObject(enemy);
                        break;
                }
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type attackNormalType = new Type(attackNormalHealth, attackNormalSpeed, attackNormalScale,
                attackNormalDamage, attackNormalImage, Color.MAGENTA, null,
                new String[]{infoNames[7],
                    infoTexts[7].substring(0,Math.min(40, infoTexts[7].length())) + "...",
                    infoTexts[7]}, DBIdNormalAttack, Type.ATTACK_TAG);

        AttackPhysics attackNormalPhysics = new AttackPhysics(0,0,attackNormalType);
        GameObject attackNormal = new GameObject(null, attackNormalPhysics, attackNormalType, 0,0);
        world.setAttackNormal(attackNormal);
        Type attackSpecialType = new Type(attackSpecialHealth, attackSpecialSpeed, attackSpecialScale,
                attackSpecialDamage, attackSpecialImage, Color.RED, null,
                new String[]{infoNames[weatherStatus.getValue()+4],
                    infoTexts[weatherStatus.getValue()+4].substring(0,Math.min(40, infoTexts[weatherStatus.getValue()+4].length())) + "...",
                    infoTexts[weatherStatus.getValue()+4]}, getDBIdSpecialAttack(), Type.ATTACK_TAG);
        AttackPhysics attackSpecialPhysics = new AttackPhysics(0,0,attackNormalType);
        GameObject attackSpecial = new GameObject(null, attackSpecialPhysics, attackSpecialType, 0,0);
        world.setAttackSpecial(attackSpecial);
    }

    public static int getNumTilesH() {
        return numTilesH;
    }

    public static int getNumTilesV() {
        return numTilesV;
    }

    /**
     * returns the id of the attacks object in the database depeding on the weatherstate
     * @return the id used in the database for the special attack
     */
    public static int getDBIdSpecialAttack(){
        switch (weatherStatus){
            case SUNNY:
                return DBIdSunnyAttack;
            case RAINY:
                return DBIdRainyAttack;
            case SNOWY:
                return DBIdSnowyAttack;
            default:
                return DBIdNormalAttack;
        }
    }

    /**
     * returns the id of the item object in the database depeding on the weatherstate
     * @return the id used in the database for the special item
     */
    public static int getDBIdSpecialItem(){
        switch (weatherStatus){
            case SUNNY:
                return DBIdSunnyItem;
            case RAINY:
                return DBIdRainyItem;
            case SNOWY:
                return DBIdSnowyItem;
            default:
                return DBIdNormalItem;
        }
    }

    /**
     * returns the id of the enemy object in the database depeding on the weatherstate
     * @return the id used in the database for the enemy
     */
    public static int getDBIdEnemy(){
        switch (weatherStatus){
            case SUNNY:
                return DBIdSunnyEnemy;
            case RAINY:
                return DBIdRainyEnemy;
            case SNOWY:
                return DBIdSnowyEnemy;
            default:
                return DBIdSunnyEnemy;
        }
    }
}
