package app.znkj.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import com.jfinal.plugin.activerecord.Record;


/**
 * 按前后台的要求，将数据整理为特定的格式
 * @author xian.zf at 2012-11-22
 *
 */
public class CreateForm {
	protected static final Log log = LogFactory.getLog(CreateForm.class);
	
	/**
	 * 根据传入的对象及数组key(key须与类的属性名称相同)，生成MAP
	 * @author xian.zf at 2012-11-23
	 * @param s 数组
	 * @param o 对象
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> createMapforObjIncArr(String[] s,Object o) throws Exception{
		Map<String,Object> mp = new HashMap<String,Object>();		
		//获取对象的类
		Class<? extends Object> javabean = o.getClass();  
		for (int i = 0, len = s.length; i < len; i++) {
			try{
				//获取类s[i]属性对应的Field对象
				Field field = javabean.getDeclaredField(s[i]);
				//获取类第i个属性的权限
				boolean accessFlag = field.isAccessible();
				//将属性权限设为可访问
				field.setAccessible(true);
				//get该属性的值
				Object f = field.get(o);
				//put值
				mp.put(s[i], f==null?"":f);
				//还原该属性的权限
				field.setAccessible(accessFlag);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return mp;
	}
	
	/**
	 * 根据传入的对象及数组key(key须与类的属性名称相同)，生成显示时间要求的MAP
	 * @author xian.zf at 2012-11-23
	 * @param s 数组
	 * @param o 对象
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> createTimMapforObjIncArr(String[] s,Object o) throws Exception{
		Map<String,Object> mp = new HashMap<String,Object>();		
		//获取对象的类
		Class<? extends Object> javabean = o.getClass();  
		for (int i = 0, len = s.length; i < len; i++) {
			try{
				//获取类s[i]属性对应的Field对象
				Field field = javabean.getDeclaredField(s[i]);
				//获取类第i个属性的权限
				boolean accessFlag = field.isAccessible();
				//将属性权限设为可访问
				field.setAccessible(true);
				//get该属性的值
				Object f = field.get(o);
				mp.put(s[i], f==null?"":(f.toString()));
				//还原该属性的权限
				field.setAccessible(accessFlag);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return mp;
	}
	
	/**
	 * 根据传入的对象及数组key，生成不包含数组key的MAP,数组为空时,生成对象所有属性key-值value的MAP
	 * @author xian.zf at 2012-11-23
	 * @param s 数组
	 * @param o 对象
	 * @return Map
	 * @throws Exception
	 */
	public static Map<String,Object> createMapforObjNoIncArr(String[] s,Object o) throws Exception{
		Map<String,Object> mp = new HashMap<String,Object>();		
		//获取对象的类
		Class<? extends Object> javabean = o.getClass();  
    	//获取类的所有属性
		Field[] fields = javabean.getDeclaredFields();
		for(int i=0,len=fields.length; i<len; i++){
			//获取该类第i个属性的名称
			String valueName = fields[i].getName();
			if(valueName.equals("serialVersionUID"))
				continue;
			Boolean flag = true;
			for(int j=0,lenStr=s.length; j<lenStr; j++){
				if(valueName.equals(s[j])){
					flag = false;
					break;
				}
			}
			if(flag){
				try{
					//获取类第i个属性的权限
					boolean accessFlag = fields[i].isAccessible();
					//将属性权限设为可访问
					fields[i].setAccessible(true);
					mp.put(valueName, fields[i].get(o)==null?"":fields[i].get(o));
					//还原该属性的权限
					fields[i].setAccessible(accessFlag);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} 
			}
		}
		return mp;
	}
	
	/**
	 * 根据传入的BigDecimal型折扣，生成前台显示的，不打折、免费、几折
	 * @author xian.zf at 2013-1-6
	 * @param big ： BigDecimal
	 * @return
	 */
	public static String crePreForm(BigDecimal big){
		if(null == big){
			log.info("生成前台显示的拆扣参数有错，big=null");
			return "不打折";
		}
		return big.compareTo(new BigDecimal(1))==0?"不打折":(
			   big.compareTo(new BigDecimal(0))==0?"免费":(
			   big.multiply(new BigDecimal(10)).stripTrailingZeros())+"折");
	}
	
	/**
	 * 根据传入的BigDecimal百分比，生成前台显示的百分比，如0.15 生成 15%
	 * @author xian.zf at 2013-1-6
	 * @param big ： BigDecimal
	 * @return
	 */
	public static String crePerForm(BigDecimal big){
		if(null == big){
			log.info("生成前台显示的百分比参数有错，big=null");
			return "不打折";
		}
		return big.multiply(new BigDecimal(100))+"%";
	}
	
