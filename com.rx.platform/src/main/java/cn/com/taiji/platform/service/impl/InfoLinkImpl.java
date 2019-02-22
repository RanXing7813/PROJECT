package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.CmsInfoLink;
import cn.com.taiji.platform.entity.CmsRelation;
import cn.com.taiji.platform.repository.CmsInfoLinkRepository;
import cn.com.taiji.platform.repository.CmsRelationRepository;
import cn.com.taiji.platform.service.InfoLinkService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName:  SysUserImpl   
 * @Description:TODO 信息服务实现类
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:28:24   
 *     
 *
 */
@Service
public class InfoLinkImpl implements InfoLinkService {

	@Inject
	private JdbcTemplate jdbcTemplate;
	@Inject
	private CmsInfoLinkRepository cmsInfoLinkRepository;
	@Inject
	private CmsRelationRepository cmsRelationRepository;

	private final String database_type = "mysql";

	// 根据栏目分类查询信息
	public Pagination findInfoLinkByInfoSortId(Pagination pag, String infosortid, String search,String userid) {
		List plist = new ArrayList();
		String sql = "select i.infolink_id,i.infolink_title,i.infolink_origin,i.created_time,i.update_time,i.infolink_type,i.infolink_url,i.infolink_state from infolink i inner join infosort_infolink k on i.infolink_id = k.infolink_id where i.delete_flog=1 ";
		if (!RTools.string.isEmpty(userid)) {
			sql += " and i.user_id=? ";
			plist.add(userid);
		}
		if (!RTools.string.isEmpty(infosortid)) {
			sql += " and k.infosort_id=? ";
			plist.add(infosortid);
		}
		if (!RTools.string.isEmpty(search)) {
			sql += " and (i.infolink_title like ?  or i.infolink_origin like ? ) ";
			plist.add("%" + search + "%");
			plist.add("%" + search + "%");
		}
		sql += " order by i.update_time desc";

		RTools.json.toJson(plist);

		Object[] array = plist.toArray();
		pag = pag.findPagination(sql, pag, array, jdbcTemplate, database_type);
		return pag;
	}

	// 保存
	public void infoLinkSave(CmsInfoLink infoLink, String infoSortId) {
		cmsInfoLinkRepository.save(infoLink);
		if (!RTools.string.isEmpty(infoSortId)) {
			cmsRelationRepository.save(new CmsRelation(infoLink, infoSortId));// 关联栏目和信息
		}

	}

	// 批量发布
	public void infoLinkPublish(String[] ids) {
		String sql = "update infolink set infolink_state=1,update_time=? where infolink_id=?";

		List<Object[]> batchArgs = new ArrayList<Object[]>();
		String updatetime = RTools.dateTimeUtil.getNowTime(1);

		for (int i = 0; i < ids.length; i++) {
			Object[] p = { updatetime, ids[i] };
			batchArgs.add(p);
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	// 批量删除
	public void delInfoLink(String[] ids) {
		// TODO Auto-generated method stub
		String sql = "update infolink set delete_flog=0,update_time=? where infolink_id=?";

		List<Object[]> batchArgs = new ArrayList<Object[]>();
		String updatetime = RTools.dateTimeUtil.getNowTime(1);

		for (int i = 0; i < ids.length; i++) {
			Object[] p = { updatetime, ids[i] };
			batchArgs.add(p);
		}
		jdbcTemplate.batchUpdate(sql, batchArgs);

	}

	// 保存
	public CmsInfoLink getInfoLink(String infolinkid) {
		return cmsInfoLinkRepository.findOne(infolinkid);
	}

}
