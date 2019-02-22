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

import cn.com.taiji.platform.dto.SysMenuDto;
import cn.com.taiji.platform.dto.SysMenuTreeDto;
import cn.com.taiji.platform.dto.SysMenuTreeManageDto;
import cn.com.taiji.platform.entity.RoleMenu;
import cn.com.taiji.platform.entity.SysMenu;
import cn.com.taiji.platform.repository.RoleMenuRepository;
import cn.com.taiji.platform.repository.SysMenuRepository;
import cn.com.taiji.platform.service.SysMenuService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName: SysMenuImpl
 * @Description:TODO 菜单实现类
 * @author: zhongdd
 * @date: 2018年5月20日 上午11:28:24
 * 
 *
 */
@Service
public class SysMenuImpl implements SysMenuService {

	@Inject
	private SysMenuRepository sysMenuRepository;
	@Inject
	private RoleMenuRepository roleMenuRepository;

	@Inject
	private JdbcTemplate jdbcTemplate;

	public Pagination<SysMenuDto> findMenuList(Pagination<SysMenuDto> pag, final String search) {
		// TODO Auto-generated method stub

		// 排序
		List<Order> orders = new ArrayList<Sort.Order>();
		Order order = new Order(Direction.ASC, "orderNo");
		orders.add(order);
		Sort sort = new Sort(orders);

		PageRequest request = new PageRequest(pag.getCurrentpage() - 1, pag.getPagesize(), sort);

		Page<SysMenu> page = sysMenuRepository.findAll(new Specification<SysMenu>() {

			public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> pl = new ArrayList<Predicate>();
				pl.add(cb.equal(root.get("state"), "1"));
				if (!RTools.string.isEmpty(search)) {
					pl.add(cb.like(root.<String>get("menuName"), "%" + search + "%"));
				}

				return cb.and(pl.toArray(new Predicate[0]));
			}
		}, request);

		List<SysMenu> dataList = page.getContent();
		List<SysMenuDto> dtolsit = new ArrayList<SysMenuDto>();
		for (SysMenu u : dataList) {
			dtolsit.add(new SysMenuDto(u));
		}

