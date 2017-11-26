package com.ucd.comp41690.team21.zenze.game.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.ucd.comp41690.team21.zenze.R;
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
    private final float tileRatio;

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
        tileSize = height / (float) numTilesH;
        tileRatio = tileSize/2;
        numTilesAcross = (int) (width / tileSize) + 2;

        Game.getInstance().UIHeight = (int)tileSize;
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
                        for (GameObject o : world.getMap()) {
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
                        float x, y;
                        for (GameObject o : world.getEntities()) {
                            if(o.type != null) {
                                x = (o.x_Pos - offset) * tileSize + tileSize / 2;
                                y = o.y_Pos * tileSize + tileSize / 2;
                                paint.setStyle(Paint.Style.FILL);
                                paint.setColor(o.type.getColour());
                                canvas.drawCircle(x, y, tileSize / 2 * o.scale, paint);
                            }
                        }
                    }

                    //Draw UI
                    paint.setColor(Color.LTGRAY);
                    canvas.drawRect(0,0,width+1,tileSize,paint);
                    paint.setColor(Color.WHITE);
                    canvas.drawCircle(tileRatio, tileRatio, tileRatio, paint);
                    //draw hearts
                    paint.setColor(Color.RED);
                    for(int i=0; i<world.getPlayer().health;i++){
                        canvas.drawCircle(tileRatio*3+i*0.75f*tileSize,tileRatio, tileRatio/2, paint);
                    }
                    //draw counters
                    paint.setColor(Color.DKGRAY);
                    Rect rect = new Rect((int)(tileSize*3.5), (int)(tileRatio/2), (int)(tileSize*4+1), (int)(tileSize*0.75f));
                    canvas.drawRect(rect, paint);
                    rect = new Rect((int)(tileSize*5), (int)(tileRatio/2), (int)(tileSize*5.5+1), (int)(tileSize*0.75f));
                    canvas.drawRect(rect, paint);
                    rect = new Rect((int)(tileSize*6.5), (int)(tileRatio/2), (int)(tileSize*7+1), (int)(tileSize*0.75f));
                    canvas.drawRect(rect, paint);
                    rect = new Rect((int)(tileSize*8), (int)(tileRatio/2), (int)(tileSize*8.5+1), (int)(tileSize*0.75f));
                    canvas.drawRect(rect, paint);

                    paint.setColor(Color.WHITE);
                    canvas.drawText(Game.getInstance().normalItemCount+"",
                            tileSize*4.25f, tileSize*0.66f, paint);

                    canvas.drawText(Game.getInstance().sunnyAttackCount+"",
                            tileSize*5.75f, tileSize*0.66f, paint);

                    canvas.drawText(Game.getInstance().rainyAttackCount+"",
                            tileSize*7.25f, tileSize*0.66f, paint);

                    canvas.drawText(Game.getInstance().snowyAttackCount+"",
                            tileSize*8.75f, tileSize*0.66f, paint);
                    //draw log
                    paint.setColor(Color.WHITE);
                    paint.setTextSize(75);
                    canvas.drawText(Game.getInstance().log, 100, tileSize+tileRatio, paint);
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
