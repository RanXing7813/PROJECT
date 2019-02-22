package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.RoleUser;

/**
 * 
 * @Description: 角色与用户关联对象
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:41:29   
 * @version V1.0 
 *
 */
@Transactional
public interface RoleUserRepository extends JpaRepository<RoleUser, String>, JpaSpecificationExecutor<RoleUser>,
		PagingAndSortingRepository<RoleUser, String> {
	
	

}
