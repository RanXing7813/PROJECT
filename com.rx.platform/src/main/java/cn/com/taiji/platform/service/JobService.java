package cn.com.taiji.platform.service;

import cn.com.taiji.util.page.Pagination;

/**
 * 
 * @Description:  定时任务
 * @author: zhongdd     
 * @date:   2018年8月2日 上午9:42:37   
 * @version V1.0 
 *
 */
public interface JobService {
	
/**
 * 
 * @Description:获取所有正在运行的job
 * @author: zhongdd
 * @date: 2018年8月2日 上午9:42:32
 * @param: @param pag
 * @param: @return       
 * @return: Pagination      
 * @throws
 */
	Pagination findRunJob(Pagination pag);


}
