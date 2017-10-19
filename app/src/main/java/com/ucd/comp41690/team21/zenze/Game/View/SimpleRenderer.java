package com.ucd.comp41690.team21.zenze.Game.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ucd.comp41690.team21.zenze.Game.GameObject;
import com.ucd.comp41690.team21.zenze.Game.GameWorld;
import com.ucd.comp41690.team21.zenze.Game.Map;
import com.ucd.comp41690.team21.zenze.Game.View.Renderer;


/**
 * Draws the Game using simple shapes like circles
 */
public class SimpleRenderer extends SurfaceView implements Renderer {

    //Stuff to draw on
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public static final int tileSize = 100;

    /**
     * Initialise the canvas for the renderer
     * @param context Android Activity the game is displayed in
     */
    public SimpleRenderer(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void render(GameWorld world) {
        int tileDimensionX = getWidth()/world.getMap().getWidth()+1;
        int tileDimensionY = getHeight()/world.getMap().getHeight()+1;

        //check if surface is valid
        if(surfaceHolder.getSurface().isValid()){
            //lock the canvas to draw
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLUE);
            for(GameObject o : world.getEntities()) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.WHITE);
                int x = o.getX_Pos()*tileDimensionX+tileDimensionX/2;
                int y = o.getY_Pos()*tileDimensionY+tileDimensionY/2;
                canvas.drawCircle(x, y, tileDimensionY/2, paint);
            }

            paint.setColor(Color.BLACK);
            Map map = world.getMap();
            for(int x = 0; x < map.getWidth(); x++){
                for (int y = 0; y < map.getHeight(); y++){
                    if(map.getTile(x,y)){
                        canvas.drawRect(
                                x*tileDimensionX,
                                y*tileDimensionY,
                                x*tileDimensionX+tileDimensionX,
                                y*tileDimensionY+tileDimensionY,
                                paint
                        );
                    }
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
