package com.jermaineberkley.Rain.level;

public class Level {
	// The level class will contain two different "methods" of level design / development.
	// Level will both contain a random level generator and the ability to build a level to spec.
	// there will also be a level editor
	
	// primarily for randomly generated levels
	private int width;
	private int height;
	
	// used to contain tile id for pre-maid level
	private int[] tiles;
	
	public Level (int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		generate_level();
	}
	
	public Level (String path) {
		load_level();
	}
	
	private void generate_level () {
		
	}
	
	private void load_level (String path) {
		
	}
	
	public void update () {
		
	}
}
