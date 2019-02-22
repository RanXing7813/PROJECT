package cn.com.taiji.platform.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/** 
* 模板信息类
* @ClassName:  ModelSysTable
* @author ranxing
* @date 2019年1月17日 下午5:19:38
*/
@Entity
@Table(name = "model_sys_table")
@NamedQuery(name = "ModelSysTable.findAll", query = " SELECT r FROM ModelSysTable r ")
public class ModelSysTable implements Serializable {

	/**   fields   */
	private static final long serialVersionUID = 6471041671309417117L;
	
	@Id
	private String id_table_name;
	private String table_name;
	private String table_cname;
	private String data_count;
	private String dept_id;
	private String dept_name;
	private int info_index;
	
	
	public ModelSysTable(){
		
	}
	
	public String getId_table_name() {
		return id_table_name;
	}
	public void setId_table_name(String id_table_name) {
		this.id_table_name = id_table_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_cname() {
		return table_cname;
	}
	public void setTable_cname(String table_cname) {
		this.table_cname = table_cname;
	}
	public String getData_count() {
		return data_count;
	}
	public void setData_count(String data_count) {
		this.data_count = data_count;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @return the info_index  getter method
	 */
	public int getInfo_index() {
		return info_index;
	}

	/**
	 * @param info_index the info_index to set  setter method
	 */
	public void setInfo_index(int info_index) {
		this.info_index = info_index;
	}


}
