package com.ucd.comp41690.team21.zenze.Game.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.Game.Game;
import com.ucd.comp41690.team21.zenze.Game.GameObject;
import com.ucd.comp41690.team21.zenze.Game.GameWorld;


/**
 * Draws the Game using simple shapes like circles
 */
public class SimpleRenderer extends SurfaceView implements Renderer {

    //Stuff to draw on
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private final int width;
    private final int height;
    private final int tileSize;

    /**
     * Initialise the canvas for the renderer
     * @param context Android Activity the game is displayed in
     */
    public SimpleRenderer(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        width = Game.getWidth();
        height = Game.getHeight();
        tileSize = height/15;
    }

    @Override
    public void render(GameWorld world) {

        //check if surface is valid
        if(surfaceHolder.getSurface().isValid()){
            //lock the canvas to draw
            canvas = surfaceHolder.lockCanvas();
            //Background color
            canvas.drawColor(Color.BLUE);
            //Draw each entity in the game world
            for(GameObject o : world.getEntities()) {
                switch(o.getTag()){
                    //represent Player as white circle
                    case GameObject.PLAYER_TAG:
                        int x = o.getX_Pos()*tileSize+tileSize/2;
                        int y = o.getY_Pos()*tileSize+tileSize/2;
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.WHITE);
                        canvas.drawCircle(x, y, tileSize/2, paint);
                        break;
                    //represent Plattforms as black boxes
                    case GameObject.PLATTFORM_TAG:
                        paint.setStyle(Paint.Style.FILL);
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(
                                o.getX_Pos()*tileSize,
                                o.getY_Pos()*tileSize,
                                o.getX_Pos()*tileSize+tileSize,
                                o.getY_Pos()*tileSize+tileSize,
                                paint
                        );
                        break;
                }

            }

            //unlock canvas after drawing
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public SurfaceView getView() {
        return this;
    }
}
