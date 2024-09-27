package io.github.INFO32024.TowerDefense_T1;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import io.github.INFO32024.TowerDefense_T1.GameObjects.Enemys.Enemy1;
import io.github.INFO32024.TowerDefense_T1.GameObjects.Towers.Tower1;
import io.github.INFO32024.TowerDefense_T1.Map.Stage1;
import towerDefense.GameObjects.base.Enemy;
import towerDefense.GameObjects.base.Tower;
import towerDefense.GameObjects.base.Wave;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class GameScreen implements Screen {
	private Stage1 stage;

	private AssetManager assetManager;
	private SpriteBatch batch;

	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;

	private Sprite mousePosSprite;

	private Circle turretRangeCircle;
	private ShapeRenderer shapeRenderer;

	private Wave wave;

	private Texture gameTexture;
	private TextureRegion enemyTexture;
	private TextureRegion towerTexture;
	private TextureRegion bulletTexture;

	private Vector2 textureOffset;

	private Queue<Vector2> enemyWay;
	int i;
	public GameScreen() {
		stage = new Stage1();
		
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();

		wave = new Wave();

		enemyWay = new Queue<Vector2>();
		

		//System.out.println(stage.getListPaths().get(0).toString());
		textureOffset = new Vector2(64, 64);
		gameTexture = new Texture(Gdx.files.internal("Asset.png"));
		enemyTexture = new TextureRegion(gameTexture, 0, 0, 64, 64);
		towerTexture = new TextureRegion(gameTexture, 64, 0, 64, 64);
		bulletTexture = new TextureRegion(gameTexture, 64, 64, 64, 64);

		mousePosSprite = new Sprite(new Texture(Gdx.files.internal("Asset.png")), 0, 16, 16, 16);

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
        Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / 16) * 16, ((int) Gdx.input.getY() / 16) * 16);
        Vector2 turretPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - 16);
        boolean validPos = true;

        for (Tower tower : towers) {
            // Comparação de Vector2 não funciona. Obrigado LIBGdx :thumbsup:
            if (tower.getPosition().x == turretPos.x && tower.getPosition().y == turretPos.y) {
                validPos = false;
                break;
            }
        }

        if (validPos) {
            towers.add(new Tower1(turretPos, textureOffset));
        }
    }

    private void onRightMouseDown(Vector2 pos) {
        Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / 16) * 16, ((int) Gdx.input.getY() / 16) * 16);
        Vector2 enemyPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - 16);
        i = new Random().nextInt(4);
		enemyWay = stage.getListPaths().get(i);
		
        enemies.add(new Enemy1(enemyPos, textureOffset, enemyWay));
        enemies.get(enemies.size() - 1).setVelocity(50);
    }

    private void mouseMovedHandle(Vector2 pos) {
        Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / 16) * 16, ((int) Gdx.input.getY() / 16) * 16);
        mousePosSprite.setPosition(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - 16);

        this.turretRangeCircle = null; // As funções providas pela classe string não rodam direito pra limpar a
                                       // variavel, tem que limpar ela aqui mesmo

        for (Tower tower : towers) {
            // D qm foi a ideia de girigo de colocar a posição do mouse e do sprite
            // diferente, em LIBGDX?????
            // Olha esse codigo, que coisa horrorosa, e nem é pq ta em JAVA
            if (mousePos.x == tower.getPosition().x
                    && Gdx.graphics.getHeight() - mousePos.y - 16 == tower.getPosition().y) {
                this.turretRangeCircle = new Circle(mousePos.x + 8, Gdx.graphics.getHeight() - mousePos.y - 8,
                        tower.getRange() * 16);
            }
        }
    }


	@Override
	public void show() {
		 
	}

	@Override
	public void render(float delta) {
		
		for (Tower tower : towers) {
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
                enemies.remove(i);
            }

            if(i ==0) {
                
            }
        }

        for (Tower tower : towers) {
            tower.update(delta);
        }

        if (wave.canSpawn(delta)) {
    		i = new Random().nextInt(4);
    		enemyWay = stage.getListPaths().get(i);
            enemies.add(new Enemy1(new Vector2(0, Gdx.graphics.getHeight() / 2), textureOffset, enemyWay));
            enemies.get(enemies.size() - 1).setVelocity(50);
        }

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
		stage.act(delta);

		batch.begin();
		
		batch.draw(stage.getBackGround(), 0,0);
        shapeRenderer.begin(ShapeType.Line);
     
        batch.setColor(Color.RED);
        batch.draw(enemyTexture, stage.retangle.x,stage.retangle.y);
        batch.setColor(Color.WHITE);
        for (Enemy enemy : enemies) {
            enemy.draw(enemyTexture, batch);
        }

        if (turretRangeCircle != null) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(turretRangeCircle.x, turretRangeCircle.y, turretRangeCircle.radius);
        }

        for (Tower tower : towers) {
            tower.draw(towerTexture, bulletTexture, batch);
        }

        
        mousePosSprite.draw(batch);
        shapeRenderer.end();
        
        batch.end();

	}

	@Override
	public void resize(int width, int height) {
		stage.resize(height, width);
		stage.act();

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
        assetManager.dispose();
	}

}
