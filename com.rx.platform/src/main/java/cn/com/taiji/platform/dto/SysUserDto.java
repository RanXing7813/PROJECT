package cn.com.taiji.platform.dto;

import java.io.Serializable;

import javax.persistence.Column;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.entity.SysUser;

/**
 * 
 * @ClassName:  SysUserDto   
 * @Description:TODO 系统用户
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:11:16   
 *     
 *
 */
public class SysUserDto implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8814646763771173646L;

	public SysUserDto() {
	}

	public SysUserDto(SysUser u) {
		BeanUtils.copyProperties(u, this);
	}

	private String id;

	private String loginname;

	private String password;

	private String username;

	private String sex;

	private String phone;

	private String email;

	private String tel;

	private String remark;

	private String ustate;

	private String uindex;

	private String createtime;

	private String updatetime;

	private String deptid;

	private String deptname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUstate() {
		return ustate;
	}

	public void setUstate(String ustate) {
		this.ustate = ustate;
	}

	public String getUindex() {
		return uindex;
	}

	public void setUindex(String uindex) {
		this.uindex = uindex;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

}
