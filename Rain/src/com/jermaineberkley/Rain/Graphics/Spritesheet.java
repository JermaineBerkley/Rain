package com.jermaineberkley.Rain.Graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private String path;
	public final int SIZE;
	// an array containing every pixel on the screen
	public int[] pixels;
	
	public static Spritesheet tiles = new Spritesheet("/Textures/spritesheet.png", 256);
	
	/* this method takes the file path of an image, in this case a spritesheet, 
	   sets the final (const) SIZE, and sets it to the value of the parameter path, and
	   sets the pixels array length to the SIZE final (const) times itself */
	
	public Spritesheet(String path, int size){
		// set the local variable path to the string provided by the "path" parameter
		this.path = path;
		// set the final "SIZE" to the value provided by the "size" parameter
		SIZE = size;
		// set the size of the pixels array to the final "SIZE" multiplied by itself (2D area L * W)
		pixels = new int[SIZE * SIZE];
		// once this is done, call load to get the spritesheet and map it
		load();
	}
	
	/* This method takes an image, in this case, a spritesheet, takes the file path given to it
	   from the spriteshhet method, gets the width and height of the image, and
	   gets the images pixel-ized RGB information */
	
	private void load () {
		try {
			BufferedImage image = ImageIO.read(Spritesheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error mon!");
		}
	}
}
