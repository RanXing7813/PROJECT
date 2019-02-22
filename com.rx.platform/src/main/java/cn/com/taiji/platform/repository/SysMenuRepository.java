package cn.com.taiji.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.SysMenu;

/**
 * 
 * @Description:  菜单
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:41:09   
 * @version V1.0 
 *
 */
@Transactional
public interface SysMenuRepository extends JpaRepository<SysMenu, String>, JpaSpecificationExecutor<SysMenu>,
		PagingAndSortingRepository<SysMenu, String> {

	/**
	 * 
	 * @Description: 根据父ID功能菜单
	 * @author: zhongdd
	 * @date: 2018年6月12日 上午10:52:38
	 * @param: @param parentId
	 * @param: @return       
	 * @return: List<SysMenu>      
	 * @throws
	 */
	@Query("select d from SysMenu d where d.parentId = ?1 and state='1' order by orderNo asc")
	List<SysMenu> findMenusByPid(String parentId);
	
	/**
	 * 
	 * @Description: 根据父ID和角色 查询已授权的功能菜单
	 * @author: zhongdd
	 * @date: 2018年6月12日 上午10:52:29
	 * @param: @param parentId 菜单父ID
	 * @param: @param roleId 角色ID
	 * @param: @return       
	 * @return: List<SysMenu>      
	 * @throws
	 */
	@Query("select DISTINCT d from SysMenu d INNER JOIN RoleMenu r on d.id=r.menuid  where d.parentId = ?1 and r.roleid in(?2) and d.state='1' order by d.orderNo asc")
	List<SysMenu> findMenusByPidAndRoleId(String parentId,List<String> roleIds);

}
