package app.znkj.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import app.znkj.common.Constant;




/**
 * 获取类的路径.
 *
 * @author Newphi
 * @version 1.0 created at 2012-8-17
 */
public class ResourcePathUtil {

	protected static final Log log = LogFactory.getLog(ResourcePathUtil.class);
	
	/**
	 * Gets the path.
	 * @author Newphi
     * @version 1.0 created at 2012-8-17
	 * @return the path
	 * @throws Exception the exception
	 */
	public static String getPath() throws Exception {
		String returnpath = null;
		String classNamePath = ResourcePathUtil.class.getName().replace(".", "/") + ".class";
		String path = ResourcePathUtil.class.getClassLoader().getResource(classNamePath).getPath();
		path = StringUtils.replace(path, "%20", " ");
		//获取操作系统版本
		String osName = System.getProperty("os.name");
		int startIndex = 0;
		//windows 系统
		if(osName.indexOf("Windows")>-1){
			startIndex = path.indexOf(".metadata/.plugins");
			if(startIndex>0){
				path = path.substring(0, startIndex);
				path = path + Constant.PROJECT_NAME + "/WebRoot";
				returnpath = StringUtils.removeStart(path, "/");
			}else{
				log.error("寻找路径出错");
			}
		}else{
			startIndex = path.indexOf("/WEB-INF/classes/");
			if(startIndex>0){
				path = path.substring(0, startIndex);
				returnpath = path;
			}else{
				log.error("寻找路径出错");
			}
		}
		
		return returnpath;
	}
	
}
