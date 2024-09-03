package towerDefense.GameObjects.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
    private static final Map<String, TextureRegion> textures = new HashMap<String, TextureRegion>();

    public static void setTexture(String key, TextureRegion value) {
        textures.put(key, value);
    }

    public static TextureRegion getTexture(String key) {
        return textures.get(key);
    }
}
