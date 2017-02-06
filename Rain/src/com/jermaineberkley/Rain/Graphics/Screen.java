package com.jermaineberkley.Rain.Graphics;

import java.util.Random;

public class Screen {
	
	private int width, height;
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
		}
	};
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	};
	
	public void render(int x_offset, int y_offset){

		for(int y = 0; y < height; y++){
			int yy = y + y_offset;
			//if(yy < 0 || yy >= height){ break; }
			for(int x = 0; x < width; x++){
				int xx = x + x_offset;
				//if(xx < 0 || xx >= width){ break; }
				
				// find the tile to be render at a specific place
				int tile_index = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK) * MAP_SIZE;
				
				pixels[x + y * width] = Sprite.grass.pixels[(x % 15) + (y & 15) * 16];
			}
		}
	};
};