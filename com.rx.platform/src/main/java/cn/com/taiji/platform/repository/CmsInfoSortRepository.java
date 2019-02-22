package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.CmsInfoSort;

/**
 * 
 * @Description: 信息分类
 * @author: zhongdd
 * @date: 2018年5月25日 下午4:41:29
 * @version V1.0
 *
 */
@Transactional
public interface CmsInfoSortRepository extends JpaRepository<CmsInfoSort, String>,
		JpaSpecificationExecutor<CmsInfoSort>, PagingAndSortingRepository<CmsInfoSort, String> {

}
