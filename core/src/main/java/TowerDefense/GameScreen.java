package TowerDefense;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import TowerDefense.Stages.base.GameStage;
import TowerDefense.Lobby.Lobby;
import TowerDefense.Stages.Stage1;
import TowerDefense.Stages.Stage2;
import TowerDefense.Stages.Stage3;

public class GameScreen implements Screen {
	private Stage stage;
	private Stage lobby;
	private int keyGame;
	private int dificultyGame;
	private boolean checkNext;

	public GameScreen() {
		this.checkNext = true;
		keyGame = 1;
		dificultyGame = 0;
		update();
	}

	@Override
	public void render(float delta) {
		switch (keyGame) {
			case 1:
				lobby.act();
				lobby.draw();
				keyGame = ((Lobby) lobby).updateKey();
				dificultyGame = ((Lobby) lobby).updateDificulty();
				break;

			default:
				stage.act();
				stage.draw();
				keyGame = ((GameStage) stage).updateKey(keyGame);
				break;
		}
		update();
	}

	private void update() {
		if (keyGame == 2 && checkNext == false) {
			stage = new Stage1(dificultyGame);
			checkNext = true;
		} else if (keyGame == 3 && checkNext == true) {
			stage = new Stage2(dificultyGame);
			checkNext = false;
		} else if (keyGame == 4 && checkNext == false) {
			stage = new Stage3(dificultyGame);
			checkNext = true;
		} else if (keyGame == 1 && checkNext == true) {
			lobby = new Lobby();
			checkNext = false;
		}

	}

	@Override
	public void resize(int width, int height) {
		// ((GameStage) stage).resize(height, width);
		// stage.act();
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
