package app.znkj.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.znkj.common.Constant;

import com.jfinal.handler.Handler;

/**
 * 获取session
 * @author xian.zf
 *
 * @date 2014-7-18
 */
public class SessionHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		HttpSession session = request.getSession();
		Enumeration<String> atts = session.getAttributeNames();
		while (atts.hasMoreElements() == true) {
			String an = atts.nextElement();
			request.setAttribute(an, session.getAttribute(an));
		}
		if(session.getAttribute(Constant.LANGUAGE)==null)
			session.setAttribute(Constant.LANGUAGE, "0");
		nextHandler.handle(target, request, response, isHandled);
	}

}
