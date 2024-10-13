package TowerDefense.Lobby;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import TowerDefense.Lobby.utils.ButtonsLobby;
import TowerDefense.Lobby.utils.ConstantsButtons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;

public class Levels {
	private ButtonsLobby button1;
	private ButtonsLobby button2;
	private ButtonsLobby button3;
	
	private boolean isEasy;
	private boolean isMedium;
	private boolean isHard;

	private int dificultySelected;

	public Levels() {
		this.button1 = new ButtonsLobby(ConstantsButtons.buttonEasy, 209,145,206,110);
		this.button1.setBaseButton(new Rectangle(209,167, 200,100));
		this.button2 = new ButtonsLobby(ConstantsButtons.buttonMedium, 409,148,206,110);
		this.button2.setBaseButton(new Rectangle(409,170, 200,100));
		this.button3 = new ButtonsLobby(ConstantsButtons.buttonHard, 609,148,206,110);
		this.button3.setBaseButton(new Rectangle(609,170, 200,100));

		this.isEasy = false;
		this.isMedium = false;
		this.isHard = false;

		this.dificultySelected = 0;
		
	}


	
	public void draw(SpriteBatch batch) {
		if(isEasy) {
			batch.setColor(Color.GRAY);
			batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
		
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button1.getButtonTexture(),button1.getX(),button1.getY(),button1.getWidth(),button1.getHeight());
			batch.setColor(Color.WHITE);
		}
		
		if(isMedium) {
			batch.setColor(Color.GRAY);
			batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button2.getButtonTexture(),button2.getX(),button2.getY(),button2.getWidth(),button2.getHeight());
			batch.setColor(Color.WHITE);
		}
		if(isHard) {
			batch.setColor(Color.GRAY);
			batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(button3.getButtonTexture(),button3.getX(),button3.getY(),button3.getWidth(),button3.getHeight());
			batch.setColor(Color.WHITE);
		}
	}

	public int getDificulty(){
		return dificultySelected;
	}

	public void update(boolean isClicked, boolean isPlayEffects){
		if(button1.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
			if(isClicked) {
				dificultySelected = 1;
				if (isPlayEffects) {
					button1.playSoudPressed();	
				}
			}
			this.isEasy = true;
		}else {
			this.isEasy = false;
		}
		
		
		if(button2.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
			if(isClicked) {
				dificultySelected = 2;
				if(isPlayEffects){
					button2.playSoudPressed();
				}
			}
			this.isMedium = true;
		}else {
			this.isMedium = false;
			
		}

		if(button3.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
			if(isClicked) {
				dificultySelected = 3;
				if(isPlayEffects){
					button3.playSoudPressed();
				}
			}
			this.isHard = true;
				
		}else {
			this.isHard = false;
			
		}
	}
}
