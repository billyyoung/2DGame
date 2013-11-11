package ca.billyyoung.game.gfx;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	public static final int SIZE = 40;
	public static final char GRASS = 'G';
	
	private char type;
	private int row, col;
	private boolean isTraversable;
	private BufferedImage[] images;
	private int imageAnimationCount = 0;
	
	public Tile(char type, int row, int col) {
		this.type = type;
		this.row = row;
		this.col = col;
		this.processType();
	}
	
	// processType
	// sets up the Tile based on its type char
	// this includes the image(s), isTraversable
	public void processType() {
		// if this is a GRASS tile
		if (type == GRASS) {
			// it is traversable
			isTraversable = true;
			
			// fill images with the grass image
			BufferedImage img = null;
			try {
				// it prepends the resource directory path to "/terrain/grass.png"
				img = ImageIO.read(Tile.class.getResourceAsStream("/terrain/grass.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// if something by chance went wrong
			if (img == null) {
				return;
			}
			images = new BufferedImage[1];
			images[0] = img;
		}
	} // processType
	
	public void render(Graphics g) {
		// draw images[imageAnimationCount];
		g.drawImage(images[imageAnimationCount], col*SIZE, row*SIZE, SIZE, SIZE, null);
	}
	
	// tick
	// in case the tile is animated
	public void tick() {
		if (images.length == 0) return;
		
		imageAnimationCount = (imageAnimationCount + 1) % images.length;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isTraversable() {
		return isTraversable;
	}
	
	public void setTraversable(boolean t) {
		isTraversable = t;
	}
	
}
