package app.znkj.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

/**
 * 检查格式，根据特定格式生成需要的字段或对象
 * @author xian.zf at 2012-11-23
 *
 */
public class CheckFormat {
	protected static final Log log = LogFactory.getLog(CheckFormat.class);
	
	/**
	 * 检验手机号码格式
	 * @author xian.zf at 2012-11-10
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){ 
    	if(null == mobiles)
    		return false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");     
        Matcher m = p.matcher(mobiles);     
        return m.matches();     
    } 
   
	/**
	 * 检验邮箱格式
	 * @author xian.zf at 2012-11-10
	 * @param email
	 * @return
	 */
    public static boolean isEmail(String email){
    	if(null == email)
    		return false;
     String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2,})*$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(email);     
        return m.matches();     
    } 
    
    /**
     * 登录帐号验证，以字母开头，长度在6~18之间，只能包含字符、数字和下划线
     * @author Newphi
     * @date 2013-8-26
     * @param account
     * @return
     */
    public static boolean isAccount(String account){
    	String regx = "^[a-zA-Z]\\w{5,17}$";
    	Pattern p = Pattern.compile(regx);
    	Matcher m = p.matcher(account);
    	return m.matches();
    }
    
    /**
	 * 检验登陆密码格式(字符或者下划线组成，长度6~16之间，区分大小写)
	 * @author xian.zf at 2012-11-10
     * @param logPassword
     * @return
     */
    public static boolean isLogPassword(String logPassword){
    	if(null == logPassword)
    		return false;
        String str="^[\\w\\W]{6,16}$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(logPassword);     
        return m.matches(); 
    }
    
    /**
	 * 检验字符串是否全数字
	 * @author xian.zf at 2012-12-06
     * @param logPassword
     * @return
     */
    public static boolean isNumber(String number){
    	if(null == number)
    		return false;
        String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
        return m.matches(); 
    }
    
    /**
	 * 检验字符串是否指定范围的数字
	 * @author xian.zf at 2012-12-06
     * @param logPassword
     * @return
     */
    public static boolean isScoNumber(String number, int beg, int end){
    	if(null == number || !isNumber(number) || beg>end)
    		return false;
    	String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);
        if(m.matches())
        	if(beg<=Integer.valueOf(number) && end>=Integer.valueOf(number))
        		return true;
        return false; 
    }
    
    /**
	 * 检验字符串是否全数字,或是空字符串 ""
	 * @author xian.zf at 2012-12-06
     * @param logPassword
     * @return
     */
    public static boolean isNumberOrEmp(String number){
    	if("".equals(number))
    		return true;
        String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
        return m.matches(); 
    }
    
    /**
	 * 检验字符串是否全数字,或是空字符串 "",或是null"
	 * @author xian.zf at 2012-12-06
     * @param logPassword
     * @return
     */
    public static boolean isNumberOrEmpOrNull(String number){
    	if(number==null || "".equals(number.replaceAll("\\s", "")))
    		return true;
        String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
        return m.matches(); 
    }
    
    /**
	 * 检验字符串是否年份
	 * @author xian.zf at 2012-12-06
     * @param logPassword
     * @return
     */
    public static boolean isYearNumber(String number){
    	if(number==null || "".equals(number.replaceAll("\\s", "")))
    		return true;
        String str="^\\d{4}$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
        return m.matches(); 
    }
    
    /**
	 * 检验字符串是否全数字,或是小数
	 * @author xian.zf at 2013-01-18
     * @param logPassword
     * @return
     */
    public static boolean isDecimal(String number){
    	if(null == number)
    		return false;
        String str="^\\d+(.\\d*)?$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
        return m.matches(); 
    }
    
    /**
     * 判断
     * @return
     */
    public static boolean isZero(BigDecimal big){
    	return new BigDecimal(0).compareTo(big)==0;
    }
    
	/**
	 * 判断状态值是否超过设定的范围（0至最大值）
	 * @author xian.zf at 2013-01-21
	 * @param state 待判断的状态值
	 * @param scope 范围最大值（0 ~ 范围最大值）
	 * @return
	 */
	public static boolean checkSateScope(Integer state, int scope){
		if(null==state || state.compareTo(scope) >0)
			return false;
		return true;
	}
	
	/**
	 * 检验字符串是否全数字,或是空字符串 "",且状态值是否超过设定的范围（0至最大值）
	 * @author xian.zf at 2013-01-21
	 * @param state 待判断的状态值
	 * @param scope 范围最大值（0 ~ 范围最大值）
	 * @return
	 */
	public static boolean checkNumAndSateScope(String number, int scope){
    	if(null == number)
    		return false;
        String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
		if(m.matches() && (Integer.valueOf(number).compareTo(scope)<0 || Integer.valueOf(number).compareTo(scope)==0))
			return true;
		else
			return false;
	}
	
	/**
	 * 检验字符串是否全数字,或是空字符串 "",且状态值是否超过设定的范围（0至最大值）
	 * @author xian.zf at 2013-01-21
	 * @param state 待判断的状态值
	 * @param scope 范围最大值（0 ~ 范围最大值）
	 * @return
	 */
	public static boolean checkNumOrEmpSateScope(String number, int scope){
    	if("".equals(number))
    		return true;
        String str="^\\d+$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(number);     
		if(m.matches() && (Integer.valueOf(number).compareTo(scope)<0 || Integer.valueOf(number).compareTo(scope)==0))
			return true;
		else
			return false;
	}
	
	/**
	 * 判断字符串是否符合时间格式：yyyy-MM-dd
	 * @author xian.zf at 2013-01-22
	 * @param time 
	 * @return
	 */
	public static boolean checkTimeFormat(String time){
    	if(null == time)
    		return false;
        String str="^\\d{4}-\\d{2}-\\d{2}$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(time);  
        return m.matches(); 
	}
	
	/**
	 * 判断字符串是否符合时间格式：yyyy-MM-dd 或者是空字符串
	 * @author xian.zf at 2013-01-22
	 * @param time 
	 * @return
	 */
	public static boolean checkTimeFormatOrEmpStr(String time){
    	if(null == time)
    		return false;
		if("".equals(time))
			return true;
        String str="^\\d{4}-\\d{2}-\\d{2}$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(time);  
        return m.matches(); 
	}
	
	/**
	 * 检查图片名是否以jpg、png、gif结尾
	 * @author xian.zf at 2013-01-26
	 * @param picName
	 * @return
	 */
	public static boolean checkPicName(String picName){
		if(null==picName || "".equals(picName))
			return false;
		String[] format = {".jpg",".png",".gif",".jpeg",".bmp"};
		boolean flag = false;
		for(String s : format){
			if(picName.endsWith(s)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 判断字符串是空或NULL
	 * @author xian.zf at 2013-03-23
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmp(String str){
		if(null==str || "".equals(str.trim()))
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是空或NULL
	 * @author xian.zf at 2013-03-23
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmp(Object str){
		if(null==str || "".equals(str.toString().trim()))
			return true;
		return false;
	}
	
	/**
	 * 效验密码安全等级（弱：0、中：1、强：2）
	 * 弱：只有数字、字母其中一种组成
	 * 中：有数字、字母两种混合组成
	 * 强：除“弱”、“中”外的所有
	 * @author xian.zf at 2013-02-26
	 * @param logPassword 经验证为6-16的字符
	 * @return
	 */
	public static int checkPasswordLevel(String logPassword){
    	if(null == logPassword)
    		return 0;
		String[] exp = {"^\\d{6,16}$", "^[a-zA-Z]{6,16}$", "^(?!\\d+)(?![a-zA-Z]+)\\w{6,16}*$"};
		int level = 0;
		boolean flag = false;//标记是否匹配
		for(int i=0,len = exp.length; i<len; i++){
	        Pattern p = Pattern.compile(exp[i]);     
	        Matcher m = p.matcher(logPassword); 
	        if(m.matches()){
	        	if(i==2)
	        		level = 1;
	        	flag = true;
	        	break;
	        }
		}
		//除“弱”、“中”外的，为强
		if(!flag)
			level = 2;
		return level;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isSql(String name) {
    	if(null == name || "".equals(name))
    		return false;
        String str="^\\d{14}.sql$";
        Pattern p = Pattern.compile(str);     
        Matcher m = p.matcher(name);  
        return m.matches(); 
	}
}
