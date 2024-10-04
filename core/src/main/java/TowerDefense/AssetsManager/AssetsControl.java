package TowerDefense.AssetsManager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsControl extends ApplicationAdapter {
    private AssetManager assetManager;
    private Music musica;
    private static float stateTime;

    private static Map<String, Texture> textures;

    @Override
    public void create() {
        assetManager = new AssetManager();
        textures = new HashMap<String, Texture>();

        // Carrega as texturas
        assetManager.load("Mermaids/PinkMermaid.png", Texture.class);
        assetManager.load("Mermaids/RedMermaid.png", Texture.class);
        assetManager.load("Mermaids/PurpleMermaid.png", Texture.class);
        assetManager.load("Mermaids/GreenMermaid.png", Texture.class);
        assetManager.load("Mermaids/BlueMermaid.png", Texture.class);

        assetManager.load("Enemies/BasicEnemy.png", Texture.class);

        assetManager.load("Sound/Goldenrod-City.mp3", Music.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("pinkMermaid", assetManager.get("Mermaids/PinkMermaid.png", Texture.class));
        textures.put("redMermaid", assetManager.get("Mermaids/RedMermaid.png", Texture.class));
        textures.put("purpleMermaid", assetManager.get("Mermaids/PurpleMermaid.png", Texture.class));
        textures.put("greenMermaid", assetManager.get("Mermaids/GreenMermaid.png", Texture.class));
        textures.put("blueMermaid", assetManager.get("Mermaids/BlueMermaid.png", Texture.class));
        textures.put("basicEnemy", assetManager.get("Enemies/BasicEnemy.png", Texture.class));

        stateTime = 0f;

        // Música de fundo
        musica = assetManager.get("Sound/Goldenrod-City.mp3", Music.class);
        musica.setLooping(true);
        musica.setVolume(0.8f);
        musica.play();
    }

    public void update(float deltaTime) {
        // Atualiza o tempo de animação
        stateTime += deltaTime;
    }

    public static TextureRegion[][] getTextureRegions(String key) {
        return TextureRegion.split(textures.get(key),
            /*textures.get(key).getWidth() / 4*/ 48,
            /*textures.get(key).getHeight() / 6*/48);
    }

    public static Animation<TextureRegion> getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
        return new Animation<TextureRegion>(frameDuration, textureRegion[line]);
    }

    public static TextureRegion getCurrentTRegion(Animation<TextureRegion> animation){
        return animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void dispose() {
        musica.dispose();
        assetManager.dispose();
    }
}
