package app.znkj.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.joda.time.DateTime;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.config.SessionInterceptor;
import app.znkj.model.AcademicCommittee;
import app.znkj.model.AnnualReport;
import app.znkj.model.Award;
import app.znkj.model.BaseModel;
import app.znkj.model.Communication;
import app.znkj.model.ConferenceArticle;
import app.znkj.model.Equipment;
import app.znkj.model.EquipmentUse;
import app.znkj.model.FundAchievement;
import app.znkj.model.Fundfile;
import app.znkj.model.Fundopen;
import app.znkj.model.IndexAchievement;
import app.znkj.model.IndexPic;
import app.znkj.model.Laboratory;
import app.znkj.model.LaboratoryChief;
import app.znkj.model.News;
import app.znkj.model.Notice;
import app.znkj.model.Patent;
import app.znkj.model.PeriodicalArticle;
import app.znkj.model.Poster;
import app.znkj.model.PosterAchievement;
import app.znkj.model.Project;
import app.znkj.model.ProjectAchievement;
import app.znkj.model.Recruit;
import app.znkj.model.Regulations;
import app.znkj.model.Relatedlink;
import app.znkj.model.ResearchDirections;
import app.znkj.model.ResearchTeam;
import app.znkj.model.Searchinfo;
import app.znkj.model.Software;
import app.znkj.model.Team;
import app.znkj.model.TeamUser;
import app.znkj.model.Thesis;
import app.znkj.model.Treatise;
import app.znkj.model.User;
import app.znkj.model.UserRole;
import app.znkj.util.CheckFormat;
import app.znkj.util.CreateForm;
import app.znkj.util.JavaBackupMysql;
import app.znkj.util.MD5;
import app.znkj.util.ResourcePathUtil;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;

/**
 * 新增或编辑--
 * @author xian.zf
 *
 * @date 2014-7-19
 */
