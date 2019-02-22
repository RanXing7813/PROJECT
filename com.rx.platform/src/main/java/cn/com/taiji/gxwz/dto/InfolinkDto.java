package cn.com.taiji.gxwz.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InfolinkDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593595984437220269L;
	/**
	 * 站点ｉｄ 自己赋值的字段
	 */
	private String websiteId;
	/**
	 * 置顶
	 */
	private int istop;
	/**
	 * 置顶中文
	 */
	private String top;
	/**
	 * 多附件文件名
	 */
	private String fileNames;
	/**
	 * 文件上传的ids
	 */
	private String fileIds;

	private String infolinkId;

	private String infosortId;
	private String index_;

	public String getIndex_() {
		return index_;
	}

	public void setIndex_(String index_) {
		this.index_ = index_;
	}

	private String content;
	private String content_;

	private String createdTime;

	private String deptId;

	private String infolinkOrigin;

	private int infolinkState;

	private String infolinkStateName;

	private String infolinkTitle;

	private String infolinkType;
	/**
	 * 中文的类型
	 */
	private String infolinkTypeCN;

	private String infolinkUrl;

	private String keyword;

	private String pic;

	private String remark;

	private String serverSaveFilename;

	private String summary;

	private Date updateTime;

	private String createdUser;
	/**
	 * 上传附件类型信息的附件名称
	 */
	private String uploadFileName;
	/**
	 * 上传附件类型信息的附件id
	 */
	private String uploadFileId;

	private String userId;

	/**
	 * 分享到其他栏目下的ｉｄｓ
	 */
	private String infosortIds;
	/**
	 * 分享到其他栏目下的names
	 */
	private String infosortNames;

	/**
	 * 关联的文件下载
	 */
	private List fileList;

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	public int getDeleteFlog() {
		return deleteFlog;
	}

	public void setDeleteFlog(int deleteFlog) {
		this.deleteFlog = deleteFlog;
	}

	private int deleteFlog;

	public String getInfolinkId() {
		return infolinkId;
	}

	public void setInfolinkId(String infolinkId) {
		this.infolinkId = infolinkId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getInfolinkOrigin() {
		return infolinkOrigin;
	}

	public void setInfolinkOrigin(String infolinkOrigin) {
		this.infolinkOrigin = infolinkOrigin;
	}

	public int getInfolinkState() {
		return infolinkState;
	}

	public void setInfolinkState(int infolinkState) {
		this.infolinkState = infolinkState;
	}

	public String getInfolinkTitle() {
		return infolinkTitle;
	}

	public void setInfolinkTitle(String infolinkTitle) {
		this.infolinkTitle = infolinkTitle;
	}

	public String getInfolinkType() {
		return infolinkType;
	}

	public void setInfolinkType(String infolinkType) {
		this.infolinkType = infolinkType;
	}

	public String getInfolinkUrl() {
		return infolinkUrl;
	}

	public void setInfolinkUrl(String infolinkUrl) {
		this.infolinkUrl = infolinkUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getServerSaveFilename() {
		return serverSaveFilename;
	}

	public void setServerSaveFilename(String serverSaveFilename) {
		this.serverSaveFilename = serverSaveFilename;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInfosortId() {
		return infosortId;
	}

	public void setInfosortId(String infosortId) {
		this.infosortId = infosortId;
	}

	public String getInfolinkStateName() {
		return infolinkStateName;
	}

	public void setInfolinkStateName(String infolinkStateName) {
		this.infolinkStateName = infolinkStateName;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getInfosortIds() {
		return infosortIds;
	}

	public void setInfosortIds(String infosortIds) {
		this.infosortIds = infosortIds;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getInfolinkTypeCN() {
		return infolinkTypeCN;
	}

	public void setInfolinkTypeCN(String infolinkTypeCN) {
		this.infolinkTypeCN = infolinkTypeCN;
	}

	public String getInfosortNames() {
		return infosortNames;
	}

	public void setInfosortNames(String infosortNames) {
		this.infosortNames = infosortNames;
	}

	public String getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(String websiteId) {
		this.websiteId = websiteId;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getUploadFileId() {
		return uploadFileId;
	}

	public void setUploadFileId(String uploadFileId) {
		this.uploadFileId = uploadFileId;
	}

	public String getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}

	public String getContent_() {
		return content_;
	}

	public void setContent_(String content_) {
		this.content_ = content_;
	}

}
