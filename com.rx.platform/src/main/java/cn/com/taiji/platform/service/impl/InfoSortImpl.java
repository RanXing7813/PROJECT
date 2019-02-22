package cn.com.taiji.platform.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.CmsInfoSort;
import cn.com.taiji.platform.repository.CmsInfoSortRepository;
import cn.com.taiji.platform.service.InfoSortService;

/**
 * 
 * @ClassName:  SysMenuImpl   
 * @Description:TODO 信息栏目实现类
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:28:24   
 *     
 *
 */
@Service
public class InfoSortImpl implements InfoSortService {

	@Inject
	private JdbcTemplate jdbcTemplate;
	@Inject
	private CmsInfoSortRepository cmsInfoSortRepository;

	// 获取所有栏目
	public List findInfoSortZTree(String rootId) {
		String sql = "SELECT t.infosort_id as id,infosort_name as name,t.parent_id as pId from infosort t where t.state=1 ORDER BY t.infosort_index asc";
		Object[] args = {};
		List menuList = jdbcTemplate.queryForList(sql, args);
		return menuList;
	}

	@Override
	public void saveInfoSort(CmsInfoSort sort) {
		// TODO Auto-generated method stub
		cmsInfoSortRepository.save(sort);
	}

	@Override
	public void delInfoSrot(String infoSortId) {
		// TODO Auto-generated method stub
		String sql = "update infosort set state=0 where infosort_id= ?";
		Object[] args = {infoSortId};
		jdbcTemplate.update(sql, args);
	}

}
