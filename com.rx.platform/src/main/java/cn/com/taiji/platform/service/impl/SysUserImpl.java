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

import cn.com.taiji.platform.dto.SysUserDto;
import cn.com.taiji.platform.entity.DeptUser;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.repository.DeptUserRepository;
import cn.com.taiji.platform.repository.SysUserRepository;
import cn.com.taiji.platform.service.SysUserService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName:  SysUserImpl   
 * @Description:TODO 系统用户实现类
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:28:24   
 *     
 *
 */
@Service
public class SysUserImpl implements SysUserService {

	@Inject
	private SysUserRepository sysUserRepository;

	@Inject
	private DeptUserRepository deptUserRepository;

	@Inject
	private JdbcTemplate jdbcTemplate;

	private final String database_type = "mysql";

	public void saveUser(SysUser sysUser) {
		// TODO Auto-generated method stub
		sysUserRepository.save(sysUser);

		// 机构用用户关系
		if (!RTools.string.isEmpty(sysUser.getDeptid())) {
			DeptUser du = new DeptUser();

			du.setUserid(sysUser.getId());
			du.setDeptid(sysUser.getDeptid());

			deptUserRepository.save(du);
		}
	}

	public Pagination<SysUserDto> findUserList(Pagination pag, final String search) {
		// TODO Auto-generated method stub

		// 排序
		List<Order> orders = new ArrayList<Sort.Order>();
		Order order = new Order(Direction.DESC, "uindex");
		orders.add(order);
		Sort sort = new Sort(orders);

		PageRequest request = new PageRequest(pag.getCurrentpage() - 1, pag.getPagesize(), sort);

		Page<SysUser> page = sysUserRepository.findAll(new Specification<SysUser>() {

			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> pl = new ArrayList<Predicate>();
				pl.add(cb.equal(root.get("ustate"), "1"));
				if (!RTools.string.isEmpty(search)) {
					pl.add(cb.or(cb.like(root.<String>get("loginname"), "%" + search + "%"),
							cb.like(root.<String>get("username"), "%" + search + "%")));
				}

				return cb.and(pl.toArray(new Predicate[0]));
			}
		}, request);

		List<SysUser> dataList = page.getContent();
		List<SysUserDto> dtolsit = new ArrayList<SysUserDto>();
		for (SysUser u : dataList) {
			dtolsit.add(new SysUserDto(u));
		}

		pag.setDatalist(dtolsit);
		pag.setPagecount(page.getTotalPages());
		pag.setDatacount(page.getTotalElements());
		return pag;
	}

	public SysUser login(String loginname, String password) {
		// TODO Auto-generated method stub
		return sysUserRepository.login(loginname, password);
	}

	public SysUser findUserByLoginName(String loginname) {
		// TODO Auto-generated method stub
		return sysUserRepository.findUserByLoginName(loginname);
	}

	public SysUserDto findUserById(String id) {
		// TODO Auto-generated method stub
		SysUser user = sysUserRepository.findOne(id);
		SysUserDto userDto = new SysUserDto(user);
		return userDto;
	}

	/**
	 * 
	 * <p>Title: delUser</p>   
	 * <p>Description: </p>   
	 * @param ids   
	 * @see cn.com.taiji.platform.service.SysUserService#delUser(java.lang.String[])
	 */
	public void delUser(String[] ids) {
		// TODO Auto-generated method stub
		String sql = "update userinfo set flag=2,updatetime=? where id = ?";

		List<Object[]> batchArgs = new ArrayList<Object[]>();
		String updatetime = RTools.dateTimeUtil.getNowTime(1);

		for (int i = 0; i < ids.length; i++) {
			Object[] p = { updatetime, ids[i] };
			batchArgs.add(p);
		}

		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	//根据角色查询用户
	public Pagination findUsersByRoleId(Pagination pag, String roleId, String search) {
		String sql = "select r.id as id,u.user_name as username,u.login_name as loginname,u.deptname,u.phone_num,u.email,u.flag asstate from userinfo u left join role_user r on u.id=r.user_id where u.flag=1 and r.role_id=? ";
		if (!RTools.string.isEmpty(search)) {
			sql += " and (u.user_name like '%" + search + "%'  or u.deptName like '%" + search + "%') ";
		}
		sql += " order by u.user_index asc";
		Object[] array = { roleId };
		pag = pag.findPagination(sql, pag, array, jdbcTemplate, database_type);
		return pag;
	}
	
	//根据机构code获取本级和下级用户
	public Pagination findUsersByDeptCode(Pagination pag, String deptCode, String search) {
		String sql = "select u.id, u.user_name as username,u.login_name as loginname,u.deptname as deptname,u.phone_num,u.email,u.flag as state from dept d inner join userinfo u on d.id=u.office_id where u.flag=1 and d.dept_desc like ? ";
		if (!RTools.string.isEmpty(search)) {
			sql += " and (u.user_name like '%" + search + "%'  or u.deptName like '%" + search + "%') ";
		}
		sql += " order by u.user_index asc";
		Object[] array = { deptCode+"%" };
		pag = pag.findPagination(sql, pag, array, jdbcTemplate, database_type);
		return pag;
	}

}
