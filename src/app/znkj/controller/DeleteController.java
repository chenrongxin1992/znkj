package app.znkj.controller;

import java.io.File;
import java.util.List;

import app.znkj.common.Constant;
import app.znkj.common.Tname;
import app.znkj.config.SessionInterceptor;
import app.znkj.model.EquipmentUse;
import app.znkj.model.FundAchievement;
import app.znkj.model.PosterAchievement;
import app.znkj.model.ProjectAchievement;
import app.znkj.model.ResearchTeam;
import app.znkj.model.TeamUser;
import app.znkj.model.UserRole;
import app.znkj.util.CreateForm;
import app.znkj.util.ResourcePathUtil;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

@Before(SessionInterceptor.class)
public class DeleteController extends Controller{
	public static final Logger log = Logger.getLogger(DeleteController.class);
	
	/**
	 * 科研管理----删除科研人员登记的实例（无关联关系）
	 * @author xian.zf
	 * @date 2014-7-23
	 */
	public void deleteResModel() {
		boolean flag = false;  
		try{
			Record rec = Db.findById(getPara("tkey"), getPara("id"));
			String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
			if(rec != null 
					&& (userid.equals(rec.getInt("register")+""))
					|| "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString()) ){ 
				if(Db.deleteById(getPara("tkey"), getPara("id")))
					flag = true;  
			}
		}catch (Exception e) {
			log.error("删除出错，tkey:" + getPara("tkey") + " id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 科研管理----删除科研项目的实例
	 * @author xian.zf
	 * @date 2014-7-28
	 */
	public void deletePro(){
		boolean flag = false;
		try{
			Record rec = Db.findById(Tname.TABLE_PROJECT, getPara("id"));
			String userid = getSession().getAttribute(Constant.SESSION_USERID).toString();
			if(rec != null 
					&& ( userid.equals(rec.getInt("register")+"")
					|| "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString()) ) ){
				if(Db.deleteById(Tname.TABLE_PROJECT, getPara("id"))){
					List<ProjectAchievement> proachList = ProjectAchievement.dao.find("select * from "+Tname.TABLE_PROJECTACH+" t where t.relid="+rec.getLong("id"));
					for(ProjectAchievement proach:proachList)
						proach.delete();
					flag = true;
				}
			}
		}catch (Exception e) {
			log.error("删除科研项目出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 科研管理----删除开放基金的实例
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deleteFund(){
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_FUNDOPEN, getPara("id"))){
					List<FundAchievement> achList = FundAchievement.dao.find("select * from "+Tname.TABLE_FUNDACH+" t where t.relid="+getPara("id"));
					for(FundAchievement ach:achList)
						ach.delete();
					flag = true;
				}
			}
		}catch (Exception e) {
			log.error("删除开放基金出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 实验室管理----删除无关联关系的实例
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deleteLabModel() {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				for(String table:Constant.LABDELETE){
					if(table.equals(getPara("tkey"))){
						if(Db.deleteById(getPara("tkey"), getPara("id")))
							flag = true;
						break;
					}
				}
			}
		}catch (Exception e) {
			log.info("删除出错，tkey:" + getPara("tkey") + " id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 实验室管理----删除研究方向
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deleteResDir() {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_RESEARCH, getPara("id"))){
					flag = true;
					List<ResearchTeam> mlist = ResearchTeam.dao.find("select * from research_team t where t.resid=" + getPara("id"));
					for(ResearchTeam model:mlist)
						model.delete();
				}
			}
		}catch (Exception e) {
			log.error("删除研究方向出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 实验室管理----删除研究团队
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deleteTeam() {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_TEAM, getPara("id"))){
					flag = true;
					List<TeamUser> tlist = TeamUser.dao.find("select * from team_user t where t.teamid=" + getPara("id"));
					for(TeamUser tuser:tlist)
						tuser.delete();
				}
			}
		}catch (Exception e) {
			log.error("删除研究团队出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 实验室管理----删除设备
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deleteEqu() {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_EQUIPMENT, getPara("id"))){
					flag = true;
					List<EquipmentUse> mlist = EquipmentUse.dao.find("select * from equipment_use t where t.equid=" + getPara("id"));
					for(EquipmentUse model:mlist)
						model.delete();
				}
			}
		}catch (Exception e) {
			log.error("删除设备出错，id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 实验室管理----删除学术海报
	 * @author xian.zf
	 * @date 2014-7-31
	 */
	public void deletePoster() {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_POSTER, getPara("id"))){
					flag = true;
					List<PosterAchievement> mlist = PosterAchievement.dao.find("select * from poster_achievement t where t.relid=" + getPara("id"));
					for(PosterAchievement model:mlist)
						model.delete();
				}
			}
		}catch (Exception e) {
			log.error("删除学术海报出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除出错"));
	}
	
