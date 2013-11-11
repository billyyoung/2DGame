package ca.billyyoung.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.swing.JFrame;

import ca.billyyoung.game.gfx.Screen;
import ca.billyyoung.game.level.Level;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 1;
	public static final String NAME = "Game";

	private JFrame frame;

	public boolean running = false;
	public int tickCount = 0;

	//private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	// private int[] colours = new int[6*6*6];

	private Screen screen;
	public InputHandler input;
	private Level level;

	public Game() {
		setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		frame = new JFrame(NAME);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		// sets size to be >= preferred size
		frame.pack();

		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// init
	public void init() {
		/*
		 * int index = 0; for (int r=0; r<6; r++) { for (int g=0; g<6; g++) {
		 * for (int b=0; b<6; b++) { int rr = (r * 255 / 5); int gg = (g * 255 /
		 * 5); int bb = (b * 255 / 5);
		 * 
		 * colours[index] = rr << 16 | gg << 8 | bb; index++; } } }
		 */
		// screen = new Screen(WIDTH, HEIGHT, new
		// SpriteSheet("/sprite_sheet.png"));
		input = new InputHandler(this);
		
		level = new Level();
		try {
			level.init("res/levels/level1");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// start
	// makes it runnable by applet/in browser
	public synchronized void start() {
		running = true;
		// thread.start() runs that thread's run() function
		new Thread(this).start();
	} // start

	// stop
	public synchronized void stop() {
		running = false;
	} // stop

	// tick
	// updates variables
	public void tick() {
		tickCount++;

		if (input.up.isPressed()) {
			screen.yOffset--;
		}
		if (input.down.isPressed()) {
			screen.yOffset++;
		}
		if (input.left.isPressed()) {
			screen.xOffset--;
		}
		if (input.right.isPressed()) {
			screen.xOffset++;
		}

		// testing pixels[]
		/*
		 * for (int i=0; i<pixels.length; i++) { pixels[i] = i + tickCount; }
		 */
	} // tick

	// render
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			// triple buffering
			createBufferStrategy(3);
			return;
		}
		/*
		 * for (int y=0; y<32;y++) { for (int x=0; x<32; x++) {
		 * screen.render(x<<3, y<<3, 0, Colours.get(555, 500, 050, 005)); } }
		 * for (int y=0; y<screen.height;y++) { for (int x=0; x<screen.width;
		 * x++) { int colourCode = screen.pixels[x + y*screen.width]; if
		 * (colourCode < 255) pixels[x + y*WIDTH]= colours[colourCode]; } }
		 */
		Graphics g = bs.getDrawGraphics();
		level.render(g);
		//g.setColor(Color.RED);
		//g.fillRect(0, 0, getWidth(), getHeight());
		//g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();

	} // render

	// run
	public void run() {
		long lastTime = System.nanoTime();
		// 60D means 60 is a double
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		boolean shouldRender = false;
		while (running) {
			// shouldRender = false;
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			// add a delay to limit the maximum number of loops
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			// if one second has gone by
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(frames + " frames, " + ticks + " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	} // run

	public static void main(String[] args) {
		new Game().start();
	}

}
