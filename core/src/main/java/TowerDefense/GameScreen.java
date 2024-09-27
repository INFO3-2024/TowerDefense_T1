package TowerDefense;

import com.badlogic.gdx.Screen;

import TowerDefense.Map.Stage1;

public class GameScreen implements Screen {
	private Stage1 stage;

	public GameScreen() {
		stage = new Stage1();
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.resize(height, width);
		stage.act();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}
}
