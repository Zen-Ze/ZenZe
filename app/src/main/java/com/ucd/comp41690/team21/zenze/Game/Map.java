package com.ucd.comp41690.team21.zenze.Game;

/**
 * Created by annalena on 19.10.17.
 */

public class Map {
    private boolean[][] map;
    private int width;
    private int height;

    public Map (boolean[][] map){
        this.map = map;
        width = map.length;
        height = map[0].length;
    }

    public boolean getTile(int x, int y) {
        return map[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
