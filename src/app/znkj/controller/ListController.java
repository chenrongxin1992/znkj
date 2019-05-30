package app.znkj.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.znkj.common.Constant;
import app.znkj.config.SessionInterceptor;
import app.znkj.service.ExcelService;
import app.znkj.service.ListService;
import app.znkj.service.impl.ExcelServiceImpl;
import app.znkj.service.impl.ListServiceImpl;
import app.znkj.util.CreateForm;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;

/**
 * 获取列表
 * @author xian.zf
 *
 * @date 2014-7-19
 */
@Before(SessionInterceptor.class)
public class ListController extends Controller{

	private ListService listService = new ListServiceImpl();
	
	private ExcelService excelService = new ExcelServiceImpl();
	
	/**
	 * 科研人员--期刊论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getPerArticleList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getPerArticleList(curpage, pagesize, name, userid, authors, year));
	}
	
	/**
	 * 科研人员--会议论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getConArticleList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getConArticleList(curpage, pagesize, name, userid, authors, year));
	}
	
	/**
	 * 科研人员--论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getThesisList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getThesisList(curpage, pagesize, name, userid, authors, year));
	}
	
	/**
	 * 科研人员--专著列表
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getTreatiseList 
	 * @return void 
	 * @throws Excetion
	 */
	public void getTreatiseList() throws Exception{
		Integer curpage = Integer.valueOf(getPara("curpage")==null?"1":getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize")==null?"15":getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getTreatiseList(curpage, pagesize, name, userid, authors, year));
	}
	
	
	/**
	 * 科研人员--获奖列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getAwardList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getAwardList(curpage, pagesize, name, userid, authors, year));
	}
	
	/**
	 * 科研人员--专利列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getPatentList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getPatentList(curpage, pagesize, name, userid, authors, year));
	}
	
	/**
	 * 科研人员--学术交流列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getCommunicationList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String type = CreateForm.creSinFormAndByts(getPara("type"));
		String time = CreateForm.creSinFormAndByts(getPara("time"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getCommunicationList(curpage, pagesize, type, time, name,userid, null));
	}
	
	/**
	 * 科研人员--科研项目列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getProjectList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String principal = CreateForm.creSinFormAndByts(getPara("principal"));//负责人
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态
		
		renderJson(listService.getProjectList(curpage, pagesize, name, principal, year,userid, status));
	}
	
	/**
	 * 科研人员--开放基金列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getFundList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String name = CreateForm.creSinFormAndByts(getPara("name"));//姓名
		String title = CreateForm.creSinFormAndByts(getPara("title"));//题目
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		
		renderJson(listService.getFundList(curpage, pagesize, name, title, year,userid));
	}
	
	/**
	 * 获取所有科研人员
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getAllResUser() throws Exception{
		renderJson(Db.find("SELECT u.id,u.`name` FROM `user` u WHERE u.id IN (SELECT r.userid FROM user_role r WHERE r.roleid=2)"));
	}
	
	/**
	 * 获取用户所有登记或关联的成果
	 * @author xian.zf
	 * @date 2014-7-25
	 * @throws Exception
	 */
	public void getUserAllAch() throws Exception {
		String tname = getPara("tkey");
		String relid = getPara("relid");
		String rename = getPara("rename");
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		renderJson(listService.getUserAllAch(userid, tname, relid, rename));
	}
	
	/**
	 * 获取所有关联项目
	 * @author xian.zf
	 * @date 2015-9-9
	 */
	public void getAllProjectList() throws Exception {
		String tname = getPara("tkey");
		String achid = getPara("achid");
		String rename = getPara("rename");
		renderJson(listService.getAllProjectList(tname, achid, rename));
		
	}
	
	/**
	 * 获取字典
	 * @author xian.zf
	 * @date 2014-7-25
	 */
	public void getDatainput() {
		String datacode = getPara("datacode");
		String classcode = getPara("classcode");
		renderJson(listService.getDatainput(datacode, classcode));
	}
	
	
	/**
	 * 管理员--实验室主任列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getLabChiefList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));//姓名
		renderJson(listService.getLabChiefList(curpage, pagesize, name));
	}
	
	/**
	 * 管理员--学术委员会列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getAcaComList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//姓名
		String tenure = CreateForm.creSinFormAndByts(getPara("tenure"));//任职
		String titles = CreateForm.creSinFormAndByts(getPara("titles"));//职称
		renderJson(listService.getAcaComList(curpage, pagesize, name, tenure, titles));
	}
	
	/**
	 * 获取关联的所有研究团队
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getRelTeam() throws Exception {
		String resid = getPara("resid");
		renderJson(listService.getRelTeam(resid));
	}
	
	/**
	 * 管理员--研究方向列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getResDirList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//名称
		renderJson(listService.getResDirList(curpage, pagesize, name));
	}
	
	/**
	 * 管理员--研究团队列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getTeamList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//名称
		renderJson(listService.getTeamList(curpage, pagesize, name, null));
	}
	
	/**
	 * 管理员--设备列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getEquipmentList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));		//名称
		String number = CreateForm.creSinFormAndByts(getPara("number"));	//编号
		String dutyman = CreateForm.creSinFormAndByts(getPara("dutyman"));	//负责人
		String manager = CreateForm.creSinFormAndByts(getPara("manager"));	//管理员
		renderJson(listService.getEquipmentList(curpage, pagesize, name, number, dutyman, manager));
	}
	
	/**
	 * 管理员-- 所有设备列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @throws Exception
	 */
	public void getAllEquList() throws Exception {
		renderJson(Db.find("select * from equipment e"));
	}
	
	/**
	 * 管理员--设备使用列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getEquipmentUseList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String equid = CreateForm.creSinFormAndByts(getPara("equid"));			//设备主键
		String equname = CreateForm.creSinFormAndByts(getPara("equname"));		//设备名称
		String username = CreateForm.creSinFormAndByts(getPara("username"));	//使用人名
		String begintime = CreateForm.creSinFormAndByts(getPara("begintime"));	//开始时间
		String endtime = CreateForm.creSinFormAndByts(getPara("endtime"));		//管理员
		renderJson(listService.getEquipmentUseList(curpage, pagesize, equid, equname, username, begintime, endtime));
	}
	
	/**
	 * 管理员--新闻列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getNewsList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String title = CreateForm.creSinFormAndByts(getPara("title"));		
		String time = CreateForm.creSinFormAndByts(getPara("time"));		
		renderJson(listService.getNewsList(curpage, pagesize, title, time, null));
	}
	
	/**
	 * 管理员--年报列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getAnnualReportList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String title = CreateForm.creSinFormAndByts(getPara("title"));		
		String year = CreateForm.creSinFormAndByts(getPara("year"));		
		renderJson(listService.getAnnualReportList(curpage, pagesize, title, year));
	}
	
	/**
	 * 管理员--学术海报列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getPosterList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));	
		String dutyman = CreateForm.creSinFormAndByts(getPara("dutyman"));	
		String year = CreateForm.creSinFormAndByts(getPara("year"));		
		renderJson(listService.getPosterList(curpage, pagesize, name, dutyman, year));
	}
	
	/**
	 * 管理员--通知公告列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getNoticeList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String title = CreateForm.creSinFormAndByts(getPara("title"));	
		String time = CreateForm.creSinFormAndByts(getPara("time"));	
		renderJson(listService.getNoticeList(curpage, pagesize, title, time, null));
	}
	
	/**
	 * 管理员--规章制度列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getRegulationList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String title = CreateForm.creSinFormAndByts(getPara("title"));	
		renderJson(listService.getRegulationsList(curpage, pagesize, title));
	}
	
	/**
	 * 管理员--学术海报---成果列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @throws Exception
	 */
	public void getAchByTkey() throws Exception {
		String tkey = CreateForm.creSinFormAndByts(getPara("tkey"));
		renderJson(listService.getAchByTkey(tkey));
	}
	
	
	/**
	 *  管理员--软件平台列表
	 * @author xian.zf
	 * @date 2014-9-24
	 * @throws Exception
	 */
	public void getSoftwareList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		renderJson(listService.getSoftwareList(curpage, pagesize, name));
	}	
	
