package app.znkj.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import app.znkj.common.Tname;
import app.znkj.service.ListService;
import app.znkj.util.CheckFormat;
import app.znkj.util.CreateForm;
import app.znkj.util.PageHelp;

public class ListServiceImpl implements ListService{

	/**
	 * 期刊论文列表
	 * @author xian.zf
	 * @date 2014-7-22
	 * @param pageNumber
	 * @param pageSize
	 * @param name
	 * @param userid
	 * @return
	 */
	@Override
	public Map<String, Object> getPerArticleList(Integer curpage, Integer pagesize, 
			String name, String userid, String authors, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_PERARTICLE + " t where 1=1";
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ;
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.relevance like '" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + "' )";
		}
		
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'";
		
		if(authors != null && !"".equals(authors))
			consql += " and t.`authors` like '%" + authors +"%'" ;
		
		consql += " order by t.publishyear desc, t.name asc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

	/**
	 * 显示作者名
	 * @author xian.zf
	 * @date 2014-7-23
	 * @param map
	 * @return
	 */
	private Map<String, Object> showAuthors(Map<String, Object> map){
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
			//作者名称
			rec.set("authorsname","");
			rec.set("eauthorsname", "");
			String[] names ;
			if(rec.getStr("authors")!=null && !"".equals(rec.getStr("authors"))){
				String authors = rec.getStr("authors");
				authors = authors.indexOf(";")>0?authors.replaceAll(";", ","):authors;
				names = getUserNameByIds(authors);
				rec.set("authorsname", names[0]);
				rec.set("eauthorsname", names[1]);
			}
		}
		map.put("tbody", list);
		return map;
	}
	
	/**
	 * 根据用户id串，获取用户的名字
	 * @author xian.zf
	 * @date 2014-7-23
	 * @param ids
	 * @return
	 */
	private String[] getUserNameByIds(String ids) {
		String names = "", enames = "";
		List<Record> rlist = Db.find("select * from "+ Tname.TABLE_USER + " u where u.id in ("+ids+")");
		for(Record rec:rlist){
			names += rec.getStr("name") + "、 ";
			enames += rec.getStr("ename") + "、 ";
		}
		int len = names.length();
		names = len>2 ? names.substring(0, len-2): "";
		len = enames.length();
		enames = len>2 ? enames.substring(0, len-2): "";
		String[] namestr = {names, enames};
		return namestr;
	}
	
	/**
	 * 根据用户名，获取搜索条件
	 * @author xian.zf
	 * @date 2014-7-23
	 * @param ids
	 * @return
	 */
	private String getUserIdsByName(String name, String tkey) {
		String idsql = "";
		List<Record> rlist = Db.find("select * from "+ Tname.TABLE_USER + " u where u.name like '%"+name+"%'");
		for(Record rec:rlist){
			idsql += " or "+ tkey + " = '" + rec.getLong("id") + "'";
			idsql += " or "+ tkey + " like '" + rec.getLong("id") + ";%'";
			idsql += " or "+ tkey + " like '%;" + rec.getLong("id") + ";%'";
			idsql += " or "+ tkey + " like '%;" + rec.getLong("id") + "'";
		}
		int len = idsql.length();
		idsql = len>3 ? ("(" + idsql.substring(3) + ")") : ("(" + tkey + "=0)");
		return idsql;
	}
	
	/**
	 * 根据Page获取前台要求的数据格式
	 * @author xian.zf
	 * @date 2014-7-22
	 * @param curpage
	 * @param pagesize
	 * @param bodysql
	 * @param consql
	 * @return
	 */
	private Map<String, Object> getMapByPage(int curpage, int pagesize, String bodysql, String consql, String userid) {
		Page<Record> page = Db.paginate(curpage, pagesize, bodysql, consql);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageArgument = new HashMap<String, Object>();
		pageArgument.put("curpage", page.getPageNumber());
		pageArgument.put("pagesize", page.getPageSize());
		pageArgument.put("pagecount", page.getTotalPage());
		pageArgument.put("total", page.getTotalRow());
		Long no = PageHelp.getStatrRow(Long.valueOf(pagesize),
				Long.valueOf(curpage), Long.valueOf(page.getTotalPage()));
		List<Record> list = page.getList();
		for(Record rec:list){
			//序号
			rec.set("num", ++no);
			//编辑权限
			if(CheckFormat.isNumber(userid) 
					&& userid.equals(rec.getInt("register").toString()))
				rec.set("isedit", 1);
			else
				rec.set("isedit", 0);
			rec = CreateForm.disposeRecord(rec, false);
		}
		resultMap.put("tbody", list);
		resultMap.put("pageArgument", pageArgument);
		return resultMap;
	}

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
	@Override
	public Map<String, Object> getConArticleList(Integer curpage,
			Integer pagesize, String name, String userid, String authors,
			String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_CONARTICLE + " t where 1=1";
//		if(userid != null && !"".equals(userid))
//			consql += " and t.register=" + userid;
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ;
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.register like '" + userid + ";%'";
			consql += " or t.register like '%;" + userid + ";%'";
			consql += " or t.register like '%;" + userid + "' )";
		}
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'";
		if(authors != null && !"".equals(authors))
			consql += " and t.`authors` like '%" + authors +"%'" ;
		consql += " order by t.publishyear desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

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
	@Override
	public Map<String, Object> getThesisList(Integer curpage, Integer pagesize,
			String name, String userid, String authors, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_THESIS + " t where 1=1";
//		if(userid != null && !"".equals(userid))
//			consql += " and t.register=" + userid;
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ;
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.relevance like '" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + "' )";
		}
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'";
		if(authors != null && !"".equals(authors))
			consql += " and " + getUserIdsByName(authors, " t.`authors`");
		
		consql += " order by t.publishyear desc";
		return showAuthors(getMapByPage(curpage, pagesize, bodysql, consql, userid));
	}
	
	/**
	 * 科研管理--专著列表
	 */
	public Map<String, Object> getTreatiseList(Integer curpage, Integer pagesize, String name,
			String userid, String authors, String year){
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_TREATISE + " t where 1=1";
//		if(userid != null && !"".equals(userid))
//			consql += " and t.register=" + userid;
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ; 
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.relevance like '" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + "' )";
		}
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%' )";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'";
		if(authors != null && !"".equals(authors))
			consql += " and " + getUserIdsByName(authors, " t.`authors`");
		
		consql += " order by t.publishyear desc"; 
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}
	
	

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
	@Override
	public Map<String, Object> getAwardList(Integer curpage, Integer pagesize,
			String name, String userid, String authors, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_AWARD + " t where 1=1";
//		if(userid != null && !"".equals(userid))
//			consql += " and t.register=" + userid;
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ;
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.relevance like '" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + "' )";
		}
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'";
		if(authors != null && !"".equals(authors))
			consql += " and " + getUserIdsByName(authors, " t.`authors`");
		
		consql += " order by t.year desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

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
	@Override
	public Map<String, Object> getPatentList(Integer curpage, Integer pagesize,
			String name, String userid, String authors, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_PATENT + " t where 1=1";
//		if(userid != null && !"".equals(userid))
//			consql += " and t.register=" + userid;
		if(userid != null && !"".equals(userid)){
			consql += " and (t.register=" + userid ;
			consql += " or t.relevance = '" + userid + "'";
			consql += " or t.relevance like '" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + ";%'";
			consql += " or t.relevance like '%;" + userid + "' )";
		}
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'";
		if(authors != null && !"".equals(authors))
			consql += " and " + getUserIdsByName(authors, " t.`authors`");
		
		consql += " order by t.year desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

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
	@Override
	public Map<String, Object> getCommunicationList(Integer curpage,
			Integer pagesize, String type, String time, String name, String userid, Boolean eflag) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_COMMUNICATION + " t where 1=1";
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(type != null && !"".equals(type))
			consql += " and t.type like '%" +type+ "%'";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(time != null && !"".equals(time))
			consql += " and t.year like '%" + time +"%'";
		if(eflag!=null && eflag)
			consql += " and t.eshow=1";
		consql += " order by t.time desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

	/**
	 * 获取用户所有登记或关联的成果
	 * @author xian.zf
	 * @date 2014-7-25
	 * @param userid
	 * @return
	 */
	@Override
	public Map<String, Object> getUserAllAch(String userid, String tname, String relid, String rename) {
		String[] achTkey = {Tname.TABLE_PERARTICLE, Tname.TABLE_CONARTICLE, Tname.TABLE_THESIS,
				Tname.TABLE_AWARD, Tname.TABLE_PATENT, Tname.TABLE_TREATISE},
				achTname = {"期刊论文","会议论文","论文","获奖","专利","专著"};
		Map<String, Object> tnameMap = new HashMap<String, Object>();
		for(int i=0, len = achTkey.length; i<len; i++){
			tnameMap.put(achTkey[i], achTname[i]);
		}
		List<Map<String, Object>> achList = new ArrayList<Map<String, Object>>(); 
//	    String 	consql =  " t where t.register=" + userid ;
//				consql += " or t.relevance = '" + userid + "'";
//				consql += " or t.relevance like '" + userid + ";%'";
//				consql += " or t.relevance like '%;" + userid + ";%'";
//				consql += " or t.relevance like '%;" + userid + "' ";
		String 	consql =  " t where 1=1";
		if (rename!=null && !"".equals(rename)){
			consql += " and ( t.name like '%" +rename+ "%' or t.ename like '%" +rename+ "%' )";
		}
		//已关联的成果		
		List<Record> ownAchList = new ArrayList<Record>();	
		boolean flag = false;
		int num = 1;
		if((Tname.TABLE_PROJECTACH.equals(tname) || Tname.TABLE_FUNDACH.equals(tname))
				&& CheckFormat.isNumber(relid)){
			ownAchList = Db.find("select * from "+ tname + " t where t.relid=" + relid);
			if(ownAchList.size()>0){
				flag = true;
				for(Record rec : ownAchList){
					Record model = Db.findById(rec.getStr("tname"), rec.getInt("achid"));
					if(model!=null){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("num", num++);
						map.put("tcname", tnameMap.get(rec.getStr("tname")));
						map.put("tname", rec.getStr("tname"));
						map.put("achname", model.getStr("name"));
						map.put("achid", model.getLong("id"));
						map.put("isown", 1);
						map.put("isshow", rec.getInt("isshow"));
						achList.add(map);
					}
				}
			}
		}
		
		for(int i=0, len = achTkey.length; i<len; i++){
			List<Record> modelList = Db.find("select * from "+ achTkey[i] + consql);
			for(Record rec : modelList){
				//判断是否已经存在
				if(flag){
					boolean haveFlag = false;
					for(Record ownrec:ownAchList){
						if(achTkey[i].equals(ownrec.getStr("tname"))
								&& rec.getLong("id").intValue() == ownrec.getInt("achid").intValue()){
							haveFlag = true;
							break;
						}
					}
					if(haveFlag)
						continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("num", num++);
				map.put("tcname", achTname[i]);
				map.put("tname", achTkey[i]);
				map.put("achname", rec.getStr("name"));
				map.put("achid", rec.getLong("id"));
				map.put("isown", 0);
				map.put("isshow", "1");
				achList.add(map);
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("tbody", achList);
		result.put("total", num>2?num-1:num);
		return result;
	}

	/**
	 * 获取字典
	 * @author xian.zf
	 * @date 2014-7-25
	 * @param datacode
	 * @param classcode
	 * @return
	 */
	@Override
	public List<Record> getDatainput(String datacode,
			String classcode) {
		String sql = "select t.datavalue, t.classcode from " + Tname.TABLE_DATAINPUT + " t where t.datacode='"+datacode+"'";
		if(classcode != null && !"".equals(classcode)){
			//清除多作的0，如：030000，变为 03
			if(classcode.endsWith("0")){
				boolean flag = true;
				while (flag) {
					classcode = classcode.substring(0, classcode.length()-1);
					if(!classcode.endsWith("0"))
						flag = false;
				}
			}
			sql += " and t.classcode like '"+classcode+"%'";
		}
		return Db.find(sql + " order by t.sort");
	}

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
	@Override
	public Map<String, Object> getProjectList(Integer curpage,
			Integer pagesize, String name, String principal, String year,
			String userid, String status) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_PROJECT + " t where 1=1";
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(principal != null && !"".equals(principal))
			consql += " and t.principal like '%" + principal +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'";
		
		if(status != null && !"".equals(status)){
			String nowTime = new DateTime().toString("yyyy-MM-dd");
			if("1".equals(status)){
				consql += " and t.restimebegin <= '" + nowTime +"'";
				consql += " and t.restimeend >= '" + nowTime +"'";
			}else
				consql += " and t.restimeend < '" + nowTime +"'";
		}
		consql += " order by t.restimebegin desc, t.name desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

	/**
	 * 开放基金列表
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
	@Override
	public Map<String, Object> getFundList(Integer curpage, Integer pagesize,
			String name, String title, String year, String userid) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_FUNDOPEN + " t where 1=1";
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(title != null && !"".equals(title))
			consql += " and t.title like '%" + title +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'";
		consql += " order by t.year desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, userid);
	}

	/**
	 * 管理员--实验室主任列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> getLabChiefList(Integer curpage,
			Integer pagesize, String name) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_LAB_CHIEF + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getAcaComList(Integer curpage, Integer pagesize,
			String name, String tenure, String titles) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_ACADEMIC_COM + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or t.ename like '%" +name+ "%')";
		if(tenure != null && !"".equals(tenure))
			consql += " and (t.tenure like '%" +tenure+ "%' or t.etenure like '%" +tenure+ "%')";
		if(titles != null && !"".equals(titles))
			consql += " and (t.titles like '%" +titles+ "%' or t.etitles like '%" +titles+ "%')";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 获取关联的所有研究团队
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param resid
	 * @return
	 */
	@Override
	public Map<String, Object> getRelTeam(String resid) {
		List<Record> tList = Db.find("select * from "+ Tname.TABLE_TEAM + " t");
		//查找是否存在已关联
		List<Record> rList = new ArrayList<Record>();
		boolean flag = false;
		if(CheckFormat.isNumber(resid)){
			rList = Db.find("select * from " + Tname.TABLE_RESEARCH_TEAM + " t where t.resid=" + resid);
			if(rList.size()>0)
				flag = true;
		}
		int num = 1;
		for(Record trec:tList){
			trec.set("num", num++);
			trec.set("isown", 0);
			if(flag){
				for(Record rrec:rList){
					if(trec.getLong("id").intValue() == rrec.getLong("teamid").intValue()){
						trec.set("isown", 1);
						break;
					}
				}
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("tbody", tList);
		result.put("total", num>2?num-1:num);
		return result;
	}

	/**
	 * 管理员--研究方向列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> getResDirList(Integer curpage, Integer pagesize,
			String name) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_RESEARCH + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--研究团队列表
	 * @author xian.zf
	 * @date 2014-7-30
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> getTeamList(Integer curpage, Integer pagesize,
			String name, String userid) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_TEAM + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(CheckFormat.isNumber(userid))
			consql += " and t.id in (SELECT u.teamid FROM team_user u WHERE u.userid="+ userid +")";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--设备列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getEquipmentList(Integer curpage,
			Integer pagesize, String name, String number, String dutyman,
			String manager) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_EQUIPMENT + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and t.name like '%" +name+ "%'";
		if(number != null && !"".equals(number))
			consql += " and t.number like '%" +number+ "%'";		
		if(dutyman != null && !"".equals(dutyman))
			consql += " and t.dutyman like '%" +dutyman+ "%'";
		if(manager != null && !"".equals(manager))
			consql += " and t.manager like '%" +manager+ "%'";		
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getEquipmentUseList(Integer curpage,
			Integer pagesize, String equid, String equname, String username,
			String begintime, String endtime) {
		String bodysql = "SELECT t.id, t.begintime, t.endtime ,"
					   +" u.`name` AS username, u.id AS uid,"
					   +" e.`name` AS equname, e.id AS eid" ,
				consql =" FROM equipment_use t, equipment e, `user` u"
					   +" WHERE t.userid=u.id AND t.equid=e.id ";
		if(equid != null && !"".equals(equid))
			consql += " and e.id=" + equid;
		if(equname != null && !"".equals(equname))
			consql += " and e.name like '%" +equname+ "%'";		
		if(username != null && !"".equals(username))
			consql += " and (u.name like '%" +username+ "%' or UPPER(u.ename) like '%"+ username.toUpperCase() +"%')";
		if(begintime != null && !"".equals(begintime))
			consql += " and t.begintime like '%" +begintime+ "%'";
		if(endtime != null && !"".equals(endtime))
			consql += " and t.endtime like '%" +endtime+ "%'";	
		consql += " order by t.endtime desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getNewsList(Integer curpage, Integer pagesize,
			String title, String time, Boolean eflag) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_NEWS + " t where 1=1";
		if(title != null && !"".equals(title))
			consql += " and t.title like '%" +title+ "%' ";
		if(time != null && !"".equals(time))
			consql += " and t.time like '%" +time+ "%' ";
		if(eflag!=null && eflag)
			consql += " and t.eshow=1";
		consql += " order by t.time desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getAnnualReportList(Integer curpage,
			Integer pagesize, String title, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_ANNREP + " t where 1=1";
		if(title != null && !"".equals(title))
			consql += " and t.title like '%" +title+ "%' ";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" +year+ "%' ";
		consql += " order by t.year desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getPosterList(Integer curpage, Integer pagesize,
			String name, String dutyman, String year) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_POSTER + " t where 1=1";
		if(name != null && !"".equals(name))
			consql += " and t.name like '%" +name+ "%' ";
		if(dutyman != null && !"".equals(dutyman))
			consql += " and t.dutyman like '%" +dutyman+ "%' ";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" +year+ "%' ";
		consql += " order by t.year desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getNoticeList(Integer curpage, Integer pagesize,
			String title, String time, Boolean eflag) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_NOTICE + " t where 1=1";
		if(title != null && !"".equals(title))
			consql += " and t.title like '%" +title+ "%' ";
		if(time != null && !"".equals(time))
			consql += " and t.time like '%" +time+ "%' ";
		if(eflag!=null && eflag)
			consql += " and t.eshow=1";
		consql += " order by t.time desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--规章制度列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @return
	 */
	@Override
	public Map<String, Object> getRegulationsList(Integer curpage,
			Integer pagesize, String title) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_REGULATIONS + " t where 1=1";
		if(title != null && !"".equals(title))
			consql += " and t.title like '%" +title+ "%' ";
		consql += " order by t.time desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--学术海报---成果列表
	 * @author xian.zf
	 * @date 2014-7-31
	 * @param tkey
	 * @return
	 */
	@Override
	public List<Record> getAchByTkey(String tkey) {
		return Db.find("select t.id, t.name from " + tkey + " t");
	}

	/**
	 * 管理员--用户管理列表
	 * @author xian.zf
	 * @date 2014-8-1
	 * @param name
	 * @param roleid
	 * @return
	 */	
	@Override
	public Map<String, Object> getAllUserList(Integer curpage, Integer pagesize, String name, String roleid) {
		String bodysql = "select u.id, u.account, u.`name`",
			    consql = " FROM `user` u WHERE 1=1";
		if(name != null && !"".equals(name))
			consql += " and u.name like '%" +name+ "%' ";
		if(CheckFormat.isNumber(roleid))
			consql += " and u.id in (SELECT r.userid FROM user_role r WHERE r.roleid="+ roleid +")";
		consql += " ORDER BY u.sort ASC, CONVERT(u.name USING gbk) ASC ";
		Page<Record> page = Db.paginate(curpage, pagesize, bodysql, consql);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageArgument = new HashMap<String, Object>();
		pageArgument.put("curpage", page.getPageNumber());
		pageArgument.put("pagesize", page.getPageSize());
		pageArgument.put("pagecount", page.getTotalPage());
		pageArgument.put("total", page.getTotalRow());
		Long no = PageHelp.getStatrRow(Long.valueOf(pagesize),
				Long.valueOf(curpage), Long.valueOf(page.getTotalPage()));
		List<Record> list = page.getList();
		String rSql = "SELECT r.`name` FROM user_role u, role r WHERE u.roleid=r.id AND u.userid=";
		for(Record rec:list){
			//序号
			rec.set("num", ++no);
			//拥有的角色名
			List<Record> urlist = Db.find(rSql + rec.getLong("id"));
			String roleName = "";
			for(Record recUr:urlist)
				roleName += recUr.getStr("name") + "/";
			if(roleName.indexOf("/")>0)
				roleName = roleName.substring(0, roleName.length()-1);
			rec.set("rolename", roleName);
		}
		resultMap.put("tbody", list);
		resultMap.put("pageArgument", pageArgument);
		return resultMap;
		
	}

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
	@Override
	public Map<String, Object> getIndexAchList(Integer curpage,
			Integer pagesize, String tkey, String achid) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_INDEXACH + " t where 1=1";
		if(tkey != null && !"".equals(tkey))
			consql += " and t.tname='" + tkey + "' ";
		if(achid != null && !"".equals(achid))
			consql += " and t.achid=" + achid ;
		consql += " order by t.cretime desc";
		Page<Record> page = Db.paginate(curpage, pagesize, bodysql, consql);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageArgument = new HashMap<String, Object>();
		pageArgument.put("curpage", page.getPageNumber());
		pageArgument.put("pagesize", page.getPageSize());
		pageArgument.put("pagecount", page.getTotalPage());
		pageArgument.put("total", page.getTotalRow());
		Long no = PageHelp.getStatrRow(Long.valueOf(pagesize),
				Long.valueOf(curpage), Long.valueOf(page.getTotalPage()));
		List<Record> list = page.getList();		
		Map<String, String> map = new HashMap<String, String>();
		map.put("project", "科研项目");
		map.put("periodical_article", "期刊论文");
		map.put("conference_article", "会议论文");
		map.put("thesis", "论文");
		map.put("award", "获奖");
		map.put("patent", "专利");
		
		for(Record rec:list){
			//序号
			rec.set("num", ++no);
			//拥有的角色名
			Record model = Db.findById(rec.getStr("tname"), rec.getInt("achid"));
			rec.set("achname", model ==null?"":model.getStr("name"));
			rec.set("tcname", map.get(rec.getStr("tname")));
		}
		resultMap.put("tbody", list);
		resultMap.put("pageArgument", pageArgument);
		return resultMap;
	}

	/**
	 * 首页----实验室概况----实验室主任
	 * @author xian.zf
	 * @date 2014-8-8
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> getIndexChiList(Integer curpage,
			Integer pagesize, String name, boolean flag) {
		Map<String, Object> map = getLabChiefList(curpage, pagesize, name);
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
			if(flag){
				rec.set("name", rec.get("ename"));
				rec.set("introduction", rec.get("eintroduction"));
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> getIndexUserList(Integer curpage,
			Integer pagesize, String degree, boolean flag) {
		String bodysql = "select u.id, u.researcharea, u.`name`, u.`ename`, u.eresearcharea, u.photo, u.email, u.grade, u.title, u.phone, u.graduationdate, u.whereabouts",
			    consql = " FROM `user` u WHERE 1=1";
		if(degree != null && !"".equals(degree))
			consql += " and u.degree like '%" +degree+ "%' ";
		consql += " ORDER BY u.sort ASC, CONVERT(u.name USING gbk) ASC ";
		Map<String, Object> map = getMapByPage(curpage, pagesize, bodysql, consql, null);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		List<Record> resultlist = new ArrayList<Record>();
		//校长
		for(Record rec:list){
			if(flag){
				rec.set("name", rec.get("ename"));
				rec.set("researcharea", rec.get("eresearcharea"));
			}
//			if(rec.getLong("id").compareTo(38L)==0)
//				resultlist.add(0, rec);
//			else if(rec.getLong("id").compareTo(39L)==0)
//				resultlist.add(1, rec);
//			else
				resultlist.add(rec);
			
		}
		map.put("tbody", resultlist);
		return map;
	}

	/**
	 * 首页----搜索
	 * @author xian.zf
	 * @date 2014-8-22
	 * @param content
	 * @param curpage
	 * @param pagesize
	 * @return
	 */
	@Override
	public Map<String, Object> getSearchList(String content, Integer curpage,
			Integer pagesize) {
		String bodysql = "select *",
			    consql = " FROM `searchinfo` s WHERE 1=1";
		if(content != null && !"".equals(content))
			consql += " and s.content like '%" +content+ "%' ";
		consql += " order by s.time desc";
		Map<String, Object> map = getMapByPage(curpage, pagesize, bodysql, consql, null);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec.set("content", "");
		return map;
	}

	/**
	 * 管理员----招聘信息列表
	 * @author xian.zf
	 * @date 2014-8-22
	 * @param curpage
	 * @param pagesize
	 * @param title
	 * @return
	 */
	@Override
	public Map<String, Object> getRecruitList(Integer curpage,
			Integer pagesize, String title, String type,boolean flag) {
		String bodysql = "select *",
			    consql = " from "+ Tname.TABLE_RECRUIT + " t where 1=1";
		if(title != null && !"".equals(title))
			consql += " and (t.title like '%" +title+ "%' or UPPER(t.etitle) like '%"+ title.toUpperCase() +"%')";
		if(type != null && !type.isEmpty())
			consql += " and t.type like '%" +type+ "%'";
		if(flag)
			consql += " and t.eshow = 1 ";
		consql += " order by t.time desc";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--成果展示列表
	 * @author xian.zf
	 * @date 2014-9-24
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */	
	@Override
	public Map<String, Object> getSoftwareList(Integer curpage,
			Integer pagesize, String name) {
		String bodysql = "select *",
			    consql = " FROM `software` t WHERE 1=1";
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.name) like '%"+ name.toUpperCase() +"%')";
		consql += " ORDER BY t.sort ASC ";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员--成果展示列表
	 * @author xian.zf
	 * @date 2014-9-25
	 * @param curpage
	 * @param pagesize
	 * @param time
	 * @return
	 */
	@Override
	public Map<String, Object> getBackupsList(Integer curpage,
			Integer pagesize, String time) {
		String bodysql = "select *",
			    consql = " FROM `backups` t WHERE 1=1";
		if(time != null && !"".equals(time))
			consql += " and t.cretime like '%" +time+ "%'";
		consql += " ORDER BY t.cretime desc ";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
		
	}

	/**
	 * 管理员--首页图片 列表
	 * @author xian.zf
	 * @date 2014-9-25
	 * @param curpage
	 * @param pagesize
	 * @param describe
	 * @return
	 */
	@Override
	public Map<String, Object> getIndexPicList(Integer curpage,
			Integer pagesize, String describe) {
		String bodysql = "select *",
			    consql = " FROM `"+ Tname.INDEX_PIC +"` t WHERE 1=1";
		if(describe != null && !"".equals(describe))
			consql += " and t.describe like '%" +describe+ "%'";
		consql += " ORDER BY t.sort asc, t.time desc ";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

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
	@Override
	public Map<String, Object> getFundfileList(Integer curpage, Integer pagesize) {
		String bodysql = "select *",
			    consql = " FROM `"+ Tname.Fundfile +"` t WHERE 1=1";
		consql += " ORDER BY t.sort asc ";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 管理员-相关网站挂接列表
	 * @author xian.zf
	 * @date 2015-9-8
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> getRelatedlinkList(Integer curpage,
			Integer pagesize, String name) {
		String bodysql = "select *",
			    consql = " FROM `"+ Tname.Relatedlink +"` t WHERE 1=1";
		if(name != null && !"".equals(name))
			consql += " and t.name like '%" +name+ "%'";
		consql += " ORDER BY t.sort asc ";
		return getMapByPage(curpage, pagesize, bodysql, consql, null);
	}

	/**
	 * 获取所有关联项目
	 * @author xian.zf
	 * @date 2015-9-9
	 * @param tname
	 * @param achid
	 * @param rename
	 * @return
	 */
	@Override
	public Map<String, Object> getAllProjectList(String tname, String achid,
			String rename) {
		String[] achTkey = {Tname.TABLE_PERARTICLE, Tname.TABLE_CONARTICLE, Tname.TABLE_THESIS,
				Tname.TABLE_AWARD, Tname.TABLE_PATENT, Tname.TABLE_TREATISE};
		boolean haveflag = false;
		for(String name:achTkey){
			if(name.equals(tname)){
				haveflag = true;
				break;
			}
		}
		List<Record> proList = new ArrayList<Record>(); 
		int num = 1;
		String 	consql =  "select t.id, t.name from "+Tname.TABLE_PROJECT+" t where 1=1";
		if (rename!=null && !"".equals(rename)){
			consql += " and ( t.name like '%" +rename+ "%' or t.ename like '%" +rename+ "%' )";
		}
		consql += " order by t.restimebegin desc, t.name desc";
		//已关联的项目
		List<Record> achList = new ArrayList<Record>();
		List<Record> achProList = new ArrayList<Record>();
		if(haveflag && achid!=null && !"".equals(achid)){
			String sql = "select * from "+Tname.TABLE_PROJECTACH + " t where t.achid=" + achid + " and t.tname='" + tname +"'";
			achList = Db.find(sql);
		}
		proList = Db.find(consql);
		for(int i=0, len=proList.size(); i<len; i++){
			Record rec = proList.get(i);
			rec.set("isown", 0);
			boolean flag = false;
			for(Record have:achList){
				if( rec.getLong("id").intValue() == have.getInt("relid") ){
					rec.set("isown", 1);
					achProList.add(0, rec);
					flag = true;
					break;
				}
			}
			if(!flag)
				achProList.add(rec);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("tbody", achProList);
		result.put("total", num>2?num-1:num);
		return result;
	}

}
