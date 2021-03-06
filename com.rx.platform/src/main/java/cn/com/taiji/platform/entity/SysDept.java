package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.dto.SysDeptDto;

@Entity
@Table(name = "dept")
@NamedQuery(name = "SysDept.findAll", query = "SELECT d FROM SysDept d")
public class SysDept implements Serializable {

	private static final long serialVersionUID = 5119673746393145493L;

	public SysDept() {

	}

	public SysDept(SysDeptDto u) {
		BeanUtils.copyProperties(u, this);
	}

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "dept_desc")
	private String deptDesc;

	@Column(name = "dept_index")
	private Integer deptIndex;

	@Column(name = "dept_name")
	private String deptName;

	@Column(name = "dept_state")
	private String deptState;

	@Column(name = "dept_type")
	private String deptType;// 1:行政区划 2:分类 3：单位 4 部门 5 处室

	@Column(name = "dept_url")
	private String deptUrl;

	@Column(name = "create_time")
	private String createTime;

	@Column(name = "creator_id")
	private String creatorId;

	@Column(name = "update_time")
	private String updateTime;

	@Column(name = "updater_id")
	private String updaterId;

	@Column(name = "flag")
	private Integer flag;

	@Column(name = "remark")
	private String remark;

	@Column(name = "parent_id")
	private String parentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Integer getDeptIndex() {
		return deptIndex;
	}

	public void setDeptIndex(Integer deptIndex) {
		this.deptIndex = deptIndex;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptState() {
		return deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptUrl() {
		return deptUrl;
	}

	public void setDeptUrl(String deptUrl) {
		this.deptUrl = deptUrl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}