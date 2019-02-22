package cn.com.taiji.platform.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.taiji.platform.entity.SysMenu;

public class SysMenuTreeDto implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public SysMenuTreeDto() {

	}

	public SysMenuTreeDto(SysMenu u) {
		this.setTitle(u.getMenuName());
		this.setIcon(u.getMenuIcon());
		this.setHref(u.getRequestUrl());
		//this.setSpread("1".equals(u.getIsparent())&&"2".equals(u.getLevel())?true:false);
		this.setSpread("1".equals(u.getIsparent()) ? true : false);
		this.setImgpath(u.getImgpath());
		this.setTarget("");
		this.setIsparent(u.getIsparent());
	}

	private String title;

	private String icon;

	private String href;

	private boolean spread;

	private String imgpath;

	private String target;
	
	private String isparent;

	private List<SysMenuTreeDto> children = new ArrayList<SysMenuTreeDto>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isSpread() {
		return spread;
	}

	public void setSpread(boolean spread) {
		this.spread = spread;
	}

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public List<SysMenuTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuTreeDto> children) {
		this.children = children;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIsparent() {
		return isparent;
	}

	public void setIsparent(String isparent) {
		this.isparent = isparent;
	}

}