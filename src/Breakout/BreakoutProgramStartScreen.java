package Breakout;

import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;



/**
 * Main GraphicsProgram for the breakout game
 * The classes that this program makes instances of are: Ball, Brick, Paddle, and Wall
 */
public class BreakoutProgramStartScreen extends GraphicsProgram {
	// PUBLIC STATIC FINAL \\
	public static final int APPLICATION_HEIGHT = 900; // This is the width and height that the window should be.
	public static final int APPLICATION_WIDTH = 1500; // These variables are automatically detected and the window is set.
	// INSTANCE VARIABLES \\
	private Ball ball;
	private Paddle paddle;
	private Wall wall;
	private int displacement; // How far down the Wall is

	private Timer timer;

	private double dx;
	private double dy;

	private double centerPoint;
	private GPoint midTop;
	private GPoint midRight;
	private GPoint midLeft;
	private GPoint midBott;

	private GObject gObj;
	private GPoint last;
	private int clickNum;

	private boolean loss;
	private boolean win;
	private int lives;
	private int numBricks;
	private int level;

	private boolean firstClick;

	private GLabel lifeLabel;
	private GLabel startLabel;
	private GLabel playAgain;
	Font font;
	Font fontSmall;



	/**
	 * The init method sets most of the important instance variables while also adding the mouse,
	 * action, and key listeners. Also, it calls the Java Timer set up method.
	 */
	public void init() {
		firstClick = true;
		loss = false;
		win = false;
		lives = 3;
		numBricks = 90;
		// FOR TESTING THE DIFFERENT LEVELS AND WINNING - Each level will end after four Bricks are hit
		//numBricks = 4;
		// -----------------------------------------------------------------------------------
		level = 1;

		clickNum = 0;
		gObj = null;
		last = null;

		font = new Font("Courier", Font.BOLD, 150);
		fontSmall = new Font("Courier", Font.BOLD, 100);


		addMouseListeners();
		addActionListeners();
		addKeyListeners();
		setUpStartScreen();
		setupJavaTimer();
	}

