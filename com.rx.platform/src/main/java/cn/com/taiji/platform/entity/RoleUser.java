package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description:  角色与用户关联表
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:38:14   
 * @version V1.0 
 *
 */
@Entity
@Table(name = "role_user")
@NamedQuery(name = "RoleUser.findAll", query = " SELECT r FROM RoleUser r ")
public class RoleUser implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public RoleUser() {

	}

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "user_id")
	private String userid;

	@Column(name = "role_id")
	private String roleid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}