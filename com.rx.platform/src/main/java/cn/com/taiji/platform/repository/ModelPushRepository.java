package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelPush;

/** 
* 模板类
* @ClassName:  ModelDataRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelPushRepository extends JpaRepository< ModelPush, String>,
		JpaSpecificationExecutor< ModelPush>, PagingAndSortingRepository< ModelPush, String> {

	/**
	 * @param imput_id methods
	 */
	@Modifying
	@Query("update ModelPush set publish_status = 'N' where model_id = ?1 ")
	void chengOne(String imput_id);

	/**
	 * @param ids methods
	 */
	@Modifying
	@Query("delete from  ModelPush c where c.model_id in ?1 ")
	void delDatas(String[] ids);

	/**
	 * @param ids
	 * @param id methods
	 */
	@Modifying
	@Query("update ModelPush set publish_status = 'N', update_id=?2 where model_id in ?1 ")
	void backSome(String[] ids, String id);

	
}
