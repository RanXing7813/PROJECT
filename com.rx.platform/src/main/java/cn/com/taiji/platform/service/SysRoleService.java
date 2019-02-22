package cn.com.taiji.platform.service;

import java.util.List;

import cn.com.taiji.platform.dto.SysRoleDto;
import cn.com.taiji.platform.entity.SysRole;
import cn.com.taiji.util.page.Pagination;

/**
 * 
 * @ClassName:  SysRoleService   
 * @Description:TODO
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:23:36   
 *     
 *
 */
public interface SysRoleService {

	Pagination<SysRoleDto> findRoleList(Pagination<SysRoleDto> pag, String search);

	void addUserAuth(String roleId, String[] ids);

	void removeUserAuth(String roleId, String[] ids);
	
	List<SysRole> findSysRolesByUserId(String userid);
	
	public SysRole findRoleByRoleName(String roleName);
	
	void saveRole(SysRole role);
	
	public void delRole(String roleId);
}
