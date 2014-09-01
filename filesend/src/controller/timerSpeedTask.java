package controller;

import java.util.TimerTask;


public class timerSpeedTask extends TimerTask{

	@Override
	public void run() {
		commondata.resetTime();
//		System.out.println("resettime");
	}
}
