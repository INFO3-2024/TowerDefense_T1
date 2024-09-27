package io.github.INFO32024.TowerDefense_T1.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

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

import io.github.INFO32024.TowerDefense_T1.GameObjects.Enemys.Pirate;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Interface.BuildMenu;
import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Enemy;
import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Mermaid;
import io.github.INFO32024.TowerDefense_T1.GameObjects.base.Wave;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Stage1 extends Stage {
	private ArrayList<Queue<Vector2>> listPaths;
	private Texture background;

	private SpriteBatch batch;

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

	private Queue<Vector2> enemyWay;

	private BuildMenu buildMode;

	private int path;
	private int playerCoins = 500;
	private BitmapFont coinsFont;

	public Stage1() {
		listPaths = getPositionsMap();
		background = new Texture(Gdx.files.internal("Map3.jpg"));

		batch = new SpriteBatch();

		towers = new ArrayList<Mermaid>();
		enemies = new ArrayList<Enemy>();

		wave = new Wave(enemies, listPaths);

		enemyWay = new Queue<Vector2>();

		coinsFont = new BitmapFont();
		coinsFont.setColor(Color.RED);

		textureOffset = 64;
		gameTexture = new Texture(Gdx.files.internal("Asset.png"));
		enemyTexture = new TextureRegion(gameTexture, 0, 0, textureOffset, textureOffset);
		towerTexture = new TextureRegion(gameTexture, textureOffset, 0, textureOffset, textureOffset);
		bulletTexture = new TextureRegion(gameTexture, textureOffset, textureOffset, textureOffset, textureOffset);

		mousePosSprite = new Sprite(new Texture(Gdx.files.internal("Asset.png")), 0, textureOffset, textureOffset,
				textureOffset);

		shapeRenderer = new ShapeRenderer();
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
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset, ((int) Gdx.input.getY() / textureOffset) * textureOffset);
        Vector2 turretPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);
        boolean validPos = true;

        if (buildMode != null) {
            Mermaid mermaid = buildMode.handleClick(new Vector2(pos.x, Gdx.graphics.getHeight() - pos.y));
			
			// Observem esse mecanismo de compra super sofistiacado
			if(this.playerCoins > mermaid.getPrice()) {
				this.playerCoins -= mermaid.getPrice();
				if (mermaid != null) {
					towers.add(mermaid);
				}
			}

            mousePosSprite.setPosition(turretPos.x, turretPos.y);
            buildMode = null;
            return;
        }

        for (Mermaid tower : towers) {
            // Comparação de Vector2 não funciona. Obrigado LIBGdx :thumbsup:
            if (tower.getPosition().x == turretPos.x && tower.getPosition().y == turretPos.y) {
                validPos = false;
                break;
            }
        }

        if (validPos) {
            buildMode = new BuildMenu(turretPos, textureOffset);
        }
	}

	private void onRightMouseDown(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset,
				((int) Gdx.input.getY() / textureOffset) * textureOffset);
		Vector2 enemyPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - textureOffset);
		path = new Random().nextInt(4);
		enemyWay = this.getListPaths().get(path);

		enemies.add(new Pirate(enemyPos, new Vector2(textureOffset, textureOffset), enemyWay));
		enemies.get(enemies.size() - 1).setVelocity(50);
	}

	private void mouseMovedHandle(Vector2 pos) {
		Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / textureOffset) * textureOffset, ((int) Gdx.input.getY() / textureOffset) * textureOffset);
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
                        tower.getRange() * textureOffset);
            }
        }
	}

	public ArrayList<Queue<Vector2>> getPositionsMap() {
		ArrayList<Queue<Vector2>> paths = new ArrayList<Queue<Vector2>>();

		try {
			ArrayList<String> lines = new ArrayList<String>();
			FileReader file = new FileReader("../lwjgl3/coords/map3.ws");
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

	public Texture getBackGround() {
		return background;
	}

	@Override
	public void draw() {
		super.draw();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		{
			batch.draw(this.getBackGround(), 0, 0);
			coinsFont.draw(batch, "Moedas: " + playerCoins, textureOffset / 4, 720 - textureOffset / 2);

			for (Enemy enemy : enemies) {
				enemy.draw(enemyTexture, batch);
			}
			
			for (Mermaid tower : towers) {
				tower.draw(towerTexture, bulletTexture, batch);
			}
			
			mousePosSprite.draw(batch);
		}
		batch.end();

		shapeRenderer.begin(ShapeType.Line);
		{
			if (turretRangeCircle != null) {
				shapeRenderer.setColor(Color.WHITE);
				shapeRenderer.circle(turretRangeCircle.x, turretRangeCircle.y, turretRangeCircle.radius);
			}
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
	public void act(float delta) {
		super.act(delta);
		update();

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
				playerCoins += enemy.dropCoins();
				enemies.remove(i);
			}

			if (i == 0) {

			}
		}

		for (Mermaid tower : towers) {
			tower.update(delta);
		}

		path = new Random().nextInt(4);
		wave.update(delta, textureOffset, path);
	}

	@Override
	public void dispose() {
		batch.dispose();
		coinsFont.dispose();
	}

	public void update() {}
	public void resize(int height, int width) {}
}