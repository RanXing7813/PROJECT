package cn.com.taiji.platform.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/** 
* 模板-导入列表
* @ClassName:  ModelPush
* @author ranxing
* @date 2019年1月17日 下午5:19:38
*/
@Entity
@Table(name = "model_data_publish")
@NamedQuery(name = "ModelPush.findAll", query = " SELECT r FROM ModelPush r ")
public class ModelPush implements Serializable {

	
	/**   fields   */
	private static final long serialVersionUID = -1064449054318706308L;

	public ModelPush(){
		
	}
	@Id
	private String model_id;
	private String cread_id;
	private char publish_status;
	private String update_id;

	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getCread_id() {
		return cread_id;
	}
	public void setCread_id(String cread_id) {
		this.cread_id = cread_id;
	}
	public char getPublish_status() {
		return publish_status;
	}
	public void setPublish_status(char publish_status) {
		this.publish_status = publish_status;
	}
	public String getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}



}
