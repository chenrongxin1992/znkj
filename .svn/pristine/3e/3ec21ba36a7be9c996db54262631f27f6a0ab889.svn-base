package app.znkj.config;

import app.znkj.common.Constant;
import app.znkj.common.Tname;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;



public class IndexInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
		//  Auto-generated method stub
		//获取本次 Action 调用所属 的 Controller
		Controller controller = ai.getController();
		String sql = "select * FROM `"+ Tname.Relatedlink +"` t ORDER BY t.sort asc";
		boolean flag = "1".equals(controller.getSession().getAttribute(Constant.LANGUAGE));
		if(flag)
			sql = "select type,icon,url,picurl,sort,ename as name FROM `"+ Tname.Relatedlink +"` t where t.eshow = 1 ORDER BY t.sort asc";
		ai.getController().getRequest().setAttribute(Constant.LINKLIST, Db.find(sql));
		ai.invoke();
	}

}