		pag.setDatalist(dtolsit);
		pag.setPagecount(page.getTotalPages());
		pag.setDatacount(page.getTotalElements());
		return pag;
	}

	public SysMenu findMenuById(String id) {
		// TODO Auto-generated method stub
		SysMenu menu = sysMenuRepository.findOne(id);
		return menu;
	}

	// 首页左边功能菜单
	public List<SysMenuTreeDto> findMeunTreeJson(String rootId, List<String> roleIds) {
		List<SysMenu> list = sysMenuRepository.findMenusByPidAndRoleId(rootId, roleIds);

		List<SysMenuTreeDto> treeList = new ArrayList<SysMenuTreeDto>();
		for (SysMenu u : list) {// 第1层

			SysMenuTreeDto mdto = new SysMenuTreeDto(u);

			if ("1".equals(u.getIsparent())) {
				// 如果是父菜单
				List<SysMenu> chirdrenlist2 = sysMenuRepository.findMenusByPidAndRoleId(u.getId(), roleIds);

				List<SysMenuTreeDto> treeList2 = new ArrayList<SysMenuTreeDto>();
				for (SysMenu cu2 : chirdrenlist2) {// 第2层
					SysMenuTreeDto mdto2 = new SysMenuTreeDto(cu2);

					if ("1".equals(cu2.getIsparent())) {// 第3层
						// 如果是父菜单
						List<SysMenu> chirdrenlist3 = sysMenuRepository.findMenusByPidAndRoleId(cu2.getId(), roleIds);

						List<SysMenuTreeDto> treeList3 = new ArrayList<SysMenuTreeDto>();

						for (SysMenu cu3 : chirdrenlist3) {
							SysMenuTreeDto mdto3 = new SysMenuTreeDto(cu3);
							if ("1".equals(cu3.getIsparent())) {// 第4层
								List<SysMenu> chirdrenlist4 = sysMenuRepository.findMenusByPidAndRoleId(cu3.getId(),
										roleIds);
								List<SysMenuTreeDto> treeList4 = new ArrayList<SysMenuTreeDto>();
								for (SysMenu cu4 : chirdrenlist4) {
									SysMenuTreeDto mdto4 = new SysMenuTreeDto(cu4);
									treeList4.add(mdto4);
								}
								mdto3.setChildren(treeList4);
							}
							treeList3.add(mdto3);
						}
						mdto2.setChildren(treeList3);
					}

					treeList2.add(mdto2);
				}
				mdto.setChildren(treeList2);
			}
			treeList.add(mdto);
		}
		return treeList;
	}

	// 菜单管理功能
	public List<SysMenuTreeManageDto> findMeunTreeManageJson(String rootId) {
		List<SysMenu> list = sysMenuRepository.findMenusByPid(rootId);

		List<SysMenuTreeManageDto> treeList = new ArrayList<SysMenuTreeManageDto>();
		for (SysMenu u : list) {// 第1层

			SysMenuTreeManageDto mdto = new SysMenuTreeManageDto(u);

			if ("1".equals(u.getIsparent())) {
				// 如果是父菜单
				List<SysMenu> chirdrenlist2 = sysMenuRepository.findMenusByPid(u.getId());

				List<SysMenuTreeManageDto> treeList2 = new ArrayList<SysMenuTreeManageDto>();
				for (SysMenu cu2 : chirdrenlist2) {// 第2层
					SysMenuTreeManageDto mdto2 = new SysMenuTreeManageDto(cu2);

					if ("1".equals(cu2.getIsparent())) {// 第3层
						// 如果是父菜单
						List<SysMenu> chirdrenlist3 = sysMenuRepository.findMenusByPid(cu2.getId());

						List<SysMenuTreeManageDto> treeList3 = new ArrayList<SysMenuTreeManageDto>();

						for (SysMenu cu3 : chirdrenlist3) {
							SysMenuTreeManageDto mdto3 = new SysMenuTreeManageDto(cu3);

							if ("1".equals(cu3.getIsparent())) {// 第4层

								// 如果是父菜单
								List<SysMenu> chirdrenlist4 = sysMenuRepository.findMenusByPid(cu3.getId());
								List<SysMenuTreeManageDto> treeList4 = new ArrayList<SysMenuTreeManageDto>();
								for (SysMenu cu4 : chirdrenlist4) {
									SysMenuTreeManageDto mdto4 = new SysMenuTreeManageDto(cu4);
									treeList4.add(mdto4);
								}
								mdto3.setChildren(treeList4);
							}

							treeList3.add(mdto3);
						}
						mdto2.setChildren(treeList3);
					}

					treeList2.add(mdto2);
				}
				mdto.setChildren(treeList2);
			}
			treeList.add(mdto);
		}
		return treeList;
	}

	// 获取所有菜单，根据角色判断是否选中
	public List findMenuZTree(String roleId) {
		String sql = "select t.id,t.pId,t.name,t.open,t.icon,(case when t.mcount>0 then 'true' else 'false' end ) as checked from(select menu_id as id,parent_id as pId,menu_name as name,menu_icon as icon,(select count(1) from role_menu where menu_id=m.menu_id and role_id =?) as mcount,order_id,(case when isparent='1' then 'true' else 'false' end) as open from menuinfo m where state=1 ) t  order by t.order_id asc ";
		Object[] args = { roleId };
		List menuList = jdbcTemplate.queryForList(sql, args);
		return menuList;
	}

	// 获取所有菜单，根据角色判断是否选中
	public List findMenuZTree() {
		String sql = "select t.id,t.pId,t.name,t.open,t.icon,'false' as checked from(select menu_id as id,parent_id as pId,menu_name as name,menu_icon as icon,order_id,(case when isparent='1' then 'true' else 'false' end) as open from menuinfo m where state=1 ) t  order by t.order_id asc ";
		List menuList = jdbcTemplate.queryForList(sql);
		return menuList;
	}

	// 菜单授权保存
	public void saveRoleAuth(String roleId, String[] ids) {

		List<RoleMenu> rList = new ArrayList<RoleMenu>();

		for (int i = 0; i < ids.length; i++) {
			RoleMenu ru = new RoleMenu();
			ru.setId(roleId + "_" + ids[i]);// 便于覆盖，避免出现重复授权导致多条记录
			ru.setRoleid(roleId);
			ru.setMenuid(ids[i]);
			rList.add(ru);
		}
		roleMenuRepository.save(rList);
	}

	/**
	 * 
	 * <p>
	 * Title: removeRoleAuth
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param roleId
	 * @param ids         改变的菜单
	 * @param selectedIds 选中的菜单
	 * @see cn.com.taiji.platform.service.SysMenuService#removeRoleAuth(java.lang.String,
	 *      java.lang.String[], java.lang.String)
	 */
	public void removeRoleAuth(String roleId, String[] ids, String selectedIds) {

		String sql = "delete from role_menu where id = ?";
		List<Object[]> batchArgs = new ArrayList<Object[]>();

		for (int i = 0; i < ids.length; i++) {
			if (!selectedIds.contains(ids[i])) {// 对改变的菜单进行过滤，不在选中菜单中的 做删除处理
				Object[] p = { roleId + "_" + ids[i] };
				batchArgs.add(p);
			}
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);

	}

	/**
	 * 保存
	 * 
	 * @param m
	 */
	public void saveMenu(SysMenu m) {

		sysMenuRepository.save(m);

		// 将上级菜单设置为父菜单属性
		String sql = "update menuinfo set isparent=? where menu_id=?";
		Object[] args = { "1", m.getParentId() };
		jdbcTemplate.update(sql, args);

	}

	// 删除菜单
	public void delMenu(String menu_id) {
		String sql = "update menuinfo set state=? where menu_id=?";
		Object[] args = { "0", menu_id };
		jdbcTemplate.update(sql, args);
	}

	// 获取所有菜单图标
	public List findAllIcons() {
		String sql = "select icons_name from sys_icons";
		List menuList = jdbcTemplate.queryForList(sql);
		return menuList;
	}

}
