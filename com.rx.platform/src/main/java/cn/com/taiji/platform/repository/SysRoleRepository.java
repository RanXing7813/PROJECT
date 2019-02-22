package cn.com.taiji.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.SysRole;

/**
 * 
 * @Description:  角色
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:40:58   
 * @version V1.0 
 *
 */
@Transactional
public interface SysRoleRepository extends JpaRepository<SysRole, String>, JpaSpecificationExecutor<SysRole>,
		PagingAndSortingRepository<SysRole, String> {

	@Query("select d from SysRole d where flag='1'")
	List<SysRole> findSysRoles();

	@Query("select d from SysRole d INNER JOIN RoleUser ru on d.id=ru.roleid where d.flag='1' and ru.userid = :userid")
	List<SysRole> findSysRolesByUserId(@Param("userid") String userid);
	
	
	@Query("select d from SysRole d where flag='1' and roleName = :roleName")
	SysRole findRoleByRoleName(@Param("roleName") String roleName);

}
