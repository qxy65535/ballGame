package ballGame;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SettingFrame extends JDialog implements ActionListener{
	
	private BallGameFrame parent;
	private JLabel ballNum;
	private JTextField tfBallNum;
	private JButton start;
	
	public SettingFrame(BallGameFrame parent, boolean modal){
		super(parent, modal);
		this.parent = parent;
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));
		setTitle("设置");
		setSize(270, 160);
		setResizable(false);
		
		Box wholeBox = Box.createVerticalBox();
		Box numBox = Box.createHorizontalBox();
		ballNum = new JLabel("弹球数目：");
		tfBallNum = new JTextField(10);
		tfBallNum.setFont(new Font("宋体", Font.PLAIN, 20));
		numBox.add(ballNum);
		numBox.add(Box.createHorizontalStrut(5));
		numBox.add(tfBallNum); 
		
		start = new JButton("开始游戏");
		start.addActionListener(this);
		
		wholeBox.add(numBox);
		wholeBox.add(Box.createVerticalStrut(20));
		wholeBox.add(start);
		add(wholeBox);
		
		
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == start){
			if (!isNumber(tfBallNum.getText()) || Integer.valueOf(tfBallNum.getText()) == 0){
				JOptionPane.showMessageDialog(this, "输入不合法！");
				return;
			}
			
			parent.setBallNum(Integer.valueOf(tfBallNum.getText()));
			this.dispose();
		}
	}
	
	private boolean isNumber(String str){  
		if ("".equals(str))
			return false;
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	 }
	
	protected void processWindowEvent(WindowEvent event){
        if (event.getID() == WindowEvent.WINDOW_CLOSING){
			System.exit(0);
        	return;
        }
        super.processWindowEvent(event);
	}

}
