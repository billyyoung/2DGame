package ca.billyyoung.game.level;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ca.billyyoung.game.gfx.Tile;

public class Level {
	public Tile[][] map;
	
	public Level() {
	}
	
	public void init(String levelInfoFile) throws IOException {
		// the format of the level info file is as follows:
		// first line: r, the number of rows
		// second line: c, number of columns
		// remaining r lines: strings of length c
		
		BufferedReader in = new BufferedReader(new FileReader(levelInfoFile));
		
		int rows = Integer.parseInt(in.readLine());
		int cols = Integer.parseInt(in.readLine());
		map = new Tile[rows][cols];
		
		String line;
		for (int r=0; r<rows; r++) {
			line = in.readLine();
			for (int c=0; c<cols; c++) {
				map[r][c] = new Tile(line.charAt(c), r, c);
			}
		}
		in.close();
	}
	
	public void render(Graphics g) {
		for (int r=0; r<map.length; r++) {
			for (int c=0; c<map[0].length; c++) {
				map[r][c].render(g);
			}
		}		
	}
	
	public void tick() {
		for (int r=0; r<map.length; r++) {
			for (int c=0; c<map[0].length; c++) {
				map[r][c].tick();
			}
		}
	}
}
