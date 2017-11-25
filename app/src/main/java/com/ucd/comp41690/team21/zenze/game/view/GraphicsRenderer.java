package com.ucd.comp41690.team21.zenze.game.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.R;
import com.ucd.comp41690.team21.zenze.game.Game;
import com.ucd.comp41690.team21.zenze.game.GameObject;
import com.ucd.comp41690.team21.zenze.game.GameWorld;


/**
 * Draws the game using simple shapes like circles
 */
public class GraphicsRenderer extends SurfaceView implements Renderer {

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
    private final float tileRatio;

    /**
     * Initialise the canvas for the renderer
     *
     * @param context Android Activity the game is displayed in
     */
    public GraphicsRenderer(Context context, GameWorld world) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();

        width = Game.getInstance().getWidth();
        height = Game.getInstance().getHeight();
        numTilesH = world.getNumTilesH();
        numTilesV = world.getNumTilesV();
        tileSize = height / (float) numTilesH;
        tileRatio = tileSize / 2;
        numTilesAcross = (int) (width / tileSize) + 2;
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
                        //set camera position
                        float offset = world.getCamera().x_Pos - (numTilesAcross / 2);
                        Game.getInstance().log = offset + "";
                        //Background
                        Bitmap background = world.getState().getBackgroundImage();
                        /*float bgRight = background.getWidth() / 2 + (offset * 100);
                        float screenMiddle = bgRight > background.getWidth() ?
                                (width - (bgRight - background.getWidth())) : (width + 1);

                        Rect rect = new Rect(0, 0, (int) screenMiddle, height + 1);
                        Rect src = new Rect((int) (offset * 100), 0, (int) bgRight, background.getHeight());

                        Rect rect2 = new Rect((int) screenMiddle, 0, width + 1, height + 1);
                        Rect src2 = new Rect(0,0,background.getWidth()/2, background.getHeight());

                        canvas.drawBitmap(world.getState().getBackgroundImage(), src, rect, paint);
                        canvas.drawBitmap(world.getState().getBackgroundImage(), src2, rect2, paint);*/

                        Rect rect = new Rect(0, 0, width + 1, height + 1);
                        Rect src = new Rect(0, 0, background.getWidth()/2, background.getHeight());
                        canvas.drawBitmap(world.getState().getBackgroundImage(), src, rect, paint);

                        //Draw the tile map
                        float left, right, top, bottom;
                        for (GameObject o : world.getMap()) {
                            left = (o.x_Pos - offset) * tileSize - o.scale * tileRatio + tileRatio;
                            if (left >= -tileSize && left <= width) {
                                right = (o.x_Pos - offset) * tileSize + o.scale * tileRatio + tileRatio;
                                top = o.y_Pos * tileSize - o.scale * tileRatio + tileRatio;
                                bottom = o.y_Pos * tileSize + o.scale * tileRatio + tileRatio;
                                rect = new Rect((int) left,
                                        (int) top,
                                        ((int) right) + 1,
                                        ((int) bottom) + 1);
                                canvas.drawBitmap(o.type.getImage(), null, rect, paint);
                            }
                        }
                        //Draw each entity in the game world
                        for (GameObject o : world.getEntities()) {
                            if (o.type != null) {
                                left = (o.x_Pos - offset) * tileSize - o.scale * tileRatio + tileRatio;
                                if (left >= -tileSize && left <= width) {
                                    right = (o.x_Pos - offset) * tileSize + o.scale * tileRatio + tileRatio;
                                    top = o.y_Pos * tileSize - o.scale * tileRatio + tileRatio;
                                    bottom = o.y_Pos * tileSize + o.scale * tileRatio + tileRatio;
                                    rect = new Rect((int) left,
                                            (int) top,
                                            ((int) right) + 1,
                                            ((int) bottom) + 1);
                                    canvas.drawBitmap(o.type.getImage(), null, rect, paint);
                                }
                            }
                        }

                        paint.setColor(Color.WHITE);
                        paint.setTextSize(75);
                        canvas.drawText(Game.getInstance().log, 100, 200, paint);
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