	/**
	 *  管理员--软件平台列表
	 * @author xian.zf
	 * @date 2014-9-24
	 * @throws Exception
	 */
	public void getIndexPicList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String describe = CreateForm.creSinFormAndByts(getPara("describe"));//描述
		renderJson(listService.getIndexPicList(curpage, pagesize, describe));
	}
	
	/**
	 *  管理员--软件平台列表
	 * @author xian.zf
	 * @date 2014-9-24
	 * @throws Exception
	 */
	public void getBackupsList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String time = CreateForm.creSinFormAndByts(getPara("time"));//名称
		renderJson(listService.getBackupsList(curpage, pagesize, time));
	}
	
	/**
	 * 管理员--用户管理列表
	 * @author xian.zf
	 * @date 2014-8-1
	 * @throws Exception
	 */
	public void getAllUserList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String roleid = CreateForm.creSinFormAndByts(getPara("roleid"));
		renderJson(listService.getAllUserList(curpage, pagesize, name, roleid));
	}
	
	/**
	 * 管理员--用户管理列表---权限列表
	 * @author xian.zf
	 * @date 2014-8-1
	 * @throws Rxception
	 */
	public void getAllRoleList() throws Exception {
		renderJson(Db.find("SELECT r.id, r.`name` FROM role r "));
	}
	
	/**
	 * 管理员--期刊论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabPerArticleList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getPerArticleList(curpage, pagesize, name, null, authors, year));
	}
	
	/**
	 * 管理员--会议论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabConArticleList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getConArticleList(curpage, pagesize, name, null, authors, year));
	}
	
	/**
	 * 管理员--论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabThesisList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getThesisList(curpage, pagesize, name, null, authors, year));
	}
	
	/**
	 * 管理员--专著列表
	 * @author deng.gzh 
	 * @date 2014-9-13 
	 * @Title: getLabThesisList 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabTreatiseList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getTreatiseList(curpage, pagesize, name, null, authors, year));
	}
	
	
	/**
	 * 管理员--获奖列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabAwardList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getAwardList(curpage, pagesize, name, null, authors, year));
	}
	
	/**
	 * 管理员--专利列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabPatentList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));
		
		renderJson(listService.getPatentList(curpage, pagesize, name, null, authors, year));
	}
	
	/**
	 * 管理员--学术交流列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getLabCommunicationList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String type = CreateForm.creSinFormAndByts(getPara("type"));
		String time = CreateForm.creSinFormAndByts(getPara("time"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		
		renderJson(listService.getCommunicationList(curpage, pagesize, type, time, name,null, null));
	}
	
	/**
	 * 管理员--科研项目列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getLabProjectList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String principal = CreateForm.creSinFormAndByts(getPara("principal"));//负责人
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态
		
		renderJson(listService.getProjectList(curpage, pagesize, name, principal, year, null, status));
	}
	
	/**
	 * 管理员--开放基金列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getLabFundList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String name = CreateForm.creSinFormAndByts(getPara("name"));//姓名
		String title = CreateForm.creSinFormAndByts(getPara("title"));//题目
		
		renderJson(listService.getFundList(curpage, pagesize, name, title, year,null));
	}
	
	/**
	 * 管理员--最新成果列表
	 * @author xian.zf
	 * @date 2014-8-5
	 * @throws Exception
	 */
	public void getIndexAchList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String tkey = CreateForm.creSinFormAndByts(getPara("tkey"));//成果--类型-表名
		String achid = CreateForm.creSinFormAndByts(getPara("achid"));//成果
		
		renderJson(listService.getIndexAchList(curpage, pagesize, tkey, achid));
	}
	
	/**
	 * 科研人员--研究团队列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getUserTeamList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		renderJson(listService.getTeamList(curpage, pagesize, name, userid));
	}
	
	/**
	 * 管理员-招聘信息列表
	 * @author xian.zf
	 * @date 2014-8-22
	 * @throws Exception
	 */
	public void getRecruitList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String title = CreateForm.creSinFormAndByts(getPara("title"));
		String type = CreateForm.creSinFormAndByts(getPara("type"));
		
