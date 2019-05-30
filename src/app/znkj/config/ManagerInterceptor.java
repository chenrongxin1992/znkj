package app.znkj.config;

import com.jfinal.aop.InterceptorStack;
import com.jfinal.plugin.spring.IocInterceptor;

/**
 * 合拼多个拦戴器
 * @author xian.zf
 *
 * @date 2014-7-19
 */
public class ManagerInterceptor extends InterceptorStack{
	@Override
	public void config() {
		addInterceptors(new IocInterceptor(), new SessionInterceptor());
	}

}
