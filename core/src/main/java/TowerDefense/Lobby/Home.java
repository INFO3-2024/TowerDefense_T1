package TowerDefense.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import TowerDefense.Lobby.utils.ButtonsLobby;
import TowerDefense.Lobby.utils.ConstantsButtons;

public class Home {
	private ButtonsLobby button1;
	private ButtonsLobby button2;
	private boolean isPlay;
	private boolean isQuit;
	
	public Home() {
		this.button1 = new ButtonsLobby(ConstantsButtons.buttonPlay, 269,95,206,110);
		this.button1.setBaseButton(new Rectangle(269,117, 200,100));
		this.button2 = new ButtonsLobby(ConstantsButtons.buttonQuit, 529,92,206,110);
		this.button2.setBaseButton(new Rectangle(529,117, 200,100));
		this.isPlay = false;
		this.isQuit = false;
	
	}


	public boolean update() {
		boolean isLevel = false;
		if(button1.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
			if(Gdx.input.isTouched()) {
				isLevel = true;
			}
			this.isPlay = true;
		}else {
			isLevel = false;;
			this.isPlay = false;
		}
		
		
		if(button2.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
			if(Gdx.input.isTouched()) {
				Gdx.app.exit();
			}
				this.isQuit = true;
		}else {
			this.isQuit = false;
		}
		return isLevel;
	}
	

	public void draw(SpriteBatch batch) {

		if(isPlay) {
			batch.setColor(Color.GRAY);
			batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
		
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
			batch.setColor(Color.WHITE);
		}
		
		if(isQuit) {
			batch.setColor(Color.GRAY);
			batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
			batch.setColor(Color.WHITE);
		}
		
	}
	

}
