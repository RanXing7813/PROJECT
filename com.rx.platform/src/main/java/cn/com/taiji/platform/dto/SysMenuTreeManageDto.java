package cn.com.taiji.platform.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.taiji.platform.entity.SysMenu;

public class SysMenuTreeManageDto implements Serializable {
	private static final long serialVersionUID = 4483292377064609558L;

	public SysMenuTreeManageDto() {

	}

	public SysMenuTreeManageDto(SysMenu u) {
		this.setId(u.getId());
		this.setName(u.getMenuName());
		this.setTitle(u.getMenuName());
		this.setIcon(u.getMenuIcon());
		// this.setHref(u.getRequestUrl());
		this.setSpread("1".equals(u.getIsparent()) ? true : false);
		this.setImgpath(u.getImgpath());
		this.setTarget("");
	}

	private String id;

	private String name;

	private String title;

	private String icon;

	private String href;

	private boolean spread;

	private String imgpath;

	private String target;

	private String level;

	private List<SysMenuTreeManageDto> children = new ArrayList<SysMenuTreeManageDto>();

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<SysMenuTreeManageDto> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenuTreeManageDto> children) {
		this.children = children;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}