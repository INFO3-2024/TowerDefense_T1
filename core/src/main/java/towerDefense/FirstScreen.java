package towerDefense;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import towerDefense.GameObjects.base.Tower;
import towerDefense.GameObjects.base.Wave;
import towerDefense.GameObjects.Enemys.Enemy1;
import towerDefense.GameObjects.Towers.TowerExample;
import towerDefense.GameObjects.base.Enemy;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private AssetManager assetManager;
    private SpriteBatch batch;

    private ArrayList<Tower> towers;
    private ArrayList<Enemy> enemies;

    private Sprite mousePosSprite;

    private Circle turretRangeCircle;
    private ShapeRenderer shapeRenderer;

    private Wave wave;

    public FirstScreen() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();

        towers = new ArrayList<Tower>();
        enemies = new ArrayList<Enemy>();

        wave = new Wave();

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
            towers.add(new TowerExample((int) turretPos.x, (int) turretPos.y, 16, 16));
        }
    }

    private void onRightMouseDown(Vector2 pos) {
        Vector2 mousePos = new Vector2(((int) Gdx.input.getX() / 16) * 16, ((int) Gdx.input.getY() / 16) * 16);
        Vector2 enemyPos = new Vector2(mousePos.x, Gdx.graphics.getHeight() - mousePos.y - 16);

        enemies.add(new Enemy1((int) enemyPos.x, (int) enemyPos.y, 16, 16));
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

    // ESSA FUNÇÃO TAVA AQUI SIMPLISMENTE EXISTINDO, NÃO FAZIA NADA KAKAKAKAKAKA
    // Vou deixar pq alguem devia querer usar, se ninguem upar codio aq antes da
    // sprint acabar, excluir
    public void create() {
    }

    private void update(float delta) {
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
        }

        for (Tower tower : towers) {
            tower.update(delta);
        }

        if (wave.canSpawn(delta)) {
            enemies.add(new Enemy1(0, Gdx.graphics.getHeight() / 2, 16, 16));
            enemies.get(enemies.size() - 1).setVelocity(50);
        }
    }

    @Override
    public void render(float delta) {
        // Não sei pq não tem uma função pra separar isso, acho que era pra ser a função
        // show, mas não faz sentido, ja que ela é chamada logo que esse frame é
        // desenhado e não recebe nenhum parametro delta, que biblioteca de m****
        update(delta);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        shapeRenderer.begin(ShapeType.Line);
        for (Enemy enemy : enemies) {
            enemy.draw(batch);
        }

        if (turretRangeCircle != null) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(turretRangeCircle.x, turretRangeCircle.y, turretRangeCircle.radius);
        }

        for (Tower tower : towers) {
            tower.draw(batch);
        }

        mousePosSprite.draw(batch);
        shapeRenderer.end();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.

        batch.dispose();
        assetManager.dispose();
    }
}
