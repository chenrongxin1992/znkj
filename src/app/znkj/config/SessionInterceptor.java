package app.znkj.config;

import javax.servlet.http.HttpSession;

import app.znkj.common.Constant;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * 后台页面拦截器、session 为空时、权限不够，不允许访问
 * @author xian.zf
 *
 * @date 2014-7-19
 */
public class SessionInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		//获取本次 Action 调用所属 的 Controller
		Controller c = ai.getController(); 
		HttpSession session = c.getRequest().getSession();
		if(session != null && session.getAttribute(Constant.SESSION_USERID) != null){
			//获取本次 Action 调用的 controller keycontroller key值
			String controllerKey = ai.getControllerKey();

			boolean flag = false;
			switch (controllerKey) {
			case "/research":
				if(session.getAttribute(Constant.RESEARCHROLE) != null
					&& "2".equals(session.getAttribute(Constant.RESEARCHROLE).toString()))
					flag = true;
				break;
			case "/manage":
				if(session.getAttribute(Constant.MANAGEROLE)!=null
					&& "1".equals(session.getAttribute(Constant.MANAGEROLE).toString()))
					flag = true;
				break;				
			default:
				flag = true;
				break;
			}
			if(flag)
				ai.invoke();
			else
				c.redirect(Constant.URL_INFO);
		}else
			c.redirect("/login"); //重定向到登录页面
		
		
	}

}
