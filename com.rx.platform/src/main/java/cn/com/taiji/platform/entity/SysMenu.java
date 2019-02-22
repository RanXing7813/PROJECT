package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import cn.com.taiji.platform.dto.SysMenuDto;

@Entity
@Table(name = "menuinfo")
@NamedQuery(name = "SysMenu.findAll", query = " SELECT r FROM SysMenu r ")
public class SysMenu implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public SysMenu() {

	}

	public SysMenu(SysMenuDto u) {
		BeanUtils.copyProperties(u, this);
	}

	@Id
	@Column(name = "menu_id")
	private String id;

	@Column(name = "menu_name")
	private String menuName;

	@Column(name = "parent_id")
	private String parentId;

	@Column(name = "request_url")
	private String requestUrl;

	@Column(name = "menu_icon")
	private String menuIcon;

	@Column(name = "menu_img_path")
	private String imgpath;

	@Column(name = "state")
	private String state;

	@Column(name = "insert_time")
	private String insetTime;

	@Column(name = "order_id")
	private String orderNo;

	@Column(name = "isparent")
	private String isparent;

	@Column(name = "level")
	private String level;

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