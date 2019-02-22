package cn.com.taiji.platform.service;

import javax.servlet.http.HttpServletRequest;

import cn.com.taiji.util.tools.log.LogBean;
import cn.com.taiji.util.tools.log.Log_uopr;

/**
 * 
 * 类名称：LogService.java   
 * 类描述：   操作日志接口
 * 创建人：zhongdd   
 * 创建时间：2017年1月12日 上午10:37:27
 * @version
 */
public interface LogService {

	public LogBean begin(Object... param);

	public Log_uopr saveUoprLog(LogBean begin, LogBean logbean_end);

	public Log_uopr success(LogBean begin, Object rs, HttpServletRequest request);

	public Log_uopr fail(LogBean begin, Object rs, HttpServletRequest request);

}