package TowerDefense.GameObjects.Interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private Rectangle rect;

    public Button(int posX, int posY, int width, int height) {
        this.rect = new Rectangle(posX, posY, width, height);
    }

    public boolean handleClick(Vector2 mousePos) {
        if (mouseOverlaps(mousePos)) {
            return true;
        }
        return false;
    }

    private boolean mouseOverlaps(Vector2 mousePos) {
        if (mousePos.x >= this.rect.x && mousePos.x <= this.rect.x + this.rect.width && mousePos.y >= this.rect.y
                && mousePos.y <= this.rect.y + this.rect.height) {
            return true;
        }
        return false;
    }

    public void draw(ShapeRenderer batch) {
        batch.begin(ShapeType.Filled);
        batch.setColor(Color.RED);
        batch.rect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
        batch.end();
    }
}
