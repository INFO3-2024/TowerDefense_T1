package game;

import com.badlogic.gdx.Game;

import TowerDefense.GameScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
	@Override
	public void create() {
		setScreen(GameScreen.getInstance());
	}
}
