package app.znkj.common;

public class Constant {
	//项目名
	public static final String PROJECT_NAME 		= "znkj";
	//保存在session下的用户ID
	public static final String SESSION_USERID 		= "sessionUserid"; 
	//保存在session下的用户账号
	public static final String SESSION_ACCOUNT 		= "sessionAccount";
	//保存在session下的用户名字
	public static final String SESSION_USERNAME 	= "sessionUserName";
	//登陆后默认跳转链接
	public static final String URL_INFO 			= "/info";
	//注销后默认跳转链接
	public static final String URL_LOGIN 			= "/login";
	
	//保存在session下的用户 研究人员权限 id
	public static final String RESEARCHROLE 	    = "researchRole";
	//保存在session下的用户 实验室管理员权限 id
	public static final String MANAGEROLE 			= "manageRole";
	//保存在session下的用户 角色名
	public static final String ROLENAME 			= "roleName";
	
	public static final Integer MANAGE_ROLEID		= 1;
	public static final Integer RESEARCH_ROLEID		= 2;
	//是否显示为英文，1表示显示
	public static final String ISENGLISH			= "isEnglish";
	//
	public static final String SUCCES				= "success";
	
	//管理员有权删除的表(无关联关系表)
	public static final String[] LABDELETE			= { Tname.TABLE_LAB_CHIEF, Tname.TABLE_ACADEMIC_COM,
														Tname.TABLE_EQUIPMENT_USE, Tname.TABLE_NEWS, 
														Tname.TABLE_ANNREP, Tname.TABLE_NOTICE, 
														Tname.TABLE_REGULATIONS, Tname.TABLE_RECRUIT,
														Tname.TABLE_COMMUNICATION, Tname.BACKUPS,
														Tname.Relatedlink, Tname.Fundfile};
	
	//临时文件保存路径
	public static final String TEMPORARY 			= "/public/attachment/temporary/";
	//照片保存路径
	public static final String PHOTOPATH 			= "/public/attachment/photo/";
	//规章制度保存路径
	public static final String RULEPDFPATH 			= "/public/attachment/rulepdf/";
	//新闻附件保存路径
	public static final String NEWSPATH 			= "/public/attachment/news/";
	//附件保存路径
	public static final String ACHIEVEMENT 			= "/public/attachment/achievement/";
	//保存路径
	public static final String ATTACHMENT 			= "/public/attachment/"; 
	//成果展示平台照片保存路径
	public static final String SOFTWAREPATH 		= "/public/attachment/software/";
	//首页图片保存路径
	public static final String INDEXPICPATH 		= "/public/attachment/indexpic/";
	
	//首页中英文状态，0表示中文、1表示英语
	public static final String LANGUAGE				= "language";
	
	//数据库
	public static final String mysqlpath			= "/usr/local/mysql/bin/";
	public static final String address				= "localhost";
	public static final String databaseName			= "znkj";
	public static final String sqlpath				= "/attachment/sqlbackups/";
	//首页论文不显示类型
	public static final String ARTTYPE				= "其它";
	
	//前台相关网站列表
	public static final String LINKLIST				= "linklist";
	
	private Constant() {};
}
