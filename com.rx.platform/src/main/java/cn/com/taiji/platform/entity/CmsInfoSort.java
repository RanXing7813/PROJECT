package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description: 信息分类
 * @author: zhongdd
 * @date: 2018年5月25日 下午4:38:14
 * @version V1.0
 *
 */
@Entity
@Table(name = "infosort")
@NamedQuery(name = "CmsInfoSort.findAll", query = " SELECT r FROM CmsInfoSort r ")
public class CmsInfoSort implements Serializable {
	private static final long serialVersionUID = -3790608357557604228L;

	public CmsInfoSort() {

	}

	@Id
	@Column(name = "infosort_id")
	private String id;
	@Column(name = "infosort_name")
	private String name;
	@Column(name = "parent_id")
	private String pid;
	@Column(name = "state")
	private int state;// '有效标识',0 无效，1：有效

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}