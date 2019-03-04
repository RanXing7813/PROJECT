package cn.com.taiji.generator.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/** 
* 模板字段类
* @ClassName:  ModelSysExport
* @author ranxing
* @date 2019年1月17日 下午5:19:38
*/
@Entity
@Table(name = "model_sys_export")
@NamedQuery(name = "ModelSysExport.findAll", query = " SELECT r FROM ModelSysExport r ")
public class ModelSysExport implements Serializable {

	/**   fields   */
	private static final long serialVersionUID = 8023279106962302927L;
	public ModelSysExport(){
		
	}
	@Id
	private String col_id;
	private String col_name;
	private String col_comment;
	private String column_type;
	private char is_uuid;
	private String model_id;
	private int index_;
	
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public int getIndex_() {
		return index_;
	}
	public void setIndex_(int index_) {
		this.index_ = index_;
	}
	public String getCol_id() {
		return col_id;
	}
	public void setCol_id(String col_id) {
		this.col_id = col_id;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getCol_comment() {
		return col_comment;
	}
	public void setCol_comment(String col_comment) {
		this.col_comment = col_comment;
	}
	public char getIs_uuid() {
		return is_uuid;
	}
	public void setIs_uuid(char is_uuid) {
		this.is_uuid = is_uuid;
	}
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	
	


}
