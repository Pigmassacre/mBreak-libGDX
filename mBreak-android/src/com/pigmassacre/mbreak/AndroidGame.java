package com.pigmassacre.mbreak;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidGame extends AndroidApplication {
    public void onCreate (android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.hideStatusBar = true;
        initialize(new MBreak(), cfg);
    }
}