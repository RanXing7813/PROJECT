package cn.com.taiji.platform.service;

import cn.com.taiji.platform.entity.CmsInfoLink;
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
public interface InfoLinkService {

	Pagination findInfoLinkByInfoSortId(Pagination pag, String infosortid, String search,String userid);

	void infoLinkPublish(String[] ids);

	void delInfoLink(String[] ids);

	void infoLinkSave(CmsInfoLink infoLink, String infoSortId);
	
	CmsInfoLink getInfoLink(String infolinkid);

}
