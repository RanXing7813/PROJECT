package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

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

import cn.com.taiji.platform.dto.SysRoleDto;
import cn.com.taiji.platform.entity.RoleUser;
import cn.com.taiji.platform.entity.SysRole;
import cn.com.taiji.platform.repository.RoleUserRepository;
import cn.com.taiji.platform.repository.SysRoleRepository;
import cn.com.taiji.platform.service.SysRoleService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName: SysRoImpl
 * @Description:TODO 系统用户实现类
 * @author: zhongdd
 * @date: 2018年5月20日 上午11:28:24
 * 
 *
 */
@Service
public class SysRoleImpl implements SysRoleService {

	@Inject
	private SysRoleRepository sysRoleRepository;
	@Inject
	private RoleUserRepository roleUserRepository;

	@Inject
	private JdbcTemplate jdbcTemplate;

	public Pagination<SysRoleDto> findRoleList(Pagination pag, final String search) {
		// TODO Auto-generated method stub

		// 排序
		List<Order> orders = new ArrayList<Sort.Order>();
		Order order = new Order(Direction.DESC, "roleName");
		orders.add(order);
		Sort sort = new Sort(orders);

		PageRequest request = new PageRequest(pag.getCurrentpage() - 1, pag.getPagesize(), sort);

		Page<SysRole> page = sysRoleRepository.findAll(new Specification<SysRole>() {

			public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> pl = new ArrayList<Predicate>();
				pl.add(cb.equal(root.get("flag"), "1"));
				if (!RTools.string.isEmpty(search)) {
					pl.add(cb.like(root.<String>get("roleName"), "%" + search + "%"));
				}
				return cb.and(pl.toArray(new Predicate[0]));
			}
		}, request);

		List<SysRole> dataList = page.getContent();
		List<SysRoleDto> dtolsit = new ArrayList<SysRoleDto>();
		for (SysRole u : dataList) {
			dtolsit.add(new SysRoleDto(u));
		}

		pag.setDatalist(dtolsit);
		pag.setPagecount(page.getTotalPages());
		pag.setDatacount(page.getTotalElements());
		return pag;
	}

	// 用户权限
	public void addUserAuth(String roleId, String[] ids) {
		// TODO Auto-generated method stub
		// String sql = "INSERT INTO role_user(id,role_id,user_id)
		// VALUES(?,?,?)";

		List<RoleUser> rList = new ArrayList<RoleUser>();

		for (int i = 0; i < ids.length; i++) {
			RoleUser ru = new RoleUser();
			ru.setId(roleId + "_" + ids[i]);// 便于覆盖，避免出现重复授权导致多条记录
			ru.setRoleid(roleId);
			ru.setUserid(ids[i]);
			rList.add(ru);
		}

		roleUserRepository.save(rList);

		updateRoleUserCount(roleId);

	}

	// 移除用户权限
	public void removeUserAuth(String roleId, String[] ids) {
		// TODO Auto-generated method stub
		String sql = "delete from role_user where id = ?";

		List<Object[]> batchArgs = new ArrayList<Object[]>();

		for (int i = 0; i < ids.length; i++) {
			Object[] p = { ids[i] };
			batchArgs.add(p);
		}

		jdbcTemplate.batchUpdate(sql, batchArgs);

		updateRoleUserCount(roleId);
	}

	// 更新角色下用户数量
	public void updateRoleUserCount(String roleId) {

		String sql = "update role set show_users=(SELECT count(1) from role_user where role_id=?) where role_id = ?";
		Object[] args = { roleId, roleId };
		jdbcTemplate.update(sql, args);
	}

	// 删除角色
	public void delRole(String roleId) {
		String sql = "update role set flag=0 where show_users=0 and role_id = ?";
		Object[] args = { roleId };
		jdbcTemplate.update(sql, args);
	}

	// 获取用户的角色
	public List<SysRole> findSysRolesByUserId(String userid) {
		return sysRoleRepository.findSysRolesByUserId(userid);
	}

	// 根据角色名称获取角色
	public SysRole findRoleByRoleName(String roleName) {
		// TODO Auto-generated method stub
		return sysRoleRepository.findRoleByRoleName(roleName);
	}

	// 保存角色
	public void saveRole(SysRole role) {
		sysRoleRepository.save(role);
	}

}
