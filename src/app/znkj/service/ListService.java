package app.znkj.service;

import java.util.List;
import java.util.Map;

import app.znkj.model.PeriodicalArticle;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public interface ListService {

	/**
	 * 期刊论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @param curpage
	 * @param pageSize
	 * @param name
	 * @param userid
	 * @return
	 */
	Map<String, Object> getPerArticleList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);
	
	/**
	 * 会议论文列表
	 * @author xian.zf
	 * @date 2014-7-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param userid
	 * @param authors
	 * @param year
	 * @return
	 */
	Map<String, Object> getConArticleList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);

	/**
	 * 论文列表
	 * @author xian.zf
	 * @date 2014-7-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param userid
	 * @param authors
	 * @param year
	 * @return
	 */
	Map<String, Object> getThesisList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);

	
	/**
	 * 科研管理--专著列表
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getTreatiseList 
	 * @return Map<String,Object> 
	 * @throws Excetion
	 */
	Map<String, Object> getTreatiseList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);
	
	
	/**
	 * 获奖列表
	 * @author xian.zf
	 * @date 2014-7-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param userid
	 * @param authors
	 * @param year
	 * @return
	 */
	Map<String, Object> getAwardList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);

	/**
	 * 专利列表
	 * @author xian.zf
	 * @date 2014-7-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param userid
	 * @param authors
	 * @param year
	 * @return
	 */
	Map<String, Object> getPatentList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year);

	/**
	 * 学术交流列表
	 * @author xian.zf
	 * @date 2014-7-25
	 * @param curpage
	 * @param pagesize
	 * @param type
	 * @param time
	 * @param userid
	 * @return
	 */
	Map<String, Object> getCommunicationList(Integer curpage, Integer pagesize,
			String type, String time, String name, String userid, Boolean eflag);

	/**
	 * 获取用户所有登记或关联的成果
	 * @author xian.zf
	 * @date 2014-7-25
	 * @param userid
	 * @return
	 */
	Map<String, Object> getUserAllAch(String userid, String tname, String relid, String rename);

	/**
	 * 获取字典
	 * @author xian.zf
	 * @date 2014-7-25
	 * @param datacode
	 * @param classcode
	 * @return
	 */
	List<Record> getDatainput(String datacode, String classcode);

	/**
	 * 项目列表
	 * @author xian.zf
	 * @date 2014-7-28
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param principal
	 * @param year
	 * @param userid
	 * @return
	 */
	Map<String, Object> getProjectList(Integer curpage, Integer pagesize, String name,
			String principal, String year, String userid, String status);

	/**
	 * 开放基金
	 * @author xian.zf
	 * @date 2014-7-28
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param title
	 * @param year
	 * @param userid
	 * @return
	 */
	Map<String, Object> getFundList(Integer curpage, Integer pagesize, String name,
			String title, String year, String userid);

	/**
	 * 管理员--实验室主任列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getLabChiefList(Integer curpage, Integer pagesize, String name);

	/**
	 * 获取学术委员会列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param tenure
	 * @param titles
	 * @return
	 */
	Map<String, Object> getAcaComList(Integer curpage, Integer pagesize, String name,
			String tenure, String titles);

	/**
	 * 获取关联的所有研究团队
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param resid
	 * @return
	 */
	Map<String, Object> getRelTeam(String resid);

	/**
	 * 管理员--研究方向列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getResDirList(Integer curpage, Integer pagesize, String name);

	/**
	 * 管理员--研究团队列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getTeamList(Integer curpage, Integer pagesize, String name, String userid);

	/**
	 *  管理员--设备列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param number
	 * @param dutyman
	 * @param manager
	 * @return
	 */
	Map<String, Object> getEquipmentList(Integer curpage, Integer pagesize, String name,
			String number, String dutyman, String manager);

	/**
	 * 管理员--设备使用列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @param curpage
	 * @param pagesize
	 * @param equid
	 * @param equname
	 * @param username
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	Map<String, Object> getEquipmentUseList(Integer curpage, Integer pagesize,
			String equid, String equname, String username, String begintime,
			String endtime);

	/**
	 * 管理员--新闻列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @param time
	 * @return
	 */
	Map<String, Object> getNewsList(Integer curpage, Integer pagesize, String title,
			String time, Boolean eflag);

	/**
	 * 管理员--年报列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @param year
	 * @return
	 */
	Map<String, Object> getAnnualReportList(Integer curpage, Integer pagesize,
			String title, String year);

	/**
	 * 管理员--学术海报列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @param dutyman
	 * @param year
	 * @return
	 */
	Map<String, Object> getPosterList(Integer curpage, Integer pagesize, String title,
			String dutyman, String year);

	/**
	 * 管理员--通知公告列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @param time
	 * @return
	 */
	Map<String, Object> getNoticeList(Integer curpage, Integer pagesize, String title,
			String time, Boolean eflag);

	/**
	 * 管理员--规章制度列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @return
	 */
	Map<String, Object> getRegulationsList(Integer curpage, Integer pagesize, String title);

	/**
	 * 管理员--学术海报---成果列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param tkey
	 * @return
	 */
	List<Record> getAchByTkey(String tkey);

	/**
	 * 管理员--用户管理列表
	 * @author xian.zf
	 * @date 2014-8-1
	 * @param name
	 * @param roleid
	 * @return
	 */
	Map<String, Object> getAllUserList(Integer curpage, Integer pagesize, String name, String roleid);

	/**
	 *  管理员--最新成果列表
	 * @author xian.zf
	 * @date 2014-8-5
	 * @param curpage
	 * @param pagesize
	 * @param tkey
	 * @param name
	 * @return
	 */
	Map<String, Object> getIndexAchList(Integer curpage, Integer pagesize, String tkey,
			String name);

	/**
	 * 首页----实验室概况----实验室主任
	 * @author xian.zf
	 * @date 2014-8-8
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getIndexChiList(Integer curpage, Integer pagesize, String name, boolean flag);

	/**
	 * 首页----实验室概况----用户管理列表
	 * @author xian.zf
	 * @date 2014-8-8
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getIndexUserList(Integer curpage, Integer pagesize, String name, boolean flag);

	/**
	 * 首页----搜索
	 * @author xian.zf
	 * @date 2014-8-22
	 * @param content
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	Map<String, Object> getSearchList(String content, Integer curpage, Integer pagesize);

	/**
	 * 管理员----招聘信息列表
	 * @author xian.zf
	 * @date 2014-8-22
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @return
	 */
	Map<String, Object> getRecruitList(Integer curpage, Integer pagesize, String title, String type,boolean flag);

	/**
	 * 管理员--成果展示列表
	 * @author xian.zf
	 * @date 2014-9-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getSoftwareList(Integer curpage, Integer pagesize, String name);

	/**
	 * 管理员--成果展示列表
	 * @author xian.zf
	 * @date 2014-9-25
	 * @param curpage
	 * @param pagesize
	 * @param time
	 * @return
	 */
	Map<String, Object> getBackupsList(Integer curpage, Integer pagesize, String time);

	/**
	 * 管理员--首页图片 列表
	 * @author xian.zf
	 * @date 2014-9-25
	 * @param curpage
	 * @param pagesize
	 * @param describe
	 * @return
	 */
	Map<String, Object> getIndexPicList(Integer curpage, Integer pagesize, String describe);

	/**
	 * 管理员-开放基金附件列表
	 * @author xian.zf
	 * @date 2015-9-8
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @param type
	 * @return
	 */
	Map<String, Object> getFundfileList(Integer curpage, Integer pagesize);

	/**
	 * 管理员-相关网站挂接列表
	 * @author xian.zf
	 * @date 2015-9-8
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	Map<String, Object> getRelatedlinkList(Integer curpage, Integer pagesize, String name);

	/**
	 * 获取所有关联项目
	 * @author xian.zf
	 * @date 2015-9-9
	 * @param tname
	 * @param achid
	 * @param rename
	 * @return
	 */
	Map<String, Object> getAllProjectList(String tname, String achid, String rename);


}