	/**
	 * 根据传入的日期、处理格式，生成前台显示的格式
	 * @author xian.zf at 2013-1-6
	 * @param date 日期
	 * @param type 需要处理日期的格式类型，如：yyyy-MM-dd HH:mm:ss 、yyyy年MM月dd日 HH:mm 等
	 * @return
	 */
	public static String creDatForm(Date date, String type){
		if(null == date || null==type || "".equals(type)){
			log.info("秘成前台显示日期格式参数有错。Date:"+date+", type:"+type);
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		//2000年开始的日期是还没有填写的,数据库设置的默认日期。
		return "2000".equals(sdf.format(date).length()>4 ? sdf.format(date).substring(0, 4) : sdf.format(date)) 
				? "" : sdf.format(date);
	}
	
	/**
	 * 将转入字符串中的单引号' 替换为\\'
	 * @author xian.zf at 2013-1-19
	 * @param str
	 * @return
	 */
	public static String creSinQuoForm(String str){
		if(null == str)
			return "";
		return str.replace("'", "\\'");
	}
	
	/**
	 * 将转入字符串中的单引号' 替换为\\' ,并进行中文转码
	 * @author xian.zf at 2013-1-19
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String creSinFormAndByts(String str) throws UnsupportedEncodingException{
		if(null == str || "".equals(str.trim()))
			return "";
		return new String(str.getBytes("8859_1"),"UTF-8").trim().replace("'", "\\'");
	}
	
	/**
	 * 将传入字符串首字母转为大写
	 * @author xian.zf at 2013-3-20
	 * @param str
	 * @return
	 */
	public static String creStr(String str){
		if(null==str ||str.length()<1)
			return str;
		if(str.length()==1)
			return str.toUpperCase();
		String s = str.substring(0, 1);
		String c = str.substring(1);
		return s.toUpperCase()+c;
	}
	
	/**
	 * 将传入字符串首字母转为小写
	 * @author xian.zf at 2013-3-20
	 * @param str
	 * @return
	 */
	public static String creStrToLow(String str){
		if(null==str ||str.length()<1)
			return str;
		if(str.length()==1)
			return str.toLowerCase();
		String s = str.substring(0, 1);
		String c = str.substring(1);
		return s.toLowerCase()+c;
	}
	
	/**
	 * 将图片加123.jpg转为123__thumb.jpg格式
	 * @author xian.zf at 2013-3-20
	 * @param photo
	 * @return
	 */
	public static String crePhoto(String photo){
		if(photo==null || photo.indexOf(".")==-1)
			return photo;
		return photo.replaceAll("^([\\w\\W]+)\\.([\\w]+)$", "$1_thumb.$2");
	}
	
	/**
	 * 将传入手机号码，中间四位加密，如13800138000转为138****8000
	 * @author xian.zf at 2013-3-20
	 * @param str
	 * @return
	 */
	public static String creMoPhone(String moPhone){
		if(null==moPhone || moPhone.length()<11)
			return moPhone;
		return moPhone.replaceAll("^([\\w\\W]{3})\\d{4}([\\w]{4})$", "$1\\*\\*\\*\\*$2");
	}
	
	/**
	 * 根据传入时间，返回当天的星期
	 * @author xian.zf at 2013-3-21
	 * @return
	 */
	public static String creChiWeek(Date date) {
		if(null==date)
			return "";
		DateTime dateTime = new DateTime(date);
		String week ="";
		switch(dateTime.getDayOfWeek()) {  
		case DateTimeConstants.SUNDAY:  
			week ="星期日";  
		    break;  
		case DateTimeConstants.MONDAY:  
			week ="星期一";  
		    break;  
		case DateTimeConstants.TUESDAY:  
			week ="星期二";  
		    break;  
		case DateTimeConstants.WEDNESDAY:  
			week ="星期三";  
		    break;  
		case DateTimeConstants.THURSDAY:  
			week ="星期四";  
		    break;  
		case DateTimeConstants.FRIDAY:  
			week ="星期五";  
		    break;  
		case DateTimeConstants.SATURDAY:  
			week ="星期六";  
		    break;  
		}  
		return week;
	}
	
	private final static Pattern url_pattern = Pattern.compile(
		    "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?");
		 
	/**
	 * 自动为文本中的url生成链接
	 * @author xian.zf at 2013-03-22
	 * @param txt
	 * @param only_oschina
	 * @return
	 */
	public static String autoMakeLink(String txt) {
	    StringBuilder html = new StringBuilder();
	    int lastIdx = 0;
	    Matcher matchr = url_pattern.matcher(txt);
	    while (matchr.find()) {
	        String str = matchr.group();
	        if(!(str.indexOf("http://")!=-1 || str.indexOf("https://")!=-1)){
	        	str = "http://" + str;
	        }
	        html.append(txt.substring(lastIdx, matchr.start()));
	        html.append("<a href='"+str+"'>"+str+"</a>");
	        lastIdx = matchr.end();
	    }
	    html.append(txt.substring(lastIdx));
	    return html.toString();
	}	
	
	/**
	 * 两个数的百分比（保留2位小数）， 分子或 分母为0时返回0
	 * @author xian.zf
	 * @date 2014-4-25
	 * @return
	 */
	public static String getPercent(BigDecimal numerator, BigDecimal denominator){
		if(numerator.compareTo(BigDecimal.ZERO)==0 || denominator.compareTo(BigDecimal.ZERO)==0)
			return BigDecimal.ZERO+"";
		return numerator.divide(denominator, 4, 5).multiply(new BigDecimal(100)).divide(BigDecimal.ONE, 2, 5)+"";
	}
	
	/**
	 * 两个数的百分比（保留2位小数）， 分子或 分母为0时返回0
	 * @author xian.zf
	 * @date 2014-4-25
	 * @return
	 */
	public static String getDivide(BigDecimal numerator, BigDecimal denominator){
		if(numerator.compareTo(BigDecimal.ZERO)==0 || denominator.compareTo(BigDecimal.ZERO)==0)
			return BigDecimal.ZERO+"";
		return numerator.divide(denominator, 2, 5)+"";
	}
	
	/**
	 * 两个数的比（保留整数）， 分子或 分母为0时返回0
	 * @author xian.zf
	 * @date 2014-6-26
	 * @return
	 */
	public static String getDivide(Object numerator, String denominator){
		if(numerator==null || "0".equals(numerator.toString()) || "0".equals(denominator))
			return "0";
		return new BigDecimal(numerator.toString()).divide(new BigDecimal(denominator), 0, 5)+"";
	}
	
	/**
	 * 将传入身份证号码，中间加密，保留前四位后四位。
	 * @author xian.zf at 2013-3-20
	 * @param str
	 * @return
	 */
	public static String creIdentityStr(String moPhone){
		if(null==moPhone)
			return "";
		int len = moPhone.length();
		if(len<9)
			return moPhone;
		return moPhone.substring(0, 4) + "****" + moPhone.substring(len - 4,  len);
	}
	
	/**
	 * 处理时间转为long
	 * @author xian.zf
	 * @date 2014-6-21
	 * @param timeStr
	 * @return
	 */
	public static long getTime(String timeStr) {
		if(timeStr.endsWith(".0"))
			timeStr = timeStr.replaceAll("\\.0", "");
		return new DateTime(timeStr.replaceAll("\\s", "T")).getMillis();
	}
	
	/**
	 * 两个数相乘,
	 * @author xian.zf
	 * @date 2014-7-4
	 * @return
	 */
	public static String getMultiply(String a, String b) {
		if(CheckFormat.isDecimal(a) && CheckFormat.isDecimal(b)){
			return new BigDecimal(a).multiply(new BigDecimal(b)).toString();
		}else
			return "";
	}
	
	/**
	 * 根据生日获取当前年龄
	 * @author xian.zf
	 * @date 2014-7-7
	 * @return
	 */
	public static int getAge(DateTime birthday, DateTime nowDate) {
		return nowDate.getYear() - birthday.getYear();
	}
	
	/**
	 * 返回成功MAP
	 * @author xian.zf
	 * @date 2014-7-21
	 * @return
	 */
	public static Map<String, Object> successMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}
	
