package app.znkj.service;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;

public interface IndexService {

	/**
	 * 登陆
	 * @author xian.zf
	 * @date 2014-7-18
	 * @param account
	 * @param password
	 * @return
	 */
	Map<String, Object> ajaxLogin(String account, String password);

	/**
	 * 首页最新成果
	 * @author xian.zf
	 * @date 2014-8-6
	 * @return
	 */
	List<Record> getNewAchList(boolean flag);

	/**
	 * 首页----通知公告
	 * @author xian.zf
	 * @date 2014-8-6
	 * @param equals
	 * @return
	 */
	List<Record> getNoticeList(boolean flag);

	/**
	 * 首页----新闻中心
	 * @author xian.zf
	 * @date 2014-8-6
	 * @param equals
	 * @return
	 */
	List<Record> getNewsList(boolean flag);

}
