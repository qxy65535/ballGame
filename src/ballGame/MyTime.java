package ballGame;

public class MyTime {
	
	private int minute;
	private int second;
	private int msec;
	
	public MyTime(){
		minute = 0;
		second = 0;
		msec = 0;
	}
	
	public void update(){
		msec += 50;
		if (msec >= 1000){
			msec = 0;
			second++;
			if (second >= 60){
				second = 0;
				minute++;
			}
		}
	}
	
	public void reset(){
		minute = 0;
		second = 0;
		msec = 0;
	}
	
	public String getTime(){
		String time;
		if (minute == 0)
			time = String.format("%.1f″", second + msec/1000.0);
		else
			time = String.format("%d′%.1f″", minute, second + msec/1000.0);
		return time;
	}
	

}
