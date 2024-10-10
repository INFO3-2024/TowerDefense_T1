package TowerDefense.AssetsManager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetsControl extends ApplicationAdapter {
    private AssetManager assetManager;
    private static float stateTime;

    private static Map<String, Texture> textures;
    private static Map<String, Music> sounds;

    @Override
    public void create() {
        assetManager = new AssetManager();
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Music>();

        // Carrega as texturas
        assetManager.load("Mermaids/PinkMermaid.png", Texture.class);
        assetManager.load("Mermaids/RedMermaid.png", Texture.class);
        assetManager.load("Mermaids/PurpleMermaid.png", Texture.class);
        assetManager.load("Mermaids/GreenMermaid.png", Texture.class);
        assetManager.load("Mermaids/BlueMermaid.png", Texture.class);

        assetManager.load("Enemies/BasicEnemy.png", Texture.class);

        assetManager.load("Cannons/Cannon.png", Texture.class);

        assetManager.load("Menus/UpgradeMenu.png", Texture.class);
        assetManager.load("Menus/BuildMenu.png", Texture.class);

        assetManager.load("Sound/NowWeAreFree(Gladiator).mp3", Music.class);
        assetManager.load("Lobby/Buttons.png", Texture.class);
        assetManager.load("Lobby/Bubbles.png", Texture.class);

        assetManager.load("Lobby/background.png", Texture.class);
        assetManager.load("Lobby/background_Levels.png", Texture.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("pinkMermaid", assetManager.get("Mermaids/PinkMermaid.png", Texture.class));
        textures.put("redMermaid", assetManager.get("Mermaids/RedMermaid.png", Texture.class));
        textures.put("purpleMermaid", assetManager.get("Mermaids/PurpleMermaid.png", Texture.class));
        textures.put("greenMermaid", assetManager.get("Mermaids/GreenMermaid.png", Texture.class));
        textures.put("blueMermaid", assetManager.get("Mermaids/BlueMermaid.png", Texture.class));
        textures.put("basicEnemy", assetManager.get("Enemies/BasicEnemy.png", Texture.class));
        textures.put("cannon", assetManager.get("Cannons/Cannon.png", Texture.class));
        textures.put("upgradeMenu", assetManager.get("Menus/UpgradeMenu.png", Texture.class));
        textures.put("buildMenu", assetManager.get("Menus/BuildMenu.png", Texture.class));
        textures.put("buttonsLobby", assetManager.get("Lobby/Buttons.png", Texture.class));
        textures.put("bubblesLobby", assetManager.get("Lobby/Bubbles.png", Texture.class));
        textures.put("background_Home", assetManager.get("Lobby/background.png", Texture.class));
        textures.put("background_Levels", assetManager.get("Lobby/background_Levels.png", Texture.class));

        sounds.put("musicLobby",  assetManager.get("Sound/NowWeAreFree(Gladiator).mp3", Music.class));

        stateTime = 0f;
    }

    public void update(float deltaTime) {
        // Atualiza o tempo de animação
        stateTime += deltaTime;
    }

    public static Texture getTexture(String key) {
        return textures.get(key);
    }

    public static Music getSounds(String key) {
        return sounds.get(key);

    }

    public static TextureRegion[][] getTextureRegions(String key, Vector2 size) {
        return TextureRegion.split(textures.get(key),
            /*textures.get(key).getWidth() / 4*/ (int)size.x,
            /*textures.get(key).getHeight() / 6*/(int)size.y);
    }

    public static TextureRegion[][] getTextureRegions(String key) {
        return getTextureRegions(key, new Vector2(48, 48));
    }

    public static Animation<TextureRegion> getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
        return new Animation<TextureRegion>(frameDuration, textureRegion[line]);
    }

    public static TextureRegion getCurrentTRegion(Animation<TextureRegion> animation){
        return animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }
}
