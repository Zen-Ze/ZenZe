package com.ucd.comp41690.team21.zenze.Game.View;

import android.view.SurfaceView;
import com.ucd.comp41690.team21.zenze.Game.*;

/**
 * Created by annalena on 19.10.17.
 */

public interface Renderer {
    public void render(GameWorld world);
    public SurfaceView getView();
    public float getViewFrustum();
}
