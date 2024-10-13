package TowerDefense.Stages.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.Cannons.Cannon;
import TowerDefense.GameObjects.Fish.Fish;
import TowerDefense.GameObjects.Interface.BuildMenu;
import TowerDefense.GameObjects.Interface.UpgradeMenu;

import TowerDefense.GameObjects.VictoryAnimation.WinAnimation;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.PowerUp;
import TowerDefense.GameObjects.base.UIElement;
import TowerDefense.GameObjects.base.Wave;
import TowerDefense.Lobby.utils.ButtonsLobby;
import TowerDefense.Lobby.utils.ConstantsButtons;
import TowerDefense.Map.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameStage extends Stage {
	protected Map mapGame;
	protected AssetsControl assetsManager;
	protected SpriteBatch batch;

	protected ArrayList<Mermaid> towers;
	protected ArrayList<Enemy> enemies;

	protected Sprite mousePosSprite;

	protected Circle turretRangeCircle;
	protected ShapeRenderer shapeRenderer;

	protected Wave wave;

	protected Texture gameTexture;
	protected Texture statusFinalGameBg;

	protected int textureOffset;
	private Music music;
	protected InterfaceMenu buildMode;

	protected int coins;

	protected JsonValue waves;

	private int stageLevel;
	private int levelDificulty;

	private UIElement coinsUIElement;
	private UIElement pirateUiElement;
	private UIElement waveUiElement;
	private UIElement skipWaveButton;
	private UIElement heartElement;

	private int lifes = 3;
	private boolean statusFinalGame;
	private boolean winnerGame;
	private int maxWaves;

	private ButtonsLobby button1;
	private ButtonsLobby button2;
	private ButtonsLobby button3;
	private int keyGame;

	private WinAnimation win;


	private boolean isPlayEffects;

	public GameStage(int stageLevel, int levelDificulty) {
		mapGame = new Map(stageLevel);
		this.stageLevel = stageLevel;
		this.levelDificulty = levelDificulty;

		batch = mapGame.getBatch();
		shapeRenderer = new ShapeRenderer();

		assetsManager = new AssetsControl();
		assetsManager.create();

		towers = new ArrayList<Mermaid>();
		enemies = new ArrayList<Enemy>();

		textureOffset = 64;
		gameTexture = new Texture(Gdx.files.internal("Asset.png"));

		mousePosSprite = new Sprite(new Texture(Gdx.files.internal("Mermaids/SelectPos.png")), 0, 0,
				textureOffset,
				textureOffset);

		this.loadWave("Stage" + this.stageLevel);

		coinsUIElement = new UIElement(new Vector2(16, 644), "UI/CoinsBackground.png");
		coinsUIElement.setOffset(new Vector2(18, 4));

		pirateUiElement = new UIElement(new Vector2(148, 644), "UI/PiratesBackground.png");
		pirateUiElement.setOffset(new Vector2(32, 5));

		waveUiElement = new UIElement(new Vector2(422, 644), "UI/WaveBackground.png");
		waveUiElement.setOffset(new Vector2(0, 5));

		skipWaveButton = new UIElement(new Vector2(900, 20), "UI/SkipButton.png");
		skipWaveButton.setOffset(new Vector2(0, -4));
		skipWaveButton.setValue("SKIP");

		heartElement = new UIElement(new Vector2(850, 644), "UI/HeartsBackground.png");
		heartElement.setOffset(new Vector2(20, 4));

		statusFinalGame = false;
		statusFinalGameBg = AssetsControl.getTexture("backgrouondWin");
		winnerGame = false;
		keyGame = stageLevel + 1;

		music = AssetsControl.getSounds("musicGame");
		music.setLooping(true);
		music.setVolume(100);
		music.play();

		
		this.button1 = new ButtonsLobby(ConstantsButtons.buttonTry, 379,48,316,80);
		this.button1.setBaseButton(new Rectangle(379,90, 290,50));

		this.button2 = new ButtonsLobby(ConstantsButtons.buttonHome, 0,600,80,80);
		this.button2.setBaseButton(new Rectangle(0,620, 70,80));

		this.button3 = new ButtonsLobby(ConstantsButtons.buttonClose, 930,600,80,80);
		this.button3.setBaseButton(new Rectangle(930,620, 70,80));

		this.win = new WinAnimation(new Vector2(235,30), new Vector2(500,300) );

	
		
		Gdx.input.setInputProcessor(new InputProcessor() {
			@Override
			public boolean touchDown(int x, int y, int pointer, int button) {
				if (button == Input.Buttons.LEFT) {
					onLeftMouseDown(new Vector2(x, y));
					return true;
				}
				if (button == Input.Buttons.RIGHT) {
					onRightMouseDown(new Vector2(x, y));
				}
				return false;
			}

			public boolean keyDown(int keycode) {
				return false;
			}

			public boolean keyUp(int keycode) {
				return false;
			}

			public boolean keyTyped(char character) {
				return false;
			}

			public boolean touchUp(int x, int y, int pointer, int button) {
				return false;
			}

			public boolean touchDragged(int x, int y, int pointer) {
				return false;
			}

			public boolean mouseMoved(int x, int y) {
				mouseMovedHandle(new Vector2(x, y));
				return false;
			}

			public boolean scrolled(float amountX, float amountY) {
				return false;
			}

			@Override
			public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
				return false;
			}
		});
	}

	private void loadWave(String fileName) {
		try {
			FileReader file = new FileReader("./core/src/main/java/TowerDefense/Waves/" + fileName + "Difficulty"
					+ this.levelDificulty + ".json");
			BufferedReader buffer = new BufferedReader(file);
			String jsonString = buffer.lines().collect(Collectors.joining());
			buffer.close();

			JsonValue json = new JsonReader().parse(jsonString);

			int timeBetweenWaves = json.getInt("timeBetweenWaves");

			maxWaves = json.getInt("maxWaves");

			waves = json.getChild("waves");

			wave = new Wave(enemies, mapGame.getListPaths(), waves, this.textureOffset, timeBetweenWaves);

			PowerUp.setEnemies(enemies);
		} catch (Exception e) {
			System.err.println("Problema ao carregar wave! Erro:" + e.getMessage());
		}
	}

	protected void onLeftMouseDown(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);
		Vector2 turretPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);

		if (skipWaveButton.handleClick(new Vector2(pos.x, Gdx.graphics.getHeight() - pos.y)) && wave.waveConcluded()
				&& !wave.ended()) {
			this.coins += wave.antecipateWave();
			return;
		}

		if (buildMode == null && mapGame.isPointOnPath(new Vector2(((int) pos.x / textureOffset) * textureOffset,
				Gdx.graphics.getHeight() - textureOffset - ((int) pos.y / textureOffset) * textureOffset))) {
			return;
		}

		// Caso de interface aberta
		if (buildMode != null) {
			buildMode.handleClick(new Vector2(pos.x, Gdx.graphics.getHeight() - pos.y));

			if (buildMode.getClass().getSimpleName().equals("BuildMenu")) {
				BuildMenu menu = (BuildMenu) buildMode;
				Mermaid mermaid = menu.getMermaid();
				if (mermaid != null && mermaid.getPrice() <= this.coins) {
					this.coins -= mermaid.getPrice();
					towers.add(mermaid);
				}
			}

			if (buildMode.getClass().getSimpleName().equals("UpgradeMenu")) {
				UpgradeMenu menu = (UpgradeMenu) buildMode;
				this.coins = menu.upgrade(coins);
			}

			mousePosSprite.setPosition(turretPos.x, turretPos.y);
			buildMode = null;

			return;
		}

		// Procura por torreta ou espaço livre
		for (Mermaid tower : towers) {
			// Comparação de Vector2 não funciona. Obrigado LIBGdx :thumbsup:
			if (tower.getPosition().x == turretPos.x && tower.getPosition().y == turretPos.y) {
				buildMode = new UpgradeMenu(tower);
				return;
			}
		}
		// Clique em espaço livre
		buildMode = new BuildMenu(turretPos, textureOffset);
	}

	protected void onRightMouseDown(Vector2 pos) {
	}

	public void setMusic(boolean isPlay){
		if (!isPlay) {
			this.music.setVolume(0);
		} else if (isPlay) {
			this.music.setVolume(100);
		}
	}

	public void setPlayEffects(boolean isPlayEffects){
		if (!isPlayEffects) {
			this.isPlayEffects = false;
		} else if (isPlayEffects) {
			this.isPlayEffects = true;
		}

	}
	protected void mouseMovedHandle(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);

		if (buildMode == null && mapGame.isPointOnPath(new Vector2(((int) pos.x / textureOffset) * textureOffset,
				Gdx.graphics.getHeight() - textureOffset - ((int) pos.y / textureOffset) * textureOffset))) {
			mousePosSprite.setPosition(-textureOffset, -textureOffset);
			return;
		}

		mousePosSprite.setPosition(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);

		if (buildMode != null) {
			buildMode.handleMouseOver(new Vector2(pos.x, Gdx.graphics.getHeight() - pos.y));

			mousePosSprite.setPosition(buildMode.getTowerPos().x, buildMode.getTowerPos().y);
		}

		this.turretRangeCircle = null; // As funções providas pela classe screen não rodam direito pra limpar a
										// variavel, tem que limpar ela aqui mesmo

		for (Mermaid tower : towers) {
			if (mousePos.x == tower.getPosition().x
					&& Gdx.graphics.getHeight() - mousePos.y - textureOffset == tower.getPosition().y) {
				this.turretRangeCircle = new Circle(mousePos.x + textureOffset / 2,
						Gdx.graphics.getHeight() - mousePos.y - 8,
						tower.getRange() * textureOffset);
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

	
		
		if(!statusFinalGame){

			for (Fish fish : assetsManager.fish.getFishList(assetsManager)) {
				fish.update(delta);  // Atualiza a posição de cada peixe
			}

			for (Mermaid tower : towers) {
				tower.setCurrentTarget(null);
				ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
				for (Enemy enemy : enemies) {
					if (tower.inRange(enemy.getPosition())) {
						enemiesInRange.add(enemy);
					}
				}
				if (!enemiesInRange.isEmpty()) {
					Enemy farthest = enemiesInRange.get(0);
					for (Enemy enemy : enemiesInRange) {
						if (farthest.getDistanceWalked() < enemy.getDistanceWalked()) {
							farthest = enemy;
						}
					}
					tower.setCurrentTarget(farthest);
					if (tower instanceof Cannon) {
						((Cannon) tower).setEnemies(this.enemies);
					}
				}
			}

			for (int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				enemy.update(delta);
				if (enemy.getLife() <= 0) {
					coins += enemy.dropCoins();
					enemies.remove(i);
				}

				if (enemy.getPassedState()) {
					enemies.remove(i);
					lifes--;
				}
			}

			for (Mermaid tower : towers) {
				tower.update(delta);
			}

			wave.update(delta);

			assetsManager.update(delta);

			if (lifes <= 0) {
				statusFinalGame = true;
				winnerGame = false;
			}

			if(stageLevel == 3 && wave.ended()){
				statusFinalGame = true;
				winnerGame = true;
			}
		}else{
            win.update(delta);
			assetsManager.update(delta);
		}

}

	@Override
	public void draw() {
		super.draw();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		if(!statusFinalGame){
			{
				mapGame.draw();

				mousePosSprite.draw(batch);

				for (Enemy enemy : enemies) {
					enemy.draw(batch);
				}

				for (Mermaid tower : towers) {
					tower.draw(batch);
				}

				for (Fish fish : assetsManager.fish.getFishList(assetsManager)) {
					batch.draw(fish.getFishTextureRegion(), 
							   fish.getXPosition(), 
							   fish.getYPosition(), 
							   fish.getFishTextureRegion().getRegionWidth(), // Mantém a largura original
							   fish.getFishTextureRegion().getRegionHeight()); // Mantém a altura original
				}

				assetsManager.render();

				if (buildMode != null) {
					buildMode.draw(batch);
				}
			}

			coinsUIElement.setValue(Integer.toString(this.coins));
			coinsUIElement.draw(batch);

			pirateUiElement.setValue(
					(wave.doneSpawning() ? Integer.toString(Math.max(0, this.wave.getWaveSize() - this.enemies.size()))
							: 0) + "/" + this.wave.getWaveSize());
			pirateUiElement.draw(batch);

			waveUiElement.setValue("ONDA " + this.wave.getWaveCount() + " / " + this.maxWaves);
			waveUiElement.draw(batch);

			heartElement.setValue(this.lifes + "/" + 3);
			heartElement.draw(batch);

			if (wave.waveConcluded() && !wave.ended() && !wave.lastWave()) {
				skipWaveButton.draw(batch);
			}

			batch.end();

			shapeRenderer.begin(ShapeType.Line);
			if (turretRangeCircle != null) {
				shapeRenderer.setColor(Color.WHITE);
				shapeRenderer.circle(turretRangeCircle.x, turretRangeCircle.y, turretRangeCircle.radius);
			}
			shapeRenderer.end();

			for (int i = enemies.size() - 1; i >= 0; i--) {
				enemies.get(i).drawLifeBar(shapeRenderer);
			}

			for (Mermaid tower : towers) {
				tower.drawPowerUpLoading(shapeRenderer);
			}
		}else{
			
			if(winnerGame){
				statusFinalGameBg = AssetsControl.getTexture("backgrouondWin");
				batch.draw(statusFinalGameBg, 0, 0);
				win.draw(batch);

			}else{
				statusFinalGameBg = AssetsControl.getTexture("backgrouondLose");
				batch.draw(statusFinalGameBg, 0, 0);
				batch.draw(AssetsControl.getTextureRegions("basicEnemy")[0][0], 100, 150,300,300);
				batch.draw(AssetsControl.getTextureRegions("basicEnemy")[4][0], 400, 150,300,300);
				batch.draw(AssetsControl.getTextureRegions("basicEnemy")[2][0], 700, 150,300,300);

			}

			if(button1.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
				batch.setColor(Color.GRAY);
				batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
				batch.setColor(Color.WHITE);

				batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
				batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());

				if(Gdx.input.isTouched()){
					if(isPlayEffects){
						button1.playSoudPressed();
					}
					this.keyGame = 5;
					music.dispose();
				}
				
			}else if(button2.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
				batch.setColor(Color.GRAY);
				batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
				batch.setColor(Color.WHITE);

				batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
				batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());

				if(Gdx.input.isTouched()){
					if(isPlayEffects){
						button2.playSoudPressed();
					}
					this.keyGame = 6;
					music.dispose();
				}

			}else if(button3.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
				batch.setColor(Color.GRAY);
				batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
				batch.setColor(Color.WHITE);

				batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
				batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());

				if(Gdx.input.isTouched()){
					if(isPlayEffects){
						button3.playSoudPressed();
					}
					Gdx.app.exit();
				}
				
			}else{
				batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
				batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
				batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
			}
			batch.end();
		}
		

	}

	public int updateKey(int keyGame) {
		if (this.wave.ended()) {
			if (keyGame < 4) {
				keyGame++;
				music.dispose();
			} else {
				keyGame = 1;
				music.dispose();
			}
		}
		if(statusFinalGame){
			return this.keyGame;
		}
		return keyGame;
	}

	// PODEMOS POR FAVOR NUNCA USAR ISSO DAQUI? AGRADECIDO
	// Agradecido fico eu. :)
	public void resize(int height, int width) {
		/* pass */}

	public Map getMapGame() {
		return mapGame;
	}

	public void setMapGame(Map mapGame) {
		this.mapGame = mapGame;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}