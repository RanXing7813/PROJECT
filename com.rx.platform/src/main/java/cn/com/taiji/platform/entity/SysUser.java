package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.dto.SysUserDto;

/**
 * 
 * @ClassName:  SysUser   
 * @Description:TODO 系统用户
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:10:56   
 *     
 *
 */
@Entity
@Table(name = "userinfo")
@NamedQuery(name = "SysUser.findAll", query = "SELECT d FROM SysUser d")
public class SysUser implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -253277209047360701L;

	public SysUser() {
	}

	public SysUser(SysUserDto u) {
		BeanUtils.copyProperties(u, this);
	}

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "login_name")
	private String loginname;

	@Column(name = "password")
	private String password;

	@Column(name = "user_name")
	private String username;

	@Column(name = "phone_num")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "remark")
	private String remark;

	@Column(name = "flag")
	private String ustate;

	@Column(name = "user_index")
	private String uindex;

	@Column(name = "create_time")
	private String createtime;

	@Column(name = "update_time")
	private String updatetime;

	@Column(name = "office_id")
	private String deptid;

	@Column(name = "deptName")
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