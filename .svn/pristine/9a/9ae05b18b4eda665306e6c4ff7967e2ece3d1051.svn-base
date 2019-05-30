package app.znkj.controller;

import java.util.List;

import org.joda.time.DateTime;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.config.SessionInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 详细
 * @author xian.zf
 *
 * @date 2014-7-23
 */
@Before(SessionInterceptor.class)
public class DetailedController extends Controller{

	/**
	 * 根据id获取期刊论文详情
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public void getPerArtById() {
		Record model = Db.findById(Tname.TABLE_PERARTICLE, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		renderJson(model);
	}
	
	/**
	 * 根据id获取会议论文详情
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public String getConArtById() {
		Record model = Db.findById(Tname.TABLE_CONARTICLE, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		renderJson(model);
		return null;
	}
	
	/**
	 * 根据id获取论文详情
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public String getThesisById() {
		Record model = Db.findById(Tname.TABLE_THESIS, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		//作者名称
		String authorsname = "";
		if(model.getStr("authors")!=null && !"".equals(model.getStr("authors"))){
			String authors = model.getStr("authors");
			authors = authors.indexOf(";")>0?authors.replaceAll(";", ","):authors;
			authorsname = getUserNameByIds(authors);
		}
		model.set("authorsname", authorsname);
		renderJson(model);
		return null;
	}
	
	/**
	 * 根据id获取专著详情
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: getTreatiseById 
	 * @return String 
	 * @throws Excetion
	 */
	public String getTreatiseById() {
		Record model = Db.findById(Tname.TABLE_TREATISE, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		} 
		model.set("url", model.get("pic")==null?"":model.get("pic"));
		renderJson(model);
		return null;
	}
	
	
	
