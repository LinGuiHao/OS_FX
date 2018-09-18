package device;

import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.print.attribute.standard.Finishings;
import java.util.TimerTask;
import device.*;

public class CountDown {
	private int limitSec;
	private int curSec;
	public CountDown(int limitSec) throws InterruptedException{
		this.limitSec = limitSec;
		this.curSec = limitSec;
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
			}
		},0,1000);
		TimeUnit.SECONDS.sleep(limitSec);
		timer.cancel();

		//倒计时结束
		/*
		 * await();
		 *
		 */

	}
}
