package TowerDefense.Stages.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.Cannons.Cannon;
import TowerDefense.GameObjects.Interface.BuildMenu;
import TowerDefense.GameObjects.Interface.Button;
import TowerDefense.GameObjects.Interface.UpgradeMenu;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.Wave;
import TowerDefense.Map.Map;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameStage extends Stage {
	protected Map mapGame;
	protected AssetsControl assetsManager;
	protected SpriteBatch batch;
	protected BitmapFont font;

	protected ArrayList<Mermaid> towers;
	protected ArrayList<Enemy> enemies;

	protected Sprite mousePosSprite;

	protected Circle turretRangeCircle;
	protected ShapeRenderer shapeRenderer;

	protected Wave wave;

	protected Texture gameTexture;
	protected TextureRegion bulletTexture;

	protected int textureOffset;

	protected InterfaceMenu buildMode;

	protected int coins = 999;

	protected JsonValue waves;

	protected Button skipWaveButton;

	private int stageLevel;
	private int levelDificulty;

	public GameStage(int stageLevel, int levelDificulty) {
		mapGame = new Map(stageLevel);
		this.stageLevel = stageLevel;
		this.levelDificulty = levelDificulty;

		batch = mapGame.getBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();

		assetsManager = new AssetsControl();
		assetsManager.create();

		towers = new ArrayList<Mermaid>();
		enemies = new ArrayList<Enemy>();

		textureOffset = 64;
		gameTexture = new Texture(Gdx.files.internal("Asset.png"));
		bulletTexture = new TextureRegion(gameTexture, textureOffset, textureOffset, textureOffset, textureOffset);

		mousePosSprite = new Sprite(new Texture(Gdx.files.internal("Asset.png")), 0, textureOffset, textureOffset,
				textureOffset);

		skipWaveButton = new Button(900, 20, 50, 24);

		this.loadWave("Stage" + this.stageLevel);

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

			int timeBetweenWaves = new JsonReader().parse(jsonString).getInt("timeBetweenWaves");

			waves = new JsonReader().parse(jsonString).getChild("waves");

			wave = new Wave(enemies, mapGame.getListPaths(), waves, this.textureOffset, timeBetweenWaves);
		} catch (Exception e) {
			System.err.println("Arquivo de waves não encontrado!");
		}
	}

	protected void onLeftMouseDown(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);
		Vector2 turretPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);

		if (skipWaveButton.handleClick(new Vector2(pos.x, Gdx.graphics.getHeight() - pos.y)) && wave.waveConcluded()
				&& !wave.ended()) {
			this.coins += (int) 2 * wave.antecipateWave();
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
		System.out.println("CLICOU DIREITO EM");
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
			// D qm foi a ideia de girigo de colocar a posição do mouse e do sprite
			// diferente, em LIBGDX?????
			// Olha esse codigo, que coisa horrorosa, e nem é pq ta em JAVA
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

		for (Mermaid tower : towers) {
			tower.setCurrentTarget(null);
			for (Enemy enemy : enemies) { // Responsavel por pegar o primeiro inimigo gerado
				if (tower.inRange(enemy.getPosition())) {
					tower.setCurrentTarget(enemy);

					if (tower instanceof Cannon) {
						((Cannon) tower).setEnemies(this.enemies);
					}

					break;
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
		}

		for (Mermaid tower : towers) {
			tower.update(delta);
		}

		wave.update(delta);

		assetsManager.update(delta);
	}

	@Override
	public void draw() {
		super.draw();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{
			mapGame.draw();

			font.draw(batch, "Coins: " + coins, 10, Gdx.graphics.getHeight() - 20);
			mousePosSprite.draw(batch);

			for (Mermaid tower : towers) {
				tower.draw(bulletTexture, batch);
			}

			for (Enemy enemy : enemies) {
				enemy.draw(batch);
			}

			assetsManager.render();

			if (buildMode != null) {
				buildMode.draw(batch);
			}
		}
		batch.end();

		shapeRenderer.begin(ShapeType.Line);
		if (turretRangeCircle != null) {
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.circle(turretRangeCircle.x, turretRangeCircle.y, turretRangeCircle.radius);
		}
		shapeRenderer.end();

		for (Enemy enemy : enemies) {
			enemy.drawLifeBar(shapeRenderer);
		}

		if (wave.waveConcluded() && !wave.ended()) {
			skipWaveButton.draw(shapeRenderer);
		}
	}

	public int updateKey(int keyGame) {
		if (this.wave.ended()) {
			if (keyGame < 4) {
				keyGame++;
			} else {
				keyGame = 1;
			}
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
		font.dispose();
	}
}