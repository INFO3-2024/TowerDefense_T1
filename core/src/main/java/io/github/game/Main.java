package io.github.game;


import com.badlogic.gdx.Game;


import io.github.INFO32024.TowerDefense_T1.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
	@Override
	public void create() {
		setScreen(new GameScreen());
		
	}
}
