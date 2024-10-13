package TowerDefense.Lobby;

import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.Gdx;
import TowerDefense.Lobby.utils.ButtonsLobby;
import TowerDefense.Lobby.utils.ConstantsButtons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Config {
    private ButtonsLobby MusicButton;
    private ButtonsLobby SfxButton;
    private ButtonsLobby buttonKeyMusic;
    private ButtonsLobby buttonKeySfx;
    private ButtonsLobby exit;

    private boolean enabledMusic;
    private boolean enabledSfx;
    private boolean closed;
    private boolean isClosedButton;

  
    public Config() {
        this.MusicButton = new ButtonsLobby(ConstantsButtons.buttonMusic, 600,300,206,110);
        this.SfxButton = new ButtonsLobby(ConstantsButtons.buttonSfx, 209,300,206,110);

        this.buttonKeySfx = new ButtonsLobby(ConstantsButtons.buttonOff, 220,148,206,110);
        this.buttonKeySfx.setBaseButton(new Rectangle(this.buttonKeySfx.getX(),this.buttonKeySfx.getY(), 200,100));


        this.buttonKeyMusic = new ButtonsLobby(ConstantsButtons.buttonOff, 620,148,206,110);
        this.buttonKeyMusic.setBaseButton(new Rectangle(this.buttonKeyMusic.getX(),this.buttonKeyMusic.getY(), 200,100));

        this.exit = new ButtonsLobby(ConstantsButtons.buttonClose, Gdx.graphics.getWidth()-80,Gdx.graphics.getHeight()-90,80,80);
		this.exit.setBaseButton(new Rectangle(this.exit.getX(),this.exit.getY()+20, 70,70));

        this.closed = false;
        this.isClosedButton =false;

    }
    
    public void update(boolean isClicked) {
    
       
        if(buttonKeyMusic.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
            if(isClicked) {
                if (enabledSfx) {
                    buttonKeyMusic.playSoudPressed();   
                }
                if(enabledMusic == true) {
                    enabledMusic = false;
                }else {
                    enabledMusic = true;
                }

            }
        }

        if(buttonKeySfx.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
            if(isClicked) {

                if(enabledSfx == true){
                    enabledSfx = false;
                }else {
                    buttonKeySfx.playSoudPressed();
                    enabledSfx = true;
                }
            }
        }

        if(exit.getBaseButton().contains(Gdx.input.getX(),((Gdx.input.getY() - 720) * -1))) {
            if(isClicked) {
                if(enabledSfx){
                    exit.playSoudPressed();
                }
                closed = true;
            }else {
                closed = false;
            }
            this.isClosedButton = true;
        }else{
            this.isClosedButton = false;
        }
    }

    public void setClosed(boolean isClosed){
        this.closed = isClosed;
    }
    public boolean isEnabledMusic() {
        return enabledMusic;
    }

    public void setEnabledMusic(boolean isPlay) {
        this.enabledMusic = isPlay;
    }
    
    public void setEnabledsfx(boolean isPlayEffects) {
        this.enabledSfx = isPlayEffects;
    }
    
    public boolean isEnabledSfx() {
        return enabledSfx;
    }

    public boolean isClosed(){
        return closed;
    }

    public void draw(SpriteBatch batch) {	
		batch.draw(MusicButton.getButtonTexture(),MusicButton.getX(),MusicButton.getY(),MusicButton.getWidth(),MusicButton.getHeight());
		batch.draw(SfxButton.getButtonTexture(),SfxButton.getX(),SfxButton.getY(),SfxButton.getWidth(),SfxButton.getHeight());
        batch.draw(exit.getButtonTexture(),exit.getX(),exit.getY(),exit.getWidth(),exit.getHeight());

        if(isClosedButton) {
			batch.setColor(Color.GRAY);
			batch.draw(exit.getButtonTexture(),exit.getX(),exit.getY(),exit.getWidth(),exit.getHeight());
		
			batch.setColor(Color.WHITE);
		}else {
			batch.draw(exit.getButtonTexture(),exit.getX(),exit.getY(),exit.getWidth(),exit.getHeight());
			batch.setColor(Color.WHITE);
		}

        if(!enabledMusic) {
            this.buttonKeyMusic.setButtonTexture(ConstantsButtons.buttonOff);
            batch.draw(buttonKeyMusic.getButtonTexture(),buttonKeyMusic.getX(), buttonKeyMusic.getY(), buttonKeyMusic.getWidth(), buttonKeyMusic.getHeight());
        }else{
            this.buttonKeyMusic.setButtonTexture(ConstantsButtons.buttonOn);
            batch.draw(buttonKeyMusic.getButtonTexture(),buttonKeyMusic.getX(),buttonKeyMusic.getY(),buttonKeyMusic.getWidth(),buttonKeyMusic.getHeight());
        }

        if(!enabledSfx) {
            this.buttonKeySfx.setButtonTexture(ConstantsButtons.buttonOff);
            batch.draw(buttonKeySfx.getButtonTexture(),buttonKeySfx.getX(), buttonKeySfx.getY(), buttonKeySfx.getWidth(), buttonKeySfx.getHeight());
        }else{
            this.buttonKeySfx.setButtonTexture(ConstantsButtons.buttonOn);
            batch.draw(buttonKeySfx.getButtonTexture(),buttonKeySfx.getX(),buttonKeySfx.getY(),buttonKeySfx.getWidth(),buttonKeySfx.getHeight());
        }
	
    }

}