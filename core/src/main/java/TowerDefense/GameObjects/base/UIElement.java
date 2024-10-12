package TowerDefense.GameObjects.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class UIElement {
    private Vector2 position;
    private Vector2 size;
    private Vector2 offset;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout layout;

    public UIElement(Vector2 pos, String assetName) {
        this.position = pos;
        this.texture = new Texture(Gdx.files.internal(assetName));
        this.size = new Vector2(this.texture.getWidth(), this.texture.getHeight());
        this.font = new BitmapFont(Gdx.files.internal("Fonts/font.fnt"));
        this.layout = new GlyphLayout();
        this.setValue("300");
        this.offset = new Vector2();
    }

    public void setOffset(Vector2 pos) {
        this.offset = pos;
    }

    public void setValue(String value) {
        this.font.getData().setScale(0.75f);
        layout.setText(font, value);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x, this.position.y);
        font.draw(batch, layout, this.position.x + (this.size.x / 2) - (this.layout.width / 2) + this.offset.x,
                this.position.y + this.size.y - (this.layout.height / 2) - this.offset.y);
    }
}
