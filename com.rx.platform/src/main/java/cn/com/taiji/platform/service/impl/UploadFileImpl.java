package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.UploadFile;
import cn.com.taiji.platform.repository.UploadFileRepository;
import cn.com.taiji.platform.service.UploadFileService;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName:  UploadImpl   
 * @Description:TODO 附件上传
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:28:24   
 *     
 *
 */
@Service
public class UploadFileImpl implements UploadFileService {

	@Inject
	private UploadFileRepository uploadFileRepository;
	@Inject
	private JdbcTemplate jdbcTemplate;

	private final String database_type = "mysql";

	public void saveUploadFile(UploadFile uf) {
		uploadFileRepository.save(uf);
	}

	public void delFile(String[] ids) {
		// TODO Auto-generated method stub
		String sql = "update upload_file set delete_flog=-1,updata_time=? where file_id = ?";

		List<Object[]> batchArgs = new ArrayList<Object[]>();
		String updatetime = RTools.dateTimeUtil.getNowTime(1);

		for (int i = 0; i < ids.length; i++) {
			Object[] p = { updatetime, ids[i] };
			batchArgs.add(p);
		}

		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	public List fileList(String fkid){
		String sql = "SELECT file_id as fid,file_name as fname,file_size as fsize from upload_file where fk_id=? and delete_flog=1 order by creat_time asc";
		Object[] args = {fkid};
		return jdbcTemplate.queryForList(sql, args);
	}
	
	public UploadFile getUploadFileByFid(String fid){
		return uploadFileRepository.findOne(fid);
	}

}
