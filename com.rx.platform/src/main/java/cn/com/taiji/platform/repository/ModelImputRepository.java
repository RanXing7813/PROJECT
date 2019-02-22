package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelImput;

/** 
* 模板信息类
* @ClassName:  ModelImputRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelImputRepository extends JpaRepository<ModelImput, String>,
		JpaSpecificationExecutor<ModelImput>, PagingAndSortingRepository<ModelImput, String> {

	/**
	 * @param imput_id methods
	 */
	@Modifying
	@Query("update ModelImput set is_del = 'Y' where imput_id = ?1 ")
	void delOne(String imput_id);
	
	
	@Modifying
	@Query(" update ModelImput c  set  c.imp_status = ?2 WHERE c.imput_id = ?1 ")
	void updateStatus(String id, String num);
	
	@Modifying
	@Query(" update ModelImput c  set  c.imp_data_num = ?2 , c.imp_status = '3' WHERE c.imput_id = ?1 ")
	void updateImput(String id, String num);


}
