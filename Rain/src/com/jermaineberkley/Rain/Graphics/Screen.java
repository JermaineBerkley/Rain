package com.jermaineberkley.Rain.Graphics;

import java.util.Random;

public class Screen {
	
	private int width;
	private int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	int time = 0;
	
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++){
			tiles[i] = random.nextInt(0x0ffffff);
			tiles[0] = 0;
		}
	};
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	};
	
	public void render(int x_offset, int y_offset){

		for(int y = 0; y < height; y++){
			// y pixel
			int yp = y + y_offset;
			if (yp < 0 || yp >= height ) { continue; }
			for(int x = 0; x < width; x++){
				// x pixel
				int xp = x + x_offset;
				if (xp < 0 || xp >= width ) { continue; }
				pixels[(x + x_offset) + (y + y_offset) * width] = Sprite.grass.pixels[(x % 15) + (y & 15) * Sprite.grass.SIZE];
			}
		}
	};
};
