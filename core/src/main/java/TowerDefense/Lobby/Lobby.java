package TowerDefense.Lobby;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import TowerDefense.AssetsManager.AssetsManager;
import TowerDefense.GameObjects.Bubbles.Bubble;

public class Lobby extends Stage {
	private Home home;
	private Levels level;
	private int KeyGame;
	private int idDificulty;
	private SpriteBatch batch;
	private Music music;
	private Texture background;
	protected int oldX;
	protected int oldY;
	private ArrayList<Bubble> bubbles;
	private boolean isLevels;

	public Lobby() {
		this.home = new Home();
		this.level = new Levels();
		this.KeyGame = 1;
		this.batch = new SpriteBatch();
		this.music = AssetsManager.getSounds("musicLobby");
		this.music.setLooping(true);
		this.music.setVolume(100);
		this.music.play();
		this.background = AssetsManager.getTexture("background_Home");
		this.oldX = 0;
		this.oldY = 0;
		this.bubbles = new ArrayList<Bubble>();
		this.isLevels = false;
		this.idDificulty =0;
	}

	public int updateKey() {
		return KeyGame;
	}

	public int updateDificulty() {
		return idDificulty;
	}

	public void setDificulty(int dificulty) {
		idDificulty = dificulty;
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

	
	@Override
	public void draw() {
		super.draw();

		batch.begin();

		batch.draw(background, 0, 0);

		if (!isLevels) {
			home.draw(batch);
		} else {
			this.background = AssetsManager.getTexture("background_Levels");
			level.draw(batch);
		}

		for (Bubble b : bubbles) {
			b.draw(batch);
		}

		batch.end();
	}

	
	@Override
	public void act(float delta) {
		super.act(delta);

		if (!isLevels) {
			isLevels = home.update();
		} else {
			this.background = AssetsManager.getTexture("background_Levels");
			level.update();
			setDificulty(level.getDificulty());
		}

		updateBubbles();
		for (Bubble b : bubbles) {
			b.update(delta);
		}

		if(idDificulty != 0){
			KeyGame = 2;
			music.dispose();
		}
	}
}