@Before(SessionInterceptor.class)
public class AddOrUpdateController extends Controller{
	public static final Logger log = Logger.getLogger(AddOrUpdateController.class);
	
	
	/**
	 * 更新个人息信
	 * @author xian.zf
	 * @date 2014-7-21
	 * @throws Exception
	 */
	public void updateUserInfor() {
		User model = getModel(User.class, "info");
		model.set("id", getSession().getAttribute(Constant.SESSION_USERID));
		saveOrUpdateModel(model, "更新个人信息出错", null);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("degree") + ";" + model.getStr("email") + ";"
					   + model.getStr("researcharea") + ";" + model.getStr("title") 
					   + ";" + model.getStr("phone") + ";" + model.getStr("profession") ;
		this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
				content, Tname.TABLE_USER, model.getLong("id"),
				"/user_details?id="+ model.getLong("id") +"&type="+ model.getStr("degree") );
	}
	
	/**
	 * 更新个人息信
	 * @author xian.zf
	 * @date 2014-7-21
	 * @throws Exception
	 */
	public String updateUserInforById() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())
				|| !CheckFormat.isNumber(getPara("id"))){
			renderJson(CreateForm.errorMap("更新保存更新个人信息出错！"));
			return null;
		}
		
		User model = getModel(User.class, "info");
		model.set("id", Long.valueOf(getPara("id")));
		saveOrUpdateModel(model, "更新个人信息出错", null);

		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("degree") + ";" + model.getStr("email") + ";"
					   + model.getStr("researcharea") + ";" + model.getStr("title") 
					   + ";" + model.getStr("phone") + ";" + model.getStr("profession") ;
		this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
				content, Tname.TABLE_USER, model.getLong("id"),
				"/user_details?id="+ model.getLong("id") +"&type="+ model.getStr("degree") );
		return null;
	}
	
	/**
	 * 
	 * @author xian.zf
	 * @date 2015-11-7
	 * @return
	 */
	public String upOrDownUserInfor() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())
				|| !CheckFormat.isNumber(getPara("id"))){
			renderJson(CreateForm.errorMap("更新保存更新个人信息出错！"));
			return null;
		}
		
		User model = User.dao.findById(getPara("id"));
		if(model != null){
			String direction = getPara("direction");
			String sql = "", error="已经是第一条了";
			long sort = model.getLong("sort");
			long changsort = 0;
			if(direction.equals("up")){
				changsort = sort - 1;
			}else{
				changsort = sort + 1;
				error="已经是最后一条了";
			}
			sql = "select * from "+Tname.TABLE_USER+" t where t.sort=" + changsort;
			User changmodel = User.dao.findFirst(sql);
			if(changmodel != null){
				changmodel.set("sort", sort);
				model.set("sort", changsort);
				changmodel.update();
				model.update();
				renderJson(CreateForm.successMap());
			}else{
				renderJson(CreateForm.errorMap(error));
			}
		}
		
		return null;
		
	}
	
	
	/**
	 * 个人中心更改密码
	 * @author xian.zf
	 * @date 2014-8-13
	 */
	public void changeOwnPassword() {
		User info = User.dao.findById(getSession().getAttribute(Constant.SESSION_USERID));
		String oldPass = getPara("oldpassword");
		String newPass = getPara("newpassword");
		boolean flag = false;
		if(oldPass!=null && newPass!=null){
			oldPass = MD5.MD5Encode(oldPass);
			if(info.getStr("password").equals(oldPass)){
				info.set("password", MD5.MD5Encode(newPass));
				flag = info.update();
			}
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("旧密码出错！"));
	}
	
	/**
	 * 新增或修改期刊
	 * @author xian.zf
	 * @date 2014-7-22
	 */
	public void saveOrUpdatePerArt() {
		PeriodicalArticle model = getModel(PeriodicalArticle.class, "model");
		saveOrUpdateModel(model, "新增或修改期刊论文出错", Tname.TABLE_PERARTICLE);
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_PERARTICLE);

		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("periodical") + ";" + model.getStr("eperiodical") + ";"
					   + model.getStr("issue") + ";" + model.getStr("issn") + ";"
					   + model.getStr("authors") + ";"+ model.getStr("publishyear");
		this.saveSearchinfo(model.getStr("name"), model.getStr("publishyear"),
				content, Tname.TABLE_PERARTICLE, model.getLong("id"),
				"/findings_details?type=0&id=" + model.getLong("id"));
	}
	
	/**
	 * 新增或修改会议论文
	 * @author xian.zf
	 * @date 2014-7-22
	 */
	public void saveOrUpdateConArt() {
		ConferenceArticle model = getModel(ConferenceArticle.class, "model");
		saveOrUpdateModel(model, "新增或修改会议论文出错", Tname.TABLE_CONARTICLE);
		
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_CONARTICLE);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("periodical") + ";" + model.getStr("eperiodical") + ";"
					   + model.getStr("issue") + ";" + model.getStr("issn") + ";"
					   + model.getStr("authors") + ";"+ model.getStr("publishyear");
		this.saveSearchinfo(model.getStr("name"), model.getStr("publishyear"),
				content, Tname.TABLE_CONARTICLE, model.getLong("id"),
				"/findings_details?type=1&id=" + model.getLong("id"));
	}
	
	/**
	 * 新增或修改论文
	 * @author xian.zf
	 * @date 2014-7-22
	 */
	public void saveOrUpdateThesis() {
		Thesis model = getModel(Thesis.class, "model");
		saveOrUpdateModel(model, "新增或修改论文出错", Tname.TABLE_THESIS);
		
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_THESIS);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("unit") + ";" + model.getStr("eunit") + ";"
					   + model.getStr("tutor") + ";" + model.getStr("publishyear");
		this.saveSearchinfo(model.getStr("name"), model.getStr("publishyear"),
				content, Tname.TABLE_THESIS, model.getLong("id"),
				"/findings_details?type=2&id=" + model.getLong("id"));
	}
	
	/** 
	 *  新增或修改专著 
	 * @author deng.gzh 
	 * @date 2014-9-12 
	 * @Title: saveOrUpdateThesis 
	 * @return void 
	 * @throws Excetion
	 */

	public void saveOrUpdateTreatise() { 
		Treatise model = getModel(Treatise.class, "model"); 
		saveOrUpdateModel(model, "新增或修改专著出错", Tname.TABLE_TREATISE);
		
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_TREATISE);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("publish") + ";"
					   + model.getStr("authors") + ";" + model.getStr("publishyear") + ";"
					   + model.get("register") + ";"
					   + model.get("publishaddr") + ";" + model.getStr("isbn");
		this.saveSearchinfo(model.getStr("name"), model.getStr("publishyear"),
				content, Tname.TABLE_TREATISE, model.getLong("id"),
				"/findings_details?type=5&id=" + model.getLong("id"));
	}
	
	/**
	 * 新增或修改获奖
	 * @author xian.zf
	 * @date 2014-7-22
	 */
	public void saveOrUpdateAward() {
		Award model = getModel(Award.class, "model");
		saveOrUpdateModel(model, "新增或修改获奖出错", Tname.TABLE_AWARD);
		
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_AWARD);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") 
					   + ";" + model.getStr("certigier") + ";" + model.getStr("ecertigier") 
					   + ";" + model.getStr("year") + ";" + model.getStr("type") 
					   + ";" + model.getStr("awardname") + ";" + model.getStr("eawardname")
					   + ";" + model.getStr("level");
		this.saveSearchinfo(model.getStr("name"), model.getStr("year"),
				content, Tname.TABLE_AWARD, model.getLong("id"),
				"/findings_details?type=3&id=" + model.getLong("id"));
	}
	
	/**
	 * 新增或修改专利
	 * @author xian.zf
	 * @date 2014-7-22
	 */
	public void saveOrUpdatePatent() {
		Patent model = getModel(Patent.class, "model");
		saveOrUpdateModel(model, "新增或修改专利出错", Tname.TABLE_PATENT);
		
		// 关联项目
		projectAchSaveOrUpData( getPara("prostr"), model.getLong("id").intValue(), Tname.TABLE_PATENT);
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") 
					   + ";" + model.getStr("certigier") + ";" + model.getStr("ecertigier") 
					   + ";" + model.getStr("year") + ";" + model.getStr("type") 
					   + ";" + model.getStr("patentname") + ";" + model.getStr("epatentname");
		this.saveSearchinfo(model.getStr("name"), model.getStr("year"),
				content, Tname.TABLE_PATENT, model.getLong("id"),
				"/findings_details?type=4&id=" + model.getLong("id"));
	}
	
	/**
	 *  管理员----新增或修改学术交流
	 * @author xian.zf
	 * @date 2014-7-25
	 */
	public void saveOrUpdateCommunication() {
		Communication model = getModel(Communication.class, "model");
		model.set("register", getSession().getAttribute(Constant.SESSION_USERID).toString());
		saveOrUpdateLabModel(model, "新增或修改学术交流出错");
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename") + ";"
					   + model.getStr("type") + ";" + model.getStr("etype") + ";"
					   + model.getStr("address") + ";" + model.getStr("time") + ";";
		this.saveSearchinfo(model.getStr("name"), model.getStr("time"),
				content, Tname.TABLE_COMMUNICATION, model.getLong("id"),
				"/exchange_details?id=" + model.getLong("id"));
	}
	
	/**
	 * 新增或修改项目
	 * @author xian.zf
	 * @date 2014-7-25
	 */
	public void saveOrUpdateProject() throws Exception{
		Project model = getModel(Project.class, "model");
		model.set("lastedituser", getSession().getAttribute(Constant.SESSION_USERID));
		model.set("lastedittime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		
		String tableName = Tname.TABLE_PROJECT;
		boolean flag = false, upflag = false;
		if(CheckFormat.isNumber(model.getLong("id")+"")){
			upflag = true;
			//只能更新自已登记的项目
			if(tableName!=null && !"".equals(tableName)){
				Record rec = Db.findById(tableName, model.getLong("id"), "register");
				upflag = false;
				if(rec != null && 
						( rec.getInt("register").toString().equals(getSession().getAttribute(Constant.SESSION_USERID).toString()) 
						|| "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())) )
					upflag = true;
			}
			if(upflag)
				flag = model.update();	
		}else{
			model.set("register", getSession().getAttribute(Constant.SESSION_USERID));
			flag = model.save();
		}
		
		if(flag){
			//关联主要成果
			String achstr = getPara("achstr");
			//是更新项目的，先删除所有
			if(upflag){
				//删除所有
				String sql = "select * from project_achievement t where t.relid=" + model.getLong("id");
				List<ProjectAchievement> proachList = ProjectAchievement.dao.find(sql);
				for(ProjectAchievement proach:proachList)
					proach.delete();				
			}
			//选有关联成果的添加
			if(achstr!=null && !"".equals(achstr)){
				List<String> achList = new ArrayList<String>();
				if(achstr.indexOf(";")>0){
					String[] achstrs = achstr.split(";");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(achstr);
				//添加
				for(String str:achList){
					String[] achstrs = str.split(",");
					ProjectAchievement proAch = new ProjectAchievement();
					proAch.set("relid", model.getLong("id"));
					proAch.set("tname", achstrs[0]);
					proAch.set("achid", achstrs[1]);
					if (achstrs.length>2){
						proAch.set("isshow", achstrs[2].equals("0")?0:1);
					}
					proAch.save();
				}
			}
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
			
			//保存全文搜索
			String content = model.getStr("name") + ";" + model.getStr("ename") 
						   + ";" + model.getStr("restimebegin") + ";" + model.getStr("restimeend") 
						   + ";" + model.getStr("efundsource") + ";" + model.getStr("eparticipant")
						   + ";" + model.getStr("digest") + ";" + model.getStr("principal")
						   + ";" + model.getStr("year") + ";" + model.getStr("fundsource")
						   + ";" + model.getStr("contractno")+ ";" + model.getStr("participant");
			this.saveSearchinfo(model.getStr("name"), model.getStr("year"),
					content, Tname.TABLE_PROJECT, model.getLong("id"),
					"/projects_details?id=" + model.getLong("id"));
		}else
			renderJson(CreateForm.errorMap("新增或修改项目失败！"));
		
	}
	
	/**
	 * 管理员---- 新增或修改开放基金
	 * @author xian.zf
	 * @date 2014-7-25
	 */
	public void saveOrUpdateFund() throws Exception{
		Fundopen model = getModel(Fundopen.class, "model");
		boolean flag = false, upflag = false;
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)!=null
				&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				upflag = true;
				if(upflag)
					flag = model.update();	
			}else{
				model.set("register", getSession().getAttribute(Constant.SESSION_USERID));
				flag = model.save();
			}
		}
		
		if(flag){
			//关联主要成果
			String achstr = getPara("achstr");
			
			log.info("\n 关联主要成果:" + achstr);
			
			//如果是更新，先删除旧的关联成果
			if(upflag){
				//删除所有
				String sql = "select * from fund_achievement t where t.relid=" + model.getLong("id");
				List<FundAchievement> fundachList = FundAchievement.dao.find(sql);
				for(FundAchievement proach:fundachList)
					proach.delete();
			}
			//保存选择关联成果
			if(achstr!=null && !"".equals(achstr)){
				List<String> achList = new ArrayList<String>();
				if(achstr.indexOf(";")>0){
					String[] achstrs = achstr.split(";");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(achstr);
				//添加
				for(String str:achList){
					String[] achstrs = str.split(",");
					FundAchievement proAch = new FundAchievement();
					proAch.set("relid", model.getLong("id"));
					proAch.set("tname", achstrs[0]);
					proAch.set("achid", achstrs[1]);
					proAch.save();
				}
			}
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
			
			//保存全文搜索
			String content = model.getStr("title") + ";" + model.getStr("keyword") 
						   + ";" + model.getStr("direction") + ";" + model.getStr("digest") 
						   + ";" + model.getStr("year") + ";" + model.getStr("fundnumber")
						   + ";" + model.get("sum") + ";" + model.getStr("data");
			this.saveSearchinfo(model.getStr("title"), model.getStr("year"),
					content, Tname.TABLE_FUNDOPEN, model.getLong("id"),
					"/foundation_details?id=" + model.getLong("id"));
		}else
			renderJson(CreateForm.errorMap("新增或修改开放基金失败！"));
		
	}
	
	/**
	 * 期刊论文、会议论文、学位论文、专著、获奖、专利等关联项目，
	 * @author xian.zf
	 * @return 
	 * @date 2015-9-10
	 * @throws Exception
	 */
	private void projectAchSaveOrUpData(String prostr, Integer achid, String tname){
		//关联项目
		//删除所有
		try {
			String sql = "select * from project_achievement t where t.achid=" + achid + " and t.tname='"+tname+"'";
			List<ProjectAchievement> proachList = ProjectAchievement.dao.find(sql);
			for(ProjectAchievement proach:proachList)
				proach.delete();				
			
			//选有关联项目的添加
			if(prostr!=null && !"".equals(prostr)){
				List<String> achList = new ArrayList<String>();
				if(prostr.indexOf(";")>0){
					String[] achstrs = prostr.split(";");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(prostr);
				//添加
				for(String proid:achList){
					ProjectAchievement proAch = new ProjectAchievement();
					proAch.set("relid", proid);
					proAch.set("tname", tname);
					proAch.set("achid", achid);
					proAch.set("isshow", 1);
					proAch.save();
				}
			}
		} catch(Exception e){
			log.error("关联项目 出错，prostr:"+prostr+" achid:"+achid+" tname:"+tname);
			e.printStackTrace();
		}

	}
	
	/**
	 * 科研人员----新增或修改model对象, model主键key必须为 id
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	private void saveOrUpdateModel(BaseModel<?> model, String errorStr, String tableName) {
		boolean flag = false;
		try {
			model.set("lastedituser", getSession().getAttribute(Constant.SESSION_USERID));
			model.set("lastedittime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				boolean upflag = true;
				//只能更新自已登记的文章
				if(tableName!=null && !"".equals(tableName)){
					Record rec = Db.findById(tableName, model.getLong("id"), "register");
					upflag = false;
					if(rec!=null &&
							(rec.getInt("register").toString().equals(getSession().getAttribute(Constant.SESSION_USERID).toString())
							|| "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())))
						upflag = true;
				}
				if(upflag)
					flag = model.update();	
			}else{
				model.set("register", getSession().getAttribute(Constant.SESSION_USERID));
				flag = model.save();
			}
		} catch (Exception e) {
			log.error(errorStr);
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
		else
			renderJson(CreateForm.errorMap(errorStr));
	}
	
	/**
	 * 管理员----更新保存实验室信息
	 * @author xian.zf
	 * @date 2014-7-28
	 */
	public String updateLab() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新实验室信息出错！"));
			return null;
		}
		Laboratory lab = getModel(Laboratory.class,  "model");
		lab.set("id", 1);
		if(lab.update())
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("更新实验室信息出错！"));
		return null;
	}
	
	/**
	 * 管理员----更新保存实验室主任信息
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateLabChief() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存实验室主任信息出错！"));
			return null;
		}
		LaboratoryChief model = getModel(LaboratoryChief.class,  "model");
		saveOrUpdateLabModel(model, "更新保存实验室主任信息出错！");
		
		//TODO 详细页
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("ename");
		this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
				content, Tname.TABLE_LAB_CHIEF, model.getLong("id"),
				"/?id=" + model.getLong("id"));
		return null;
	}	
	
	/**
	 * 管理员----更新保存学术委员会信息
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateAcaComm() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存学术委员会信息出错！"));
			return null;
		}
		AcademicCommittee model = getModel(AcademicCommittee.class,  "model");
		saveOrUpdateLabModel(model, "更新保存学术委员会信息出错！");
		
		//保存全文搜索
		String content = model.getStr("name") + ";" + model.getStr("titles") + ";"
					   + model.getStr("tenure") + ";" + model.getStr("profession") + ";";
		this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
				content, Tname.TABLE_ACADEMIC_COM, model.getLong("id"),
				"/exchange_details?id=" + model.getLong("id"));
		return null;
	}
	
	/**
	 * 管理员----更新保存研究方向
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateResDir() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存研究方向信息出错！"));
			return null;
		}
		ResearchDirections model = getModel(ResearchDirections.class,  "model");
		boolean flag = false, upflag = false;
		try {
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				flag = model.update();	
				upflag = true;
			}else
				flag = model.save();
		} catch (Exception e) {
			log.error("更新保存研究方向信息出错！");
			e.printStackTrace();
		}
		if(flag){
			//添加关联研究团队
			String achstr = getPara("achstr");
			//更新的先删除
			if(upflag){
				//删除所有
				String sql = "select * from research_team t where t.resid=" + model.getLong("id");
				List<ResearchTeam> resTeamList = ResearchTeam.dao.find(sql);
				for(ResearchTeam resTeam:resTeamList)
					resTeam.delete();
			}
			if(achstr!=null && !"".equals(achstr)){
				List<String> achList = new ArrayList<String>();
				if(achstr.indexOf(";")>0){
					String[] achstrs = achstr.split(";");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(achstr);
				//添加
				for(String str:achList){
					ResearchTeam resTeam = new ResearchTeam();
					resTeam.set("resid", model.getLong("id"));
					resTeam.set("teamid", str);
					resTeam.save();
				}
			}
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
			
			//保存全文搜索
			String content = model.getStr("name") + ";" + model.getStr("ename") + ";";
			this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
					content, Tname.TABLE_RESEARCH, model.getLong("id"),
					"/studies");
		}else
			renderJson(CreateForm.errorMap("更新保存研究方向信息出错！"));
		return null;
	}
	
	/**
	 * 管理员----更新保存研究团队
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateTeam() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存研究团队出错！"));
			return null;
		}
		Team model = getModel(Team.class,  "model");
		boolean flag = false, upflag = false;
		try {
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				flag = model.update();	
				upflag = true;
			}else
				flag = model.save();
		} catch (Exception e) {
			log.error("更新保存研究团队出错！");
			e.printStackTrace();
		}
		if(flag){
			//添加关联科研人员
			String achstr = getPara("achstr");
			//更新的先删除
			if(upflag){
				//删除所有
				String sql = "select * from team_user t where t.teamid=" + model.getLong("id");
				List<TeamUser> teamUserList = TeamUser.dao.find(sql);
				for(TeamUser teamUser:teamUserList)
					teamUser.delete();
			}
			if(achstr!=null && !"".equals(achstr)){
				List<String> achList = new ArrayList<String>();
				if(achstr.indexOf("|;|")>0){
					String[] achstrs = achstr.split("\\|;\\|");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(achstr);

				//添加
				for(String str:achList){
					TeamUser teamUser = new TeamUser();
					teamUser.set("teamid", model.getLong("id"));
					String[] achstrs = str.split("\\|,\\|");
					teamUser.set("userid", achstrs[0]);
					teamUser.set("duty",   achstrs[1]);
					teamUser.save();
				}
			}
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
		}else
			renderJson(CreateForm.errorMap("更新保存研究团队出错！"));
		return null;
	}
	
	/**
	 * 管理员----更新保存设备
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateEqu() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存设备出错！"));
			return null;
		}
		Equipment model = getModel(Equipment.class,  "model");
		saveOrUpdateLabModel(model, "更新保存设备出错！");
		return null;
	}
	
	/**
	 * 管理员----更新保存设备使用记录
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateEquUse() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存设备使用记录出错！"));
			return null;
		}
		EquipmentUse model = getModel(EquipmentUse.class,  "model");
		saveOrUpdateLabModel(model, "更新保存设备使用记录出错！");
		return null;
	}
	
	/**
	 * 管理员----更新保存新闻
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateNews() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存新闻出错！"));
			return null;
		}
		News model = getModel(News.class,  "model");
		saveOrUpdateLabModel(model, "更新保存新闻出错！");
		
		//保存全文搜索
		String content = model.getStr("title") + ";" + model.getStr("etitle") + ";"
					   + model.getStr("time")  ;
		this.saveSearchinfo(model.getStr("title"), model.getStr("time"),
				content, Tname.TABLE_NEWS, model.getLong("id"),
				"/news_details?id=" + model.getLong("id"));
		return null;
	}
	
	/**
	 * 管理员----更新保存年报管理
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateAnnualReport() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存年报管理出错！"));
			return null;
		}
		AnnualReport model = getModel(AnnualReport.class,  "model");
		saveOrUpdateLabModel(model, "更新保存年报管理出错！");
		return null;
	}
	
	/**
	 * 管理员----更新保存学术海报
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdatePoster() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存学术海报出错！"));
			return null;
		}
		Poster model = getModel(Poster.class,  "model");
		boolean flag = false, upflag = false;
		try {
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				flag = model.update();	
				upflag = true;
			}else
				flag = model.save();
		} catch (Exception e) {
			log.error("更新保存学术海报出错！");
			e.printStackTrace();
		}
		if(flag){
			//添加关联成果
			String achstr = getPara("achstr");
			//更新的先删除
			if(upflag){
				//删除所有
				String sql = "select * from poster_achievement t where t.relid=" + model.getLong("id");
				List<PosterAchievement> posAchList = PosterAchievement.dao.find(sql);
				for(PosterAchievement posAch:posAchList)
					posAch.delete();
			}
			if(achstr!=null && !"".equals(achstr)){
				List<String> achList = new ArrayList<String>();
				if(achstr.indexOf(";")>0){
					String[] achstrs = achstr.split(";");
					for(String str:achstrs)
						achList.add(str);
				}else
					achList.add(achstr);
				//添加
				for(String str:achList){
					PosterAchievement posAch = new PosterAchievement();
					String[] achstrs = str.split(",");
					posAch.set("tname", achstrs[0]);
					posAch.set("achid",   achstrs[1]);
					posAch.set("resid", model.getLong("id"));
					posAch.save();
				}
			}
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
		}else
			renderJson(CreateForm.errorMap("更新保存学术海报出错！"));
		return null;
	}
	
	/**
	 * 管理员----更新保存通知公告
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateNotice() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存通知公告出错！"));
			return null;
		}
		Notice model = getModel(Notice.class,  "model");
		saveOrUpdateLabModel(model, "更新保存通知公告出错！");
		
		//保存全文搜索
		String content = model.getStr("title") + ";" + model.getStr("etitle") + ";"
					   + model.getStr("time")  ;
		this.saveSearchinfo(model.getStr("title"), model.getStr("time"),
				content, Tname.TABLE_NOTICE, model.getLong("id"),
				"/notice_details?id=" + model.getLong("id"));
		return null;
	}
	
	/**
	 * 管理员----更新保存规章制度
	 * @author xian.zf
	 * @date 2014-7-29
	 * @return
	 */
	public String saveOrUpdateRegulations() throws Exception{
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存规章制度出错！"));
			return null;
		}
		String path = ResourcePathUtil.getPath() ;
		//本地使用
		UploadFile upfile = getFile("uploadpdf",  path + Constant.TEMPORARY);
		Regulations model = getModel(Regulations.class,  "model");
		if(upfile != null){
			String ufName = upfile.getFileName();
			
			String newName = new DateTime().toString("yyyyMMddHHmmss") 
					       + ufName.substring(ufName.lastIndexOf("."));
			
			if(upfile.getFile().renameTo(new File(path + Constant.RULEPDFPATH + newName))){
				model.set("pdf", Constant.RULEPDFPATH + newName);
				model.set("pdfname", ufName);
				
				//如果是修改、删除原来的文件
				if(CheckFormat.isNumber(model.getLong("id")+"")){
					Record rule = Db.findById(Tname.TABLE_REGULATIONS, model.getLong("id"));
					if(rule!=null)
						DeleteController.deleteFile(rule.getStr("pdf"));
				}
			}
		}else{
			//如果是修改
			if((model.getStr("pdf")==null || "".equals(model.getStr("pdf")))
					&& CheckFormat.isNumber(model.getLong("id")+"")){
				Record rule = Db.findById(Tname.TABLE_REGULATIONS, model.getLong("id"));
				if(rule!=null)
					DeleteController.deleteFile(rule.getStr("pdf"));
			}
		}
		saveOrUpdateLabModel(model, "更新保存规章制度出错！");
		
		//保存全文搜索
		String content = model.getStr("title") + ";" + model.getStr("pdfname") ;
		this.saveSearchinfo(model.getStr("title"), model.getStr("time"),
				content, Tname.TABLE_REGULATIONS, model.getLong("id"),
				"/regulation_details?id=" + model.getLong("id"));
		
		return null;
	}
	
	/**
	 * 管理员----更新保存用户
	 * @author xian.zf
	 * @date 2014-8-1
	 * @return
	 */
	public String saveOrUpdateUser() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存用户出错！"));
			return null;
		}
		User model = getModel(User.class,  "model");
		boolean flag = false, upflag = false;
		try {
			if(CheckFormat.isNumber(model.getLong("id")+"")){
				model.remove("password");
				flag = model.update();	
				upflag = true;
			}else{
				model.set("password", MD5.MD5Encode(model.getStr("password")));
				//设置排序
				String sql = "select * from "+Tname.TABLE_USER+" u order by u.sort desc" ;
				User changmodel = User.dao.findFirst(sql);
				if(changmodel == null){
					model.set("sort", 1);
				} else {
					model.set("sort", changmodel.getLong("sort") + 1 );
				}
				flag = model.save();
				//保存全文搜索
				String content = model.getStr("name") + ";";
				this.saveSearchinfo(model.getStr("name"), new DateTime().toString("yyyy-MM-dd"),
						content, Tname.TABLE_USER, model.getLong("id"),
						"/user_details?id="+ model.getLong("id") +"&type=教师" );
			}
			
			if(flag){
				//需要添加的角色
				if(upflag){
					//先删除之前的关联权限
					List<UserRole> urList = UserRole.dao.find("SELECT * FROM user_role u WHERE u.userid=" + model.getLong("id"));
					for(UserRole ur:urList)
						ur.delete();
				}
				String roleids = getPara("roleids");
				List<String> newrolList = new ArrayList<String>();
				if(roleids.indexOf(",")>0){
					String[] achstrs = roleids.split(",");
					for(String str:achstrs)
						newrolList.add(str);
				}else
					newrolList.add(roleids);
				for(String str:newrolList){
					UserRole userRole = new UserRole();
					userRole.set("roleid", str);
					userRole.set("userid", model.getLong("id"));
					userRole.save();
				}
				renderJson(CreateForm.successMap(model.getLong("id").toString()));
			}else
				renderJson(CreateForm.errorMap("更新保存用户！"));
		} catch (Exception e) {
			log.error("更新保存用户出错！");
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 管理员----更新保存招聘
	 * @author xian.zf
	 * @date 2014-8-21
	 * @return
	 */
	public String saveOrUpdateRecruit() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存招聘出错！"));
			return null;
		}
		Recruit model = getModel(Recruit.class,  "model");
		saveOrUpdateLabModel(model, "更新保存招聘出错！");
		//保存全文搜索
		String content = model.getStr("title") + ";" + model.getStr("etitle")
					   + ";" + model.getStr("time") ;
		this.saveSearchinfo(model.getStr("title"), model.getStr("time"),
				content, Tname.TABLE_RECRUIT, model.getLong("id"),
				"/job_details?id=" + model.getLong("id"));
		return null;
	}
	
	/**
	 * 更新保存----最新成果
	 * @author xian.zf
	 * @date 2014-8-5
	 * @throws Exception
	 */
	public String saveOrUpdateIndexAch() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("最新成果出错！"));
			return null;
		}

		String path = ResourcePathUtil.getPath() ;
		//本地使用
		UploadFile upfile = getFile("upfile",  path + Constant.ACHIEVEMENT);
		IndexAchievement model = getModel(IndexAchievement.class,  "model");
		if(upfile != null){
			String ufName = upfile.getFileName();
			String newName = new DateTime().toString("yyyyMMddHHmmss") 
					       + ufName.substring(ufName.lastIndexOf("."));
			if(upfile.getFile().renameTo(new File(path + Constant.ACHIEVEMENT + newName))){
				model.set("pic", Constant.ACHIEVEMENT + newName);
				//如果是修改、删除原来的文件
				if(CheckFormat.isNumber(model.getLong("id")+"")){
					Record oldmodel = Db.findById(Tname.TABLE_INDEXACH, model.getLong("id"));
					if(oldmodel != null)
						DeleteController.deleteFile(oldmodel.getStr("pic"));
				}
			}
		}
		model.set("cretime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));//时间
		saveOrUpdateLabModel(model, "最新成果出错！");
		return null;
	}
	
	/**
	 * 更新保存----成果展示平台
	 * @author xian.zf
	 * @date 2014-9-24
	 * @throws Exception
	 */
	public String saveOrUpdateSoftware() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("成果展示平台出错！"));
			return null;
		}

		String path = ResourcePathUtil.getPath() ;
		//本地使用
		UploadFile upfile = getFile("upfile",  path + Constant.SOFTWAREPATH);
		Software model = getModel(Software.class,  "model");
		if(upfile != null){
			String ufName = upfile.getFileName();
			String newName = new DateTime().toString("yyyyMMddHHmmss") 
					       + ufName.substring(ufName.lastIndexOf("."));
			if(upfile.getFile().renameTo(new File(path + Constant.SOFTWAREPATH + newName))){
				model.set("pic", Constant.SOFTWAREPATH + newName);
				//如果是修改、删除原来的文件
				if(CheckFormat.isNumber(model.getLong("id")+"")){
					Record oldmodel = Db.findById(Tname.SOFTWARE, model.getLong("id"));
					if(oldmodel != null)
						DeleteController.deleteFile(oldmodel.getStr("pic"));
				}
			}
		}
		saveOrUpdateLabModel(model, "成果展示平台出错！");
		return null;
	}
	
	/**
	 * 更新保存----首页图片
	 * @author xian.zf
	 * @date 2014-9-24
	 * @throws Exception
	 */
	public String saveOrUpdateIndexPic() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("首页图片出错！"));
			return null;
		}

		String path = ResourcePathUtil.getPath() ;
		//本地使用
		UploadFile upfile = getFile("upfile",  path + Constant.INDEXPICPATH);
		IndexPic model = getModel(IndexPic.class,  "model");
		model.set("time", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
		if(upfile != null){
			String ufName = upfile.getFileName();
			String newName = new DateTime().toString("yyyyMMddHHmmss") 
					       + ufName.substring(ufName.lastIndexOf("."));
			if(upfile.getFile().renameTo(new File(path + Constant.INDEXPICPATH + newName))){
				model.set("pic", Constant.INDEXPICPATH + newName);
				//如果是修改、删除原来的文件
				if(CheckFormat.isNumber(model.getLong("id")+"")){
					Record oldmodel = Db.findById(Tname.INDEX_PIC, model.getLong("id"));
					if(oldmodel != null)
						DeleteController.deleteFile(oldmodel.getStr("pic"));
				}
			}
		}
		saveOrUpdateLabModel(model, "首页图片出错！");
		return null;
	}
	
	
	/**
	 * 管理员----更新保存开放基金附件
	 * @author xian.zf
	 * @date 2015-9-7
	 * @return
	 */
	public String saveOrUpdateFundfile() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存开放基金附件出错！"));
			return null;
		}
		Fundfile model = getModel(Fundfile.class,  "model");
		if(!CheckFormat.isNumber(model.getLong("id")+"")){
			Fundfile lastfile = Fundfile.dao.findFirst("select * from "+Tname.Fundfile+" t order by t.sort desc");
			if (lastfile != null) {
				model.set("sort", lastfile.getInt("sort")+1 );
			}
		}
		saveOrUpdateLabModel(model, "更新保存开放基金附件出错！");
		return null;
	}
	
	/**
	 * 上下移开放基金附件
	 * @author xian.zf
	 * @date 2015-9-8
	 * @return
	 */
	public String upOrDownFundfile() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存开放基金附件出错！"));
			return null;
		}
		Fundfile model = Fundfile.dao.findById(getPara("id"));
		if(model!=null){
			String direction = getPara("direction");
			String sql = "", error="已经是第一条了";
			int sort = model.getInt("sort");
			int changsort = 0;
			if(direction.equals("up")){
				changsort = sort - 1;
			}else{
				changsort = sort + 1;
				 error="已经是最后一条了";
			}
			sql = "select * from "+Tname.Fundfile+" t where t.sort=" + changsort;
			Fundfile changmodel = Fundfile.dao.findFirst(sql);
			if(changmodel!=null){
				changmodel.set("sort", sort);
				model.set("sort", changsort);
				changmodel.update();
				model.update();
				renderJson(CreateForm.successMap());
			}else{
				renderJson(CreateForm.errorMap(error));
			}
		}
		return null;
	}
	
	/**
	 * 管理员----更新保存相关网站挂接
	 * @author xian.zf
	 * @date 2015-9-7
	 * @return
	 */
	public String saveOrUpdateRelatedlink() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存相关网站挂接出错！"));
			return null;
		}
		Relatedlink model = getModel(Relatedlink.class,  "model");
		
		if(!CheckFormat.isNumber(model.getLong("id")+"")){
			Relatedlink lastfile = Relatedlink.dao.findFirst("select * from "+Tname.Relatedlink+" t order by t.sort desc");
			if (lastfile != null) {
				model.set("sort", lastfile.getInt("sort")+1 );
			}
		}
		saveOrUpdateLabModel(model, "更新保存相关网站挂接出错！");
		return null;
	}
	
	/**
	 * 上下移相关网站挂接
	 * @author xian.zf
	 * @date 2015-9-8
	 * @return
	 */
	public String upOrDownRelatedlink() {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("更新保存开放基金附件出错！"));
			return null;
		}

		Relatedlink model = Relatedlink.dao.findById(getPara("id"));
		if(model!=null){
			String direction = getPara("direction");
			String sql = "", error="已经是第一条了";
			int sort = model.getInt("sort");
			int changsort = 0;
			if(direction.equals("up")){
				changsort = sort - 1;
			}else{
				changsort = sort + 1;
				 error="已经是最后一条了";
			}
			sql = "select * from "+Tname.Relatedlink+" t where t.sort=" + changsort;
			Relatedlink changmodel = Relatedlink.dao.findFirst(sql);
			if(changmodel!=null){
				changmodel.set("sort", sort);
				model.set("sort", changsort);
				changmodel.update();
				model.update();
				renderJson(CreateForm.successMap());
			}else{
				renderJson(CreateForm.errorMap(error));
			}
		}
		return null;
	}
	
	/**
	 * 重置密码
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void changePassword() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("重置密码出错！"));
		}else{
			User user = User.dao.findById(getPara("id"));
			if(user != null 
					&& user.set("password", MD5.MD5Encode("111111")).update())
				renderJson(CreateForm.successMap());
			else
				renderJson(CreateForm.errorMap("重置密码出错！"));
		}
	}
	
	/**
	 * 检查是该帐号是否可以注册（非重复）
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void checkAccount() throws Exception {
		User user = User.dao.findFirst("select * from user u where u.account='"+ getPara("account") +"'");
		if(user==null)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("存在重复帐号！"));
	}
	
	/**
	 * 管理员----新增或修改model对象, model主键key必须为 id
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	private void saveOrUpdateLabModel(BaseModel<?> model, String errorStr) {
		boolean flag = false;
		try {
			if(CheckFormat.isNumber(model.getLong("id")+""))
				flag = model.update();	
			else
				flag = model.save();
		} catch (Exception e) {
			log.error(errorStr);
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap(model.getLong("id").toString()));
		else
			renderJson(CreateForm.errorMap(errorStr));
	}
	
	
	/**
	 * 上传文件至临时文件夹
	 * @author xian.zf
	 * @date 2014-7-24
	 * @return
	 */
	public String uploadTempFile() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try{
			String path = ResourcePathUtil.getPath() + Constant.TEMPORARY;
			//本地使用
//			String path = PathKit.getWebRootPath();
			UploadFile upfile = getFile("upfile",  path );
			if(upfile!=null){
				String ufName = upfile.getFileName();
				System.out.print("\n ufName : " + ufName);
				
				String newName = new DateTime().toString("yyyyMMddHHmmss") 
						       + ufName.substring(ufName.lastIndexOf("."));
				System.out.print("\n newName : " + newName);
				
				if(upfile.getFile().renameTo(new File(path + newName)))
					System.out.print("\n 转名成功: " + true);
				mp.put(Constant.SUCCES, true);
				mp.put("url", Constant.TEMPORARY + newName);
				mp.put("oldname", ufName);
				mp.put("filename", newName);
			}else
				mp.put(Constant.SUCCES, false);
		}catch(Exception e){
			mp.put(Constant.SUCCES, false);
			log.error("上传至临时文件夹出错");
			e.printStackTrace();
		}
