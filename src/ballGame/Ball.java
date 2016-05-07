package ballGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
	private final int RADIUS = 40;
	private int x, y;
	private int ballSpeedX, ballSpeedY;
	private Color color;
	private int width, height;

	
	public Ball(int width, int height, int ballSpeedX, int ballSpeedY){
		this.width = width;
		this.height = height;
		this.ballSpeedX = ballSpeedX;
		this.ballSpeedY = ballSpeedY;
		
		Random random = new Random();
		x = random.nextInt(width);
		y = random.nextInt(height);
		color = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		
		if (color.equals(new Color(244, 244, 244)))
			color = new Color(0, 244, 244);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Color getColor(){
		return color;
	}
	public void paint(Graphics g){
		g.setColor(color);
		g.fillOval(x, y, RADIUS, RADIUS);
	}
	
	public void ballMove(){
		if (x + ballSpeedX + RADIUS >= width){
			ballSpeedX *= -1;
			x = width - RADIUS;
		}
		else if (x + ballSpeedX <= 0){
			ballSpeedX *= -1;
			x = 0;
		}
		else
			x += ballSpeedX;
		
		if (y + ballSpeedY + RADIUS >= height){
			ballSpeedY *= -1;
			y = height - RADIUS;
		}
		else if (y + ballSpeedY <= RADIUS){
			ballSpeedY *= -1;
			y = RADIUS;
		}
		else
			y += ballSpeedY;
	}
	
}
