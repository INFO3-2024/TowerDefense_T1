package TowerDefense.Map;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

public class Map {
	private ArrayList<Queue<Vector2>> listPaths;
    private Texture background;
	private SpriteBatch batch;
    int stage;
    
	public Map(int stage) {
		this.stage = stage;
		this.listPaths = getPositionsMap();
		this.background = new Texture(Gdx.files.internal("Map"+ stage +".jpg"));

		batch = new SpriteBatch();
	}
	
	private ArrayList<Queue<Vector2>> getPositionsMap() {
		ArrayList<Queue<Vector2>> paths = new ArrayList<Queue<Vector2>>();
		
		try {
		ArrayList<String> lines = new ArrayList<String>();
		FileReader file = new FileReader("../lwjgl3/coords/map" + stage + ".coord");
		BufferedReader buffer = new BufferedReader(file);
		String lineFile = buffer.readLine();
		
		while(lineFile != null) {
			lines.add(lineFile);
			lineFile = buffer.readLine();
		}
		
        for (String line : lines) {
        	Queue<Vector2> path = new Queue<Vector2>();
        	String coords [] = line.split(";");
        	for(String coord : coords) {
        		path.addLast(new Vector2(Float.parseFloat(coord.split(",")[0]), Float.parseFloat(coord.split(",")[1])));
        	}
			
        	paths.add(path);
        }
        
		}catch(Exception e) {
			System.out.println("ERRO IN : " + e.getMessage());
		}
        
		return paths;
	}

	public void draw() {
		batch.draw(this.getBackground(), 0, 0);
	}
	
	public ArrayList<Queue<Vector2>> getListPaths() {
		return this.listPaths;
	}
	
	public Texture getBackground() {
		return this.background;
	}
	
	public void setBackground( Texture background) {
		this.background = background;
	}

	public SpriteBatch getBatch() {
		return this.batch;
	}
}
