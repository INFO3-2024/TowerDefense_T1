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
	private boolean enableSoundMusic;
	private boolean enableSoundEffects;
	public GameScreen() {
		checkNext = false;
		keyGame = 1;
		dificultyGame = 0;
		enableSoundMusic = true;
		enableSoundEffects = true;
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
				enableSoundMusic = ((Lobby)lobby).getMusicPlay();
				enableSoundEffects = ((Lobby)lobby).getPlayEffects();
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
		if(keyGame == 5){
			checkNext = true;
		}		
		if(keyGame == 6){
			checkNext = true;
		}
		

		if (keyGame == 1 && checkNext == false) {
			if(lobby !=null){

				lobby.dispose();
			}
			lobby = new Lobby();
			checkNext = true;
			((Lobby)lobby).setMusicPlay(enableSoundMusic);
			((Lobby)lobby).setPlayEffects(enableSoundEffects);
		}
		else if (keyGame == 2 && checkNext == true) {
			stage = new Stage1(dificultyGame);
			checkNext = false;
			((GameStage)stage).setMusic(enableSoundMusic);
			((GameStage)stage).setPlayEffects(enableSoundEffects);

		} else if (keyGame == 3 && checkNext == false) {

			stage = new Stage2(dificultyGame);
			checkNext = true;
			((GameStage)stage).setMusic(enableSoundMusic);
			((GameStage)stage).setPlayEffects(enableSoundEffects);

		} else if (keyGame == 4 && checkNext == true) {

			stage = new Stage3(dificultyGame);
			checkNext = false;
			((GameStage)stage).setMusic(enableSoundMusic);
			((GameStage)stage).setPlayEffects(enableSoundEffects);

		}  else if (keyGame == 5 && checkNext == true) {

			stage.dispose();
			stage = new Stage1(dificultyGame);
			keyGame = 2;
			checkNext = false;
			((GameStage)stage).setMusic(enableSoundMusic);
			((GameStage)stage).setPlayEffects(enableSoundEffects);

		} else if (keyGame == 6 && checkNext == true) {
			
			boolean oldConfigurationMusic = ((Lobby)lobby).getMusicPlay();
			boolean oldConfigurationEffects = ((Lobby)lobby).getPlayEffects();
			enableSoundMusic = oldConfigurationMusic;
			enableSoundEffects = oldConfigurationEffects;
			keyGame = 1;
			lobby = new Lobby();
			((Lobby)lobby).setMusicPlay(enableSoundMusic);
			((Lobby)lobby).setPlayEffects(enableSoundEffects);
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
