package app.znkj.service.impl;

import java.io.File;
import java.io.FileOutputStream;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record; 

import app.znkj.common.Tname;
import app.znkj.service.ExcelService;
import app.znkj.util.ResourcePathUtil;

/**
 * 
 * @author xian.zf
 *
 * @date 2013-4-7
 */
public class ExcelServiceImpl implements ExcelService{
	protected static final Log log = LogFactory.getLog(ExcelServiceImpl.class);


	/**
	 * 生成简单的excel，无合拼，无样式
	 * @param fileName
	 * @param fieldName
	 * @param list
	 * @param sheetName
	 * @return
	 */
	private boolean getOneExcel(String fileName, String fieldName[][], List<Record> list, String sheetName) {
		try{
			HSSFWorkbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet(sheetName);
			Row row = sheet.createRow((int)0);   
			row.setHeight((short) 800);//行高
		    // 设置字体    
		    HSSFFont columnHeadFont = wb.createFont();    
		    columnHeadFont.setFontName("宋体");    
		    columnHeadFont.setFontHeightInPoints((short) 10);    
		    // 列头的样式    
		    HSSFCellStyle columnHeadStyle = wb.createCellStyle();    
		    columnHeadStyle.setFont(columnHeadFont);    
		    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
		    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
		    columnHeadStyle.setLocked(true);    
		    columnHeadStyle.setWrapText(true);    
		    columnHeadStyle.setLeftBorderColor(HSSFColor.BLACK.index);// 左边框的颜色    
		    columnHeadStyle.setBorderLeft((short) 1);// 边框的大小 
		    columnHeadStyle.setRightBorderColor(HSSFColor.BLACK.index);// 右边框的颜色  
		    columnHeadStyle.setBorderRight((short) 1);// 边框的大小
		    columnHeadStyle.setBottomBorderColor(HSSFColor.BLACK.index);// 下边框的颜色  
		    columnHeadStyle.setBorderBottom((short) 1);// 边框的大小
		    columnHeadStyle.setTopBorderColor(HSSFColor.BLACK.index);
		    columnHeadStyle.setBorderTop((short) 1);// 边框的大小
		    
		    // 设置列宽    		    
		    int flen = fieldName.length;
			 for(int i=0; i<flen; i++) {
				 if(i==0)
					 sheet.setColumnWidth((short)i, (short)2000);
				 else
					 sheet.setColumnWidth((short)i, (short)6000);
				 Cell cell =  row.createCell(i);
				 cell.setCellValue(fieldName[i][0]);
				 cell.setCellStyle(columnHeadStyle);
			 }
			 
			 for(int i=0, len=list.size(); i<len; i++) {
				 row = sheet.createRow((int)i+1);
				 row.setHeight((short) 500);//行高
				 for(int j=0; j<flen; j++) {
					 if(fieldName[j][1].equals("")) {
						 continue;
					 }
					 Record rec = list.get(i);
					 Object t = rec.get(fieldName[j][1])==null?"":rec.get(fieldName[j][1]);
					 
					 if(t == null) {
						 continue;
					 } else { 
						 Cell cell = row.createCell(j);
						 cell.setCellStyle(columnHeadStyle);
						 cell.setCellValue(t.toString());
					 }
					 
				 }
			 }
			 
			 File file = new File(fileName);
			 if(file.exists()) { //发现已经存在则删除
				 file.delete();
			 }
			 FileOutputStream fileOut = new FileOutputStream(file);
		     wb.write(fileOut);
		     fileOut.close();
		     return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 科研项目excel列表
	 * @author xian.zf
	 * @date 2014-9-11
	 * @param curpage
	 * @param pagesize
	 * @param name
	 * @param principal
	 * @param year
	 * @param userid
	 * @param status
	 * @return
	 */
	@Override
	public Map<String, Object> getProjectListExcel(String name, String excelName ,String principal, String year,
			String userid, String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_PROJECT + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(principal != null && !"".equals(principal))
			consql += " and t.principal like '%" + principal +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'";
		if(status != null && !"".equals(status)){
			String nowTime = new DateTime().toString("yyyy-MM-dd");
			if("1".equals(status)){
				consql += " and t.restimebegin <= '" + nowTime +"'";
				consql += " and t.restimeend >= '" + nowTime +"'";
			}else
				consql += " and t.restimeend < '" + nowTime +"'";
		}
		consql += " order by t.restimebegin desc, t.name desc ";
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){
			rec.set("num", i++);//设置序号
			String tsql = "select h.tname as name ,h.achid as achid from "+Tname.TABLE_PROJECTACH +" h  where h.relid ="+rec.get("id");
			List<Record>  proje = Db.find(tsql);
			int ttt = 0;
			String nuttt="";
			for(Record pro : proje){ 
				Record hhh = Db.findById(pro.getStr("name"),pro.get("achid"));
				if(hhh==null||hhh.get("name")==null){
					continue;
				}
				nuttt+=++ttt+"."+hhh.getStr("name")+"     ";
			}
			rec.set("ach", nuttt);
		} 
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath(); 
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"}, 
				{"名称", "name"},
				{"(英文)名称","ename"},
				{"负责人","principal"},
				{"(英文)负责人","eprincipal"},
				{"参与人","participant"},
				{"(英文)参与人","eparticipant"},
				{"年份","year"},
				{"经费来源","fundsource"},
				{"(英文)经费来源","efundsource"},
				{"金额(万元)","money"},
				{"(开始)研究时间","restimebegin"},
				{"(结束)研究时间","restimeend"},
				{"摘要","digest"},
				{"(英文)摘要","edigest"}, 
				{"成果","ach"},
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		}  
		return mp;
	}

	
	/**
	 * 科研管理--期刊论文excel
	 * 
	 * @author deng.gzh
	 * @date 2014-9-11
	 * @Title: getPerarticle
	 * @throws Excetion
	 */
	public  Map<String, Object> getPerarticle(String name,String excelName, String authors, String year, String userid,String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_PERARTICLE + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'"; 
//		consql += " order by t.lastedittime desc, t.name desc ";
		consql += " order by t.publishyear desc, t.name asc";
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList) {
			rec.set("num", i++);//设置序号  
			String array[] = rec.getStr("authors").split(";");
			String sss = "";
			for(int th=1,the=array.length;th<=the;th++){
				if(!"".equals(array[th-1])){ 
					String brray[] = array[th-1].split(","); 
					try{
//						sss+=th+"."+"作者："+brray[0]+"(作者英文："+brray[1]+") 单位："+brray[2]+"(单位英文："+brray[3]+") 作者类别："+(brray[4].equals("1")?"通讯作者   ":"")+(brray[5].equals("1")?"作者  ":"")+"	 ";
						if(brray[0].equals(brray[1]))
							sss+= th+"."+brray[0]+ (brray[4].equals("1")?"*":"") + " ";
						else
							sss+= th+"."+brray[0]+"("+brray[1]+") "+ (brray[4].equals("1")?"*":"");
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						sss+=th+"."+array[th-1];
						continue;
					}
				}
			}
			rec.set("chauthor", sss); 
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_PERARTICLE, achid));
			
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"}, 
				{"期刊名称", "periodical"},
				{"（英文）期刊名称","eperiodical"},
				{"论文名称","name"},
				{"（英文）论文名称","ename"},
				{"全部作者","chauthor"},
				{"出版年","publishyear"},
				{"状态","status"},
				{"收录情况","include"},
				{"卷号","reelnumber"},
				{"期号","issue"},
				{"刊号","issn"},
				{"页码","pagination"},
				{"摘要","digest"}, 
				{"（英文）摘要","edigest"},
				{"全文链接","pdfurl"},
				{"关联人员","relevancename"},
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
	
	/**
	 * 科研管理--会议论文excel
	 * 
	 * @author deng.gzh
	 * @date 2014-9-11
	 * @Title: getPerarticle
	 * @throws Excetion
	 */
	public Map<String,Object> getConarticle(String name,String excelName, String authors, String year, String userid,
			String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_CONARTICLE + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'"; 
//		consql += " order by t.lastedittime desc, t.name desc ";
		consql += " order by t.publishyear desc";
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){
			rec.set("num", i++);//设置序号   
			String array[] = rec.getStr("authors").split(";");
			String sss = "";
			for(int th=1,the=array.length;th<=the;th++){
				if(!"".equals(array[th-1])){
					String brray[] = array[th-1].split(",");
					try{ 
//					sss+=th+"."+"作者："+brray[0]+"(作者英文："+brray[1]+") 单位："+brray[2]+"(单位英文："+brray[3]+")  作者类别："+(brray[4].equals("1")?"通讯作者":"")+(brray[5].equals("1")?"作者":"")+"	 ";
						if(brray[0].equals(brray[1]))
							sss+= th+"."+brray[0]+ (brray[4].equals("1")?"*":"") + " ";
						else
							sss+= th+"."+brray[0]+"("+brray[1]+") "+ (brray[4].equals("1")?"*":"");
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						sss+=th+"."+array[th-1];
						continue;
					}
				}
			}
			rec.set("chauthor", sss); 
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_CONARTICLE, achid));
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"}, 
				{"会议名称", "periodical"},
				{"（英文）会议名称","eperiodical"},
				{"论文名称","name"},
				{"（英文）论文名称","ename"},
				{"全部作者","chauthor"},
				{"年份","publishyear"},
				{"会议地址","address"},
				{"（英文）会议地址","eaddress"},
				{"页码","pagination"}, 
				{"摘要","digest"}, 
				{"（英文）摘要","edigest"}, 
				{"关联人员","relevancename"},
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
 
	/**
	 * 科研管理--学位论文excel列表
	 * 
	 * @author deng.gzh
	 * @date 2014-9-11
	 * @Title: getThesisa
	 * @throws Excetion
	 */
	public Map<String,Object> getThesisa(String name,String excelName, String authors, String year, String userid,
			String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_THESIS + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'"; 
//		consql += " order by t.lastedittime desc, t.name desc ";
		consql += " order by t.publishyear desc";
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){ 
			rec.set("num", i++);//设置序号   
			String array[] = rec.get("authors").toString().split(",");
			int j=0;
			String sss="";
			for(int th=1,thes=array.length;th<=thes;th++){
				Record  thsss = Db.findById("user", array[th-1]);
				if(thsss==null||thsss.get("name")==null){
					continue;
				}
				sss+=++j+"."+thsss.get("name")+"  ";
			}
			rec.set("authorsName", sss);
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_THESIS, achid));
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"},  
				{"论文名称","name"},
				{"（英文）论文名称","ename"}, 
				{"作者","authorsName"},
				{"指导老师", "tutor"}, 
				{"年份","publishyear"},
				{"单位","unit"},
				{"（英文）单位","eunit"},
				{"页数","pagination"}, 
				{"摘要","digest"}, 
				{"（英文）摘要","edigest"}, 
				{"关联人员","relevancename"},
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
	
	/**
	 * 科研管理--专著登记excel列表
	 * 
	 * @author deng.gzh
	 * @date 2014-9-13
	 * @Title: getTreatise
	 * @throws Excetion
	 */
	public Map<String,Object> getTreatiseexcel(String name,String excelName, String authors, String year, String userid ) throws Exception{
		String consql = "select * from "+ Tname.TABLE_TREATISE + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' )";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.publishyear like '%" + year +"%'"; 
//		consql += " order by  t.name desc ";
		consql += " order by t.publishyear desc"; 
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){ 
			rec.set("num", i++);//设置序号   
			String array[] = rec.getStr("authors").split(";");
			String sss = "";
			for(int th=1,the=array.length;th<=the;th++){
				if(!"".equals(array[th-1])){
					String brray[] = array[th-1].split(",");
					try{ 
//					sss+=th+"."+"作者："+brray[0]+"(作者英文："+brray[1]+") 单位："+brray[2]+"(单位英文："+brray[3]+") 作者类别："+(brray[4].equals("1")?"通讯作者":"")+(brray[5].equals("1")?"作者":"")+"	 ";
						if(brray[0].equals(brray[1]))
							sss+= th+"."+brray[0]+ (brray[4].equals("1")?"*":"") + " ";
						else
							sss+= th+"."+brray[0]+"("+brray[1]+") "+ (brray[4].equals("1")?"*":"");
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						sss+=th+"."+array[th-1];
						continue;
					}
				}
			}
			rec.set("chauthor", sss); 
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_TREATISE, achid));
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"},  
				{"著作名称","name"},
				{"（英文）著作名称","ename"},
				{"作者","chauthor"},
				{"出版社", "publish"}, 
				{"(英文)出版社", "epublish"}, 
				{"出版年","publishyear"},
				{"出版地","publishaddr"},
				{"(英文)出版地","epublishaddr"},
				{"ISBN号码","isbn"},
				{"页数","pagination"}, 
				{"版本","versions"}, 
				{"摘要","digest"}, 
				{"（英文）摘要","edigest"},
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
	
	
	/**
	 * 科研管理--获奖登记excel列表
	 * 
	 * @author deng.gzh
	 * @date 2014-9-11
	 * @Title: getAwardexcel
	 * @throws Excetion
	 */
	public Map<String,Object> getAwardexcel(String name,String excelName, String authors, String year, String userid,
			String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_AWARD + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'"; 
//		consql += " order by t.lastedittime desc, t.name desc ";
		consql += " order by t.year desc";

		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){ 
			rec.set("num", i++);//设置序号   
			String array[] = rec.getStr("authors").split(";");
			String sss = "";
			for(int th=1,the=array.length;th<=the;th++){
				if(!"".equals(array[th-1])){ 
					String brray[] = array[th-1].split(",");
					try{
//					  sss+=th+"."+"作者："+brray[0]+"(作者英文："+brray[1]+") 单位："+brray[2]+"(单位英文："+brray[3]+")  作者类别："+(brray[4].equals("1")?"本人":"非本人")+"	 ";
						if(brray[0].equals(brray[1]))
							sss+= th+"."+brray[0]+  " ";
						else
							sss+= th+"."+brray[0]+"("+brray[1]+") ";
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace(); 
						sss+=th+"."+array[th-1];
						continue;
					}
				}
			}
			rec.set("chauthor", sss); 
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_AWARD, achid));
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"},  
				{"名称","name"},
				{"（英文）名称","ename"}, 
				{"完成人","chauthor"},
				{"奖励名称", "awardname"}, 
				{"（英文）奖励名称","eawardname"},
				{"奖级类别","type"},
				{"奖级等级","level"},
				{"授予单位","certigier"}, 
				{"（英文）授予单位","ecertigier"}, 
				{"授予年份","year"}, 
				{"关联人员","relevancename"} ,
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
	
	/**
	 * 科研管理--专利登记excel列表
	 * 
	 * @author deng.gzh
	 * @date 2014-9-11
	 * @Title: getPatentexcel
	 * @throws Excetion
	 */
	public Map<String,Object> getPatentexcel(String name,String excelName, String authors, String year, String userid,
			String status) throws Exception{
		String consql = "select * from "+ Tname.TABLE_PATENT + " t where 1=1"; 
		if(userid != null && !"".equals(userid))
			consql += " and t.register=" + userid ;
		if(name != null && !"".equals(name))
			consql += " and (t.name like '%" +name+ "%' or UPPER(t.ename) like '%"+ name.toUpperCase() +"%')";
		if(authors != null && !"".equals(authors))
			consql += " and t.authors like '%" + authors +"%'";
		if(year != null && !"".equals(year))
			consql += " and t.year like '%" + year +"%'"; 
//		consql += " order by t.lastedittime desc, t.name desc ";
		consql += " order by t.year desc";
		List<Record> recList = Db.find(consql);
		int i=1;
		for(Record rec:recList){ 
			rec.set("num", i++);//设置序号  
			String array[] = rec.getStr("authors").split(";");
			String sss = "";
			for(int th=1,the=array.length;th<=the;th++){
				if(!"".equals(array[th-1])){ 
					String brray[] = array[th-1].split(",");
					try{
//						 sss+=th+"."+"作者："+brray[0]+"(作者英文："+brray[1]+") 单位："+brray[2]+"(单位英文："+brray[3]+")  作者类别："+(brray[4].equals("1")?"本人":"非本人")+"	 ";
						if(brray[0].equals(brray[1]))
							sss+= th+"."+brray[0]+ (brray[4].equals("1")?"*":"") + " ";
						else
							sss+= th+"."+brray[0]+"("+brray[1]+") "+ (brray[4].equals("1")?"*":"");
					}catch(ArrayIndexOutOfBoundsException e){
						e.printStackTrace();
						sss+=th+"."+array[th-1];
						continue;
					}
				}
			}
			rec.set("chauthor", sss); 
			String achid = rec.get("id")+"";
			rec.set("ProName", getProName(Tname.TABLE_PATENT, achid));
		}
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/public/attachment/excel/"+excelName+".xls";
		String fieldName[][] = {
				{"序号", "num"},  
				{"名称","name"},
				{"（英文）名称","ename"}, 
				{"完成人","chauthor"},
				{"专利名称", "patentname"}, 
				{"（英文）专利名称","epatentname"},
				{"专利号","patentno"},
				{"专利类别","type"},
				{"授予单位","certigier"}, 
				{"（英文）授予单位","ecertigier"}, 
				{"授予年份","year"}, 
				{"关联人员","relevancename"},
				{"关联项目","ProName"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, recList, "科研项目列表")){
			mp.put("success", true);
			mp.put("url", fileName); 
		}else{
			mp.put("success", false);
			mp.put("url", ""); 
		} 
		return mp;
	}
	
	
	
	private String getProName(String tname , String achid ) {
		//已关联的项目
		List<Record> achList = new ArrayList<Record>();
		String proName = "";
		if( achid!=null && !"".equals(achid)){
			String sql = "select p.name from "+Tname.TABLE_PROJECTACH + " t , "+Tname.TABLE_PROJECT+" p  where t.achid=" + achid + " and t.tname=  '"+tname+"'  and p.id = t.relid and t.isshow =1  ";
			achList = Db.find(sql);
		}
		for(Record rec : achList){
			proName += rec.getStr("name")+";";
		}
		if(!"".equals(proName)){
			proName = new String(proName.substring(0, proName.lastIndexOf(";")));
		}
		return proName ;
	}
	
