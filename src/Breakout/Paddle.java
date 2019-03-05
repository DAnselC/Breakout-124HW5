package Breakout;

import acm.graphics.GRect;

import java.awt.*;

/**
 * Ansel
 *
 * The Paddle class is used within BreakoutProgram
 */
public class Paddle extends GRect {
    private double paddleX;
    private double paddleY;
    protected int length;

	/**
     * The constructor
     * @param x the x coordinate for the Paddle
     * @param y the y coordinate for the Paddle
     * @param width the width of the Paddle
     * @param height the height of the Paddle
     * @param color the Color of the paddle
	 */
    public Paddle(double x, double y, int width, int height, Color color) {
        super(x, y, width, height);
        paddleX = x;
        paddleY = y;
        length = width;

        this.setFilled(true);
        this.setFillColor(color);
    }

    /**
     * This method is called to set the Paddle back to its original set position.
     */
    protected void originalPosition(){
        this.setLocation(paddleX, paddleY);
    }
}
