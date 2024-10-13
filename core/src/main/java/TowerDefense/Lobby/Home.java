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
	private boolean isConfig;
	private ButtonsLobby button3;

	public Home() {
		this.button1 = new ButtonsLobby(ConstantsButtons.buttonPlay, 269,95,206,110);
		this.button1.setBaseButton(new Rectangle(269,117, 200,100));
		this.button2 = new ButtonsLobby(ConstantsButtons.buttonQuit, 529,92,206,110);
		this.button2.setBaseButton(new Rectangle(529,117, 200,100));
		this.button3 = new ButtonsLobby(ConstantsButtons.buttonSettings, Gdx.graphics.getWidth()-80,Gdx.graphics.getHeight()-90,80,80);
		this.button3.setBaseButton(new Rectangle(this.button3.getX(),this.button3.getY()+20, 70,70));
		this.isPlay = false;
		this.isQuit = false;
		this.isConfig = false;
	

	}

	public int update(int Screen, boolean isClicked,boolean isPlayEffects) {
		
			if(button1.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
				if(isClicked) {

					if(isPlayEffects){
						button1.playSoudPressed();
					}
					
					Screen = 2;
				}
				this.isPlay = true;
			}else {
				Screen =0;
				this.isPlay = false;
			}
			
			
			if(button2.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
				if(isClicked) {
					if(isPlayEffects){
						button2.playSoudPressed();
					}
					Gdx.app.exit();
				}
					this.isQuit = true;
			}else {
				this.isQuit = false;
			}
			if(button3.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))){
				if(isClicked) {
					if (isPlayEffects) {
						button3.playSoudPressed();
					}	
					Screen = 1;
				}
				this.isConfig = true;
			}else{
				this.isConfig = false;
			}

		
		return Screen;
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
		if(isConfig) {
			batch.setColor(Color.GRAY);
			batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
			batch.setColor(Color.WHITE);
		}
		
	}
	

}
