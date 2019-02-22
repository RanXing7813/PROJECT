package cn.com.taiji.platform.service;

import java.util.List;

import cn.com.taiji.platform.dto.SysMenuDto;
import cn.com.taiji.platform.dto.SysMenuTreeDto;
import cn.com.taiji.platform.dto.SysMenuTreeManageDto;
import cn.com.taiji.platform.entity.SysMenu;
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
public interface SysMenuService {

	Pagination<SysMenuDto> findMenuList(Pagination<SysMenuDto> pag, String search);

	SysMenu findMenuById(String id);

	List<SysMenuTreeDto> findMeunTreeJson(String rootId,List<String> roleIds);

	List<SysMenuTreeManageDto> findMeunTreeManageJson(String rootId);

	List findMenuZTree(String roleId);

	void saveRoleAuth(String roleId, String[] ids);
	
	void removeRoleAuth(String roleId, String[] ids,String selectedIds);
	
	void saveMenu(SysMenu m);
	
	List findAllIcons();
	
	void delMenu(String menu_id);
	
	public List findMenuZTree();

}
