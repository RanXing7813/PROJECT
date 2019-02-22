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
@Table(name = "model_imput")
@NamedQuery(name = "ModelImput.findAll", query = " SELECT r FROM ModelImput r ")
public class ModelImput implements Serializable {

	/**   fields   */
	private static final long serialVersionUID = -3562954122838464865L;
	
	public ModelImput(){
		
	}
	
	
	@Id
	private String imput_id;
	private String imp_model_name;
	private String imp_status;
	private String imp_data_num;
	private String create_id;
	private String create_name;
	private String imp_version;
	private String imp_table_name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_time;
	
	
	public String getImp_version() {
		return imp_version;
	}
	public void setImp_version(String imp_version) {
		this.imp_version = imp_version;
	}
	public String getImp_table_name() {
		return imp_table_name;
	}
	public void setImp_table_name(String imp_table_name) {
		this.imp_table_name = imp_table_name;
	}
	public String getImput_id() {
		return imput_id;
	}
	public void setImput_id(String imput_id) {
		this.imput_id = imput_id;
	}
	public String getImp_model_name() {
		return imp_model_name;
	}
	public void setImp_model_name(String imp_model_name) {
		this.imp_model_name = imp_model_name;
	}
	public String getImp_status() {
		return imp_status;
	}
	public void setImp_status(String imp_status) {
		this.imp_status = imp_status;
	}
	public String getImp_data_num() {
		return imp_data_num;
	}
	public void setImp_data_num(String imp_data_num) {
		this.imp_data_num = imp_data_num;
	}
	public String getCreate_id() {
		return create_id;
	}
	public void setCreate_id(String create_id) {
		this.create_id = create_id;
	}
	public String getCreate_name() {
		return create_name;
	}
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	/**
	 * @return the create_time  getter method
	 */
	public Date getCreate_time() {
		return create_time;
	}
	/**
	 * @param create_time the create_time to set  setter method
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}


}
