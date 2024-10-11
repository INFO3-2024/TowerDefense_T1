package TowerDefense;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import TowerDefense.Stages.base.GameStage;
import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.Lobby.Lobby;
import TowerDefense.Stages.Stage1;
import TowerDefense.Stages.Stage2;
import TowerDefense.Stages.Stage3;

public class GameScreen implements Screen {
	private static final GameScreen INSTANCE = new GameScreen();

	private AssetsManager assetsManager;

	private Stage stage;
	private Stage lobby;
	private int keyGame;
	private int dificultyGame;
	private boolean checkNext;

	private GameScreen() {
		this.checkNext = true;

		assetsManager = AssetsManager.getInstance();

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

		assetsManager.update(delta);
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

	public static GameScreen getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		assetsManager.dispose();
	}

	@Override
	public void resize(int width, int height){ /* pass */ }

	@Override
	public void show() { /* pass */ }

	@Override
	public void pause() { /* pass */ }

	@Override
	public void resume() { /* pass */ }

	@Override
	public void hide() { /* pass */ }
}
