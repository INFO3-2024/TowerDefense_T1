package TowerDefense.AssetsManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TesteAssets extends ApplicationAdapter {
    private AssetManager assetManager;
    private SpriteBatch batch;
    private Texture fundo, pinkMermaid_attack, pinkMermaid_idle;
    private Music musica;
    private Animation<TextureRegion> pinkAnimation_attack, pinkAnimation_idle;
    private float stateTime;

    @Override
    public void create() {
        assetManager = new AssetManager();
        batch = new SpriteBatch();

        // Carrega textura
        assetManager.load("assets/BG/BG.png", Texture.class);
        assetManager.load("assets/PinkMermaid 1.png", Texture.class);
        assetManager.load("assets/PinkMermaid-idle.png", Texture.class);
        assetManager.load("assets/041 Goldenrod City.mp3", Music.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter textura carregada
        fundo = assetManager.get("assets/BG/BG.png", Texture.class);
        pinkMermaid_attack = assetManager.get("assets/PinkMermaid 1.png", Texture.class);
        pinkMermaid_idle = assetManager.get("assets/PinkMermaid-idle.png", Texture.class);
        // Animação pinkMermaid
        // Assumindo que tens 4 frames e a imagem é organizada numa linha
        TextureRegion[][] tmpFrames_attack = TextureRegion.split(pinkMermaid_attack, pinkMermaid_attack.getWidth() / 4,
                pinkMermaid_attack.getHeight());
        TextureRegion[] pinkFrames_attack = new TextureRegion[4];

        TextureRegion[][] tmpFrames_idle = TextureRegion.split(pinkMermaid_idle, pinkMermaid_idle.getWidth() / 4,
                pinkMermaid_idle.getHeight());
        TextureRegion[] pinkFrames_idle = new TextureRegion[4];

        // Copiar os frames para o array de animação
        for (int i = 0; i < 4; i++) {
            pinkFrames_attack[i] = tmpFrames_attack[0][i];

            pinkFrames_idle[i] = tmpFrames_idle[0][i]; // Assumindo que todos os frames estão na primeira linha
        }

        // Criar a animação
        pinkAnimation_attack = new Animation<>(0.15f, pinkFrames_attack);
        pinkAnimation_idle = new Animation<>(0.17f, pinkFrames_idle);
        stateTime = 0f;

        // Música de fundo
        musica = assetManager.get("assets/041 Goldenrod City.mp3", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.8f);
        musica.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atualiza o tempo de animação
        stateTime += Gdx.graphics.getDeltaTime();

        // Obtém o frame atual da animação
        TextureRegion currentFrame_attack = pinkAnimation_attack.getKeyFrame(stateTime, true);
        TextureRegion currentFrame_idle = pinkAnimation_idle.getKeyFrame(stateTime, true);
        // Desenhar os elementos no ecrã
        batch.begin();
        batch.draw(fundo, 0, 0);
        batch.draw(currentFrame_attack, 200, 50, 100, 100);
        batch.draw(currentFrame_idle, 500, 50, 100, 100);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        musica.dispose();
        assetManager.dispose();
    }
}
