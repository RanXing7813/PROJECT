package cn.com.taiji.platform.service;

import cn.com.taiji.platform.dto.SysUserDto;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.Pagination;

/**
 * 
 * @ClassName:  SysUserService   
 * @Description:TODO
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:23:36   
 *     
 *
 */
public interface SysUserService {

	void saveUser(SysUser sysUser);

	Pagination<SysUserDto> findUserList(Pagination pag, String search);

	SysUser login(String loginname, String password);

	SysUser findUserByLoginName(String loginname);

	SysUserDto findUserById(String id);

	void delUser(String[] ids);

	Pagination findUsersByRoleId(Pagination pag, String roleId, String search);

	Pagination findUsersByDeptCode(Pagination pag, String deptCode, String search);

}
