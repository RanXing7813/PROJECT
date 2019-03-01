package cn.com.taiji.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelSysTable;

/** 
* 模板信息类
* @ClassName:  ModelSysTableRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelSysTableRepository extends JpaRepository<ModelSysTable, String>,
		JpaSpecificationExecutor<ModelSysTable>, PagingAndSortingRepository<ModelSysTable, String> {

}