	/**
	 * 根据id获取获奖详情
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public String getAwardById() {
		Record model = Db.findById(Tname.TABLE_AWARD, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		//完成人
/*		String authorsname = "";
		if(model.getStr("authors")!=null && !"".equals(model.getStr("authors"))){
			String authors = model.getStr("authors");
			authors = authors.indexOf(";")>0?authors.replaceAll(";", ","):authors;
			authorsname = getUserNameByIds(authors);
		}
		model.set("authorsname", authorsname);*/
		renderJson(model);
		return null;
	}
	
	/**
	 * 根据id获取专利详情
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public String getPatentById() {
		Record model = Db.findById(Tname.TABLE_PATENT, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		//完成人
/*		String authorsname = "";
		if(model.getStr("authors")!=null && !"".equals(model.getStr("authors"))){
			String authors = model.getStr("authors");
			authors = authors.indexOf(";")>0?authors.replaceAll(";", ","):authors;
			authorsname = getUserNameByIds(authors);
		}
		model.set("authorsname", authorsname);*/
		renderJson(model);
		return null;
	}
	
	/**
	 * 根据id获取学术交流详情
	 * @author xian.zf
	 * @date 2014-7-28
	 */
	public void getCommunicationById(){
		Record model = Db.findById(Tname.TABLE_COMMUNICATION, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		renderJson(model);
	}
	
	/**
	 * 根据id获取科研项目详情
	 * @author xian.zf
	 * @date 2014-7-28
	 */
	public void getProjectById(){
		Record model = Db.findById(Tname.TABLE_PROJECT, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		//TODO 关联的成果
		
		renderJson(model);
	}
	
	/**
	 * 根据id获取开放基金详情
	 * @author xian.zf
	 * @date 2014-7-28
	 */
	public void getFundOpenById(){
		Record model = Db.findById(Tname.TABLE_FUNDOPEN, getPara("id"));
		int userid = Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString());
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null 
				|| (0==manageRole && model.getInt("register")!=userid)){
			render(Constant.URL_INFO);
		}
		renderJson(model);
	}
	
	/**
	 * 管理员----获取实验室详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getLabInfo() {
		Record model = Db.findById(Tname.TABLE_LABORATORY, 1);
		renderJson(model);
	}
	
	/**
	 * 管理员----获取实验室主任详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getLabChiefInfo() {
		Record model = Db.findById(Tname.TABLE_LAB_CHIEF,  getPara("id"));
		
		renderJson(model);
	}
	
	/**
	 * 管理员----获取学术委员会信息
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getAcaComInfo() {
		Record model = Db.findById(Tname.TABLE_ACADEMIC_COM,  getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 管理员----获取研究方向信息
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getResDirInfo() {
		Record model = Db.findById(Tname.TABLE_RESEARCH,  getPara("id"));
		// 已关联的团队
		List<Record> resTemList = Db.find("SELECT t.* FROM research_team r, team t WHERE r.id=r.teamid AND r.resid="+getPara("id"));
		model.set("teamlist", resTemList);
		renderJson(model);
	}
	
	/**
	 * 管理员----获取研究团队信息
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getTeamInfo() {
		Record model = Db.findById(Tname.TABLE_TEAM,  getPara("id"));
		// 已关联的科研人员
		List<Record> userList = Db.find("SELECT u.id, u.name, u.gender, t.duty FROM team_user t, user u WHERE t.userid=u.id AND t.teamid="+getPara("id"));
		model.set("userlist", userList);
		renderJson(model);
	}
	
	/**
	 * 管理员----获取设备详细信息
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getEquipmentInfo() {
		Record model = Db.findById(Tname.TABLE_EQUIPMENT,  getPara("id"));
		// 当前使用人
		String nowTime = new DateTime().toString("yyyy-MM-dd HH:mm"),
			   sql = "SELECT t.id, t.begintime, t.endtime,"
				   +" u.`name` AS username, u.id AS uid"
				   +" FROM equipment_use t, equipment e, `user` u"
				   +" WHERE t.userid=u.id AND t.equid=e.id AND t.equid="+ getPara("id") 
				   +" AND t.begintime<='" + nowTime + "'"
				   +" AND t.endtime>='" + nowTime + "'";
		Record equUseModel = Db.findFirst(sql);
		model.set("user", equUseModel==null?"":equUseModel.getStr("username"));
		renderJson(model);
	}
	
	/**
	 * 管理员----获取设备使用详细信息
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getEquipmentUseInfo() {
		String sql = "SELECT t.id, t.begintime, t.endtime ,"
				   +" u.`name` AS username, u.id AS uid,"
				   +" e.`name` AS equname, e.id AS eid"
				   +" FROM equipment_use t, equipment e, `user` u"
				   +" WHERE t.userid=u.id AND t.equid=e.id AND t.id="+ getPara("id");
		Record model = Db.findFirst(sql);
		renderJson(model);
	}
	
	/**
	 * 根据id获取新闻详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getNewsByid() {
		Record model = Db.findById(Tname.TABLE_NEWS, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据id获取年报详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getAnnualReportByid() {
		Record model = Db.findById(Tname.TABLE_ANNREP, getPara("id"));
		renderJson(model);
	}
	
	/**
	 *  根据id获取学术海报详情
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void getPosterInfo() {
		Record model = Db.findById(Tname.TABLE_POSTER,  getPara("id"));
		// 已关联的成果
		List<Record> posAchList = Db.find("SELECT * FROM poster_achievement p WHERE p.relid="+getPara("id"));
		model.set("posachList", posAchList);
		renderJson(model);
	}
	
	/**
	 * 根据id获取通知公告详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getNoticeByid() {
		Record model = Db.findById(Tname.TABLE_NOTICE, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据id获取规章制度详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getRegulationsByid() {
		Record model = Db.findById(Tname.TABLE_REGULATIONS, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据id获取招聘详情
	 * @author xian.zf
	 * @date 2014-8-21
	 */
	public void getRecruitByid() {
		Record model = Db.findById(Tname.TABLE_RECRUIT, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据id获取最新成果详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getIndexAchByid() {
		Record model = Db.findById(Tname.TABLE_INDEXACH, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 管理员--获取用户权限设置
	 * @author xian.zf
	 * @date 2014-8-1
	 */
	public void getUserRoleInfo() {
		Record model = Db.findById(Tname.TABLE_USER, getPara("id"));
		int manageRole = Integer.valueOf(getSession().getAttribute(Constant.MANAGEROLE).toString());
		if(model == null || manageRole != 1)
			render(Constant.URL_INFO);
		model.set("urolelist", Db.find("SELECT u.roleid FROM user_role u WHERE u.userid=" + getPara("id")));
		model.set("password", "");
		renderJson(model);
	}
	
	
	/**
	 * 根据id获取实列详情
	 * @author xian.zf
	 * @date 2014-7-24
	 */
	public void getModelByid() {
		Record model = Db.findById(getPara("tkey"), getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据用户id串，获取用户的名字
	 * @author xian.zf
	 * @date 2014-7-23
	 * @param ids
	 * @return
	 */
	private String getUserNameByIds(String ids) {
		String names = "";
		List<Record> rlist = Db.find("select * from "+ Tname.TABLE_USER + " u where u.id in ("+ids+")");
		for(Record rec:rlist)
			names += rec.getStr("name") + "、 ";
		int len = names.length();
		return len>2 ? names.substring(0, len-2): "";
	}
	
	/**
	 * 根据id获取软件平台详情
	 * @author xian.zf
	 * @date 2014-9-24
	 */
	public void getSoftwareByid() {
		Record model = Db.findById(Tname.SOFTWARE, getPara("id"));
		renderJson(model);
	}
	
	/**
	 * 根据id获取软件平台详情
	 * @author xian.zf
	 * @date 2014-9-24
	 */
	public void getIndexPicByid() {
		Record model = Db.findById(Tname.INDEX_PIC, getPara("id"));
		renderJson(model);
	}
}
