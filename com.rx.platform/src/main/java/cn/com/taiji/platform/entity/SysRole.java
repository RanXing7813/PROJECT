package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.dto.SysRoleDto;

@Entity
@Table(name = "role")
@NamedQuery(name = "SysRole.findAll", query = " SELECT r FROM SysRole r ")
public class SysRole implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public SysRole() {

	}

	public SysRole(SysRoleDto u) {
		BeanUtils.copyProperties(u, this);
	}

	@Id
	@Column(name = "role_id")
	private String id;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_description")
	private String roleDescription;

	@Column(name = "flag")
	private String flag;

	@Column(name = "show_users")
	private int usrecount;

//	@Id
//	@Column(name = "role_id")
//	private String roleid;
//	@Column(name = "role_name")
//	private String rolename;
//	@Column(name = "remark")
//	private String remark;
//	@Column(name = "role_description")
//	private String role_description;
//	@Column(name = "create_time")
//	private Date createtime;
//	@Column(name = "creator_id")
//	private String creatorid;
//	@Column(name = "flag")
//	private int flag;
//	@Column(name = "role_index")
//	private int roleindex;
//	@Column(name = "update_time")
//	private Date updatetime;
//	@Column(name = "updater_id")
//	private String updaterid;
//	@Column(name = "sys_flag")
//	private String sysflag;
//	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getUsrecount() {
		return usrecount;
	}

	public void setUsrecount(int usrecount) {
		this.usrecount = usrecount;
	}

}