	/**
	 * 返回成功MAP
	 * @author xian.zf
	 * @date 2014-7-21
	 * @return
	 */
	public static Map<String, Object> successMap(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("id", id);
		return map;
	}
	
	/**
	 * 返回失败MAP
	 * @author xian.zf
	 * @date 2014-7-21
	 * @return
	 */
	public static Map<String, Object> errorMap(String errorStr){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("error", errorStr);
		return map;
	}
	
	/**
	 * 处理前台显示中英文等需求
	 * @author xian.zf
	 * @date 2014-8-30
	 * @return
	 */
	public static Record disposeRecord(Record rec, boolean flag) {
		for (Iterator<Entry<String, Object>> it = rec.getColumns().entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> e = it.next();
			if (e.getValue() == null || e.getValue().toString().trim().isEmpty()) {
				e.setValue("");
				//中文为空的，用英文替代
				if(rec.get("e"+e.getKey()) != null)
					e.setValue(rec.get("e"+e.getKey()));
			}
			//选择显示英文的
			if(flag){
				if(rec.get("e"+e.getKey())!=null 
						&& !rec.get("e"+e.getKey()).toString().trim().isEmpty())
					e.setValue(rec.get("e"+e.getKey()));
			}
		}
		return rec;
	}
	
	/**
	 * 处理前台显示中英文等需求
	 * @author xian.zf
	 * @date 2014-8-30
	 * @return
	 */
	public static List<Record> disposeRecord(List<Record> recList, boolean flag, String url) {
		for(Record rec:recList){
			rec = disposeRecord(rec, flag);
			rec.set("url", url + rec.get("id"));
		}
		return recList;
	}
	
	/**
	 * 
	 * @author xian.zf
	 * @date 2014-9-3
	 * @param begin
	 * @param end
	 * @return
	 */
	public static  String creProTime(String begin, String end){
		String time="";
		if(CheckFormat.checkTimeFormat(begin))
			time += begin.substring(0, 4) + "-";
		if(CheckFormat.checkTimeFormat(end))
			time += end.substring(0, 4);
		return time;
	}
}
