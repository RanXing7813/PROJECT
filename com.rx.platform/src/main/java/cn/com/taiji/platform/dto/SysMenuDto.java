package cn.com.taiji.platform.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.entity.SysMenu;

public class SysMenuDto implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public SysMenuDto() {

	}

	public SysMenuDto(SysMenu u) {
		BeanUtils.copyProperties(u, this);
	}

	private String id;

	private String menuName;

	private String parentId;

	private String requestUrl;

	private String menuIcon;

	private String imgpath;

	private String state;

	private String insetTime;

	private String orderNo;

	private String isparent;

	private String level;

	private List<SysMenu> children = new ArrayList<SysMenu>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getInsetTime() {
		return insetTime;
	}

	public void setInsetTime(String insetTime) {
		this.insetTime = insetTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public String getIsparent() {
		return isparent;
	}

	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}