package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelSysExport;
import cn.com.taiji.platform.entity.ModelSysTable;

/** 
* 模板字段类
* @ClassName:  ModelInfoRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelSysExportRepository extends JpaRepository<ModelSysExport, String>,
		JpaSpecificationExecutor<ModelSysExport>, PagingAndSortingRepository<ModelSysExport, String> {

}
