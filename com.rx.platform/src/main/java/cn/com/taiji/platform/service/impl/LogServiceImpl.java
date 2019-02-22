package cn.com.taiji.platform.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.util.tools.log.LogBean;
import cn.com.taiji.util.tools.log.Log_uopr;
import cn.com.taiji.util.tools.log.XLog;

@Transactional
@Service
public class LogServiceImpl extends XLog implements LogService {

	private static List<Log_uopr> UOPRLOGLIST = new ArrayList<Log_uopr>();
	private static String uoprsql = "insert into log_uopr (Uopr_id,Uopr_num,Uopr_state,Uopr_sysname,Uopr_class,Uopr_method,Uopr_line,Uopr_btime,Uopr_etime,Uopr_timelen,Uopr_param,Uopr_result,Uopr_user_id,Uopr_user_name,uopr_dept_id,uopr_dept_name) values(UUID(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private List<Log_uopr> secuoprloglist = new ArrayList();
	private final int MAXLOGNUM = 50;
	private static final String SYSTEM_FLAG = "CMS";
	
	@Inject
	private JdbcTemplate jdbcTemplate;

	@Override
	public LogBean begin(Object... param) {
		// TODO Auto-generated method stub
		return super.begin(param);
	}

	@Override
	public Log_uopr saveUoprLog(LogBean begin, LogBean logbean_end) {
		Log_uopr uopr = new Log_uopr();
		uopr.setUopr_id(begin.getLog_id());
		uopr.setUopr_state(logbean_end.getLog_state());
		uopr.setUopr_sysname(SYSTEM_FLAG);
		uopr.setUopr_class(logbean_end.getLog_class());
		uopr.setUopr_method(logbean_end.getLog_method());
		uopr.setUopr_line(logbean_end.getLog_lineNum());
		uopr.setUopr_btime(begin.getLog_btime());
		uopr.setUopr_etime(logbean_end.getLog_etime());
		uopr.setUopr_timelen((int) logbean_end.getLog_timeLength());
		uopr.setUopr_param(begin.getLog_parm());
		uopr.setUopr_result(logbean_end.getLog_result());
		return uopr;
	}

	@Override
	public Log_uopr fail(LogBean begin, Object rs, HttpServletRequest request) {
		LogBean logbean_end;

		logbean_end = super.endFail(begin, rs);
		Log_uopr uopr = this.saveUoprLog(begin, logbean_end);
		// 操作人信息
		HttpSession session = request.getSession();
		Object o = session.getAttribute("user");
		if (o != null) {
			SysUser user = (SysUser) o;
			uopr.setUopr_user_id(user.getId());
			uopr.setUopr_user_name(user.getUsername());
			uopr.setUopr_dept_id(user.getDeptid());
			uopr.setUopr_dept_name(user.getDeptname());
		}

		addToUOPRCache(uopr);
		// HNTools.dao.insert(uopr);
		return uopr;
	}

	@Override
	public Log_uopr success(LogBean begin, Object rs, HttpServletRequest request) {
		LogBean logbean_end;

		logbean_end = super.endSuccess(begin, rs);
		Log_uopr uopr = this.saveUoprLog(begin, logbean_end);
		// 操作人信息
		HttpSession session = request.getSession();
		Object o = session.getAttribute("user");
		if (o != null) {
			SysUser user = (SysUser) o;
			uopr.setUopr_user_id(user.getId());
			uopr.setUopr_user_name(user.getUsername());
			uopr.setUopr_dept_id(user.getDeptid());
		}

		addToUOPRCache(uopr);
		// HNTools.dao.insert(uopr);
		return uopr;
	}

	private void addToUOPRCache(Log_uopr uopr) {
		/* 设置初始值 */
		if (UOPRLOGLIST.size() >= MAXLOGNUM) {
			System.out.println("UOPRLOGLIST-start:" + UOPRLOGLIST.size());
			for (Log_uopr uopr1 : UOPRLOGLIST) {
				secuoprloglist.add(uopr1);
			}
			UOPRLOGLIST = new ArrayList<Log_uopr>();
			new UoprThread().start();
			System.out.println("UOPRLOGLIST-end:" + UOPRLOGLIST.size());
		}
		UOPRLOGLIST.add(uopr);
		// System.out.println("UOPRLOGLIST.size()：" + UOPRLOGLIST.size());
	}

	class UoprThread extends Thread {
		public void run() {
			try {
				System.out.println("start:" + secuoprloglist.size());
				long start = System.currentTimeMillis();
				Object[] params = null;
				List<Object[]> pList = new ArrayList();
				for (int i = 0; i < secuoprloglist.size(); i++) {
					Log_uopr uopr = (Log_uopr) secuoprloglist.get(i);

					params = new Object[15];

					// params[0] = uopr.getUopr_id();
					params[0] = uopr.getUopr_num();
					params[1] = uopr.getUopr_state();
					params[2] = uopr.getUopr_sysname();
					params[3] = uopr.getUopr_class();
					params[4] = uopr.getUopr_method();
					params[5] = uopr.getUopr_line();
					Timestamp btime = new Timestamp(uopr.getUopr_btime().getTime());
					Timestamp etime = new Timestamp(uopr.getUopr_etime().getTime());
					params[6] = btime;
					params[7] = etime;
					params[8] = uopr.getUopr_timelen();
					params[9] = uopr.getUopr_param();
					params[10] = uopr.getUopr_result();
					params[11] = uopr.getUopr_user_id();
					params[12] = uopr.getUopr_user_name();
					params[13] = uopr.getUopr_dept_id();
					params[14] = uopr.getUopr_dept_name();

					pList.add(params);
				}
				secuoprloglist = new ArrayList<Log_uopr>();
				jdbcTemplate.batchUpdate(uoprsql, pList);
				// System.out.println("Log_uopr save 入库完成,用时：" +
				// (System.currentTimeMillis() - start));
			} catch (Exception e) {
				secuoprloglist = new ArrayList<Log_uopr>();
				System.out.println("errorend:" + secuoprloglist.size());
				e.printStackTrace();
			} finally {
				secuoprloglist = new ArrayList<Log_uopr>();
				System.out.println("end:" + secuoprloglist.size());
			}

		}
	}

	private static String getMACNAME() {
		if (ip == null) {
			setSysInfo();
		}
		return MACNAME;
	}

	private static String getIp() {
		if (ip == null) {
			setSysInfo();
		}
		return ip;
	}

	private static String ip = null;
	private static String MACNAME = null;

	private static void setSysInfo() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress();
			MACNAME = addr.getHostName();
		} catch (Exception e) {
			ip = "error";
			MACNAME = "error";
			e.printStackTrace();
		}

	}

	public String catchLocalIP() {
		InetAddress localIP = null;
		try {
			localIP = InetAddress.getLocalHost();

		} catch (UnknownHostException e) {

		}
		return localIP.getHostAddress();

	}

}
