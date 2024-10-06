package TowerDefense;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import TowerDefense.Stages.base.GameStage;
import TowerDefense.Stages.Stage1;
import TowerDefense.Stages.Stage2;
import TowerDefense.Stages.Stage3;


public class GameScreen implements Screen {
	private Stage stage;

	public GameScreen() {
		stage = new Stage3();
	}

	@Override
	public void render(float delta) {
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		((GameStage)stage).resize(height, width);
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
