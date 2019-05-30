package app.znkj.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.config.IndexInterceptor;
import app.znkj.service.IndexService;
import app.znkj.service.impl.IndexServiceImpl;
import app.znkj.util.CheckFormat;
import app.znkj.util.CreateForm;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 首页---重定向控制器
 * @author xian.zf
 *
 * @date 2014-7-18
 */
@Before(IndexInterceptor.class)
public class IndexController extends Controller{				

	private IndexService indexService = new IndexServiceImpl();
	
	/**
	 * 首页
	 * @author xian.zf
	 * @date 2014-7-18
	 * @throws Exception
	 */
	public void index() throws Exception{
		//最新成果
		setAttr("achlist", indexService.getNewAchList("1".equals(getSession().getAttribute(Constant.LANGUAGE))));
		//通知公告
		setAttr("noticelist", indexService.getNoticeList("1".equals(getSession().getAttribute(Constant.LANGUAGE))));
		//新闻中心
		setAttr("newslist", indexService.getNewsList("1".equals(getSession().getAttribute(Constant.LANGUAGE))));
		//封面图片
		setAttr("piclist", Db.find("select * FROM `"+ Tname.INDEX_PIC +"` t ORDER BY t.sort asc, t.time desc "));
		
		render("index.html");
	}
	
	/**
	 * 首页--登陆页
	 * @author xian.zf
	 * @date 2014-7-18
	 * @throws Exception
	 */
	public void login() throws Exception{
		if(getSession()!=null && getSession().getAttribute(Constant.SESSION_USERID)!=null){
			redirect("/info");
		}else
			render("login.html");
	}
	
