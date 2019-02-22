package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description:  信息栏目和信息关系表
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:38:14   
 * @version V1.0 
 *
 */
@Entity
@Table(name = "infosort_infolink")
@NamedQuery(name = "CmsRelation.findAll", query = " SELECT r FROM CmsRelation r ")
public class CmsRelation implements Serializable {

	private static final long serialVersionUID = -8498920754475461516L;

	public CmsRelation() {

	}
	
	public CmsRelation(CmsInfoLink infoLink,String infoSortId) {
		this.setInfoid(infoSortId+"_"+infoLink.getInfolinkid());
		this.setInfolinkid(infoLink.getInfolinkid());
		this.setInfosortid(infoSortId);
	}

	@Id
	@Column(name = "infoid")
	private String infoid;
	@Column(name = "infolink_id")
	private String infolinkid;
	@Column(name = "infosort_id")
	private String infosortid;
	@Column(name = "istop")
	private String istop;
	@Column(name = "link_index")
	private String linkindex;

	public String getInfoid() {
		return infoid;
	}

	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}

	public String getInfolinkid() {
		return infolinkid;
	}

	public void setInfolinkid(String infolinkid) {
		this.infolinkid = infolinkid;
	}

	public String getInfosortid() {
		return infosortid;
	}

	public void setInfosortid(String infosortid) {
		this.infosortid = infosortid;
	}

	public String getIstop() {
		return istop;
	}

	public void setIstop(String istop) {
		this.istop = istop;
	}

	public String getLinkindex() {
		return linkindex;
	}

	public void setLinkindex(String linkindex) {
		this.linkindex = linkindex;
	}

}