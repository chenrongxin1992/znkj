package app.znkj.controller;

import app.znkj.config.SessionInterceptor;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 个人中心--重定向控制器
 * @author xian.zf
 *
 * @date 2014-7-18
 */
@Before(SessionInterceptor.class)
public class CenterController extends Controller{
	
	/**
	 * 个人中心--个人信息
	 * @author xian.zf
	 * @date 2014-7-21
	 * @throws Exception
	 */
	public void index() throws Exception {
		render("info.html");
	}
	
	/**
	 * 改密码
	 * @author xian.zf
	 * @date 2014-7-18
	 * @throws Exception
	 */
	public void password() throws Exception {
		render("pwdChange.html");
	}
	
}
