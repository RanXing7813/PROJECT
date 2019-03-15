package cn.com.taiji.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.generator.entity.ModelSysExport2;


/** 
* 模板字段类
* @ClassName:  ModelInfoRepository
* @author ranxing
* @date 2019年1月17日 下午5:20:23
*/
@Transactional
public interface ModelSysExportRepository2 extends JpaRepository<ModelSysExport2, String>,
		JpaSpecificationExecutor<ModelSysExport2>, PagingAndSortingRepository<ModelSysExport2, String> {

}
