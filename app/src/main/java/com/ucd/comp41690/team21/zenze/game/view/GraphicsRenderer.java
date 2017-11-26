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

    //UI Elements
    private Bitmap[] UIBitmaps;
    private Bitmap heart;

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

        //Initialise UI
        UIBitmaps = new Bitmap[7];
        UIBitmaps[0] = world.getPlayer().type.getImage();
        UIBitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
        UIBitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.item_normal);
        UIBitmaps[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_sunny);
        UIBitmaps[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_rainy);
        UIBitmaps[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.attack_snowy);
        UIBitmaps[6] = world.getState().getEnemyImage();
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

                        //Draw UI
                        paint.setColor(Color.LTGRAY);
                        //canvas.drawRect(0,0,width+1,tileSize,paint);
                        rect = new Rect(0, 0, (int) tileSize+ 1, (int) tileSize + 1);
                        canvas.drawBitmap(UIBitmaps[0], null, rect, paint);
                        //draw hearts
                        top = tileSize/4;
                        bottom = tileRatio+top;
                        left = tileSize+top;
                        right = tileSize*2 - top;
                        for(int i=0; i<world.getPlayer().health;i++){
                            rect = new Rect((int)left, (int)top, (int) right, (int) bottom);
                            canvas.drawBitmap(UIBitmaps[1], null, rect, paint);
                            left += bottom;
                            right += bottom;
                        }
                        //draw counters
                        paint.setColor(Color.BLACK);
                        rect = new Rect((int)(tileSize*3.5), (int)top, (int)(tileSize*4+1), (int)bottom);
                        canvas.drawBitmap(UIBitmaps[2], null, rect, paint);
                        canvas.drawText(Game.getInstance().normalItemCount+"",
                                tileSize*4.25f, tileSize*0.66f, paint);
                        rect = new Rect((int)(tileSize*5), (int)top, (int)(tileSize*5.5+1), (int)bottom);
                        canvas.drawBitmap(UIBitmaps[3], null, rect, paint);
                        canvas.drawText(Game.getInstance().sunnyAttackCount+"",
                                tileSize*5.75f, tileSize*0.66f, paint);
                        rect = new Rect((int)(tileSize*6.5), (int)top, (int)(tileSize*7+1), (int)bottom);
                        canvas.drawBitmap(UIBitmaps[4], null, rect, paint);
                        canvas.drawText(Game.getInstance().rainyAttackCount+"",
                                tileSize*7.25f, tileSize*0.66f, paint);
                        rect = new Rect((int)(tileSize*8), (int)top, (int)(tileSize*8.5+1), (int)bottom);
                        canvas.drawBitmap(UIBitmaps[5], null, rect, paint);
                        canvas.drawText(Game.getInstance().snowyAttackCount+"",
                                tileSize*8.75f, tileSize*0.66f, paint);
                        //draw log
                        paint.setColor(Color.WHITE);
                        paint.setTextSize(75);
                        canvas.drawText(Game.getInstance().log, 100, tileSize+tileRatio, paint);
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
