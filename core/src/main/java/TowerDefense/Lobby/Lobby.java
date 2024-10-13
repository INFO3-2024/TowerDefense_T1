package TowerDefense.Lobby;

import java.util.ArrayList;
import java.util.Iterator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import TowerDefense.AssetsManager.AssetsControl;
import TowerDefense.GameObjects.Bubbles.Bubble;

public class Lobby extends Stage {
	private Home home;
	private Levels level;
	private int KeyGame;
	private int idDificulty;
	private SpriteBatch batch;
	private Music music;
	private Texture background;
	private AssetsControl assetsManager;
	protected int oldX;
	protected int oldY;
	private ArrayList<Bubble> bubbles;
	private Config config;
	private int Screen;

	private long lastClick;
	private boolean isClicked;
	private boolean isPlay;
	private boolean isPlayEffects;
	public Lobby() {
		this.assetsManager = new AssetsControl();
		this.assetsManager.create();
		this.home = new Home();
		this.level = new Levels();
		this.KeyGame = 1;
		this.batch = new SpriteBatch();
		this.music = AssetsControl.getSounds("musicLobby");
		this.music.setLooping(true);
		this.music.setVolume(100);
		this.music.play();
		this.background = AssetsControl.getTexture("background_Home");
		this.oldX = 0;
		this.oldY = 0;
		this.bubbles = new ArrayList<Bubble>();
		this.idDificulty = 0;
		this.config = new Config();
		this.Screen = 0;
		this.lastClick = TimeUtils.nanoTime();
		this.isClicked = false;
		config.setEnabledMusic(isPlay);
		config.setEnabledsfx(isPlayEffects);
	}

	public int updateKey() {
		if (KeyGame > 1) {
			music.dispose();
		}
		return KeyGame;

	}

	public int updateDificulty() {
		return idDificulty;
	}

	public void setDificulty(int dificulty) {
		idDificulty = dificulty;
	}

	public boolean getMusicPlay (){
		if (!config.isEnabledMusic()) {
			isPlay = false;
			return isPlay;
			
		} else {
			isPlay =true;
			return isPlay;
			
		}
	}

	public boolean getPlayEffects (){
		if (!config.isEnabledSfx()) {
			isPlayEffects = false;
			return isPlayEffects;
			
		} else {
			isPlayEffects = true;
			return isPlayEffects;
		}
	}


	public void setMusicPlay(boolean isPlay){
		if (!isPlay) {
			this.isPlay = false;
			this.music.setVolume(0);
			config.setEnabledMusic(isPlay);
		} else if (isPlay) {
			this.isPlay = true;
			this.music.setVolume(100);
			config.setEnabledMusic(isPlay);
		}
	}

	public void setPlayEffects(boolean isPlayEffects){
			if (!isPlayEffects) {
				this.isPlayEffects = false;
				config.setEnabledsfx(isPlayEffects);
			} else if (isPlayEffects) {
				this.isPlayEffects = true;
				config.setEnabledsfx(isPlayEffects);
			}

	}
	public void updateBubbles() {
		if (Gdx.input.getX() - 60 != oldX && (Gdx.input.getY() - 660) * -1 != oldY) {
			if (Gdx.input.isTouched()) {
				if (bubbles.isEmpty()) {
					Bubble bubble = new Bubble(new Vector2(Gdx.input.getX() - 60, (Gdx.input.getY() - 660) * -1),
							new Vector2(100, 100));
					bubble.setPosition(new Vector2(Gdx.input.getX() - 60, (Gdx.input.getY() - 660) * -1));
					bubbles.add(bubble);
				} else {
					Bubble bubble = new Bubble(new Vector2(bubbles.get(bubbles.size() - 1).getPosition().x - 60,
							(Gdx.input.getY() - 660) * -1), new Vector2(100, 100));
					bubble.setPosition(new Vector2(Gdx.input.getX() - bubble.getSpace() - 60,
							(Gdx.input.getY() - bubble.getSpace() - 660) * -1));
					bubbles.add(bubble);
				}
			}

		}

		oldX = Gdx.input.getX() - 60;
		oldY = (Gdx.input.getY() - 660) * -1;

		Iterator<Bubble> iter = bubbles.iterator();
		while (iter.hasNext()) {
			Bubble b = iter.next();
			if (b.getPosition().y > 720 - 100) {
				iter.remove();
			}
		}
	}

	public void draw() {
		super.draw();

		batch.begin();

		batch.draw(background, 0, 0);
		switch (Screen) {
			case 0:
				this.background = AssetsControl.getTexture("background_Home");
				home.draw(batch);
				break;
			case 1:
				this.background = AssetsControl.getTexture("backgroud_settings");
				config.draw(batch);
				break;
			case 2:
				this.background = AssetsControl.getTexture("background_Levels");
				level.draw(batch);
				break;

			default:
				break;
		}

		for (Bubble b : bubbles) {
			b.draw(batch);
		}

		batch.end();
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if ( TimeUtils.nanoTime() - lastClick > 100000000 && Gdx.input.isTouched() && isClicked == false) {
			isClicked = true;
			this.lastClick = TimeUtils.nanoTime();
		}

			switch (Screen) {
				case 0:
					Screen = home.update(Screen,isClicked,isPlayEffects);
					config.setClosed(false);
					break;
				case 1:
					
					config.update(isClicked);
					break;
				case 2:
					
					level.update(isClicked,isPlayEffects);
					setDificulty(level.getDificulty());
	
				default:
					break;
			}
			isClicked = false;
			


		updateBubbles();
		for (Bubble b : bubbles) {
			b.update(delta);
		}

		if (idDificulty != 0) {
			KeyGame = 2;

		}

		if (Screen == 1) {
			if (!config.isEnabledMusic()) {
				this.music.setVolume(0);
			} else if (config.isEnabledMusic()) {
				this.music.setVolume(100);
			}

			if (!config.isEnabledSfx()) {
				isPlayEffects = false;
			} else if (config.isEnabledSfx()) {
				isPlayEffects = true;
			}


			if (config.isClosed()) {
				Screen = 0;
			}
		}
		assetsManager.update(delta);
	}


}
