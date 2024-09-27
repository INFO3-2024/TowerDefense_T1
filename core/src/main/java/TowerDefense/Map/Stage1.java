package TowerDefense.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
import com.badlogic.gdx.utils.Queue;

import TowerDefense.GameObjects.Interface.BuildMenu;
import TowerDefense.GameObjects.Interface.UpgradeMenu;
import TowerDefense.GameObjects.base.Enemy;
import TowerDefense.GameObjects.base.InterfaceMenu;
import TowerDefense.GameObjects.base.Mermaid;
import TowerDefense.GameObjects.base.Wave;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Stage1 extends Stage {
	private ArrayList<Queue<Vector2>> listPaths;
	private Texture background;

	private SpriteBatch batch;
	private BitmapFont font;

	private ArrayList<Mermaid> towers;
	private ArrayList<Enemy> enemies;

	private Sprite mousePosSprite;

	private Circle turretRangeCircle;
	private ShapeRenderer shapeRenderer;

	private Wave wave;

	private Texture gameTexture;
	private TextureRegion enemyTexture;
	private TextureRegion towerTexture;
	private TextureRegion bulletTexture;

	private int textureOffset;

	private InterfaceMenu buildMode;

	private int coins = 999;

	public Stage1() {
		listPaths = getPositionsMap();
		background = new Texture(Gdx.files.internal("Map3.jpg"));

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();

		towers = new ArrayList<Mermaid>();
		enemies = new ArrayList<Enemy>();

		wave = new Wave(enemies, listPaths);

		textureOffset = 64;
		gameTexture = new Texture(Gdx.files.internal("Asset.png"));
		enemyTexture = new TextureRegion(gameTexture, 0, 0, textureOffset, textureOffset);
		towerTexture = new TextureRegion(gameTexture, textureOffset, 0, textureOffset, textureOffset);
		bulletTexture = new TextureRegion(gameTexture, textureOffset, textureOffset, textureOffset, textureOffset);

		mousePosSprite = new Sprite(new Texture(Gdx.files.internal("Asset.png")), 0, textureOffset, textureOffset,
				textureOffset);

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

	private void onLeftMouseDown(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);
		Vector2 turretPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);

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
		buildMode = new BuildMenu(turretPos);
	}

	private void onRightMouseDown(Vector2 pos) {
		System.out.println("CLICOU DIREITO EM");
	}

	private void mouseMovedHandle(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);
		mousePosSprite.setPosition(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);

		if (buildMode != null) {
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
				this.turretRangeCircle = new Circle(mousePos.x + 8, Gdx.graphics.getHeight() - mousePos.y - 8,
						tower.getRange() * 16);
			}
		}
	}

	public ArrayList<Queue<Vector2>> getPositionsMap() {
		ArrayList<Queue<Vector2>> paths = new ArrayList<Queue<Vector2>>();

		try {
			System.out.println("Diretorio atual: " + new java.io.File(".").getAbsolutePath());
			ArrayList<String> lines = new ArrayList<String>();
			FileReader file = new FileReader("./lwjgl3/coords/map1.ws");
			BufferedReader in = new BufferedReader(file);
			String lineFile = in.readLine();

			while (lineFile != null) {
				lines.add(lineFile);
				lineFile = in.readLine();
			}

			for (String line : lines) {
				Queue<Vector2> path = new Queue<Vector2>();
				String coords[] = line.split(";");
				for (String coord : coords) {
					path.addLast(
							new Vector2(Float.parseFloat(coord.split(",")[0]), Float.parseFloat(coord.split(",")[1])));
				}
				paths.add(path);
			}

		} catch (Exception e) {
			System.out.println("ERRO IN : " + e.getMessage());
		}

		return paths;
	}

	public ArrayList<Queue<Vector2>> getListPaths() {
		return listPaths;
	}

	public Texture getBackground() {
		return background;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (Mermaid tower : towers) {
			tower.setCurrentTarget(null);
			for (Enemy enemy : enemies) { // Responsavel por pegar o primeiro inimigo gerado
				if (tower.inRange(enemy.getPosition())) {
					tower.setCurrentTarget(enemy);
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

		wave.update(delta, textureOffset, 0);
	}

	@Override
	public void draw() {
		super.draw();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.draw(this.getBackground(), 0, 0);

		for (Enemy enemy : enemies) {
			enemy.draw(enemyTexture, batch);
		}

		for (Mermaid tower : towers) {
			tower.draw(towerTexture, bulletTexture, batch);
		}

		font.draw(batch, "Coins: " + coins, 10, 20);
		mousePosSprite.draw(batch);

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

		if (buildMode != null) {
			buildMode.draw(shapeRenderer);
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void resize(int height, int width) {
	}
}