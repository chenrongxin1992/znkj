package app.znkj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.model.User;
import app.znkj.service.IndexService;
import app.znkj.service.ListService;
import app.znkj.service.impl.IndexServiceImpl;
import app.znkj.service.impl.ListServiceImpl;
import app.znkj.util.CreateForm;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author xian.zf
 *
 * @date 2014-7-19
 */
public class OtherController extends Controller{
	

	private IndexService indexService = new IndexServiceImpl();

	private ListService listService = new ListServiceImpl();
	
	
	/**
	 * 登陆
	 * @author xian.zf
	 * @date 2014-7-18
	 * @throws Exception
	 */
	public String ajaxLogin() throws Exception{
		String account = CreateForm.creSinQuoForm(getPara("account"));
		String password = CreateForm.creSinQuoForm(getPara("password"));
		String code = getPara("code");
		if(validateCode(code)){
			Map<String, Object> map = new HashMap<String, Object>();
			//判断是否已登陆
			if(getSession().getAttribute(Constant.SESSION_USERID)!=null){
				map.put("url", Constant.URL_INFO);
				map.put("success", 1);
				return null;
			}
			map = indexService.ajaxLogin(account, password);
			
			if(map.get(Constant.SUCCES).toString().equals("1")){
				//存session
				@SuppressWarnings("unchecked")
				Map<String, Object> sMap = (Map<String, Object>) map.get("sMap");
				for(Map.Entry<String, Object> entry:sMap.entrySet())
					setSessionAttr(entry.getKey(), entry.getValue());
				//清空
				map.put("sMap", "");
				map.put("url", Constant.URL_INFO);
			}
			renderJson(map);
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Constant.SUCCES, 0);
			map.put("error", "验证码错误！");
			renderJson(map);
		}
		return null;
	}
	
	/**
	 * 注销
	 * @author xian.zf
	 * @date 2014-7-31
	 * @return
	 * @throws Exception
	 */
	public void loginOut() throws Exception {
		if(getSession()!=null){
			getSession().invalidate();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.SUCCES, true);
		map.put("url", Constant.URL_LOGIN);
		renderJson(map);
	}
	
	/**
	 * 判断验证码 是否正确
	 * 
	 * @return
	 * @throws Excetion
	 */
	public boolean validateCode(String validateCode) throws Exception {
		String makeValidateCode = (String) getSession().getAttribute("validationCode");
		if (validateCode != null && makeValidateCode != null) {
			return makeValidateCode.equalsIgnoreCase(validateCode);
		} else {
			return false;
		}
	}
	
	/**
	 * 获取个人息信
	 * @author xian.zf
	 * @date 2014-7-21
	 * @throws Exception
	 */
	public void getUserInfo() throws Exception {
		String id = getSession().getAttribute(Constant.SESSION_USERID).toString();
		User user = User.dao.findById(id);
		user.set("password", "");
		renderJson(user);
	}
	
	/**
	 * 下载文件
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void downLoadFile() throws Exception {
		renderFile(getPara("filepath"));
	}
	
	/**
	 * 选择语言，
	 * @author xian.zf
	 * @date 2014-8-6
	 * @throws Exception
	 */
	public void setLanguage() throws Exception {
		setSessionAttr(Constant.LANGUAGE, "1".equals(getPara("language"))?"1":"0");
		renderJson(CreateForm.successMap());
	}
	
	/**
	 * 首页----实验室概况----实验室主任
	 * @author xian.zf
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void getIndexChiList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String name = CreateForm.creSinFormAndByts(getPara("name"));//姓名
		renderJson(listService.getIndexChiList(curpage, pagesize, name, "1".equals(getSession().getAttribute(Constant.LANGUAGE))));
	}
	
	/**
	 * 首页----实验室概况----实验室设备
	 * @author xian.zf
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void getIndexEquList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = Integer.valueOf(getPara("pagesize"));
		String name = CreateForm.creSinFormAndByts(getPara("name"));		//名称
		String number = CreateForm.creSinFormAndByts(getPara("number"));	//编号
		String dutyman = CreateForm.creSinFormAndByts(getPara("dutyman"));	//负责人
		String manager = CreateForm.creSinFormAndByts(getPara("manager"));	//管理员
		renderJson(listService.getEquipmentList(curpage, pagesize, name, number, dutyman, manager));
	}
	
	/**
	 * 首页----研究团队----用户管理列表
	 * @author xian.zf
	 * @date 2014-8-1
	 * @throws Exception
	 */
	public void getIndexUserList() throws Exception {
		Integer curpage = 1;
		Integer pagesize = 1000;
		String degree = CreateForm.creSinFormAndByts(getPara("type"));
		renderJson(listService.getIndexUserList(curpage, pagesize, degree, "1".equals(getSession().getAttribute(Constant.LANGUAGE))));
	}
	
	/**
	 * 首页----科学研究----研究方向
	 * @author xian.zf
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void getIndexResDirList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//名称
		
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		Map<String, Object> map = listService.getResDirList(curpage, pagesize, name);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
			rec = CreateForm.disposeRecord(rec, flag);
		}
		renderJson(map);
	}
	
	/**
	 * 首页----科学研究----项目
	 * @author xian.zf
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void getIndexProjectList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String status = CreateForm.creSinFormAndByts(getPara("status"));//状态
		
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		Map<String, Object> map = listService.getProjectList(curpage, pagesize, null, null, null,null, status);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
//			if("纵向".equals(rec.getStr("category")))
//				rec.set("type", rec.getStr("category") + " " +rec.getStr("typetwo"));
//			else
//				rec.set("type", rec.getStr("category") + " "+ rec.getStr("partner"));
			//执行时间
			rec.set("time", CreateForm.creProTime(rec.getStr("restimebegin"), rec.getStr("restimeend")));

			rec = CreateForm.disposeRecord(rec, flag);
/*			//9.1新排版
			String compose = rec.getStr("name") + "("+ rec.getStr("contractno") +"), "
						   + rec.getStr("fundsource") + ", 负责人-"
						   + rec.getStr("principal") + ", ";
			if(rec.getStr("participant")!=null && !rec.getStr("participant").isEmpty()){
				compose += "参与人-"+ rec.getStr("participant") + ", ";
			}
			compose += rec.getStr("time") + " 年";*/
			//9.1新排版
			String compose = rec.getStr("name") + "("+ rec.getStr("contractno") +"), "
						   + rec.getStr("fundsource") + ", ";
			if(flag)
				compose +="PI-";
			else
				compose +="负责人-";
			compose	+= rec.getStr("principal") + ", ";
			if(rec.getStr("participant")!=null && !rec.getStr("participant").isEmpty()){
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
		}
		renderJson(map);
	}
	
	/**
	 * 首页----科学研究----研究成果
	 * @author xian.zf
	 * @date 2014-8-8
	 * @throws Exception
	 */
	public void getIndexAchList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String type = getPara("type");
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		switch (type) {
		case "0":
			map = listService.getPerArticleList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> list = (List<Record>) map.get("tbody");
			for(Record rec:list){
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
				if(rec.getStr("reelnumber")!=null && !rec.getStr("reelnumber").isEmpty() &&
						rec.getStr("issue")!=null && !rec.getStr("issue").isEmpty()){
					compose += "("+ rec.getStr("issue") +")";
				}
//				compose +=  ", "
//						+ rec.getStr("pagination") + ".("
//						+ rec.getStr("include") + ").";
				compose +=  ", " + rec.getStr("pagination") + ".";
				if(!Constant.ARTTYPE.equals(rec.getStr("include").trim()))
					compose += "("+ rec.getStr("include") +").";
				rec.set("compose", compose);
			}
			break;
		case "1":
			map = listService.getConArticleList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> listc = (List<Record>) map.get("tbody");
			for(Record rec:listc){
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
			}
			break;
		case "2":
			map = listService.getThesisList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> listt = (List<Record>) map.get("tbody");
			for(Record rec:listt){
				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("authorsname") +". "
							   + rec.getStr("name") + "[D]. "
							   + rec.getStr("unit") + ". "
							   + rec.getStr("publishyear") + ". "
							   + rec.getStr("tutor") + "";
				rec.set("compose", compose);
			}
			break;	
		case "3":
			map = listService.getAwardList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> lista = (List<Record>) map.get("tbody");
			for(Record rec:lista){
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
				
				rec.set("certigier", rec.getStr("certigier")==null?"":rec.getStr("certigier").replaceAll(";", ", "));
				//9.1新排版
				String compose = rec.getStr("name") +". "
							   + rec.getStr("awardname") + ". "
							   + rec.getStr("certigier") + ". "
							   + rec.getStr("authorsname") + ". "
							   + rec.getStr("year");
				rec.set("compose", compose);
			}
			break;	
		case "4":
			map = listService.getPatentList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> listp = (List<Record>) map.get("tbody");
			for(Record rec:listp){
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
			}
			
			break;	
		case "5":
			map = listService.getTreatiseList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> tlist = (List<Record>) map.get("tbody");
			for(Record rec:tlist){
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
			}		
			break;
		default:
			map = listService.getPerArticleList(curpage, pagesize, null, null, null, null);
			@SuppressWarnings("unchecked")
			List<Record> listpa = (List<Record>) map.get("tbody");
			for(Record rec:listpa){
				String authors = rec.get("authors");
				String authorsStr = "";
				if(authors.indexOf(";")>0){
					String[] autStrs = authors.split(";");
					for(String str:autStrs){
						String[] strs = str.split(",");
						authorsStr += strs[0];
						if("1".equals(strs[2]))
							authorsStr += "*,";
						else
							authorsStr += ",";
					}
				}else{
					String[] strs = authors.split(",");
					authorsStr += strs[0];
					if("1".equals(strs[2]))
						authorsStr += "*,";
					else
						authorsStr += ",";
				}
				rec.set("authorsname", authorsStr);
				rec.set("eauthorsname", rec.getStr("authorsname"));
				rec = CreateForm.disposeRecord(rec, flag);
				//9.1新排版
				String compose = rec.getStr("authorsname") +", "
							   + rec.getStr("name") + ", "
							   + rec.getStr("periodical") + ", "
							   + rec.getStr("publishyear") + ", Vol.("
							   + rec.getStr("issue") + "), pp"
							   + rec.getStr("pagination") + ".("
							   + rec.getStr("include") + ").";
				rec.set("compose", compose);
			}
			break;
		}
