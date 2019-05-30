package app.znkj.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings({ "serial", "rawtypes" })
public class BaseModel<M extends Model> extends Model<M>{
	
	/**
	 * 根据sql搜索对象 
	 * @return
	 * @throws Excetion
	 */
	public M getbysql(String sql ){  
		return findFirst(sql);
	}
	
	/**
	 * 根据sql搜索列表
	 */ 
	public List  getListByHql(String sql) {
		return find(sql);
	}
	
	/**
	 * 根据id搜索数据
	 */
	public 	M getById(Long id){
		return findById(id);
	}
	
	/**
	 * 保存操作
	 */
	public void save(M object){
		object.save();
	}
}
