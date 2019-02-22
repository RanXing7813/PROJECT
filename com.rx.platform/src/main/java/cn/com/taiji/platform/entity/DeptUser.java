package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description:  机构与用户关联表
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:38:14   
 * @version V1.0 
 *
 */
@Entity
@Table(name = "dept_user")
@NamedQuery(name = "DeptUser.findAll", query = " SELECT r FROM DeptUser r ")
public class DeptUser implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public DeptUser() {

	}

	@Id
	@Column(name = "id")
	private String userid;

	@Column(name = "dept_id")
	private String deptid;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

}