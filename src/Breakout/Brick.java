package Breakout;

import acm.graphics.GRect;

import java.awt.*;

/**
 * Ansel Colby
 *
 * The Brick class is used within BreakoutProgram and Wall.
 */
public class Brick extends GRect {
	/**
     * Constructor
     * @param width the width for the Brick
     * @param height the height for the Brick
     * @param color the Color to be set
     */
    public Brick(double width, double height, Color color) {
        super(width, height);
        this.setFilled(true);
        this.setFillColor(color);
    }
}
