package io.github.INFO32024.TowerDefense_T1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen{

    private AssetManager assetManager;
    private SpriteBatch batch;
    private Texture fundo, personagemRun, personagemJump, aterrizar;
    private Music  musica;
    
    @Override
    public void show() {
        // Prepare your screen here.
    }

    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();
        
        // Load assets
        assetManager.load("assets/BG/BG.png", Texture.class);
        assetManager.load("assets/png/Run(4).png", Texture.class);
        assetManager.load("assets/png/Jump(4).png", Texture.class);
        assetManager.load("assets/png/Jump(11).png", Texture.class);
        assetManager.load("assets/background_music.mp3", Music.class);

        // ...

        // Retrieve assets
        fundo = assetManager.get("assets/BG/BG.png", Texture.class);
        personagemRun = assetManager.get("assets/png/Run(4).png", Texture.class);
        personagemJump = assetManager.get("assets/png/Jump(4).png", Texture.class);
        aterrizar = assetManager.get("assets/png/Jump(11).png", Texture.class);
        musica = assetManager.get("assets/background_music.mp3", Music.class);
        musica = assetManager.get("assets/background_music.mp3", Music.class);

       musica.setLooping(true);
       musica.setVolume(0.8f);

       musica.play();    
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(fundo, 0, 0);
        batch.draw(personagemRun, 0, 0, 100, 100);
        batch.draw(personagemJump, 200, 50, 100, 100);
        batch.draw(aterrizar, 400, 0, 100, 100);
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
        musica.dispose();
        assetManager.dispose();
    }
}