//		renderJson(mp);
		render(new JsonRender(mp).forIE());
		return null;
	}
	
	/**
	 * 个人中心---上传文件至临时文件夹
	 * @author xian.zf
	 * @date 2014-7-24
	 * @return
	 */
	public String uploadTempPhotoFile() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try{
			String path = ResourcePathUtil.getPath() + Constant.TEMPORARY;
			//本地使用
//			String path = PathKit.getWebRootPath();
			UploadFile upfile = getFile("upfile",  path );
			if(upfile!=null){
				String ufName = upfile.getFileName();
				String newName = new DateTime().toString("yyyyMMddHHmmss") 
						       + ufName.substring(ufName.lastIndexOf("."));
				File f = upfile.getFile();
				int needwidth=300;
				int needheight=300;
				double h=1;
				BufferedImage src = ImageIO.read(f); // 读入文件  
		        int width = src.getWidth(); // 得到源图宽  
		        int height = src.getHeight(); // 得到源图长  
		        if(width>height){
		        	if(width<300){
		        		h= 1.0*300/width;
		        		needwidth=(int)(width*h);
		        		needheight=(int)(height*h);
		        	}else{
		        		h= 1.0*width/300;
		        		needwidth=(int)(width/h);
		        		needheight=(int)(height/h);
		        	}
		        }else{
		        	if(height<300){
		        		h= 1.0*300/height;
		        		needwidth=(int)(width*h);
		        		needheight=(int)(height*h);
		        	}else{

		        		h= 1.0*height/300;
		        		needwidth=(int)(width/h);
		        		needheight=(int)(height/h);
		        	}
		        }
		        BufferedImage tag = new BufferedImage(needwidth ,needheight, BufferedImage.TYPE_INT_RGB); // 缩放图像
		        Image image = src.getScaledInstance(needwidth ,needheight,Image.SCALE_SMOOTH); 
		        Graphics g = tag.getGraphics();  
		        g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
		        g.dispose();
		        ImageIO.write(tag, "JPEG", new File(path + newName));// 输出到文件流  
				mp.put(Constant.SUCCES, true);
				mp.put("url", Constant.TEMPORARY + newName);
				mp.put("oldname", ufName);
				mp.put("filename", newName);
			}else
				mp.put(Constant.SUCCES, false);
		}catch(Exception e){
			mp.put(Constant.SUCCES, false);
			log.error("上传至临时文件夹出错");
			e.printStackTrace();
		}
