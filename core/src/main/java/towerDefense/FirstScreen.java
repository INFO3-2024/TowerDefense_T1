package towerDefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class FirstScreen implements Screen {

    private AssetManager assetManager;
    private SpriteBatch batch;

    public FirstScreen() {
        batch = new SpriteBatch();
        assetManager = new AssetManager();
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    // ESSA FUNÇÃO TAVA AQUI SIMPLISMENTE EXISTINDO, NÃO FAZIA NADA KAKAKAKAKAKA
    // Vou deixar pq alguem devia querer usar, se ninguem upar codio aq antes da
    // sprint acabar, excluir
    public void create() {
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
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