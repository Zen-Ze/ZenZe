package com.ucd.comp41690.team21.zenze.game.view;

import android.view.SurfaceView;
import com.ucd.comp41690.team21.zenze.game.*;

/**
 * Created by annalena on 19.10.17.
 */

public interface Renderer {
    void render(GameWorld world);
    SurfaceView getView();
    float getViewFrustum();
}