	/**
	 * 实验室概况
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void lab() throws Exception{
		Record model = Db.findById(Tname.TABLE_LABORATORY, 1);
		if("1".equals(getSession().getAttribute(Constant.LANGUAGE)))
			model.set("description", model.getStr("edescription"));
		setAttr("lab", model);
		render("lab.html");
	}
	
	/**
	 * 研究团队
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void team() throws Exception{
		render("team.html");
	}
	
	/**
	 * 科学研究
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void studies() throws Exception{
		render("studies.html");
	}
	
	/**
	 * 学术交流
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void exchanges() throws Exception{
		render("exchanges.html");
	}
	
	/**
	 * 开放基金
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void foundation() throws Exception{
		//开放基金附件
		setAttr("fundfilelist", Db.find("select * FROM `"+ Tname.Fundfile +"` t ORDER BY t.sort asc "));
		
		render("foundation.html");
	}
	
	/**
	 * 规章制度
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void regulations() throws Exception{
		render("regulations.html");
	}
	
	/**
	 * 新闻中心
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void news() throws Exception{
		render("news.html");
	}
	
	/**
	 * 联系我们
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void contact() throws Exception{
		render("contact.html");
	}
	
	/**
	 * 通知公告
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void notice() throws Exception{
		render("notice.html");
	}
	
	/**
	 * 实验室主任
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void director() throws Exception{
		String[] fields = {"name", "introduction"};
		String sql = "select id, tenure, photo";
		Boolean f = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		for(int i = 0, len = fields.length; i < len; i += 1){
			sql += ", " + (f?
					"e" :  "") + fields[i] + " as " + fields[i];
		}
		sql += " from "+ Tname.TABLE_LAB_CHIEF + " order by id desc limit 1";
		setAttr("director", Db.findFirst(sql));
		render("director.html");
	}
	
	/**
	 * 实验室设备
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void equipment() throws Exception{
		render("equipment.html");
	}
	
	/**
	 * 博士后
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void postdoctoral() throws Exception{
		render("postdoctoral.html");
	}
	
	/**
	 * 研究生
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void graduates() throws Exception{
		render("graduates.html");
	}
	
	/**
	 * 毕业生
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void graduate() throws Exception{
		render("graduate.html");
	}
	
	/**
	 * 行政助理
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void assistant() throws Exception{
		render("assistant.html");
	}
	
	/**
	 * 研究项目
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void projects() throws Exception{
		render("projects.html");
	}
	
	/**
	 * 研究成果
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void findings() throws Exception{
		render("findings.html");
	}

	/**
	 * 成果展示
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void platform() throws Exception{
		render("platform.html");
	}
	/**
	 * 学着来访
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void come_to_visit() throws Exception{
		render("come_to_visit.html");
	}
	
	/**
	 * 学着出访
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void visiting() throws Exception{
		render("visiting.html");
	}
	
	/**
	 * 国际合作
	 * @author Li.hq
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void cooperation() throws Exception{
		render("cooperation.html");
	}
	
	public void job() throws Exception{
		render("job.html");
	}
	
	public void job_postdoctoral() throws Exception{
		render("job_postdoctoral.html");
	}
	
	public void job_graduates() throws Exception{
		render("job_graduates.html");
	}
	
	public void job_assistant() throws Exception{
		render("job_assistant.html");
	}
	
	public void committee() throws Exception{
		render("committee.html");
	}
	
	public void phd() throws Exception{
		render("phd.html");
	}
	
	public void masters() throws Exception{
		render("masters.html");
	}
	
	public void scholars() throws Exception{
		render("scholars.html");
	}
	
	/**
	 * 学术会议详细页
	 * @author Li.hq
	 * @date 2014-8-17
	 * @throws Exception
	 */
	public void exchange_details() throws Exception{
		String id = getPara("id");
		Record exchange = Db.findById(Tname.TABLE_COMMUNICATION, id);
		Boolean is_null = exchange == null;
		int sub_index = 0;
		String[] typeToIntMapping = {"学术会议", "学者来访", "学者出访", "国际合作"};
		if(!is_null){
			sub_index = Arrays.asList(typeToIntMapping).indexOf(exchange.getStr("type"));
			exchange.set("pageview", exchange.getInt("pageview") + 1);
			Db.update(Tname.TABLE_COMMUNICATION, exchange);
			
			exchange = CreateForm.disposeRecord(exchange, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		
		setAttr("exchange", exchange);
		
		_renderDetails("exchanges", sub_index, is_null);
	}
	
	/**
	 * 开放基金详细页
	 * @author Li.hq
	 * @date 2014-8-18
	 * @throws Exception
	 */
	public void foundation_details() throws Exception{
		String id = getPara("id");
		Boolean is_null = false;
		if(CheckFormat.isNumber(id)){
			Record foundation = Db.findById(Tname.TABLE_FUNDOPEN, id);
			is_null = foundation == null;
			
			// 关联的成果
			List<Record> modelList = Db.find("SELECT * FROM fund_achievement f WHERE f.relid=" + id);
			Map<String, Integer> map = new HashMap<String, Integer>();	
			map.put("periodical_article", 0);
			map.put("conference_article", 1);
			map.put("thesis", 2);
			map.put("award", 3);
			map.put("patent", 4);
			String[] achName = {"期刊论文","会议论文","学位论文","获奖","专利"};
			int num = 1;
			List<Record> indexModelList = new ArrayList<Record>();
			for(Record model : modelList){
				Record achModel = Db.findById(model.getStr("tname"), model.getInt("achid"));
				if(achModel == null){
					Db.delete("fund_achievement", model);
					continue;
				}
				model.set("url", "/findings_details?id=" + model.getInt("achid") +"&type="+ map.get(model.getStr("tname")));//url
				model.set("name", CheckFormat.isNullOrEmp(achModel.getStr("name"))?achModel.getStr("tname"):achModel.getStr("name"));//名称
				model.set("type", achName[map.get(model.getStr("tname"))]);//类型
				model.set("num", num++);//序号
				indexModelList.add(model);
			}
			setAttr("foundation", foundation);
			//关联的成果列表
			setAttr("achlist", indexModelList);
			
		}
		_renderDetails("exchanges", 4, is_null);
	}
	
	/**
	 * 规章制度详细页
	 * @author Li.hq
	 * @date 2014-8-17
	 * @throws Exception
	 */
	public void regulation_details() throws Exception{
		String id = getPara("id");
		Record regulation = Db.findById(Tname.TABLE_REGULATIONS, id);
		Boolean is_null = regulation == null;
		if(!is_null){
//			if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
//				regulation.set("title", regulation.getStr("etitle"));
//				regulation.set("content", regulation.getStr("econtent"));
//			}
			regulation = CreateForm.disposeRecord(regulation, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		
		setAttr("regulation", regulation);
		
		_renderDetails("regulations", 0, is_null);
	}
	
	/**
	 * 人才招聘详细页
	 * @author Li.hq
	 * @date 2014-8-26
	 * @throws Exception
	 */
	public void job_details() throws Exception{
		String id = getPara("id");
		Record article = Db.findById(Tname.TABLE_RECRUIT, id);
		Boolean is_null = article == null;
		if(!is_null){
			article.set("pageview", article.getInt("pageview") + 1);
			Db.update(Tname.TABLE_RECRUIT, article);
//			if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
//				article.set("title", article.getStr("etitle"));
//				article.set("content", article.getStr("econtent"));
//			}
			article = CreateForm.disposeRecord(article, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		
		String[] degreeToIntMapping = {"教师", "博士后", "研究生", "行政助理"};
		int sub_index = Arrays.asList(degreeToIntMapping).indexOf(article.getStr("type"));
		
		setAttr("article", article);
		
		_renderDetails("job", sub_index, is_null);
	}
	
	/**
	 * 新闻详细页
	 * @author Li.hq
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void news_details() throws Exception{
		String id = getPara("id");
		Record article = Db.findById(Tname.TABLE_NEWS, id);
		Boolean is_null = article == null;
		if(!is_null){
			article.set("pageview", article.getInt("pageview") + 1);
			Db.update(Tname.TABLE_NEWS, article);
//			if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
//				article.set("title", article.getStr("etitle"));
//				article.set("content", article.getStr("econtent"));
//			}
			article = CreateForm.disposeRecord(article, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		
		setAttr("article", article);
		
		_renderDetails("news", 0, is_null);
	}
	
	/**
	 * 通知详细页
	 * @author Li.hq
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void notice_details() throws Exception{
		String id = getPara("id");
		Record article = Db.findById(Tname.TABLE_NOTICE, id);
		Boolean is_null = article == null;
		if(!is_null){
			article.set("pageview", article.getInt("pageview") + 1);
			Db.update(Tname.TABLE_NOTICE, article);
//			if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
//				article.set("title", article.getStr("etitle"));
//				article.set("content", article.getStr("econtent"));
//			}
			article = CreateForm.disposeRecord(article, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		setAttr("article", article);
		_renderDetails("news", 1, is_null);
	}
	
	/**
	 * 研究方向详细页
	 * @author Li.hq
	 * @date 2014-8-14
	 * @throws Exception
	 */
	public void studies_details() throws Exception{
		String id = getPara("id");
		Record article = Db.findById(Tname.TABLE_NOTICE, id);
		Boolean is_null = article == null;
		if(!is_null){
			article.set("pageview", article.getInt("pageview") + 1);
			Db.update(Tname.TABLE_NOTICE, article);
//			if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
//				article.set("title", article.getStr("etitle"));
//				article.set("content", article.getStr("econtent"));
//			}
			article = CreateForm.disposeRecord(article, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		setAttr("article", article);
		_renderDetails("news", 1, is_null);
	}
	
	/**
	 * 研究项目详细页
	 * @author Li.hq
	 * @date 2014-8-14
	 * @throws Exception
	 */
	public void projects_details() throws Exception{
		String id = getPara("id");
		Record project = null;
		if(CheckFormat.isNumber(id))
			project = Db.findById(Tname.TABLE_PROJECT, id);
		Boolean is_null = project == null;
		if(!is_null){
			project.set("pic", "");
			Record index = Db.findFirst("SELECT * FROM index_achievement a WHERE a.tname='" + Tname.TABLE_PROJECT + "' AND a.achid=" +id); 
			if(index!=null)
				project.set("pic", index.getStr("pic"));
			project = CreateForm.disposeRecord(project, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
			
			//关联需要显示的项目
			List<Record> proachlist = Db.find("SELECT * FROM "+Tname.TABLE_PROJECTACH+" a WHERE a.relid=" + id + " AND a.isshow=1" ); 
			String[] achTkey = {Tname.TABLE_PERARTICLE, 
					Tname.TABLE_CONARTICLE, 
					Tname.TABLE_THESIS, 
					Tname.TABLE_TREATISE,
					Tname.TABLE_AWARD, 
					Tname.TABLE_PATENT}, 
					achTname = {"期刊论文",
					"会议论文",
					"论文",
					"专著",
					"获奖",
					"专利"};
			List<Record> resultproachlist = new ArrayList<Record>();
			for(Record rec:proachlist){
				Record ach = Db.findById(rec.getStr("tname"), rec.getInt("achid"));
				if(ach != null){
					int i = 0;
					boolean flag = false;
					for (String achtname:achTkey) {
						if ( achtname.equals(rec.getStr("tname")) ) {
							flag = true;
							break;
						}
						i++;
					}
					if (flag) {
						rec.set("achurl", "/findings_details?id=" + rec.getInt("achid") +"&type="+ i);
						rec.set("achtype", achTname[i]);
						rec.set("achname", ach.getStr("name") );
						if( "1".equals(getSession().getAttribute(Constant.LANGUAGE)) ) {
							rec.set("achname", ach.getStr("ename"));
						}
						resultproachlist.add(rec);
					}
				}
			}
			setAttr("proAchList", resultproachlist);
		}
		setAttr("project", project);
		_renderDetails("research", 1, is_null);
	}
	
	/**
	 * 研究成果详细页
	 * @author Li.hq
	 * @date 2014-8-15
	 * @throws Exception
	 */
	public void findings_details() throws Exception{
		String id = getPara("id"),
			 type = getPara("type");
		String tname = "";
		Record finding = null;
		String[] authorsname =  {"",""};
		if(CheckFormat.isNumber(id)){
			switch (type) {
			case "0":
				finding = Db.findById(Tname.TABLE_PERARTICLE, id);
				tname = Tname.TABLE_PERARTICLE;
				//作者名称,中英文一样
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					String authorsStr = "", eauthorsStr="";
					if(authors.indexOf(";")>0){
						String[] autStrs = authors.split(";");
						for(String str:autStrs){
							String[] strs = str.split(",");
							authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
							eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
							//通信作者
							if("1".equals(strs[4])){
								authorsStr += "*, ";
								eauthorsStr += "*, ";
							}else{
								authorsStr += ", ";
								eauthorsStr += ", ";
							}
						}
					}else{
						String[] strs = authors.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
					if(!authorsStr.isEmpty())
						authorsStr = authorsStr.substring(0, authorsStr.length()-2);
					if(!eauthorsStr.isEmpty())
						eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
					authorsname[0] = authorsStr;
					authorsname[1] = eauthorsStr;
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[1]);
				break;
			case "1":
				finding = Db.findById(Tname.TABLE_CONARTICLE, id);
				tname = Tname.TABLE_CONARTICLE;
				//作者名称,中英文一样
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					String authorsStr = "", eauthorsStr="";
					if(authors.indexOf(";")>0){
						String[] autStrs = authors.split(";");
						for(String str:autStrs){
							String[] strs = str.split(",");
							authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
							eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
							//通信作者
							if("1".equals(strs[4])){
								authorsStr += "*, ";
								eauthorsStr += "*, ";
							}else{
								authorsStr += ", ";
								eauthorsStr += ", ";
							}
						}
					}else{
						String[] strs = authors.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
					if(!authorsStr.isEmpty())
						authorsStr = authorsStr.substring(0, authorsStr.length()-2);
					if(!eauthorsStr.isEmpty())
						eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
					authorsname[0] = authorsStr;
					authorsname[1] = eauthorsStr;
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[0]);
				break;
			case "2":
				finding = Db.findById(Tname.TABLE_THESIS, id);
				tname = Tname.TABLE_THESIS;
				//作者名称
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					authors = authors.indexOf(";")>0?authors.replaceAll(";", ","):authors;
					authorsname = getUserNameByIds(authors);
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[1]);
				break;	
			case "3":
				finding = Db.findById(Tname.TABLE_AWARD, id);
				tname = Tname.TABLE_AWARD;
				//作者名称
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					String authorsStr = "", eauthorsStr="";
					if(authors.indexOf(";")>0){
						String[] autStrs = authors.split(";");
						for(String str:autStrs){
							String[] strs = str.split(",");
							authorsStr += (strs[0].isEmpty()?strs[1]:strs[0])+ ", ";//中文名
							eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1])+ ", ";//英文名
						}
					}else{
						String[] strs = authors.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";
						eauthorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";//英文名

					}
					if(!authorsStr.isEmpty())
						authorsStr = authorsStr.substring(0, authorsStr.length()-2);
					if(!eauthorsStr.isEmpty())
						eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
					authorsname[0] = authorsStr;
					authorsname[1] = eauthorsStr;
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[1]);
				break;	
			case "4":
				finding = Db.findById(Tname.TABLE_PATENT, id);
				tname = Tname.TABLE_PATENT;
				//作者名称
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					String authorsStr = "", eauthorsStr="";
					if(authors.indexOf(";")>0){
						String[] autStrs = authors.split(";");
						for(String str:autStrs){
							String[] strs = str.split(",");
							authorsStr += (strs[0].isEmpty()?strs[1]:strs[0])+ ", ";//中文名
							eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1])+ ", ";//英文名
						}
					}else{
						String[] strs = authors.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";
						eauthorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";//英文名

					}
					if(!authorsStr.isEmpty())
						authorsStr = authorsStr.substring(0, authorsStr.length()-2);
					if(!eauthorsStr.isEmpty())
						eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
					authorsname[0] = authorsStr;
					authorsname[1] = eauthorsStr;
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[1]);
				break;	
			case "5":
				finding = Db.findById(Tname.TABLE_TREATISE, id);
				tname = Tname.TABLE_TREATISE;
				//作者名称,中英文一样
				if(finding!=null && finding.getStr("authors")!=null 
						&& !"".equals(finding.getStr("authors"))){
					String authors = finding.getStr("authors");
					String authorsStr = "", eauthorsStr="";
					if(authors.indexOf(";")>0){
						String[] autStrs = authors.split(";");
						for(String str:autStrs){
							String[] strs = str.split(",");
							authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
							eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
							//通信作者
							if("1".equals(strs[4])){
								authorsStr += "*, ";
								eauthorsStr += "*, ";
							}else{
								authorsStr += ", ";
								eauthorsStr += ", ";
							}
						}
					}else{
						String[] strs = authors.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
					if(!authorsStr.isEmpty())
						authorsStr = authorsStr.substring(0, authorsStr.length()-2);
					if(!eauthorsStr.isEmpty())
						eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
					authorsname[0] = authorsStr;
					authorsname[1] = eauthorsStr;
				}
				finding.set("authors", authorsname[0]);
				finding.set("eauthors", authorsname[0]);
				break;
			default:
				break;
			}
		}
		

		Boolean is_null = finding == null;
		//英文更改
		if(!is_null ){
			finding.set("pic", finding.get("pic")==null?"":finding.get("pic"));
			Record index = Db.findFirst("SELECT * FROM index_achievement a WHERE a.tname='" + tname + "' AND a.achid=" +id); 
			if(index!=null)
				finding.set("pic", index.getStr("pic"));
			finding = CreateForm.disposeRecord(finding, "1".equals(getSession().getAttribute(Constant.LANGUAGE)));
		}
		setAttr("finding_type", type);
		setAttr("finding", finding);
		_renderDetails("research", 2, is_null);
	}
	
	/**
	 * 根据用户id串，获取用户的名字
	 * @author xian.zf
	 * @date 2014-7-23
	 * @param ids
	 * @return
	 */
	private String[] getUserNameByIds(String ids) {
		String names = "", enames="";
		List<Record> rlist = Db.find("select * from "+ Tname.TABLE_USER + " u where u.id in ("+ids+")");
		for(Record rec:rlist){
			names += rec.getStr("name") + "、 ";
			enames += rec.getStr("ename") + "、 ";
		}
		int len = names.length();
		names = len>2 ? names.substring(0, len-2): "";
		int elen = enames.length();
		enames = elen>2 ? enames.substring(0, elen-2): "";
		String[] nameStr = {names,enames};
		return nameStr;
	}
	
	/**
	 * 研究团队详细页
	 * @author Li.hq
	 * @date 2014-8-14
	 * @throws Exception
	 */
	public void user_details() throws Exception{
		String id = getPara("id");
		Record model = Db.findById(Tname.TABLE_USER, id);
		Boolean is_null = model == null;
		Map<String, Object> map = new HashMap<String, Object>();
		String[] degreeToIntMapping = {"教师", "博士后", "博士生", "硕士生", "特约/访问学者", "行政助理"};
		if(!is_null){
			map.put("photo", model.getStr("photo"));
			map.put("email", model.getStr("email"));
			map.put("grade", model.getStr("grade"));
		
			map.put("phone", model.getStr("phone"));
			map.put("fax", model.getStr("fax"));
			//英文更改
			boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
			if(flag){
				String[] gender = {"female", "male"};
				map.put("gender", gender[model.getInt("gender")]);
				map.put("name", model.getStr("ename"));
				map.put("degree", model.getStr("degree"));
				map.put("profession", model.getStr("eprofession"));
				map.put("researcharea", model.getStr("eresearcharea"));
				map.put("workresume", model.getStr("eworkresume"));
				map.put("introduce", model.getStr("eintroduce"));
				map.put("title", model.getStr("etitle"));
				
			}else{
				String[] gender = {"女", "男"};
				map.put("gender", gender[model.getInt("gender")]);
				map.put("name", model.getStr("name"));
				map.put("degree", model.getStr("degree"));
				map.put("profession", model.getStr("profession"));
				map.put("researcharea", model.getStr("researcharea"));
				map.put("workresume", model.getStr("workresume"));
				map.put("introduce", model.getStr("introduce"));
				map.put("title", model.getStr("title"));

			}
			
			//个人项目
			List<Record> projectlist = Db.find("select * from "+ Tname.TABLE_PROJECT + " t where t.register=" + id + " order by t.restimebegin desc, t.name desc");
//			projectlist = CreateForm.disposeRecord(projectlist, flag, "/projects_details?id=");
			for(Record rec:projectlist){
//				if("纵向".equals(rec.getStr("category")))
//					rec.set("type", rec.getStr("category") +" "+ rec.getStr("typeone") + " " +rec.getStr("typetwo"));
//				else
//					rec.set("type", rec.getStr("category") +" "+ rec.getStr("partner"));
				//执行时间
				rec.set("time", CreateForm.creProTime(rec.getStr("restimebegin"), rec.getStr("restimeend")));

				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("name") + "("+ rec.getStr("contractno") +"), "
							   + rec.getStr("fundsource") + ", ";
				if(flag)
					compose +="PI-";
				else
					compose +="负责人-";
				compose	+= rec.getStr("principal") + ", ";
				if(rec.getStr("participant")!=null && !rec.getStr("participant").isEmpty()){
//					compose += "参与人-"+ rec.getStr("participant") + ", ";
					if(flag)
						compose +="Co-PI-";
					else
						compose +="参与人-";
					compose += rec.getStr("participant") + ", ";
				}
				compose += rec.getStr("time") ;
				if(!flag)
					compose += " 年";
				rec.set("compose", compose);
				rec.set("url", "/projects_details?id=" + rec.get("id"));
			}
			map.put("projectlist", projectlist);
			
			//关联的成果条件
			String consql = " t.register=" + id 
					+ " or t.relevance = '" + id + "'"
					+ " or t.relevance like '" + id + ";%'"
					+ " or t.relevance like '%;" + id + ";%'"
					+ " or t.relevance like '%;" + id + "' ";
			//期刊论文
			List<Record> perartlist = Db.find("select * from "+ Tname.TABLE_PERARTICLE + " t where " + consql + " order by t.publishyear desc, t.name asc");
//			map.put("perartlist", CreateForm.disposeRecord(perartlist, flag, "/findings_details?type=0&id="));
			for(Record rec:perartlist){
				String authors = rec.get("authors");
				String authorsStr = "", eauthorsStr="";
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
					eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
					//通信作者
					if("1".equals(strs[4])){
						authorsStr += "*, ";
						eauthorsStr += "*, ";
					}else{
						authorsStr += ", ";
						eauthorsStr += ", ";
					}
				}
				if(!authorsStr.isEmpty())
					authorsStr = authorsStr.substring(0, authorsStr.length()-2);
				if(!eauthorsStr.isEmpty())
					eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", eauthorsStr);
				rec = CreateForm.disposeRecord(rec, flag);
				
				//9.1新排版
				String compose = rec.getStr("authorsname") +". "
							   + rec.getStr("name") + ". "
							   + rec.getStr("periodical") + ". "
							   + rec.getStr("publishyear") + ", "
							   + rec.getStr("reelnumber") ;
				if(rec.getStr("issue")!=null && !rec.getStr("issue").isEmpty()){
					compose += "("+ rec.getStr("issue") +")";
				}
				compose +=  ", " + rec.getStr("pagination") + ".";
				if(!Constant.ARTTYPE.equals(rec.getStr("include").trim()))
					compose += "("+ rec.getStr("include") +").";
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=0&id=" + rec.get("id"));
			}
			map.put("perartlist", perartlist);
			
			//会议论文
			List<Record> conartlist = Db.find("select * from "+ Tname.TABLE_CONARTICLE + " t where " + consql + " order by t.publishyear desc");
//			map.put("conartlist", CreateForm.disposeRecord(conartlist, flag, "/findings_details?type=1&id="));
			for(Record rec : conartlist){
				String authors = rec.get("authors");
				String authorsStr = "", eauthorsStr="";
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
					eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
					//通信作者
					if("1".equals(strs[4])){
						authorsStr += "*, ";
						eauthorsStr += "*, ";
					}else{
						authorsStr += ", ";
						eauthorsStr += ", ";
					}
				}
				if(!authorsStr.isEmpty())
					authorsStr = authorsStr.substring(0, authorsStr.length()-2);
				if(!eauthorsStr.isEmpty())
					eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", eauthorsStr);
				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("authorsname") +". "
							   + rec.getStr("name") + ". "
							   + rec.getStr("periodical") + ". "
							   + rec.getStr("publishyear") + ". "
							   + rec.getStr("address") + ", "
							   + rec.getStr("pagination") + ".";
				if(!Constant.ARTTYPE.equals(rec.getStr("include").trim()))
					compose += "("+ rec.getStr("include") +").";
				
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=1&id=" + rec.get("id"));
			}
			map.put("conartlist", conartlist);
			
			
			// 学位论文
			List<Record> thesislist = Db.find("select * from "+ Tname.TABLE_THESIS + " t where " + consql + " order by t.publishyear desc");
			for(Record rec : thesislist){
				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("authorsname") +". "
							   + rec.getStr("name") + "[D]. "
							   + rec.getStr("unit") + ". "
							   + rec.getStr("publishyear") + ". "
							   + rec.getStr("tutor") + "";
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=2&id=" + rec.get("id"));
			}
			map.put("thesislist", thesislist);
			
			//获奖
			List<Record> awardlist = Db.find("select * from "+ Tname.TABLE_AWARD + " t where " + consql + " order by t.year desc");
			// 处理格式 （排名/总人数）
			for(Record rec : awardlist){
				String authors = rec.get("authors");
				String authorsStr = "", eauthorsStr="";
				boolean isauthors = false;
				int len = 0, ranking=1;
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0])+ ", ";//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1])+ ", ";//英文名
						len++;//作者总数加1
						//判断是否作者
						if((model.getStr("name")!=null && model.getStr("name").equals(strs[0]))
								|| ( model.getStr("ename")!=null && model.getStr("ename").equals(strs[1]) )){
							isauthors = true;
							ranking = len;
						}
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";
					eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]) + ", ";//英文名
					len++;//作者总数加1
					//判断是否作者
					if((model.getStr("name")!=null && model.getStr("name").equals(strs[0]))
							|| ( model.getStr("ename")!=null && model.getStr("ename").equals(strs[1]) ))
						isauthors = true;

				}
				if(!authorsStr.isEmpty())
					authorsStr = authorsStr.substring(0, authorsStr.length()-2);
				if(!eauthorsStr.isEmpty())
					eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", eauthorsStr);
				rec = CreateForm.disposeRecord(rec, flag);
				
				rec.set("certigier", rec.getStr("certigier")==null?"":rec.getStr("certigier").replaceAll(";", ", "));
				
				//9.1新排版
				String compose = rec.getStr("name") +". "
							   + rec.getStr("awardname") + ". "
							   + rec.getStr("certigier") + ". "
							   + rec.getStr("year")+ ". ";
				if( isauthors )
					compose += "("+ ranking +"/"+ len +")";
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=3&id=" + rec.get("id"));
			}
			map.put("awardlist", awardlist);
			
			//专利
			List<Record> patentlist = Db.find("select * from "+ Tname.TABLE_PATENT + " t where " + consql + " order by t.year desc");
