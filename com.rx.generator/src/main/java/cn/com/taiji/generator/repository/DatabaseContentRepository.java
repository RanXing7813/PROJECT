package cn.com.taiji.generator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.generator.entity.DatabaseContent;

/**
 * 
 * @Description:  信息服务
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:41:29   
 * @version V1.0 
 *
 */
@Transactional
public interface DatabaseContentRepository extends JpaRepository<DatabaseContent, String>,
		JpaSpecificationExecutor<DatabaseContent>, PagingAndSortingRepository<DatabaseContent, String> {

}
