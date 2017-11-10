package com.ucd.comp41690.team21.zenze.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameWorld;


/**
 * Draws the game using simple shapes like circles
 */
public class SimpleRenderer extends SurfaceView implements Renderer {

    //Stuff to draw on
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //dimensions
    private final int numTilesH;
    private final int numTilesV;
    private final int numTilesAcross;
    private final int width;
    private final int height;
    private final float tileSize;

    /**
     * Initialise the canvas for the renderer
     *
     * @param context Android Activity the game is displayed in
     */
    public SimpleRenderer(Context context, GameWorld world) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        width = Game.getInstance().getWidth();
        height = Game.getInstance().getHeight();
        numTilesH = world.getNumTilesH();
        numTilesV = world.getNumTilesV();
        tileSize = height / (float)numTilesH;
        numTilesAcross = (int)(width / tileSize) + 2;
    }

    @Override
    public void render(GameWorld world) {
        canvas = null;

        synchronized (surfaceHolder) {
            try {
                //check if surface is valid
                if (surfaceHolder.getSurface().isValid()) {
                    //lock the canvas to draw
                    if ((canvas = surfaceHolder.getSurface().lockHardwareCanvas()) != null) {
                        //Background color
                        canvas.drawColor(world.getState().getBackground()
                        );

                        //set camera position
                        float offset = world.getCamera().x_Pos - (numTilesAcross / 2);

                        //Draw the tile map
                        for (GameObject o : world.getMap()){
                            //represent as black rectangles
                            paint.setStyle(Paint.Style.FILL);
                            paint.setColor(Color.BLACK);
                            canvas.drawRect(
                                    (o.x_Pos - offset) * tileSize,
                                    o.y_Pos * tileSize,
                                    (o.x_Pos - offset) * tileSize + tileSize,
                                    o.y_Pos * tileSize + tileSize,
                                    paint
                            );
                        }
                        //Draw each entity in the game world
                        for (GameObject o : world.getEntities()) {
                            switch (o.getTag()) {
                                //represent Player as white circle
                                case GameObject.PLAYER_TAG:
                                    float x = (o.x_Pos - offset) * tileSize + tileSize / 2;
                                    float y = o.y_Pos * tileSize + tileSize / 2;
                                    paint.setStyle(Paint.Style.FILL);
                                    paint.setColor(Color.WHITE);
                                    canvas.drawCircle(x, y, tileSize / 2 * o.scale, paint);
                                    break;
                            }
                        }

                        paint.setColor(Color.WHITE);
                        paint.setTextSize(75);
                        canvas.drawText(Game.getInstance().log, 100,200,paint);
                    }
                }
            } finally {
                if (canvas != null) {
                    //unlock canvas after drawing
                    surfaceHolder.getSurface().unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    @Override
    public SurfaceView getView() {
        return this;
    }

    @Override
    public float getViewFrustum() {
        return numTilesAcross / 2;
    }
}
