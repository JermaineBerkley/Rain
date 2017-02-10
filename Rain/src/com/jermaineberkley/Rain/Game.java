package com.jermaineberkley.Rain;

//for java's canvas
import java.awt.Canvas;
import java.awt.Color;
//for screen sizing
import java.awt.Dimension;
//for graphics
import java.awt.Graphics;
//for buffer strategy
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
//for JFrame
import javax.swing.JFrame;
//imported, created class
import com.jermaineberkley.Rain.Graphics.Screen;
//imported, created class
import com.jermaineberkley.Rain.Input.Keyboard;

//game is a sub class of canvas through extends, giving it canvas's properties and methods
//java.awt.Canvas import needed to use the Canvas class
public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// public means that the item is available through out the overall program
	// dimensions of the window
	public static int width = 300;
	public static int height = width / 16 * 9;
	public static int scale = 3; 
	// title of the game
	public static String title = "Rain";
	// private means the item is only viewable in context to the current class(Game.java)
	// Thread: a process within a process or sub process
	private Thread thread;
	// This stands for java frame, as opposed to a frame which java also has. ** RESEARCH THIS **
	private JFrame frame;
	// This creates an instance of the Keyboard class that can be used and manipulated in the application
	private Keyboard key;
	// imported from my created screen class
	private Screen screen;
	// used to check the state of the game. If it's false, the game is not running
	private boolean running = false;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game (){
		// this is one of the methods that come to the game class via game extending canvas
		// dimension deals with the area of the canvas
		// java.awt.Dimension import needed 
		Dimension r_Dimension = new Dimension(width * scale, height * scale);
		setPreferredSize(r_Dimension);
		// initiating the screen class
		screen = new Screen(width, height);
		// this creates a new instance of Jframe
		frame = new JFrame();
		// initiating the keyboard
		key = new Keyboard();
		// Remember to always add the input to the key listener
		addKeyListener(key);
	};
	
	// synchronized is used as a way to ensure no "over-lap of instructions"
	// start start's the "app-let"
	public synchronized void start (){
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	};
	
	// stop prevents program from running after window is closed 
	public synchronized void stop (){
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
	};
	
	public void run() {
		long last_time = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;                                                                                 
		
		while(running){
			long now = System.nanoTime();
			delta += (now - last_time) / ns;
			last_time = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println(updates + " updates at " + frames + " framaes per second.");
				frame.setTitle(title + "  " + updates + " updates at " + frames + " framaes per second.");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	};
	
	public void render (){
		// java.awt.image import needed for buffer strategy 
		BufferStrategy r_buffer_strategy = getBufferStrategy(); 
		// a buffer is and array of pixels, placements, and colors and everything that makes up the next frame.
		// the image is stored in an array while the current picture is displaying, making it easier to display the next frame
		// so that it doesn't render live
		
		// mini-singleton
		if(r_buffer_strategy == null){
			// if there's no buffer strategy for the canvas, create one with a value of three
			// buffer strategy @ 3: [frame on screen] [rendered frame in waiting] [rendered frame in waiting]
			//                            [1]                      [2]                         [3]
			createBufferStrategy(3);
			return;
		}
		// clear method form the Screen class
		screen.clear();
		// method from the created Screen class
		screen.render(x, y);
		// this loop takes the mapped image from the pixels array, gets 
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = screen.pixels[i];
		}
		
		// java.awt.Graphics import needed for Graphics
		// this bit of code links graphics to the buffer strategy 
		Graphics r_graphics = r_buffer_strategy.getDrawGraphics();
		
		// draw all objects between graphics and .dispose
		
		// set the color of the rect to be filled to black
		r_graphics.setColor(new Color(108, 114, 122));
		
		// fill the rect with the color black with this x and y position, and the get width and get height methods
		r_graphics.fillRect(0, 0, getWidth(), getHeight());
		r_graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		r_graphics.dispose();
		// buffered image (image yet to show) doesn't show up automatically, you must "show" them
		r_buffer_strategy.show();
	};
	
	int x = 0;
	int y = 0;
	
	public void update(){
		key.update();
		if (key.up){ y--; }
		else if (key.down) { y++; }
		else if (key.left) { x--; }
		else if (key.right) { x++; }
	};
	
	public static void main(String[] args){
		// this creates an object instance of the Game class for reference named rain
		Game rain = new Game();
		// window resizing lead to graphical errors
		rain.frame.setResizable(false);
		// creates a title
		rain.frame.setTitle(Game.title);
		// add game component rain to the Jframe
		rain.frame.add(rain);
		// sets the size of the frame to component spec.
		rain.frame.pack();
		// when the user closes the window, the application terminates
		rain.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this centers the window
		rain.frame.setLocationRelativeTo(null);
		// actually shows the window
		rain.frame.setVisible(true);
		// this accesses the the start method that rain inherited from the Game class, and starts the game
		rain.start();
	};
	
};