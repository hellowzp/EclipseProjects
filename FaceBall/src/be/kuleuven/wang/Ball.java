package be.kuleuven.wang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ball {

	private int radius;
	private int xOrigin, yOrigin;
	private static int xRange, yRange;
	private float xVelocity, yVelocity;
	//private int maxVelocity;

	private Color color;
	private static final Color[] colors = { Color.orange, Color.green,
			Color.pink, Color.blue, Color.cyan, Color.yellow, Color.magenta };
	private static int colorID = 0;

	/**
	 * Create a new circle at random position
	 */
	public Ball(int x_Range, int y_Range, int velocity) {
		radius = 25; // default radius
		Ball.xRange = x_Range;
		Ball.yRange = y_Range;
		//this.maxVelocity = maxV;
		xOrigin = (int) (Math.floor(Math.random() * (xRange - 2 * radius)))
				+ radius;
		yOrigin = (int) (Math.floor(Math.random() * (yRange - 2 * radius)))
				+ radius;
//		while ((Math.pow(xVelocity, 2) + Math.pow(yVelocity, 2)) < 50) {
//			xVelocity = (float) (Math.random() * maxVelocity - maxVelocity / 2);
//			yVelocity = (float) (Math.random() * maxVelocity - maxVelocity / 2);
//			System.out.println(this.getXVelocity()+" "+this.getYVelocity());
//		}
		double radians = Math.random() * 2 * Math.PI;
		xVelocity = (float) ( velocity * Math.cos(radians));
		yVelocity = (float) ( velocity * Math.sin(radians));
		
		color = colors[colorID];
		incrementColor();
	}

	public Ball(int xOrigin, int yOrigin) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		color = Color.red;
		xVelocity = 0;
		yVelocity = 0;
		radius=20;
	}

	public static void incrementColor() {
		if (colorID == 6)
			colorID = 0;
		else
			colorID++;
	}

	public Ball(int xOrigin, int yOrigin, int xVelocity, int yVelocity) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

	public static void decrementColor() {
		if (colorID == 0)
			colorID = 6;
		else
			colorID--;
	}

	public Color getColor() {
		return color;
	}

	public float getXVelocity() {
		return xVelocity;
	}

	public float getYVelocity() {
		return yVelocity;
	}

	public int getXOrigin() {
		return xOrigin;
	}

	public int getYOrigin() {
		return yOrigin;
	}
	
	private void setOrigin(int X, int Y) {
		xOrigin = X;
		yOrigin = Y;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setXVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setYVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public void setVelocity(float xVelocity, float yVelocity) {
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
	}

	public void moveAround() {
		int maxX = xRange - radius - 1;
		int maxY = yRange - radius - 1;
		int minX = radius + 1;
		int minY = radius + 1;

		boolean leftCollision = xOrigin + xVelocity <= minX;
		boolean rightCollision = xOrigin + xVelocity >= maxX;
		boolean upCollision = yOrigin + yVelocity <= minY;
		boolean downCollision = yOrigin + yVelocity >= maxY;
		if (leftCollision || rightCollision || upCollision || downCollision) {
			if (leftCollision) {
				xOrigin = minX;
				xVelocity = -xVelocity;
			}
			if (upCollision) {
				yOrigin = minY;
				yVelocity = -yVelocity;
			}
			if (rightCollision) {
				xOrigin = maxX;
				xVelocity = -xVelocity;
			}
			if (downCollision) {
				yOrigin = maxY;
				yVelocity = -yVelocity;
			}
		}else{
			xOrigin += xVelocity;
			yOrigin += yVelocity;
		}
	}

	public boolean borderCollisonCheck() {
		boolean leftCollision = xOrigin + xVelocity <= radius;
		boolean rightCollision = xOrigin + xVelocity >= xRange - radius;
		boolean upCollision = yOrigin + yVelocity <= radius;
		boolean downCollision = yOrigin + yVelocity >= yRange - radius;
		return (leftCollision || rightCollision || upCollision || downCollision);
	}
	
	public boolean ballCollisionCheck(Ball ball) {
		if (!this.equals(ball)) {
			int dx = ball.getXOrigin() - this.getXOrigin();
			int dy = ball.getYOrigin() - this.getYOrigin();
			double ds = Math.pow(dx, 2) + Math.pow(dy, 2);
			if (ds <= Math.pow((ball.getRadius() + this.getRadius()), 2)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean ballCollisionCheck(ArrayList<Ball> balls) {
		for (Ball ball : balls) {
			if(ballCollisionCheck(ball)) return true;
		}
		return false;
	}
	
	public static boolean ballCollisionCheck(Ball a, Ball b) {
		return a.ballCollisionCheck(b);
	}

	public void collisionResponse(Ball ball) {
		double dx = this.getXOrigin() - ball.getXOrigin();
		double dy = this.getYOrigin() - ball.getYOrigin();
		double ds = Math.sqrt(dx*dx+dy*dy);
		double dr = this.getRadius() + ball.getRadius();
		int newThis_X = (int) (this.getXOrigin() + ((this.getRadius()*(dr-ds))/dr)*(dx/ds));
		int newThis_Y = (int) (this.getYOrigin() + ((this.getRadius()*(dr-ds))/dr)*(dy/ds));
		int newThat_X = (int) (ball.getXOrigin() - ((ball.getRadius()*(dr-ds))/dr)*(dx/ds));
		int newThat_Y = (int) (ball.getYOrigin() - ((ball.getRadius()*(dr-ds))/dr)*(dy/ds));
		this.setOrigin(newThis_X,newThis_Y);
		ball.setOrigin(newThat_X, newThat_Y);
		
		if(this.getColor()!=Color.red && ball.getColor()!=Color.red) {
			float xTemp = this.getXVelocity();
			float yTemp = this.getYVelocity();
			this.setXVelocity(ball.getXVelocity());
			this.setYVelocity(ball.getYVelocity());
			ball.setXVelocity(xTemp);
			ball.setYVelocity(yTemp);
		}else{
//			this.setXVelocity(-this.getXVelocity());
//			this.setYVelocity(-this.getYVelocity());
//			ball.setXVelocity(-ball.getYVelocity());
//			ball.setYVelocity(-ball.getYVelocity());
		}
	}
	
	public static void ballCollisionResponse(ArrayList<Ball> balls) {
		List<Ball> synBalls = Collections.synchronizedList(balls);
		for (Ball a : synBalls) {
			//System.out.println( Thread.activeCount());
			List<Ball> restBalls = synBalls.subList(balls.indexOf(a)+1, balls.size());			
			for (Ball b : restBalls) {
				//System.out.println( Thread.activeCount());
				if (a.ballCollisionCheck(b)) {
					a.collisionResponse(b);
				}
			}
		}
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(color);
		g2d.fillOval(xOrigin - radius, yOrigin - radius, 2 * radius, 2 * radius);
	}
}