	/**
	 * 删除附件
	 * @author xian.zf
	 * @throws Exception 
	 * @date 2014-8-2
	 */
	public void deleteFile() throws Exception {
		//具有管理员权限才能修改
		if(getSession().getAttribute(Constant.MANAGEROLE)==null
				|| !"1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
			renderJson(CreateForm.errorMap("无权删除！"));
		}
		String path = getPara("filePath");
		path = ResourcePathUtil.getPath() + path;
		File file = new File(path);
		if(file.exists()){
			if (file.delete()) {
				renderJson(CreateForm.successMap());
			} else {
				renderJson(CreateForm.errorMap("删除失败！"));
			}
		}else
			renderJson(CreateForm.errorMap("文件不存在！"));
		
	}
	
	/**
	 * 删除文件
	 * @author xian.zf
	 * @date 2014-8-2
	 * @param filePath
	 * @throws Exception
	 */
	public static boolean deleteFile(String filePath) throws Exception {
		String path = ResourcePathUtil.getPath() + filePath;
		File file = new File(path);
		boolean flag = false;
		if(file.exists())
			if (file.delete()) 
				flag = true;
			else
				log.error("删除文件出失败，filePath："+filePath);
		else
			log.error("删除的文件不存在，filePath："+filePath);
		return flag;
	}
	
	/**
	 * 删除规章制度
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void deleteRule() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				Record model = Db.findById(Tname.TABLE_REGULATIONS, getPara("id"));
				if(model!=null && Db.deleteById(Tname.TABLE_REGULATIONS, getPara("id")))
					flag = true;
					//存在附件的删除附件
					if(model.getStr("pdf")!=null && !"".equals(model.getStr("pdf")))
						deleteFile(model.getStr("pdf"));
			}
		}catch (Exception e) {
			log.info("删除出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除规章制度"));
	}
	
	/**
	 * 删除用户
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void deleteUser() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				if(Db.deleteById(Tname.TABLE_USER, getPara("id"))){
					flag = true;
					List<UserRole> mlist = UserRole.dao.find("select * from user_role t where t.userid=" + getPara("id"));
					for(UserRole model:mlist)
						model.delete();
				}
			}
		}catch (Exception e) {
			log.error("删除用户出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除用户"));
	}
	
	/**
	 * 删除最新成果
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void deleteIndexAch() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				Record model = Db.findById(Tname.TABLE_INDEXACH, getPara("id"));
				if(model!=null && Db.deleteById(Tname.TABLE_INDEXACH, getPara("id")))
					flag = true;
					//存在附件的删除附件
					if(model.getStr("pic")!=null && !"".equals(model.getStr("pic")))
						deleteFile(model.getStr("pic"));
			}
		}catch (Exception e) {
			log.info("删除出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除最新成果"));
	}
	
	/**
	 * 删除软件平台
	 * @author xian.zf
	 * @date 2014-8-4
	 * @throws Exception
	 */
	public void deleteSoftware() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				Record model = Db.findById(Tname.SOFTWARE, getPara("id"));
				if(model!=null && Db.deleteById(Tname.SOFTWARE, getPara("id")))
					flag = true;
					//存在附件的删除附件
					if(model.getStr("pic")!=null && !"".equals(model.getStr("pic")))
						deleteFile(model.getStr("pic"));
			}
		}catch (Exception e) {
			log.info("删除出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除软件平台"));
	}
	
	/**
	 * 删除开放基金附件
	 * @author xian.zf
	 * @date 2015-9-8
	 * @throws Exception
	 */
	public void deleteFundfile() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				Record model = Db.findById(Tname.Fundfile, getPara("id"));
				if(model!=null && Db.deleteById(Tname.Fundfile, getPara("id")))
					flag = true;
					//存在附件的删除附件
					if(model.getStr("url")!=null && !"".equals(model.getStr("url")))
						deleteFile(model.getStr("url"));
			}
		}catch (Exception e) {
			log.info("删除出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除开放基金附件"));
	}
	
	/**
	 * 删除相关网站
	 * @author xian.zf
	 * @date 2015-9-8
	 * @throws Exception
	 */
	public void deleteRelatedlink() throws Exception {
		boolean flag = false;
		try{
			//具有管理员权限才能删除
			if(getSession().getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(getSession().getAttribute(Constant.MANAGEROLE).toString())){
				Record model = Db.findById(Tname.Relatedlink, getPara("id"));
				if(model!=null && Db.deleteById(Tname.Relatedlink, getPara("id")))
					flag = true;
					//存在附件的删除附件
					if(model.getStr("url")!=null && !"".equals(model.getStr("url")))
						deleteFile(model.getStr("url"));
					//存在附件的删除附件
					if(model.getStr("picurl")!=null && !"".equals(model.getStr("picurl")))
						deleteFile(model.getStr("picurl"));
			}
		}catch (Exception e) {
			log.info("删除出错， id:"+ getPara("id"));
			e.printStackTrace();
		}
		if(flag)
			renderJson(CreateForm.successMap());
		else
			renderJson(CreateForm.errorMap("删除相关网站"));
	}
	
}
