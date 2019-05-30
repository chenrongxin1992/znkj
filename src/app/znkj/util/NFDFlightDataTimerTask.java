package app.znkj.util;

import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

/**
 * 定时任务执行
 * @author xian.zf
 *
 * @date 2013-1-19
 */
public class NFDFlightDataTimerTask extends TimerTask {
	protected static final Log log = LogFactory.getLog(NFDFlightDataTimerTask.class);
	
	
	@Override
	public void run() {
	  try {
		  System.out.print("\n -------------执行了-------------  \n\n ");
		  //备份
		  JavaBackupMysql.backup(new DateTime().toString("yyyyMMddHHmmss") + ".sql", null);
		  
	  } catch (Exception e) {
		  log.error("-------------定时器执行了解析信息发生异常--------------");
	  }
	}
}
	 
