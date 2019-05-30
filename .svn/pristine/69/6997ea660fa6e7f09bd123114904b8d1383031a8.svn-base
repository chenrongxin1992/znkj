package app.znkj.util;

import java.util.Timer;

import org.joda.time.DateTime;

/**
 * 定时更新任务
 * @author xian.zf
 *
 * @date 2013-1-19
 */
public class TimerManager {
	 //时间间隔 7天
	private static final long PERIOD_DAY = 7 * 24 * 60 * 60 * 1000;
	  
	public TimerManager() {		 
		/*** 定制每周、周日 03:00 执行方法 ***/
		DateTime nowdate = new DateTime();
		DateTime start = new DateTime(nowdate.getYear(), nowdate.getMonthOfYear(), nowdate.getDayOfMonth()
				, 3, 0, 0);
		//确定第一次执行的时间
		for(int i=0; i<7; i++){
			if(start.getDayOfWeek()==7)
				break;
			start = start.plusDays(1);
		}   

		Timer timer = new Timer();
		NFDFlightDataTimerTask task = new NFDFlightDataTimerTask();
		//安排指定的任务在指定的时间开始进行重复的固定延迟执行。
		timer.schedule(task, start.toDate(), PERIOD_DAY);
	}	 

}
