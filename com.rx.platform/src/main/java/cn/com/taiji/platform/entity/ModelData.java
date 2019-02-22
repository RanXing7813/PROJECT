package cn.com.taiji.platform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/** 
* 模板-导入列表
* @ClassName:  ModelSysTable
* @author ranxing
* @date 2019年1月17日 下午5:19:38
*/
@Entity
@Table(name = "model_data")
@NamedQuery(name = "ModelData.findAll", query = " SELECT r FROM ModelData r ")
public class ModelData implements Serializable {

	/**   fields   */
	private static final long serialVersionUID = -2278712380455598080L;

	public ModelData(){
		
	}
	
	
	@Id
	private String model_id;;
	private String model_zyid;
	private String model_zyname;
	private String model_zydept_id;
	private String model_zydept_name;
	private String model_info_table_name;
	private String model_info_table_cname;
	private String model_name;
	private String model_address;
	private String cread_id;
	private String cread_name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date   cread_time;
	private String update_id;
	private String   update_name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date update_time;
	private String model_version;
	private char is_del;

	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getModel_zyid() {
		return model_zyid;
	}
	public void setModel_zyid(String model_zyid) {
		this.model_zyid = model_zyid;
	}
	public String getModel_zyname() {
		return model_zyname;
	}
	public void setModel_zyname(String model_zyname) {
		this.model_zyname = model_zyname;
	}
	public String getModel_zydept_id() {
		return model_zydept_id;
	}
	public void setModel_zydept_id(String model_zydept_id) {
		this.model_zydept_id = model_zydept_id;
	}
	public String getModel_zydept_name() {
		return model_zydept_name;
	}
	public void setModel_zydept_name(String model_zydept_name) {
		this.model_zydept_name = model_zydept_name;
	}
	public String getModel_info_table_name() {
		return model_info_table_name;
	}
	public void setModel_info_table_name(String model_info_table_name) {
		this.model_info_table_name = model_info_table_name;
	}
	public String getModel_info_table_cname() {
		return model_info_table_cname;
	}
	public void setModel_info_table_cname(String model_info_table_cname) {
		this.model_info_table_cname = model_info_table_cname;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public String getModel_address() {
		return model_address;
	}
	public void setModel_address(String model_address) {
		this.model_address = model_address;
	}
	public String getCread_id() {
		return cread_id;
	}
	public void setCread_id(String cread_id) {
		this.cread_id = cread_id;
	}
	public String getCread_name() {
		return cread_name;
	}
	public void setCread_name(String cread_name) {
		this.cread_name = cread_name;
	}
	public Date getCread_time() {
		return cread_time;
	}
	public void setCread_time(Date cread_time) {
		this.cread_time = cread_time;
	}
	public String getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}
	public String getModel_version() {
		return model_version;
	}
	public void setModel_version(String model_version) {
		this.model_version = model_version;
	}
	public String getUpdate_name() {
		return update_name;
	}
	public void setUpdate_name(String update_name) {
		this.update_name = update_name;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public char getIs_del() {
		return is_del;
	}
	public void setIs_del(char is_del) {
		this.is_del = is_del;
	}
	


}