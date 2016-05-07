package ballGame;

public class Main {
	public static void main(String[] args){
		BallGameFrame ballGameFrame = new BallGameFrame(900, 700);
		ballGameFrame.setVisible(true);
		ballGameFrame.setResizable(false);
		ballGameFrame.setLocationRelativeTo(null);
		//ballGameFrame.startGame();
		
		SettingFrame setting = new SettingFrame(ballGameFrame, true);
		setting.setLocationRelativeTo(ballGameFrame);
		setting.setVisible(true);
		
		ballGameFrame.startGame();
		
		System.out.println(ballGameFrame.getContentPane().getWidth());
		
	}
}
