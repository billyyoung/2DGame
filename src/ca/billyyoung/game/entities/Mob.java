package ca.billyyoung.game.entities;

import ca.billyyoung.game.level.Level;

public abstract class Mob extends Entity {

	// movingDir v.s. x and yMovingDir
	
	protected String name;
	protected int speed;
	protected boolean isMoving;
	protected int[] movingDir;
	
	public Mob(Level level, String name, int x, int y, int speed) {
		super(level);
		this.name = name;
		this.x = x;
		this.y = y;
		this.speed = speed;
		movingDir = new int[2];
	}
	
	public void move() {
		x += movingDir[0] * speed;
		y += movingDir[1] * speed;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isMoving() {
		return isMoving;
	}
	
	public int[] getMovingDir() {
		return movingDir;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public void setMovingDir(int[] movingDir) {
		if (movingDir.length != 2) {
			// bad things happen
		}
		this.movingDir = movingDir;
	}
	
	public void setXMovingDir(int xMovingDir) {
		movingDir[0] = xMovingDir;
	}
	
	public void setYMovingDir(int yMovingDir) {
		movingDir[1] = yMovingDir;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int s) {
		speed = s;
	}
}