//		renderJson(listService.getRecruitList(curpage, pagesize, title, type));
		renderJson(listService.getRecruitList(curpage, pagesize, title, type,false));
	}
	
	
	/**
	 * 管理员-开放基金附件列表
	 * @author xian.zf
	 * @date 2015-9-8
	 * @throws Exception
	 */
	public void getFundfileList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		renderJson(listService.getFundfileList(curpage, pagesize));
	}
	
	/**
	 * 管理员-相关网站挂接列表
	 * @author xian.zf
	 * @date 2015-9-8
	 * @throws Exception
	 */
	public void getRelatedlinkList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));
		renderJson(listService.getRelatedlinkList(curpage, pagesize, name));
	}
	
	
	
	
	/**
	 * 获取当前登陆科研人员
	 * @author xian.zf
	 * @date 2014-7-22
	 * @throws Exception
	 */
	public void getResUser() throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", getSession().getAttribute(Constant.SESSION_USERID));
		map.put("name", getSession().getAttribute(Constant.SESSION_USERNAME));
		list.add(map);
		renderJson(list);
	}
	
	/** 生成excel ------开始 **/
	
	/**
	 * 科研人员--科研项目excel列表
	 * @author xian.zf
	 * @date 2014-9-11
	 * @throws Exception
	 */
	public void getProjectListExcel() throws Exception {
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String principal = CreateForm.creSinFormAndByts(getPara("principal"));//负责人
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
//		Map<String,Object> map =excelService.getProjectListExcel(name,"科研项目", principal, year,userid, status);
		renderJson(excelService.getProjectListExcel(name,"Project", principal, year,userid, status));
//		getExcel("/public/attachment/excel/","科研项目"+".xls");
	}
	
	/**
	 * 科研人员--期刊论文excel列表
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getPerarticle 
	 * @return void 
	 * @throws Excetion
	 */
	public void getPerarticle() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getPerarticle(name,"JournalArticles", authors, year,userid, status));	
	}
	
	/**
	 * 科研管理--会议论文excel
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getConarticle 
	 * @return void 
	 * @throws Excetion
	 */
	public void getConarticle() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getConarticle(name, "ConferenceArticles",authors, year,userid, status));	
	}
	
	/**
	 * 科研管理--学位论文excel列表
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getThesisa 
	 * @return void 
	 * @throws Excetion
	 */
	public void getThesisa() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getThesisa(name,"Thesis", authors, year,userid, status));
	}
	
	
	/**
	 * 科研管理--专著excel
	 * @author deng.gzh 
	 * @date 2014-9-13 
	 * @Title: getTreatise 
	 * @return void 
	 * @throws Excetion
	 */
	public void getTreatiseexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString(); 
		renderJson(excelService.getTreatiseexcel(name,"TreatiseArticles", authors, year,userid));
	}
	
	
	
	/**
	 * 科研管理--获奖登记excel
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getAwardexcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getAwardexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getAwardexcel(name,"Award", authors, year,userid, status));
	}
	
	/**
	 * 科研管理--专利登记excel列表
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getPatentexcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getPatentexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getPatentexcel(name,"Patent", authors, year,userid, status));
	}
	
	
	/**
	 * 实验室--科研项目excel列表
	 * @author xian.zf
	 * @date 2014-9-11
	 * @throws Exception
	 */
	public void getLabProjectListExcel() throws Exception {
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String principal = CreateForm.creSinFormAndByts(getPara("principal"));//负责人
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态
		
		renderJson(excelService.getProjectListExcel(name, "Project",principal, year, null, status));
	}
	
	
	/**
	 * 实验室管理--期刊论文excel列表
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getPerarticle 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabPerarticle() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getPerarticle(name,"JournalArticles", authors, year,null, status));	
	}
	
	/**
	 * 实验室管理--会议论文excel
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getConarticle 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabConarticle() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getConarticle(name, "ConferenceArticles",authors, year,null, status));	
	}
	
	/**
	 * 实验室管理--学位论文excel列表
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getThesisa 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabThesisa() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getThesisa(name,"Thesis", authors, year,null, status));
	}
	
	
	
	/**
	 * 实验室管理--专著excel
	 * @author deng.gzh 
	 * @date 2014-9-13 
	 * @Title: getTreatiseexcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabTreatiseexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString(); 
		renderJson(excelService.getTreatiseexcel(name,"TreatiseArticles", authors, year,null));
	}
	
	/**
	 * 实验室管理--获奖登记excel
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getAwardexcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabAwardexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getAwardexcel(name,"Award", authors, year,null, status));
	}
	
	
	/**
	 * 实验室管理--专利登记excel列表
	 * @author deng.gzh 
	 * @date 2014-9-11 
	 * @Title: getPatentexcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getLabPatentexcel() throws Exception{
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String authors = CreateForm.creSinFormAndByts(getPara("authors"));//作者
		String name = CreateForm.creSinFormAndByts(getPara("name"));//名称
//		String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态 
		renderJson(excelService.getPatentexcel(name,"Patent", authors, year,null, status));
	}
	
	/**
	 * 下载excel路由
	 * @author deng.gzh 
	 * @date 2014-9-13 
	 * @Title: getExcel 
	 * @return void 
	 * @throws Excetion
	 */
	public void getExcel(String path ,String Name) throws Exception{ 
//		System.out.println(Name);
		String znkjPath = this.getClass().getClassLoader().getResource("").getPath()
				.replace("%20", " ")
				.replace("/WEB-INF/classes/", "");
		path = znkjPath + path;	
//		File file = new File(path); 
		InputStream is = new BufferedInputStream(new FileInputStream(path));
		byte[] bs = new byte[is.available()];
		is.read(bs);
		is.close();
		OutputStream os = new BufferedOutputStream(getResponse().getOutputStream());
		os.write(bs);
		os.flush();
		os.close();
//		redirect(path+Name);
	}
	
	
	/** 生成excel ------结束 **/
}
