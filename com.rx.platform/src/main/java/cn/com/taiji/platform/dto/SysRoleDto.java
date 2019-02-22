package cn.com.taiji.platform.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.entity.SysRole;

public class SysRoleDto implements Serializable {

	private static final long serialVersionUID = 5119673746393145493L;

	public SysRoleDto() {

	}

	public SysRoleDto(SysRole u) {
		BeanUtils.copyProperties(u, this);
	}

	private String id;

	private String roleName;

	private String roleDescription;

	private String flag;

	private int usrecount;

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