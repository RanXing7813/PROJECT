package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelData;
import cn.com.taiji.platform.entity.ModelSysTable;

/** 
* 模板类
* @ClassName:  ModelDataRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelDataRepository extends JpaRepository< ModelData, String>,
		JpaSpecificationExecutor< ModelData>, PagingAndSortingRepository< ModelData, String> {

	/**
	 * @param imput_id methods
	 */
	@Modifying
	@Query("update ModelData set is_del = 'Y' where model_id = ?1 ")
	void delOne(String imput_id);

	/**
	 * @param ids methods
	 */
	@Modifying
	@Query("update ModelData set is_del = 'Y' where model_id in ?1 ")
	void delDatas(String[] ids);

	/**
	 * @param modelId methods
	 */
	@Modifying
	@Query("update ModelData set model_version = model_version+1 where model_id = ?1 ")
	void modelVersionAdd(String modelId);

	
}