/*	*//**
	 * 经济促进局->生成问题列表excel
	 * @author xian.zf
	 * @date 2013-11-1
	 * @param title
	 * @param name
	 * @param lead
	 * @param dep
	 * @param state
	 * @return
	 * @throws Exception
	 *//*
	@Override
	public Map<String, Object> creProblemExcel(String title, String name,
			String lead, String dep, String state, String begintime,
			String endtime, String systype) throws Exception {
		String sql ="SELECT *",
			   whereStr =" FROM ns_problem p WHERE p.delstate=0";
		// 选择系统的
		if("1".equals(systype)){
			whereStr += " AND (p.relate_id IN (SELECT e.id FROM ns_enterprise e WHERE e.group_id in (SELECT g.id FROM ns_group g WHERE g.leader is null)))";
		}
		if("0".equals(systype)){
			whereStr += " AND (p.relate_id IN (SELECT e.id FROM ns_enterprise e WHERE e.group_id in (SELECT g.id FROM ns_group g WHERE g.leader is not null)))";
		}
		
		if(title!=null && !"".equals(title)){
			whereStr +=" AND p.title LIKE '%"+title+"%'";
		}
		if(name!=null && !"".equals(name)){
			whereStr +=" AND (p.relate_id IN (SELECT e.id FROM ns_enterprise e WHERE e.name LIKE '%" + name + "%'))";
		}
		if(lead!=null && !"".equals(lead)){
			whereStr +=" AND (p.focus_id IN (SELECT t.id FROM ns_problem_tran t WHERE t.accept IN (SELECT u.id FROM ns_user u WHERE u.name LIKE '%"+lead+"%'))"
			          +" OR p.relate_id IN (SELECT e.id FROM ns_enterprise e WHERE e.group_id IN (SELECT g.id FROM ns_group g WHERE g.leader IN (SELECT u.id FROM ns_user u WHERE u.name LIKE '%"+lead+"%'))))";
		}
		if(dep!=null && !"".equals(dep)){
			whereStr +=" AND (p.focus_id IN (SELECT t.id FROM ns_problem_tran t WHERE t.accept IN (SELECT d.id FROM ns_department d WHERE d.name LIKE '%"+dep+"%'))"
					  +" OR p.relate_id IN (SELECT e.id FROM ns_enterprise e WHERE e.group_id IN (SELECT g.id FROM ns_group g WHERE g.header IN (SELECT d.id FROM ns_department d WHERE d.name LIKE '%"+dep+"%'))))";
		}
		
		//当前时间
		DateTime nowTime = new DateTime();
		if(state!=null && !"".equals(state)){
			if(state.equals("0") || state.equals("1"))
				whereStr +=" AND p.state="+state;
			else{
				DateTime extensionTime = nowTime.minusDays(14);
				if(state.equals("2"))
					whereStr +=" AND p.state=0";
				else
					whereStr +=" AND p.state=1";
				whereStr +=" AND UNIX_TIMESTAMP(p.createtime) <= UNIX_TIMESTAMP('"+extensionTime.toString("yyyy-MM-dd HH:mm:ss")+"')";
			}
		}
		
		if(CheckFormat.checkTimeFormat(begintime))
			whereStr +=" AND DATE_FORMAT(p.endtime,'%Y-%m-%d')>='"+begintime+"'";
		if(CheckFormat.checkTimeFormat(endtime))
			whereStr +=" AND DATE_FORMAT(p.endtime,'%Y-%m-%d')<='"+endtime+"'";
		
		List<Problem> conList = baseDao.getObjectListBySql(sql + whereStr + " ORDER BY p.createtime asc " , Problem.class);
		List<Map<String, Object>> tbody = new ArrayList<Map<String, Object>>();
		int num = 1;
		for(Problem pro:conList){
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("num", num++);//序号
			mp.put("createtime", new DateTime(pro.getCreatetime()).toString("yyyy年MM月dd日"));//发起时间
			//查找发起者名称
			mp.put("name", "");//企业名称
			mp.put("leader", "");//挂点区领导
			mp.put("linkman", "");//企业联系人
			mp.put("linkmanPhone", "");//企业联系人电话
			if(pro.getType()==0){
				Enterprise enterprise = baseDao.getObjectBySql("SELECT * FROM ns_enterprise e WHERE e.id=?",
						Enterprise.class,new Object[]{pro.getRelate_id()});
				mp.put("name", enterprise==null?"":enterprise.getName());
				if(enterprise!=null){
					//企业对应挂点小组的领导
					User user = baseDao.getObjectBySql("SELECT * FROM ns_user u WHERE u.id=(SELECT g.leader FROM ns_group g WHERE g.id="+enterprise.getGroup_id()+")", User.class);
					mp.put("leader", user==null?"":user.getName());
					user = baseDao.getObjectBySql("SELECT * FROM ns_user u WHERE u.id="+enterprise.getLinkman(), User.class);
					mp.put("linkman", user==null?"":user.getName());
					mp.put("linkmanPhone", user==null?"":user.getPhone());
				}
			}
			
			mp.put("content", pro.getContent()==null?"":pro.getContent().replaceAll("<[a-zA-Z]+[1-9]?[^><]*>|</[a-zA-Z]+[1-9]?>|\\&[a-zA-Z]{1,10};", ""));//企业反映问题(去掉HTML标签)
			Other_item type = baseDao.getObjectBySql("SELECT * FROM ns_other_item t where t.id="+pro.getItem(), Other_item.class);
			mp.put("protype", type==null?"":type.getName());//企业反映问题问题类别
			//办理部门
			List<Problem_tran> tranList = baseDao.getObjectListBySql("SELECT * FROM ns_problem_tran t WHERE t.problem_id=? ORDER BY t.id ASC",
					Problem_tran.class,new Object[]{pro.getId()});
			
			String doman = "";
			for(int i=0, len=tranList.size(); i<len; i++){
				Problem_tran tran = tranList.get(i);
				//有人处理
				if(tran.getAccept()!=0 && tran.getType()!=0){
					Department depar = baseDao.getObjectBySql("SELECT * FROM ns_department d WHERE d.id=?",
							Department.class,new Object[]{tran.getAccept()});
					if(depar!=null)
						doman += depar.getName()+"、 ";
				}
			}
			mp.put("doman", doman.equals("")?"":doman.substring(0, doman.lastIndexOf("、")) );//办理部门
			mp.put("result", pro.getResult()==null?"":pro.getResult());//办理情况
//			mp.put("state", pro.getState()==1?"已解决":"正在解决");//后续解决状况
			DateTime createTime = new DateTime(pro.getCreatetime());
			int days = Days.daysBetween(createTime, nowTime).getDays();
			if(days>14)
				mp.put("state", pro.getState()==1?"延期已处理":"延期");
			else
				mp.put("state", pro.getState()==1?"已处理":"未处理");
			
			tbody.add(mp);
		}
		
		//准备生成excel的数据格式： 记算
		String path = ResourcePathUtil.getPath();
		String fileName = "/attachment/other/问题跟踪报表.xls";
		String fieldName[][] = {
				{"序号", "num"}, 
				{"发起时间", "createtime"}, 
				{"企业名称", "name"}, 
				{"挂点区领导", "leader"},
				{"企业联系人", "linkman"},
				{"联系电话", "linkmanPhone"},
				{"企业反映问题", "content"},
				{"问题类别", "protype"},
				{"办理部门", "doman"},
				{"办理情况", "result"},
				{"后续解决状况", "state"}
			};
		
		Map<String,Object> mp = new HashMap<String,Object>();
		if(this.getOneExcel(path+fileName , fieldName, tbody, "问题跟踪汇总表")){
			mp.put("success", true);
			mp.put("url", fileName);
			return mp;
		}else{
			mp.put("success", false);
			mp.put("url", "");
			return mp;
		}
	}*/

	
}
