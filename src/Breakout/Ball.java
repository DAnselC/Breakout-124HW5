package Breakout;

import acm.graphics.GOval;

import java.awt.*;

/**
 * Ansel Colby
 *
 * This is the Ball class that BreakoutProgram uses. It is a GOval that will "bounce" around the screen
 */
public class Ball extends GOval {
    protected int diameter;
    private int x;
    private int y;

    /**
     *
     * @param x x coordinate for the Ball
     * @param y y coordinate for the Ball
     * @param width the width of the Ball
     * @param height the height of the Ball
     * @param color the Color to be set
     */
    public Ball(int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.setFilled(true);
        this.setFillColor(color);
        diameter = width;
        this.x = x;
        this.y = y;
    }

	/**
     * Called to set the ball to its original set location
     */
    protected void originalPostition(){
        this.setLocation(x, y);
    }
}
