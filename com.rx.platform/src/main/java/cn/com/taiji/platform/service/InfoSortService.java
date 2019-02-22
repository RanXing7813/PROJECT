package cn.com.taiji.platform.service;

import java.util.List;

import cn.com.taiji.platform.entity.CmsInfoSort;

/**
 * 
 * @ClassName:  InfoSortService   
 * @Description:TODO
 * @author: zhongdd
 * @date:   2018年5月20日 上午11:23:36   
 *     
 *
 */
public interface InfoSortService {

	List findInfoSortZTree(String rootId);
	
	void saveInfoSort(CmsInfoSort sort);
	
	void delInfoSrot(String infoSortId);
}
