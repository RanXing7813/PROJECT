package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description:  信息表
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:38:14   
 * @version V1.0 
 *
 */
@Entity
@Table(name = "infolink")
@NamedQuery(name = "CmsInfoLink.findAll", query = " SELECT r FROM CmsInfoLink r ")
public class CmsInfoLink implements Serializable {

	private static final long serialVersionUID = -8498920754475461516L;

	public CmsInfoLink() {

	}

	@Id
	@Column(name = "infolink_id")
	private String infolinkid;
	@Column(name = "infolink_url")
	private String infolinkurl;// 'url 连接的值',
	@Column(name = "dept_id")
	private String deptid;// '部门ｉｄ',
	@Column(name = "infolink_type")
	private String infolinktype;// 信息类型 0信息；1外部链接
	@Column(name = "infolink_title")
	private String infolinktitle;// '信息标题',
	@Column(name = "created_time")
	private String createdtime;// '创建的时间',
	@Column(name = "update_time")
	private String updatetime;// '更新时间',
	@Column(name = "infolink_state")
	private int infolinkstate;// '信息状态',0 草稿，1：已发布
	@Column(name = "content")
	private String content;// '编辑组编辑的内容',
	@Column(name = "infolink_origin")
	private String infolinkorigin;// '来源',
	@Column(name = "summary")
	private String summary;// '摘要',
	@Column(name = "user_id")
	private String userid;// '创建人',
	@Column(name = "remark")
	private String remark;// '备注',
	@Column(name = "keyword")
	private String keyword;// '关键字',
	@Column(name = "delete_flog")
	private int deleteflog;// '删除标志',0 已删除，1：未删除

	public String getInfolinkid() {
		return infolinkid;
	}

	public void setInfolinkid(String infolinkid) {
		this.infolinkid = infolinkid;
	}

	public String getInfolinkurl() {
		return infolinkurl;
	}

	public void setInfolinkurl(String infolinkurl) {
		this.infolinkurl = infolinkurl;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getInfolinktype() {
		return infolinktype;
	}

	public void setInfolinktype(String infolinktype) {
		this.infolinktype = infolinktype;
	}

	public String getInfolinktitle() {
		return infolinktitle;
	}

	public void setInfolinktitle(String infolinktitle) {
		this.infolinktitle = infolinktitle;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getInfolinkstate() {
		return infolinkstate;
	}

	public void setInfolinkstate(int infolinkstate) {
		this.infolinkstate = infolinkstate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInfolinkorigin() {
		return infolinkorigin;
	}

	public void setInfolinkorigin(String infolinkorigin) {
		this.infolinkorigin = infolinkorigin;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getDeleteflog() {
		return deleteflog;
	}

	public void setDeleteflog(int deleteflog) {
		this.deleteflog = deleteflog;
	}

}