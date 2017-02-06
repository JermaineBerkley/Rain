package com.jermaineberkley.Rain.Graphics;

public class Sprite {
	
	// size of the sprite on the sprite sheet
	private final int SIZE;
	// x coordinate of the given sprite
	private int x;
	// y coordinate of the given sprite
	private int y;
	// this array is used to make a map of all pixels in a given sprite image
	public int[] pixels;
	// 
	private Spritesheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, Spritesheet.tiles);
	
	public Sprite (int size, int x, int y, Spritesheet sheet) {
		// set the final "SIZE" to the value provided by the "size" parameter
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	private void load () {
		for (int y = 0; y < SIZE; y++ ) {
			for (int x = 0; x < SIZE; x++) {
				/* take a pixel from the spritsheet and assign it to to the exact same pixel 
				   in the array that holds the mapped image */
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * 16];
			}
		}
	}
}

