package ballGame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class BallGameFrame extends JFrame{
	private int width;
	private int height;
	private Ball[] balls;
	private Graphics g;
	private int ballNum;
	private int mouseX;
	private int mouseY;
	private int titleHeight;
	private int left;
	

	private MyTime time;
	private Timer timer;
	private boolean gameStart = false;
	private boolean gameOver = false;
	private boolean gameStopped = false;
	
	public BallGameFrame(int width, int height){
		super("弹了个球");
		this.width = width;
		this.height = height;
		ballNum = 0;
		g = getGraphics();

		setSize(width, height);
		
		Font font = new Font("宋体", Font.PLAIN, 15); 
		UIManager.put("Button.font",font); 
		UIManager.put("Label.font",font); 
		
		
		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent event){
				mouseX = event.getX();
				mouseY = event.getY();
			}
			
			public void mouseDragged(MouseEvent event){
				mouseX = event.getX();
				mouseY = event.getY();
			}
			
			public void mouseExited(MouseEvent event){
				mouseX = 0;
				mouseY = 0;
			}
		});

	}
	
	public void startGame(){
		titleHeight = height - getContentPane().getHeight();
		left = width - getContentPane().getWidth();
		gameStart = true;
		gameStopped = false;
		gameOver = false;
		time = new MyTime();
		
		//System.out.println("ballNum " + ballNum + "2");
		
		balls = new Ball[ballNum];
		
        BufferedImage bf = new BufferedImage(40, 40, BufferedImage.TYPE_INT_BGR); //缓冲图片
        Graphics bg = bf.createGraphics();  //缓冲图片的graphics对象
        bg.setColor(Color.black);
        bg.drawRect(0, 0, 40, 40);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(bf, new Point(0, 0), "norm");	

		this.setCursor(cursor);
		
		if (balls == null){
			balls = new Ball[ballNum];
		}
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < ballNum; ++i){
			balls[i] = new Ball(width, height);
			executorService.execute(new BallThread(balls[i])); 
		}
		executorService.shutdown();
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {				
				time.update();
				repaint();
				
				if (gameOver && !gameStopped){
					gameStopped = true;
					this.cancel();
					if (JOptionPane.showConfirmDialog(BallGameFrame.this, "游戏结束！您存活了" 
				        + time.getTime() + "再来一局？", "游戏结束", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
						startGame();
					}
					//JOptionPane.showMessageDialog(BallGameFrame.this, "游戏结束！您存活了" + time.getTime());
//					gameStopped = true;
//					this.cancel();
					
				}
				
		    }
		}, 0 , 50);

	}
	
	
	public void paint(Graphics g){
        BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR); //缓冲图片
        Graphics bg = bf.createGraphics();  //缓冲图片的graphics对象
        bg.setColor(new Color(244, 244, 244));
        bg.fillRect (0, 0, width, height);
        
		if (!gameStart){
			g.drawImage(bf, 0, 0, null);
	        return;
		}

       //使用bg对象绘制所有图像，也就是代替原来的g
//		if (balls == null){
//			balls = new Ball[ballNum];
//			ExecutorService executorService = Executors.newCachedThreadPool();
//			for (int i = 0; i < ballNum; ++i){
//				balls[i] = new Ball(width, height);
//				executorService.execute(new BallThread(balls[i])); 
//			}
//			executorService.shutdown();
//		}
		
		for (int i = 0; i < ballNum; ++i){
			if (balls[i] != null)
				balls[i].paint(bg);
		}
		bg.setFont(new Font("宋体", Font.PLAIN, 30));
		bg.setColor(Color.black);
		bg.drawString(time.getTime(), 100, 100);
        g.drawImage(bf, 0, 0, null); //只需绘制bf一张即可，不会有闪烁现象
        

        
//		Random r = new Random();
//		int x = r.nextInt(width);
//		int y = r.nextInt(height);
//		int radius = r.nextInt(40)+10; 
//		Color color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
//		g.setColor(Color.BLACK);
//		g.fillOval(50, 50, 50, 50);
	}
	
	private class BallThread implements Runnable{
		Ball ball;
		double x, y, distance;
		public BallThread(Ball ball){
			this.ball = ball;
		}
		
		public void run(){
			while (!gameOver){
				ball.ballMove();
				x = Math.abs(mouseX - ball.getX());
				y = Math.abs(mouseY - ball.getY());
				distance = x*x + y*y;
				if (distance < 40*40 || mouseX >= width - 40 || mouseX <= left
						|| mouseY >= height - 40 || mouseY <= titleHeight){
					gameOver = true;
					break;
				}
				try{
					Thread.sleep(50);
//					System.out.println("111");
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setBallNum(int num){
		//System.out.println("ballNum " + ballNum + "1");
		ballNum = num;
	}
	
    protected void processWindowEvent(WindowEvent event) {  
        if (event.getID() == WindowEvent.WINDOW_CLOSING){
        	System.out.println("close");
			System.exit(0);
        	return;
        }
        super.processWindowEvent(event);
    }  
}

