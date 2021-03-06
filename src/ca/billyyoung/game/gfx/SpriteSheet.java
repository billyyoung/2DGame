package ca.billyyoung.game.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	public String path;
	public int width;
	public int height;
	
	// 0xAARRGGBB stored in pixels[i]
	public int[] pixels;
	
	public SpriteSheet(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// if something by chance went wrong
		if (image == null) {
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		for (int i=0; i<pixels.length; i++) {
			// bitwise AND to remove the alpha in 0xAARRGGBB
			pixels[i] = (pixels[i] & 0xff)/ (255/4);
		}
	}
}