	/**
	 * This method sets up the timer, declares how long it will pause each time. The timer calls mainGame() every time through.
	 */
	public void setupJavaTimer() {
		timer = new Timer(2, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mainGame();
			}
		});
	}

	/**
	 * This method is called within the init method, and it adds the title and the Start Game label.
	 */
	private void setUpStartScreen() {
		setBackground(Color.BLACK);
		add(makeBREAKOUT(), 175, 200);

		startLabel = new GLabel("START GAME");
		startLabel.setFont(fontSmall);
		startLabel.setColor(Color.WHITE);
		add(startLabel, 400, 600);


	}

	/**
	 * This method is called once the user clicks on the Start Game label. It sets background colors, the life
	 * label, and it creates the instances of the wall class, ball class, and paddle class. The ball is created in
	 * another method -- setUpBall()
	 */
	private void setUpMainGame(){
		setBackground(Color.BLACK);
		lifeLabel = new GLabel("Lives: "+lives);
		lifeLabel.setFont("Courier Bold-50");
		lifeLabel.setColor(Color.WHITE);
		add(lifeLabel, 0, getHeight()-100);

		wall = new Wall(level);
		displacement = wall.getDisplacement();
		add(wall, 0, displacement);

		setUpBall();

		paddle = new Paddle(getWidth()/2f, getHeight()-250, 150, 25, Color.WHITE);
		add(paddle);
		paddle.sendToFront();
	}

	/**
	 * This is the main loop, it is called every time through the Java Timer. The order of events that happen:
	 *
	 * Moves the ball by dx and dy
	 * Calls update() to update the lives and check where the middle edges of the ball are
	 * Checks to see if the ball hit a wall with checkSideWalls()
	 * Checks to see if the ball hit a brick with checkBricks()
	 * Checks a win
	 * Checks to see if a life was lost
	 * Checks a loss
	 *
	 * The win and loss actions are also in this method, within if statements.
	 */
	private void mainGame() {
		ball.move(dx, dy);
		update();
		checkSideWalls();
		checkPaddle();
		checkBricks();
		if(win)timer.stop();
		checkLifeLost();
		if(loss)timer.stop();

		if(win){
			GLabel winL = new GLabel("YOU WON!!", (getWidth()/2f)-400, getHeight()/2f);
			winL.setColor(Color.WHITE);
			winL.setFont("Courier Bold-150");
			add(winL);
			if (level!=3) {
				makePlayAgain();
			}


		}else if (loss){
			GLabel lossL = new GLabel("YOU LOST :(", (getWidth()/2f)-400, getHeight()/2f);
			lossL.setColor(Color.RED);
			lossL.setFont("Courier Bold-150");
			add(lossL);
			makePlayAgain();
		}
	}

	/**
	 * This method is called within mainGame(), and it updates the life label and grabs certain coordinate points of the ball.
	 */
	private void update(){
		lifeLabel.setLabel("Lives: "+lives);

		centerPoint = ball.getX() + (ball.diameter/2f);

		midTop = new GPoint(ball.getX() + (ball.diameter/2f), ball.getY());
		midLeft = new GPoint(ball.getX(), ball.getY() + (ball.diameter/2f));
		midRight = new GPoint(ball.getX()+ball.diameter, ball.getY() + (ball.diameter/2f));
		midBott = new GPoint(ball.getX() + (ball.diameter/2f), ball.getY()+ball.diameter);
	}

	/**
	 * This method is called to make the BREAKOUT title label. Each letter is a GLabel with a different color.
	 * @return returns all of the GLabels within one GCompound
	 */
	private GCompound makeBREAKOUT() {
		GCompound breakout = new GCompound();

		GLabel B = new GLabel("B");
		BREAKOUThelper(B, font, Color.BLUE);
		breakout.add(B, 0, 0);

		GLabel R = new GLabel("R");
		BREAKOUThelper(R, font, Color.RED);
		breakout.add(R, 150, 0);

		GLabel E = new GLabel("E");
		BREAKOUThelper(E, font, Color.GREEN);
		breakout.add(E, 300, 0);

		GLabel A = new GLabel("A");
		BREAKOUThelper(A, font, Color.MAGENTA);
		breakout.add(A, 450, 0);

		GLabel K = new GLabel("K");
		BREAKOUThelper(K, font, Color.YELLOW);
		breakout.add(K, 600, 0);

		GLabel O = new GLabel("O");
		BREAKOUThelper(O, font, Color.ORANGE);
		breakout.add(O, 750, 0);

		GLabel U = new GLabel("U");
		BREAKOUThelper(U, font, Color.CYAN);
		breakout.add(U, 900, 0);

		GLabel T = new GLabel("T");
		BREAKOUThelper(T, font, Color.PINK);
		breakout.add(T, 1050, 0);

		return breakout;
	}

	/**
	 * This method is called to set the font and color of each letter in the BREAKOUT title GLabel
	 * @param letter the GLabel that is to have the font and color changed
	 * @param font the font to change
	 * @param color the color to change
	 */
	private void BREAKOUThelper(GLabel letter, Font font, Color color) {
		letter.setFont(font);
		letter.setColor(color);
	}

	/**
	 * This method is called within mainGame(), and it checks to see if the ball went off the bottom of the screen.
	 * If it did go past, then it moves the ball to it's original position and resets dx, dy, lives, and if lives is
	 * equal to 0 loss is set to true.
	 */
	private void checkLifeLost(){
		if(ball.getY()>getHeight()){
			ball.originalPostition();
			dx = 2;
			dy = -2;
			lives --;
			if (lives==0)loss = true;
			update();
		}
	}

	/**
	 * This method makes the Play Again? label, which if clicked will reset the game to the beginning. The text
	 * and position will be different for a loss and for a win.
	 */
	private void makePlayAgain() {
		playAgain = new GLabel("Play Again?");
		playAgain.setFont(fontSmall);
		playAgain.setColor(Color.WHITE);
		if (win) {
			playAgain.setLabel("Continue Playing?");
			add(playAgain, 350, 700);
		} else {
			add(playAgain, 500, 700);
		}
	}

	/**
	 * This method is called within mainGame(), and it asks the Wall class to see if the Ball hit a Brick.
	 * It also checks to see if all of the bricks have been hit. If this is true, win is set to true.
	 */
	private void checkBricks() {
		if (wall.brickHit(midBott, displacement)){
			dy = -2;
			numBricks--;
		} if (wall.brickHit(midTop, displacement)) {
			dy = 2;
			numBricks--;
		} if (wall.brickHit(midRight, displacement)) {
			dx = -2;
			numBricks--;
		} if (wall.brickHit(midLeft, displacement)) {
			dx = 2;
			numBricks--;
		}
		if(numBricks==0)win = true;
	}

	/**
	 * This method checks to see if the ball hit or went past one of the side walls or the top wall. If the ball
	 * hits or goes past one of them, it sets dx or dy to be the opposite value so that the ball will "bounce off"
	 */
	private void checkSideWalls(){
		if (ball.getY() <= 0) {
			dy = 2;
		} if (ball.getX() + ball.diameter >= getWidth()) {
			dx = -2;
		} if (ball.getX() <= 0) {
			dx = 2;
		}
	}

	/**
	 * This method checks to see if the ball hit the paddle. There are four possible places for the ball to hit the paddle,
	 * and each one has a different effect on its motion.
	 *
	 * The very left of the paddle: dx and dy are set to -2, the ball will bounce to the left a little faster
	 * Between leftmost and the center: dx is set to -1.3 and dy is set to -2. The ball will bounce a little slower left.
	 * Between the center and rightmost: the opposite of left middle
	 * The very right of the paddle: the opposite of the leftmost section
	 */
	private void checkPaddle() {
		if (leftmost()){
			dx = -2;
			dy = -2;
		}else if (leftmid()){
			dx = -1.3;
			dy = -2;
		}else if (rightmid()){
			dx = 1.3;
			dy = -2;
		} else if (rightmost()) {
			dx = 2;
			dy = -2;
		}
	}

	/**
	 * Checks to see if the ball is on the leftmost part of the paddle
	 * @return returns true or false
	 */
	private boolean leftmost(){
		return ball.getY() + ball.diameter == paddle.getY() && ball.getX()+ball.diameter >= paddle.getX() && centerPoint < paddle.getX() + 40;
	}

	/**
	 * Checks to see if the ball is on the left middle part of the paddle
	 * @return returns true or false
	 */
	private boolean leftmid(){
		return ball.getY() + ball.diameter == paddle.getY() && centerPoint >= paddle.getX()+40 && centerPoint < paddle.getX() + 75;
	}

	/**
	 * Checks to see if the ball is on the right middle part of the paddle
	 * @return returns true or false
	 */
	private boolean rightmid(){
		return ball.getY() + ball.diameter == paddle.getY() && centerPoint >= paddle.getX() + 75 && centerPoint < paddle.getX() + 109;
	}

	/**
	 * Checks to see if the ball is on the rightmost part of the paddle
	 * @return returns true or false
	 */
	private boolean rightmost(){
		return ball.getY() + ball.diameter == paddle.getY() && centerPoint >= paddle.getX()+109 && ball.getX() <= paddle.getX() + paddle.length;
	}

	/**
	 * The method that creates the instance of the Ball class
	 * It also sets dx and dy to their original values
	 */
	private void setUpBall(){
		dx = 2;
		dy = -2;
		ball = new Ball((getWidth()/2)+50, getHeight()-300, 50, 50, Color.GREEN);
		add(ball);
	}

	/**
	 * This method is called when the game progresses to the next level or starts over.
	 * It increases the level by 1, increases the life total by 1, and resets the brick count.
	 * If the user lost, it resets the lives to their original amount, and stays at the same level.
	 * Finally, it resets some important boolean values: firstClick, loss, and win
	 */
	private void resetGame(){
		level++;
		lives++;
		numBricks = 90;
		// FOR TESTING THE DIFFERENT LEVELS AND WINNING - Each level will end after four Bricks are hit
		//numBricks = 4;
		// -----------------------------------------------------------------------------------
		if(loss){
			lives = 3;
			level--;
		}
		firstClick = true;
		loss = false;
		win = false;
	}

	/**
	 * This method is called when the mouse is pressed and records where it was pressed.
	 * If the mouse was pressed on the Start Game label it will change the label's color.
	 * If this is the first click and the mouse click is on the paddle, the timer will start
	 * 		and gObj is set to the paddle. Otherwise clickNum will be reset and gObj will be set to null.
	 * @param e the location of the mouse
	 */
	public void mousePressed(MouseEvent e) {
		GPoint test = new GPoint(e.getX(), e.getY());
		if (getElementAt(test)!=null && getElementAt(test)==startLabel){
			startLabel.setColor(Color.BLUE);
		}

		if (clickNum==0) {
			last = new GPoint(e.getPoint());
			if (getElementAt(last) == paddle) {
				if(firstClick) {
					timer.start();
					firstClick = false;
				}
				gObj = getElementAt(last);
			}
			clickNum = 1;
		}else{
			gObj = null;
			clickNum = 0;
		}
	}

	/**
	 * Called when the user clicks the mouse.
	 * If the user clicks on startLabel: Everything is removed from the screen and setUpMainGame() is called again
	 * If the user clicks on playAgain: Everything is removed from the screen -- resetGame() and setUpMainGame are called
	 * @param e the position of the mouse on the screen
	 */
	public void mouseClicked(MouseEvent e) {
		GPoint test = new GPoint(e.getX(), e.getY());
		if (getElementAt(test)!=null && getElementAt(test)==startLabel) {
			startLabel.setColor(Color.WHITE);
			removeAll();
			setUpMainGame();
		}
		if (getElementAt(test)!=null && getElementAt(test)==playAgain) {
			playAgain.setColor(Color.BLUE);
			removeAll();
			resetGame();
			setUpMainGame();
		}
	}

	/**
	 * Called when the user moves the mouse while gObj is set to the Paddle.
	 * If it is set to the paddle it will be moved however far the user moved the mouse. HOWEVER it will only
	 * be moved in a horizontal direction.
	 * @param e the location of the mouse
	 */
	public void mouseMoved(MouseEvent e) {
		if (gObj != null) {
			gObj.move(e.getX() - last.getX(), 0);
			last = new GPoint(e.getPoint());
		}
	}

	/**
	 * Called when the user presses one of the arrow keys.
	 * If the user presses the left or right arrow key it will move the paddle
	 * left or right by 15 pixels.
	 * @param e the key that is pressed
	 */
	public void keyPressed(KeyEvent e) {
		if (paddle != null) {
			if (firstClick){
				firstClick = false;
				timer.start();
			}
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT: paddle.move(-15, 0); break;
				case KeyEvent.VK_RIGHT: paddle.move(+15, 0); break;
			}
		}
	}

}
