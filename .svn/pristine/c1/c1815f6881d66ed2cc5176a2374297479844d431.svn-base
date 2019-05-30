package app.znkj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;

import com.jfinal.config.JFinalConfig;

import app.znkj.model.Backups;

/**
 * 利用cmd调用mysqldump备份、恢复 mysql
 * @author xian.zf at 2013-05-26
 *
 * @date 2013-5-26
 */
public class JavaBackupMysql {
	protected static final Log log = LogFactory.getLog(JavaBackupMysql.class);
	private static final Properties props; 
	private static final String username; 
	private static final String password;
	private static final String mysqlpaths;
	private static final String address;
	private static final String databaseName;
	private static final String sqlpath;
	private static final String urlpath;
	
	static{
		System.out.println("执行备份初始化数据！");
		//
		String part = "";
		props = new Properties();
		try {
			part = ResourcePathUtil.getPath();
			InputStream inputStream = new FileInputStream(new File(part + "/WEB-INF/database_config.txt"));
			props.load(inputStream);
		} catch (Exception e) {
			log.error("database_config.txt加载出错");
			e.printStackTrace();
		}
		username = props.getProperty("datasource-user");// 用户名
		password = props.getProperty("datasource-password");// 密码 
		mysqlpaths = props.getProperty("mysqlpath");// mysqldump.exe文件路径  （linux 下mysql自带的mysqldump 工具路径）
		address = props.getProperty("address");// 数据库地址
		databaseName = props.getProperty("databaseName");// 要备份的数据库名
		sqlpath = part + props.getProperty("sqlpath");
		urlpath = props.getProperty("sqlpath");
	}
	
	/**
	 * 备份数据库
	 * @author xian.zf at 2013-05-26
	 * @param sqlName ： 指定备份文件名
	 */
    public static Map<String, Object> backup(String sqlName, Integer userid) {  
	
        File backupath = new File(sqlpath);
        if (!backupath.exists()) { // 如果不存在文件夹，则创建
            backupath.mkdir();  
        }  
        StringBuffer sb = new StringBuffer();  
  
        sb.append(mysqlpaths);  
        sb.append("mysqldump ");  
        sb.append("--opt ");  
        sb.append("-h ");  
        sb.append(address);  
        sb.append(" ");  
        sb.append("--user=");  
        sb.append(username);  
        sb.append(" ");  
        sb.append("--password=");  
        sb.append(password);  
        sb.append(" ");  
        sb.append("--lock-all-tables=true ");  
        sb.append("--result-file=");  
        sb.append(sqlpath);  
        sb.append(sqlName);  
        sb.append(" ");  
        sb.append("--default-character-set=utf8 ");  
//        sb.append("--default-character-set=gb2312 ");//注意你的mysql数据库设置的字符集，要与这设置的一致否则，汉字会乱码。  
        sb.append(databaseName);  
        sb.append(" ");  
        System.out.println("\n 执行命令语句：" + sb);  //TODO 删除除
        Runtime cmd = Runtime.getRuntime();  
        Map<String, Object> mp = new HashMap<String, Object>();
        try {  
            Process p = cmd.exec(sb.toString()); 			//TODO 上版本后去掉注
            mp.put("success", true);
            mp.put("error", "");
            
            //添加一条记录
        	Backups backups = new Backups();
      	  	backups.set("cretime", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
      	  	backups.set("name", sqlName);
      	  	backups.set("url", urlpath + sqlName);
      	  	backups.set("userid", userid);
      	  	backups.save();
        } catch (Exception e) { 
            mp.put("success", false);
            mp.put("error", "备份数据库出错!");
        	log.error("备份mysql数据库出错。执行命令语句：" + sb.toString());
            e.printStackTrace();  
        } 
        
        return mp;
    }
    
    /**
     * 根据文件名，还原mysql(还原前，先备份一次)
     * @author xian.zf at 2013-05-26
     * @param sqlName
     * @return
     */  
    public static Map<String, Object> recover(String SqlName, Integer userid) throws IOException{
    	//还原前，先备份一次
    	backup(new DateTime().toString("yyyyMMddHHmmss") + ".sql", userid);
    	
    	Map<String, Object> mp = new HashMap<String, Object>();
    	
        Runtime runtime = Runtime.getRuntime();
        //-u后面是用户名，-p是密码-p后面最好不要有空格，-family是数据库的名字，--default-character-set=utf8，这句话一定的加
        //又是讨人厌的编码问题，在恢复的时候设置一下默认的编码就可以了。
//        Process process = runtime.exec("mysql -u root -proot --default-character-set=utf8 ns");
//        Process process = runtime.exec("mysql -u "+username+" -p"+password+" --default-character-set=utf8 " + databaseName);
        Process process = runtime.exec("mysql -u "+username+" -p"+password+" " + databaseName);
        String str = null;
        try{
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sqlpath + SqlName)));
            StringBuffer sb = new StringBuffer();
            while((str = br.readLine()) != null){
                sb.append(str+"\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
            mp.put("success", true);
            mp.put("error", "");
        }catch(Exception e){
            mp.put("success", false);
            mp.put("error", "还原数据库出错!sql:"+SqlName);
            log.error(str);
        	log.error(getErrMsg(process));
        	e.printStackTrace();
        }
        return mp;
    }
    
    private static String getErrMsg(Process p) throws IOException{ 
         StringBuilder errMsg = new StringBuilder(); 
         InputStream in = p.getErrorStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf8")); 
         for(String inStr = null;(inStr = br.readLine()) != null;){ 
             errMsg.append(inStr).append("<br>"); 
         } 
         in.close(); 
         br.close(); 
        return errMsg.length() == 0 ? null : errMsg.toString(); 
     }
    
}
