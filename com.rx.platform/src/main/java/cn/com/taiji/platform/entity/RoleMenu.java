package cn.com.taiji.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description:  角色与菜单关联表
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:38:14   
 * @version V1.0 
 *
 */
@Entity
@Table(name = "role_menu")
@NamedQuery(name = "RoleMenu.findAll", query = " SELECT r FROM RoleMenu r ")
public class RoleMenu implements Serializable {

	/**   
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8174198264880681721L;

	public RoleMenu() {

	}

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "menu_id")
	private String menuid;

	@Column(name = "role_id")
	private String roleid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

}