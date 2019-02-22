package cn.com.taiji.platform.service;

import java.util.List;
import java.util.Map;

import cn.com.taiji.platform.dto.SysDeptDto;
import cn.com.taiji.platform.entity.SysDept;
import cn.com.taiji.util.page.Pagination;

/**
 * 
 * @ClassName:  SysUserService   
 * @Description:TODO
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:23:36   
 *     
 *
 */
public interface SysDeptService {

	Pagination<SysDeptDto> findDeptList(Pagination<SysDeptDto> pag, final String search, final String rootId);

	SysDeptDto findDeptById(String id);

	 List<Map> listDeptByRootId(String rootId, String checkedIds);
	 
	 void saveDept(SysDept sysDept);

}
