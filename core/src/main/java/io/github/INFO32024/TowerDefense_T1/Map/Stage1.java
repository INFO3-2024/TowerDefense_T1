package io.github.INFO32024.TowerDefense_T1.Map;



import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Queue;

public class Stage1 extends Stage{
	private ArrayList<Queue<Vector2>> listPaths;
    private Texture background;
    public Rectangle retangle;
    
	public Stage1() {
		listPaths = getPositionsMap();
		background = new Texture(Gdx.files.internal("Map3.jpg"));
		retangle = new Rectangle();
		retangle.x = 0;
		retangle.y = 480;
		retangle.width = 64;
		retangle.height = 64;
	}

	public ArrayList<Queue<Vector2>> getPositionsMap() {
		ArrayList<Queue<Vector2>> paths = new ArrayList<Queue<Vector2>>();
		
		try {
		ArrayList<String> lines = new ArrayList<String>();
		FileReader file = new FileReader("coords/map3.coord");
		BufferedReader in = new BufferedReader(file);
		String lineFile = in.readLine();
		
		while(lineFile != null) {
			lines.add(lineFile);
			lineFile = in.readLine();
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
	
	public ArrayList<Queue<Vector2>> getListPaths() {
		return listPaths;
	}
	
	public Texture getBackGround() {
		return background;
	}
	
	public void update() {
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			retangle.y += 3f;
			System.out.println(retangle.x + "," + retangle.y);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			retangle.y -= 3f;
			System.out.println(retangle.x + "," + retangle.y);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			retangle.x -= 3f;
			System.out.println(retangle.x + "," + retangle.y);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			retangle.x += 3f;
			System.out.println(retangle.x + "," + retangle.y);
		}
		
		
	}
	
	@Override
	public void draw() {
		super.draw();
		
		
	}
	
	@Override
	public void act(float delta) {	
		super.act(delta);
		update();
	}
	
	public void resize(int height, int width){

	}

}
