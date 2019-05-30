package app.znkj.config;

import app.znkj.common.Tname;
import app.znkj.controller.AddOrUpdateController;
import app.znkj.controller.CenterController;
import app.znkj.controller.DeleteController;
import app.znkj.controller.IndexController;
import app.znkj.controller.DetailedController;
import app.znkj.controller.ListController;
import app.znkj.controller.ManageController;
import app.znkj.controller.OtherController;
import app.znkj.controller.ResearchController;
import app.znkj.controller.SBDMV2014;
import app.znkj.controller.VcodeController;
import app.znkj.model.AcademicCommittee;
import app.znkj.model.AnnualReport;
import app.znkj.model.Award;
import app.znkj.model.Backups;
import app.znkj.model.Communication;
import app.znkj.model.Equipment;
import app.znkj.model.EquipmentUse;
import app.znkj.model.FundAchievement;
import app.znkj.model.Fundfile;
import app.znkj.model.Fundopen;
import app.znkj.model.IndexAchievement;
import app.znkj.model.IndexPic;
import app.znkj.model.News;
import app.znkj.model.Notice;
import app.znkj.model.Patent;
import app.znkj.model.PeriodicalArticle;
import app.znkj.model.ConferenceArticle;
import app.znkj.model.Laboratory;
import app.znkj.model.LaboratoryChief;
import app.znkj.model.Poster;
import app.znkj.model.PosterAchievement;
import app.znkj.model.Project;
import app.znkj.model.ProjectAchievement;
import app.znkj.model.Recruit;
import app.znkj.model.Regulations;
import app.znkj.model.Relatedlink;
import app.znkj.model.ResearchDirections;
import app.znkj.model.ResearchTeam;
import app.znkj.model.Role;
import app.znkj.model.Searchinfo;
import app.znkj.model.Software;
import app.znkj.model.Team;
import app.znkj.model.TeamUser;
import app.znkj.model.Thesis;
import app.znkj.model.Treatise;
import app.znkj.model.User;
import app.znkj.model.UserRole;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class BaseConfig extends JFinalConfig{

	/**
	 * 常量配置
	 */
	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setUrlParaSeparator("&"); //设置参数的间隔符为&
	}

	/**
	 * 路由信息
	 */
	@Override
	public void configRoute(Routes me) {
		//重定向路由
		//首页
		me.add("/", IndexController.class, "/pages/index");
		//个人中心
		me.add("/info", CenterController.class, "/pages/user");
		//研究团队
		me.add("/research", ResearchController.class, "/pages/research");
		//管理
		me.add("/manage", ManageController.class, "/pages/manage");
		
		//  SBDMV2014.htm  路由 
		me.add("/SBDMV2014", SBDMV2014.class, "/");
		
		
		//非重定向路由
		//新增或编辑
		me.add("/add", AddOrUpdateController.class);
		//获取列表
		me.add("/list", ListController.class);
		//详情
		me.add("/detailed", DetailedController.class);
		//删除
		me.add("/delete", DeleteController.class);
		//其他请求
		me.add("/other", OtherController.class);
		
		//公共--生成验证码
		me.add("/vcode",VcodeController.class); 
	}

	/**
	 * 插件信息
	 */
	@Override
	public void configPlugin(Plugins me) {
		loadPropertyFile("database_config.txt");  //加载数据库连接信息
		
		//C3P连接池
		C3p0Plugin cp = new C3p0Plugin(getProperty("datasource-url"),getProperty("datasource-user"),getProperty("datasource-password").trim()); 
		me.add(cp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
		me.add(arp); 
		arp.addMapping(Tname.TABLE_USER, User.class);
		arp.addMapping(Tname.TABLE_ROLE, Role.class);
		arp.addMapping(Tname.TABLE_USER_ROLE, UserRole.class);
		arp.addMapping(Tname.TABLE_RESEARCH, ResearchDirections.class);
		arp.addMapping(Tname.TABLE_RESEARCH_TEAM, ResearchTeam.class);
		arp.addMapping(Tname.TABLE_LAB_CHIEF, LaboratoryChief.class);
		arp.addMapping(Tname.TABLE_LABORATORY, Laboratory.class);
		arp.addMapping(Tname.TABLE_ACADEMIC_COM, AcademicCommittee.class);
		arp.addMapping(Tname.TABLE_PERARTICLE, PeriodicalArticle.class);
		arp.addMapping(Tname.TABLE_CONARTICLE, ConferenceArticle.class);
		arp.addMapping(Tname.TABLE_THESIS, Thesis.class);
		arp.addMapping(Tname.TABLE_AWARD, Award.class);
		arp.addMapping(Tname.TABLE_PATENT, Patent.class);
		arp.addMapping(Tname.TABLE_COMMUNICATION, Communication.class);
		arp.addMapping(Tname.TABLE_PROJECT, Project.class);
		arp.addMapping(Tname.TABLE_PROJECTACH, ProjectAchievement.class);
		arp.addMapping(Tname.TABLE_FUNDOPEN, Fundopen.class);
		arp.addMapping(Tname.TABLE_FUNDACH, FundAchievement.class);
		arp.addMapping(Tname.TABLE_TEAM, Team.class);
		arp.addMapping(Tname.TABLE_TEAM_USER, TeamUser.class);
		arp.addMapping(Tname.TABLE_EQUIPMENT, Equipment.class);
		arp.addMapping(Tname.TABLE_EQUIPMENT_USE, EquipmentUse.class);
		arp.addMapping(Tname.TABLE_NEWS, News.class);
		arp.addMapping(Tname.TABLE_ANNREP, AnnualReport.class);
		arp.addMapping(Tname.TABLE_POSTER, Poster.class);
		arp.addMapping(Tname.TABLE_POSTER_ACH, PosterAchievement.class);
		arp.addMapping(Tname.TABLE_NOTICE, Notice.class);
		arp.addMapping(Tname.TABLE_REGULATIONS, Regulations.class);
		arp.addMapping(Tname.TABLE_INDEXACH, IndexAchievement.class);
		arp.addMapping(Tname.TABLE_RECRUIT, Recruit.class);
		arp.addMapping(Tname.SEARCHINFO, Searchinfo.class);
		arp.addMapping(Tname.TABLE_TREATISE, Treatise.class);
		arp.addMapping(Tname.BACKUPS, Backups.class);
		arp.addMapping(Tname.SOFTWARE, Software.class);
		arp.addMapping(Tname.INDEX_PIC, IndexPic.class);
		arp.addMapping(Tname.Fundfile, Fundfile.class);
		arp.addMapping(Tname.Relatedlink, Relatedlink.class);
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new Basehandler()); 
		me.add(new SessionHandler());
	}

}
