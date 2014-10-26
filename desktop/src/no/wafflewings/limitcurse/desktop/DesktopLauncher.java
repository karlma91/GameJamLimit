package no.wafflewings.limitcurse.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

import no.wafflewings.limitcurse.LimitCurse;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		//config.fullscreen = true;
		Settings settings = new Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        //TexturePacker.process(settings, "../images", "../android/assets", "game");
		new LwjglApplication(new LimitCurse(), config);
	}
}
