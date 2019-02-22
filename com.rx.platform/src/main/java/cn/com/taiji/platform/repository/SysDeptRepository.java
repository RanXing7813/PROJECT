package cn.com.taiji.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.SysDept;

/**
 * 
 * @Description:  机构单位
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:41:20   
 * @version V1.0 
 *
 */
@Transactional
public interface SysDeptRepository extends JpaRepository<SysDept, String>, JpaSpecificationExecutor<SysDept>,
		PagingAndSortingRepository<SysDept, String> {

	/**
	 * 查询所有（未标记为删除的）
	 * @return
	 */
	@Query("select d from SysDept d where d.flag=1")
	List<SysDept> findAllDepts();

	/**
	 * 查询部门列表
	 * @return
	 */
	@Query("select d from SysDept d where d.id in ?1 and flag='1'")
	List<SysDept> findDepts(String[] dept_id);

}
