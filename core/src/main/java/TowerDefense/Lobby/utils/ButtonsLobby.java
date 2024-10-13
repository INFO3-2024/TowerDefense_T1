package TowerDefense.Lobby.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import TowerDefense.AssetsManager.AssetsControl;

public class ButtonsLobby {

	private int x;
	private int y;
	private int height;
	private int width;
	private TextureRegion buttonTexture;
	private Rectangle baseButton;
	private Music soundPressed;
	private final static HashMap<String, TextureRegion> texturesButtons = loadButton(AssetsControl.getTextureRegions("buttonsLobby"));

	public ButtonsLobby(String typeButton, int x, int y, int width, int height) {
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
		this.setWidth(width);
		this.setButtonTexture(typeButton);
		this.soundPressed = AssetsControl.getSounds("soundButton");
	}

	public int getX() {
		return x;
	}

	public void playSoudPressed(){
		soundPressed.play();
	}

	public void setSoudVlume(int value){
		soundPressed.setVolume(value);;
	}
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public TextureRegion getButtonTexture() {
		return buttonTexture;
	}

	public void setButtonTexture(String newTexutureButton) {
		this.buttonTexture = texturesButtons.get(newTexutureButton);
	}

	public Rectangle getBaseButton() {
		return baseButton;
	}

	public void setBaseButton(Rectangle baseButton) {
		this.baseButton = baseButton;
	}

	private static HashMap<String, TextureRegion> loadButton(TextureRegion[][] buttonsRegions) {
		HashMap<String, TextureRegion> buttons = new HashMap<String, TextureRegion>();

		try {
			FileReader file = new FileReader("../assets/Lobby/buttons_coords_size.txt");
			BufferedReader in = new BufferedReader(file);
			String line = in.readLine();
			while (line != null) {

				String nameButton = line.split(";")[0];
				String positionButton = line.split(";")[1];
				String sizeButton = line.split(";")[2];

				buttonsRegions[0][0].setRegion(Integer.parseInt(positionButton.split(",")[0]),
						Integer.parseInt(positionButton.split(",")[1]), Integer.parseInt(sizeButton.split(",")[0]),
						Integer.parseInt(sizeButton.split(",")[1]));

				buttons.put(nameButton, new TextureRegion(buttonsRegions[0][0]));

				line = in.readLine();
			}
			in.close();
		} catch (Exception e) {
			System.out.println("Erro : " + e.getMessage());
		}

		return buttons;
	}

}
