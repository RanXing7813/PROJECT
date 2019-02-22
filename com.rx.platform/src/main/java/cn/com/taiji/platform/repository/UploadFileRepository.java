package cn.com.taiji.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.UploadFile;

/**
 * 
 * @Description:  角色
 * @author: zhongdd     
 * @date:   2018年5月25日 下午4:40:58   
 * @version V1.0 
 *
 */
@Transactional
public interface UploadFileRepository extends JpaRepository<UploadFile, String>, JpaSpecificationExecutor<UploadFile>,
		PagingAndSortingRepository<UploadFile, String> {

	@Query(" select c from UploadFile c where c.fkid = ?1")
	UploadFile getOneByFkid(String fkid );
	
	
}
