package Breakout;

import acm.graphics.GCompound;
import acm.graphics.GPoint;

import java.awt.*;

/**
 * Ansel Colby
 *
 * The Wall class is used within BreakoutProgram. It takes care of making the wall
 * and creating the different levels.
 */
public class Wall extends GCompound {
	private int displacement;
	private int currentLevel;

	/**
	 * The constructor
	 * @param level int value for the level that is to be created
	 */
	public Wall(int level) {
		switch (level) {
			case 1: level1(); break;
			case 2: level2(); break;
			case 3: level3(); break;
		}
	}

	/**
	 * A chained constructor for when no level is given. This will create the default white level.
	 */
	public Wall() {
		for(int a = 0; a < 6; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.WHITE), b * 100, a*30);
			}
		}
	}

	/**
	 * The method that is called to see if the Ball collided with a Brick in the Wall.
	 * @param pos the GPoint where the ball is
	 * @param displacement an it value for how far down the Wall is
	 * @return returns true if the ball hit a Brick
	 */
	protected boolean brickHit(GPoint pos, int displacement){
		double posx = pos.getX();
		double posy = pos.getY()-displacement;
		if (getElementAt(posx, posy) instanceof Brick) {
			remove(getElementAt(posx, posy));
			return true;
		}
		return false;
	}

	/**
	 * The creation of the first level.
	 */
	private void level1(){
		displacement = 150;
		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.RED), b * 100, a*30);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.BLUE), b*100, (a*30)+60);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.CYAN), b*100, (a*30)+120);
			}
		}
	}

	/**
	 * The creation of the second level. This one is a bit further down.
	 */
	private void level2(){
		displacement = 250;
		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.GREEN), b * 100, a*30);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.YELLOW), b*100, (a*30)+60);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.ORANGE), b*100, (a*30)+120);
			}
		}
	}

	/**
	 * The creation of the third level. This one is the furthest down.
	 */
	private void level3(){
		displacement = 350;
		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.BLUE), b * 100, a*30);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.ORANGE), b*100, (a*30)+60);
			}
		}

		for(int a = 0; a < 2; a++) {
			for (int b = 0; b < 15; b++) {
				add(new Brick(100, 30, Color.BLUE), b*100, (a*30)+120);
			}
		}
	}

	/**
	 * A getter for the displacement value
	 * @return returns the int value of the displacement
	 */
	public int getDisplacement() {
		return displacement;
	}

	/**
	 * A getter for the current level
	 * @return returns the int value of the level
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}
}