/*		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
			//TODO 因备研究成果的作者栏重新设置、暂时这样处理给首页显示
			String authors = rec.get("authors");
			if(authors.indexOf(",")>0){
				rec.set("authorsname", authors.substring(0, authors.indexOf(",")));
				rec.set("eauthorsname", rec.getStr("authorsname"));
			}
//			if(flag){
//				rec.set("name", rec.get("ename"));
//				rec.set("authorsname", rec.get("eauthorsname"));
//			}
			rec = CreateForm.disposeRecord(rec, flag);
		}
*/
		renderJson(map);
	}
	
	/**
	 * 首页----学术交流
	 * @author xian.zf
	 * @date 2014-8-11
	 */
	public void getIndexComList() throws Exception{
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String type = CreateForm.creSinFormAndByts(getPara("type"));
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		Map<String, Object> map = listService.getCommunicationList(curpage, pagesize, type, null, null,null, flag);
		
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec = CreateForm.disposeRecord(rec, flag);
		renderJson(map);
	}
	
	
	/**
	 * 首页----研究团队----研究人员详细
	 * @author xian.zf
	 * @date 2014-8-11
	 * @throws Exception
	 */
	public void getIndexUserInfo() throws Exception {
		Record model = Db.findById(Tname.TABLE_USER, getPara("id"));
		if(model == null)
			redirect("/team");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gender", model.getInt("gender"));
		map.put("photo", model.getStr("photo"));
		if("1".equals(getSession().getAttribute(Constant.LANGUAGE))){
			map.put("name", model.getStr("ename"));
			map.put("profession", model.getStr("eprofession"));
			map.put("researcharea", model.getStr("eresearcharea"));
			map.put("workresume", model.getStr("eworkresume"));
			map.put("introduce", model.getStr("eintroduce"));
		}else{
			map.put("name", model.getStr("name"));
			map.put("profession", model.getStr("profession"));
			map.put("researcharea", model.getStr("researcharea"));
			map.put("workresume", model.getStr("workresume"));
			map.put("introduce", model.getStr("eintroduce"));
		}
		renderJson(map);
	}
	
	/**
	 * 首页----规章制度列表
	 * @author xian.zf
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void getIndexRegulationList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String title = CreateForm.creSinFormAndByts(getPara("title"));	
		//TODO 英文 显示情况
		renderJson(listService.getRegulationsList(curpage, pagesize, title));
	}
	
	/**
	 * 首页----开放基金
	 * @author xian.zf
	 * @date 2014-8-12
	 */
	public void getIndexOpenFunList() throws Exception{
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String year = CreateForm.creSinFormAndByts(getPara("year"));//年分
		String name = CreateForm.creSinFormAndByts(getPara("name"));//姓名
		String title = CreateForm.creSinFormAndByts(getPara("title"));//题目
		//TODO 语言设置
		renderJson(listService.getFundList(curpage, pagesize, name, title, year,null));
	}
	
	/**
	 * 首页----新闻列表
	 * @author xian.zf
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void getIndexNewsList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		Map<String, Object> map = listService.getNewsList(curpage, pagesize, null, null, flag);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec = CreateForm.disposeRecord(rec, flag);
		
		renderJson(map);
	}
	
	/**
	 *  首页----通知公告列表
	 * @author xian.zf
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void getIndexNoticeList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		Map<String, Object> map = listService.getNoticeList(curpage, pagesize, null, null, flag);
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec = CreateForm.disposeRecord(rec, flag);
		
		renderJson(map);
	}

	/**
	 *  首页----成果展示平台信息
	 * @author xian.zf
	 * @date 2014-8-12
	 * @throws Exception
	 */
	public void getPlatformList() throws Exception {
		Integer curpage = 1;
		Integer pagesize = 1000;
		Map<String, Object> map = listService.getSoftwareList(curpage, pagesize, null);
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list){
			rec = CreateForm.disposeRecord(rec, flag);
		}
		renderJson(map);
	}
	
	/**
	 * 首面的全文搜索
	 * @author xian.zf
	 * @date 2014-8-22
	 * @throws Exception
	 */
	public void getSearchList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String content = CreateForm.creSinFormAndByts(getPara("content"));
		renderJson(listService.getSearchList(content.replaceAll("<|>|/", ""), curpage, pagesize));
	}
	
	/**
	 * 首页--学术委员会列表
	 * @author xian.zf
	 * @date 2014-7-29
	 * @throws Exception
	 */
	public void getAcaComList() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String name = CreateForm.creSinFormAndByts(getPara("name"));	//姓名
		Map<String, Object> map = listService.getAcaComList(curpage, pagesize, name, null, null);
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec = CreateForm.disposeRecord(rec, flag);
		renderJson(map);
	}
	
	/**
	 * 首页--人才招聘列表
	 * @author xian.zf
	 * @date 2014-8-25
	 * @throws Exception
	 */
	public void getRecruit() throws Exception {
		Integer curpage = Integer.valueOf(getPara("curpage"));
		Integer pagesize = 1000;
		String type = CreateForm.creSinFormAndByts(getPara("type"));	
		boolean flag = "1".equals(getSession().getAttribute(Constant.LANGUAGE));
//		Map<String, Object> map = listService.getRecruitList(curpage, pagesize, null, type);
		Map<String, Object> map = listService.getRecruitList(curpage, pagesize, null, type,flag);
		
//		if(flag){
//			@SuppressWarnings("unchecked")
//			List<Record> list = (List<Record>) map.get("tbody");
//			for(Record rec:list){
//				rec.set("title", rec.get("etitle"));
//				rec.set("content", rec.get("econtent"));
//			}
//		}
		@SuppressWarnings("unchecked")
		List<Record> list = (List<Record>) map.get("tbody");
		for(Record rec:list)
			rec = CreateForm.disposeRecord(rec, flag);
		renderJson(map);
	}
	
}