//			map.put("patentlist", CreateForm.disposeRecord(patentlist, flag, "/findings_details?type=4&id="));
			for(Record rec : patentlist){
				String authors = rec.get("authors");
				String authorsStr = "", eauthorsStr="";
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0])+ ", ";//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1])+ ", ";//英文名
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";
					eauthorsStr += (strs[0].isEmpty()?strs[1]:strs[0]) + ", ";//英文名

				}
				if(!authorsStr.isEmpty())
					authorsStr = authorsStr.substring(0, authorsStr.length()-2);
				if(!eauthorsStr.isEmpty())
					eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", eauthorsStr);
				
				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("authorsname")  +". "
							   + rec.getStr("patentname")  + ". "
							   + rec.getStr("type")+ "("
							   + rec.getStr("country")+ "), "
							   + rec.getStr("year") + ", " 
							   + rec.getStr("status") + " : "
							   + rec.getStr("patentno") ;
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=4&id=" + rec.get("id"));
			}
			map.put("patentlist", patentlist);
			
			//专著
			List<Record> treatiselist = Db.find("select * from "+ Tname.TABLE_TREATISE + " t where " + consql + " order by t.publishyear desc");
			for(Record rec : treatiselist){
				String authors = rec.get("authors");
				String authorsStr = "", eauthorsStr="";
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
						eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
						//通信作者
						if("1".equals(strs[4])){
							authorsStr += "*, ";
							eauthorsStr += "*, ";
						}else{
							authorsStr += ", ";
							eauthorsStr += ", ";
						}
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += (strs[0].isEmpty()?strs[1]:strs[0]);//中文名
					eauthorsStr += (strs[1].isEmpty()?strs[0]:strs[1]);//英文名
					//通信作者
					if("1".equals(strs[4])){
						authorsStr += "*, ";
						eauthorsStr += "*, ";
					}else{
						authorsStr += ", ";
						eauthorsStr += ", ";
					}
				}
				if(!authorsStr.isEmpty())
					authorsStr = authorsStr.substring(0, authorsStr.length()-2);
				if(!eauthorsStr.isEmpty())
					eauthorsStr = eauthorsStr.substring(0, eauthorsStr.length()-2);
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", eauthorsStr);
				rec = CreateForm.disposeRecord(rec, flag);
				
				//9.1新排版
				String compose = rec.getStr("authorsname") +". "
							   + rec.getStr("name") + ". ";
				if(rec.getStr("versions")!=null && !rec.getStr("versions").isEmpty())
					compose += rec.getStr("versions") + ", ";
				compose += rec.getStr("publishaddr")+ " : " 
						+ rec.getStr("publish") + ", "
						+ rec.getStr("publishyear") + ". pp"
						+ rec.getStr("pagination");
				rec.set("compose", compose);
				rec.set("url", "/findings_details?type=5&id=" + rec.get("id"));
			}
			map.put("treatiselist", treatiselist);
						
			
		}
		int sub_index = Arrays.asList(degreeToIntMapping).indexOf(model.getStr("degree"));
		setAttr("user", map);
		_renderDetails("team", sub_index > -1 ? sub_index : 0, is_null);
	}
	
	/**
	 * 渲染详细页
	 * @param nav_index 请参考common.ftl文件里定义的navigator对象
	 * @param sub_index 请参考common.ftl文件里定义的navigator对象
	 * @param is_404
	 * @throws Exception
	 */
	private void _renderDetails(String nav_index, int sub_index, Boolean is_404) throws Exception{
		setAttr("nav_index", nav_index);
		setAttr("sub_index", sub_index);
		
		render("details.ftl", is_404 ? 400 : 200);
	}
	
	public void search() throws Exception{
		String queryString = CreateForm.creSinFormAndByts(getPara("q"));
		
		setAttr("q", queryString.replaceAll("\"", "&#34;"));
		
		render("search.ftl", 200);
	}
	
	
	/** 成果展示路由 ----开始 **/
	
	public void panorama() throws Exception {
		render("achievements/Panorama/New9.123 Panorama1.html");
	}

	/** 成果展示路由----结整 **/
	
	/** 2015-10-14 需求 meeting 路由**/
	public void meeting() throws Exception {
		render("meeting.html");
	}
	
}
