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
@Table(name = "model_data_dept")
@NamedQuery(name = "ModelDataDept.findAll", query = " SELECT r FROM ModelDataDept r ")
public class ModelDataDept implements Serializable {

	

	/**   fields   */
	private static final long serialVersionUID = -1377400461706728052L;

	public ModelDataDept(){
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String model_id;
	private String dept_id;
	private String dept_full_name;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel_id() {
		return model_id;
	}
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	public String getDept_id() {
		return dept_id;
	}
	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}
	public String getDept_full_name() {
		return dept_full_name;
	}
	public void setDept_full_name(String dept_full_name) {
		this.dept_full_name = dept_full_name;
	}



}