//		renderJson(mp);
		render(new JsonRender(mp).forIE());
		return null;
	}
	
	/**
	 * 临时文件夹转移至文件夹
	 * @author xian.zf
	 * @date 2014-7-24
	 * @return
	 */
	public String uploadPhotoFile() {
		try{
			String path = PathKit.getWebRootPath();
			UploadFile file = getFile("photo",  path + Constant.PHOTOPATH);
			file.getFile().renameTo(new File(path + Constant.PHOTOPATH + "1235646.jpg"));
		}catch(Exception e){
			log.error("上传至临时文件夹出错");
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 剪切上传的头像
	 * @author xian.zf
	 * @date 2014-8-2
	 * @return
	 * @throws Exception
	 */
	public String changePicture() throws Exception{
		int x=0;
		int y = 0;
		String xx = getPara("x")==null?"0":getPara("x");
		String yy = getPara("y")==null?"0":getPara("y");
		String filename = getPara("filename");
		int needwidth = 150;
		int needheight = 220;
		String needwid =  getPara("needwidth")==null?"150":getPara("needwidth");
		String needhei = getPara("needheight")==null?"220":getPara("needheight");
		try{
			needwidth=needwid.matches("[-+]?[0-9]*")?Integer.parseInt(needwid):(int)Float.parseFloat(needwid);
			needheight=needhei.matches("[-+]?[0-9]*")?Integer.parseInt(needhei):(int)Float.parseFloat(needhei);
			x=xx.matches("[-+]?[0-9]*")?Integer.parseInt(xx):(int)Float.parseFloat(xx);
			y=yy.matches("[-+]?[0-9]*")?Integer.parseInt(yy):(int)Float.parseFloat(yy);
			Image img;
			ImageFilter cropFilter;
			String path = ResourcePathUtil.getPath() ;
			
			BufferedImage bi = ImageIO.read(new File(path + Constant.TEMPORARY + filename));
			int srcwidth = bi.getWidth();
			int srcheight = bi.getHeight();
			Image image = bi.getScaledInstance(srcwidth, srcheight,  
	                    Image.SCALE_DEFAULT);  
            cropFilter = new CropImageFilter(x, y, needwidth, needheight);  
            img = Toolkit.getDefaultToolkit().createImage(  
                    new FilteredImageSource(image.getSource(), cropFilter));  
            BufferedImage tag = new BufferedImage(needwidth, needheight,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = tag.getGraphics();  
            g.drawImage(img, 0, 0, null); // 绘制缩小后的图  
            g.dispose();  
            // 输出为文件  
    		String fileType = filename.substring(filename.lastIndexOf("."));
    		String dataString = new DateTime().toString("yyyyMMddhhmmss");
            ImageIO.write(tag, "JPEG", new File(path + Constant.PHOTOPATH + dataString + fileType));
            Map<String,Object>  map = new HashMap<String,Object>();
    	    map.put("success", true);
    	    map.put("picname", dataString + fileType);
    		map.put("message", Constant.PHOTOPATH + dataString + fileType);
    		if(!"".equals(filename)){
    			File  pic = new  File(path + Constant.TEMPORARY +filename);
        		if(pic.exists()){
        			pic.delete();
        		}
    		}
    		
//    		renderJson(map);
    		render(new JsonRender(map).forIE());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 上传文件至文件夹
	 * @author xian.zf
	 * @date 2014-08-05
	 * @return
	 */
	public String uploadFile() {
		Map<String, Object> mp = new HashMap<String, Object>();
		try{
			String path = ResourcePathUtil.getPath() + Constant.ATTACHMENT + getPara("type") +"/";
			UploadFile upfile = getFile("upfile",  path);
			if(upfile!=null){
				String ufName = upfile.getFileName();
				
				System.out.print("文件原名：" + ufName);
				
				String newName = new DateTime().toString("yyyyMMddHHmmss") 
						       + ufName.substring(ufName.lastIndexOf("."));
				//转名
				if(upfile.getFile().renameTo(new File(path + newName)))
					System.out.print("转名成功：" + newName);

				mp.put(Constant.SUCCES, true);
				mp.put("url",  Constant.ATTACHMENT + getPara("type") +"/" + newName);
				mp.put("oldname", ufName);
				mp.put("filename", newName);
			}else
				mp.put(Constant.SUCCES, false);
		}catch(Exception e){
			mp.put(Constant.SUCCES, false);
			log.error("上传文件至"+ getPara("type") +"文件夹出错");
			e.printStackTrace();
		}
//		renderJson(mp);
		render(new JsonRender(mp).forIE());
		return null;
	}
	
	/**
	 * 上传文件至文件夹
	 * @author xian.zf
	 * @date 2014-08-05
	 * @return
	 */
	public String uploadImgFile() {
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("error", 1);
		try{
			String path = ResourcePathUtil.getPath() + Constant.ATTACHMENT + getPara("type") +"/";
			UploadFile upfile = getFile("imgFile",  path);
			if(upfile!=null){
				String ufName = upfile.getFileName();
				
				System.out.print("文件原名：" + ufName);
				
				String newName = new DateTime().toString("yyyyMMddHHmmss") 
						       + ufName.substring(ufName.lastIndexOf("."));
				//转名
				if(upfile.getFile().renameTo(new File(path + newName)))
					System.out.print("转名成功：" + newName);

				mp.put(Constant.SUCCES, true);
				mp.put("url",  Constant.ATTACHMENT + getPara("type") +"/" + newName);
				mp.put("oldname", ufName);
				mp.put("filename", newName);
				mp.put("error", 0);
			}else
				mp.put(Constant.SUCCES, false);
		}catch(Exception e){
			mp.put(Constant.SUCCES, false);
			log.error("上传文件至"+ getPara("type") +"文件夹出错");
			e.printStackTrace();
		}
//		renderJson(mp);
		render(new JsonRender(mp).forIE());
		return null;
	}
	
	/**
	 * 科研管理--专著图片上传
	 * @author deng.gzh 
	 * @date 2014-9-13 
	 * @Title: uploadTreatiseFile 
	 * @return String 
	 * @throws Excetion
	 */
	public String uploadTreatiseFile() {
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("error", 1);
		try{
			String path = ResourcePathUtil.getPath() + Constant.ATTACHMENT +getPara("type") +"/";
			UploadFile upfile = getFile("imgFile",  path);
			if(upfile!=null){
				String ufName = upfile.getFileName();
				
				System.out.print("文件原名：" + ufName);
				
				String newName = new DateTime().toString("yyyyMMddHHmmss") 
						       + ufName.substring(ufName.lastIndexOf("."));
				
				//压缩图片
				Thumbnails.of(path  + ufName)
				          .size(400, 500)  //压缩比例为400*500
				          .outputFormat(ufName.substring(ufName.lastIndexOf(".")+1) )//图片输出格式 
				          .toFile(path + ufName);//输出地址
				
				//转名
				if(upfile.getFile().renameTo(new File(path + newName)))
					System.out.print("转名成功：" + newName);

				mp.put(Constant.SUCCES, true);
				mp.put("url",  Constant.ATTACHMENT + getPara("type") +"/" + newName);
				mp.put("oldname", ufName);
				mp.put("filename", newName);
				mp.put("error", 0); 
			}else
				mp.put(Constant.SUCCES, false);
		}catch(Exception e){
			mp.put(Constant.SUCCES, false);
			log.error("上传文件至"+ getPara("type") +"文件夹出错");
			e.printStackTrace();
		}
//		renderJson(mp);
		render(new JsonRender(mp).forIE());
		return null;
	}
	
	
	
	/**
	 * 新增或修改 全文修搜信息
	 * @author xian.zf
	 * @date 2014-8-22
	 * @throws Exception
	 */
	private void saveSearchinfo(String title, String time, String content,
			String tname,Long relid, String url) {
		String sql = "SELECT * FROM searchinfo s WHERE s.tname='"+tname+"'"
				  + " AND s.relid=" + relid ;
		Searchinfo model = Searchinfo.dao.findFirst(sql);
		boolean flag = false;
		if(!CheckFormat.checkTimeFormat(time)){
			if(CheckFormat.isYearNumber(time))
				time = time + new DateTime().toString("-MM-dd");
			else
				time = new DateTime().toString("yyyy-MM-dd");
		}
		if(model == null){
			model = new Searchinfo();
			flag = true;
		}
		model.set("time", time);
		model.set("title", title);
		model.set("tname", tname);
		model.set("content", content);
		model.set("relid", relid);
		model.set("url", url);
		if(flag)
			model.save();
		else
			model.update();
	}
	
	/**
	 * 备份数据
	 * @author xian.zf
	 * @date 2014-9-25
	 * @return
	 * @throws Exception
	 */
	public String backups() throws Exception {
		renderJson(JavaBackupMysql.backup(new DateTime().toString("yyyyMMddHHmmss") + ".sql", 
				Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString())));
		return null;
	}
	
	/**
	 * 还原数据
	 * @author xian.zf
	 * @date 2014-9-25
	 * @throws Exception
	 */
	public void recover() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("没有管理员权限！"));
		}else{
			renderJson(JavaBackupMysql.recover( getPara("name"), 
					Integer.valueOf(getSession().getAttribute(Constant.SESSION_USERID).toString())));
		}
	}
}
