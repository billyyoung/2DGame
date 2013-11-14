package ca.billyyoung.game.entities;

import java.awt.Color;
import java.awt.Graphics;

import ca.billyyoung.game.InputHandler;
import ca.billyyoung.game.gfx.Tile;
import ca.billyyoung.game.level.Level;

public class Player extends Mob {
	
	private InputHandler input;
	private int tickCount = 0;
	private String username;
	
	// click one direction, move 1 unit in that direction
	private int oldX = 0, oldY = 0;
	
	public Player(Level level, int x, int y, InputHandler input, String username) {
		// Mob(level, name, x, y, speed)
		super(level, "Player", x, y, 1);
		this.input = input;
		this.username = username;
	}
	
	public void tick() {
		if (!isMoving) {
			int xDir = 0;
			int yDir = 0;
			
			if (input != null) {
				// if you're not moving
				if (input.up.isPressed()) {
					yDir--;
				}
				if (input.down.isPressed()) {
					yDir++;
				}
				if (input.left.isPressed()) {
					xDir--;
				}
				if (input.right.isPressed()) {
					xDir++;
				}
			}
			if (xDir != 0 || yDir != 0) {
				isMoving = true;
				oldX = x;
				oldY = y;
				movingDir[0] = xDir;
				movingDir[1] = yDir;
				move();
			}
		}
		// if the player is moving
		else {
			// if the player has moved one tile
			if ( (x-oldX)/Tile.SIZE == movingDir[0] && (y-oldY)/Tile.SIZE == movingDir[1]) {
				isMoving = false;
			} else {
				move();
			}
		}
		tickCount++;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 40, 40);
	}
	
	public String getUsername() {
		return username;
	}
}
