package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelDataDept;
import cn.com.taiji.platform.entity.ModelPush;

/** 
* 模板类
* @ClassName:  ModelDataRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelDataDeptRepository extends JpaRepository< ModelDataDept, String>,
		JpaSpecificationExecutor< ModelDataDept>, PagingAndSortingRepository< ModelDataDept, String> {

	/**
	 * @param ids methods
	 */
	@Modifying
	@Query("delete from  ModelDataDept c where c.model_id in ?1 ")
	void delDatas(String[] ids);

	
}
