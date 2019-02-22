package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @ClassName:  UploadFile   
 * @Description:TODO 附件
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:10:56   
 *     
 *
 */
@Entity
@Table(name = "upload_file")
@NamedQuery(name = "SysUser.UploadFile", query = "SELECT d FROM UploadFile d")
public class UploadFile implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 5164609405511370802L;

	public UploadFile() {
	}

	@Id
	@Column(name = "file_id")
	private String fileid;// '附件ID',
	@Column(name = "creat_time")
	private String creattime;// '创建时间',
	@Column(name = "delete_flog")
	private String deleteflog;// '删除标记（1：有效，-1：无效）',
	@Column(name = "file_name")
	private String filename;// '文件原名称',
	@Column(name = "file_size")
	private Long filesize;// '文件大小',
	@Column(name = "file_type")
	private String filetype;// '文件类型',
	@Column(name = "fk_id")
	private String fkid;// '对象ID',
	@Column(name = "updata_time")
	private String updatatime;// 更新时间',
	@Column(name = "file_save_path")
	private String filesavepath;// '文件保存路径',
	@Column(name = "file_save_name")
	private String filesavename;// '文件保存名称',

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getCreattime() {
		return creattime;
	}

	public void setCreattime(String creattime) {
		this.creattime = creattime;
	}

	public String getDeleteflog() {
		return deleteflog;
	}

	public void setDeleteflog(String deleteflog) {
		this.deleteflog = deleteflog;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFkid() {
		return fkid;
	}

	public void setFkid(String fkid) {
		this.fkid = fkid;
	}

	public String getUpdatatime() {
		return updatatime;
	}

	public void setUpdatatime(String updatatime) {
		this.updatatime = updatatime;
	}

	public String getFilesavepath() {
		return filesavepath;
	}

	public void setFilesavepath(String filesavepath) {
		this.filesavepath = filesavepath;
	}

	public String getFilesavename() {
		return filesavename;
	}

	public void setFilesavename(String filesavename) {
		this.filesavename = filesavename;
	}

}