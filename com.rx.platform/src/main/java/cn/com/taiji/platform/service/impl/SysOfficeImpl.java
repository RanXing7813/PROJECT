package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.dto.SysDeptDto;
import cn.com.taiji.platform.entity.SysDept;
import cn.com.taiji.platform.repository.SysDeptRepository;
import cn.com.taiji.platform.service.SysOfficeService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName:  SysUserImpl   
 * @Description:TODO 部门实现类
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:28:24   
 *     
 *
 */
@Service
public class SysOfficeImpl implements SysOfficeService {

	@Inject
	private JdbcTemplate jdbcTemplate;

	public List<Map> listDeptByRootId(String rootId, String checkedIds) {
		String sql = "select id,parent_id as pId,dept_name as name,dept_desc as deptcode from dept t where parent_id = ? order by dept_index asc";
		Object[] params = { rootId };
		List deptList = jdbcTemplate.queryForList(sql, params);
		for (int i = 0; i < deptList.size(); i++) {
			((Map) deptList.get(i)).put("isParent",isSubNodeByDeptId(String.valueOf(((Map) deptList.get(i)).get("id"))));
			if (!RTools.string.isBlank(checkedIds)) {
				((Map) deptList.get(i)).put("checked", isChecked((Map) deptList.get(i), checkedIds));
			}
		}
		return deptList;
	}

	public boolean isSubNodeByDeptId(String deptId) {
		String sql = "select id from dept where parent_id=?";
		Object[] params = { deptId };
		List deptList = jdbcTemplate.queryForList(sql, params);
		return deptList != null && deptList.size() > 0 ? true : false;
	}

	public boolean isChecked(Map node, String checkedIds) {
		return checkedIds.contains(String.valueOf(node.get("id")));
	}

}
