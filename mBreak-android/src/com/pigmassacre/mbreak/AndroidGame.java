package com.pigmassacre.mbreak;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.view.Display;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

@SuppressLint("NewApi")
public class AndroidGame extends AndroidApplication {
    public void onCreate (android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Display display = getWindowManager().getDefaultDisplay();
        int newScale;
        if (android.os.Build.VERSION.SDK_INT >= 13) {
        	Point size = new Point();
            display.getSize(size);
            newScale = size.x / Settings.BASE_SCREEN_WIDTH;
        } else {
        	newScale = display.getWidth() / Settings.BASE_SCREEN_WIDTH;
        }
        Settings.GAME_SCALE = newScale;
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.hideStatusBar = true;
        initialize(new MBreak(), cfg);
    }
}