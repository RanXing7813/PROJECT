package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import cn.com.taiji.util.tools.RTools;

/**
 * 
 * 类名称：DataSourceImpl.java 类描述： 数据源管理 创建人：zhongdd 创建时间：2016年10月28日 上午9:50:21
 * 
 * @version
 */
@Service
public class DataSourceImpl implements ApplicationContextAware, BeanNameAware {

	private String beanName;
	private ApplicationContext applicationContext;

	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public ApplicationContext getApplicationContext() {
		if (applicationContext == null) {
			applicationContext = new FileSystemXmlApplicationContext(
					this.getClass().getResource("/") + "spring/app-config.xml");
		}
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Inject
	private JdbcTemplate jdbcTemplate;

	public DataSource getDriverManagerDataSource(String dbid) {
		if ("sgongsj".equals(dbid)) {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName("com.sybase.jdbc3.jdbc.SybDriver");
			ds.setUsername("shengxinuser");
			ds.setPassword("sEbJ38H#");
			// ds.setDriverClassName("com.sybase.jdbc3.jdbc.SybDriver");
			ds.setUrl("jdbc:sybase:Tds:19.16.172.24:7220/AICRSD_UNITY?charset=cp936");
			System.out.println("DBinfo:jdbc:sybase:Tds:19.16.172.24:7220/AICRSD_UNITY?charset=cp936");
			return ds;
		} else {
			DataSource dataSource = (DataSource) this.getApplicationContext().getBean(dbid);
			return dataSource;
		}
	}

	public static JdbcTemplate sgongsjjt;
	// public static Connection conn;
//	static {
//		getJdbcTemplate();
//	}

	public static void getJdbcTemplate() {

	}

	/**
	 * oracle----
	 * 
	 * @param sql
	 * @param filter
	 * @param sort
	 * @param group
	 * @param currentPage
	 * @param pageSize
	 * @param selectCloum
	 * @param sqlType
	 * @return
	 */
	public String fillSelectSql(String sql, Map<String, String> filter, Map<String, String> sort,
			String group, int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}

			// 根据不同数据库类型进行分页
			String dbtype = RTools.properties.getValue("dbtype");
			if ("oracle".equals(dbtype)) {
				sql = "select * from ( select ROWNUM as rowno ,ta.* from (" + sql + ") ta) tt where  tt.rowno > "
						+ (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage * pageSize + "";
			} else if ("mysql".equals(dbtype)) {
				sql = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
			}

		}
		return sql;
	}
	
	public String fillSelectSql(String sql, Map<String, String> filter, Map<String, String> sort, String group,
			int currentPage, int pageSize, String selectCloum, int sqlType, String sel) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			
			//根据不同数据库类型进行分页
			String dbtype = RTools.properties.getValue("dbtype");
			if ("oracle".equals(dbtype)) {
				sql = "select " + sel + " from ( select ROWNUM as rowno ,ta.* from (" + sql + ") ta) tt where  tt.rowno > "
						+ (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage * pageSize + "";
			} else if ("mysql".equals(dbtype)) {
				sql = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
			}
		}
		return sql;
	}
	
	public String fillSelectSqlForSqlView(String sql, Map<String, String> filter, Map<String, String> sort,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(">=")) {
					String[] ks = key.split(">=");
					if (index == 0) {
						sql += " where " + ks[0] + ">= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " >= '" + value + "' ";
					}
				} else if (key.contains("=in")) {
					String[] ks = key.split("=in");
					if (index == 0) {
						sql += " where " + ks[0] + " in (" + value + ") ";
					} else {
						sql += " and " + ks[0] + " in (" + value + ") ";
					}
				} else if (key.contains("isnotnull")) {
					String[] ks = key.split("isnotnull");
					if (index == 0) {
						sql += " where " + ks[0] + " is not null";
					} else {
						sql += " and " + ks[0] + " is not null ";
					}
				} else if (key.contains("<>")) {
					String[] ks = key.split("<>");
					if (index == 0) {
						sql += " where " + ks[0] + " <> '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <> '" + value + "' ";
					}
				} else if (key.contains("<=")) {
					String[] ks = key.split("<=");
					if (index == 0) {
						sql += " where " + ks[0] + " <= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <= '" + value + "' ";
					}
				} else {
					if (value == null) {
						if (key.contains(":")) {
							String[] ks = key.split(":");
							if (index == 0) {
								sql += " where " + ks[0] + " is " + value + " ";
							} else {
								sql += " and " + ks[0] + " is " + value + " ";
							}
						}
					} else {
						if (key.contains(":")) {
							String[] ks = key.split(":");
							if (index == 0) {
								sql += " where " + ks[0] + "= '" + value + "' ";
							} else {
								sql += " and " + ks[0] + "= '" + value + "' ";
							}
						} else {
							if (index == 0) {
								sql += " where " + key + " like '%" + value + "%' ";
							} else {
								sql += " and " + key + " like '%" + value + "%' ";
							}
						}
					}
				}
				index++;
			}
		}

		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}
		return sql;
	}

	/**
	 * ranxing
	 */
	public String fillSelectSqlForSqlView2(String sql, Map<String, Object> filter, Map<String, String> sort,
			int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		Object value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, Object> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(">=")) {
					String[] ks = key.split(">=");
					if (index == 0) {
						sql += " where " + ks[0] + " >= " + value;
					} else {
						sql += " and " + ks[0] + " >= " + value;
					}
				} else if (key.contains("<=")) {
					String[] ks = key.split("<=");
					if (index == 0) {
						sql += " where " + ks[0] + " <= " + value;
					} else {
						sql += " and " + ks[0] + " <= " + value;
					}
				} else if (key.contains("<")) {
					String[] ks = key.split("<");
					if (index == 0) {
						sql += " where " + ks[0] + " < " + value;
					} else {
						sql += " and " + ks[0] + " < " + value;
					}
				} else {
					if (value == null) {
						if (key.contains(":")) {
							String[] ks = key.split(":");
							if (index == 0) {
								sql += " where " + ks[0] + " is " + value + " ";
							} else {
								sql += " and " + ks[0] + " is " + value + " ";
							}
						}
					} else {
						if (key.contains(":")) {
							String[] ks = key.split(":");
							if (index == 0) {
								sql += " where " + ks[0] + "= '" + value + "' ";
							} else {
								sql += " and " + ks[0] + "= '" + value + "' ";
							}
						} else if (key.contains("_._")) {
							String[] ks = key.split("_._");
							if (index == 0) {
								sql += " where " + ks[0] + "= '" + value + "' ";
							} else {
								sql += " or " + ks[0] + "= '" + value + "' ";
							}
						} else {
							if (index == 0) {
								sql += " where " + key + " like '%" + value + "%' ";
							} else {
								sql += " and " + key + " like '%" + value + "%' ";
							}
						}
					}
				}
				index++;
			}
		}

		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}
		return sql;
	}

	public int selectCountForSqlView2(String SqlView, Map<String, Object> filter, DataSource datasource)
			throws Exception {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = fillSelectSqlForSqlView2(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, java.lang.Integer.class);
		return rtn;
	}

	public int selectCountForSqlView(String SqlView, Map<String, String> filter, JdbcTemplate jdbcTemplate)
			throws Exception {
		String sql = fillSelectSqlForSqlView(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, java.lang.Integer.class);
		return rtn;
	}

	public int selectCountForSqlView(String SqlView, Map<String, String> filter) throws Exception {
		String sql = fillSelectSqlForSqlView(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, java.lang.Integer.class);
		return rtn;
	}

	public String fillSelectSqlForSqlViewAddGroup(String sql, Map<String, String> filter, Map<String, String> sort,
			String group, int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				} else if (key.contains(">=")) {
					String[] ks = key.split(">=");
					if (index == 0) {
						sql += " where " + ks[0] + ">= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " >= '" + value + "' ";
					}
				} else if (key.contains("<=")) {
					String[] ks = key.split("<=");
					if (index == 0) {
						sql += " where " + ks[0] + "<= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <= '" + value + "' ";
					}
				} else if (key.contains("=in")) {
					String[] ks = key.split("=in");
					if (index == 0) {
						sql += " where " + ks[0] + " in (" + value + ") ";
					} else {
						sql += " and " + ks[0] + " in (" + value + ") ";
					}
				} else if (key.contains("<>")) {
					String[] ks = key.split("<>");
					if (index == 0) {
						sql += " where " + ks[0] + " <> '" + value + "' ";
					} else {
						sql += " and " + ks[0] + " <> '" + value + "' ";
					}
				} else if (key.contains("isnotnull")) {
					String[] ks = key.split("isnotnull");
					if (index == 0) {
						sql += " where " + ks[0] + " is not null";
					} else {
						sql += " and " + ks[0] + " is not null ";
					}
				} else if (key.contains("is")) {
					String[] ks = key.split("is");
					if (index == 0) {
						sql += " where " + ks[0] + " is null)";
					} else {
						sql += " and " + ks[0] + " is null)";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + " like '%" + value + "%' ";
					} else {
						sql += " and " + key + " like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() > 0) {
				sql += "  order by ";
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += key + " " + value + " ,";
				}
				sql = sql.substring(0, sql.length() - 1);
			}
			sql += " LIMIT " + (currentPage - 1) * pageSize + "," + pageSize + "";
		}

		return sql;
	}

	public int selectCountForSqlView1(String SqlView, Map<String, String> filter) throws Exception {
		String sql = fillSelectSqlForSqlView(SqlView, filter, null, 0, 0, null, 1);
		int rtn = jdbcTemplate.queryForObject(sql, java.lang.Integer.class);
		return rtn;
	}

	/**
	 * oracle----
	 * 
	 * @param sql
	 * @param filter
	 * @param sort
	 * @param group
	 * @param currentPage
	 * @param pageSize
	 * @param selectCloum
	 * @param sqlType
	 * @return
	 */
	public String fillOracleSelectForSqlViewAddGroup(String sql, Map<String, String> filter, Map<String, String> sort,
			String group, int currentPage, int pageSize, String selectCloum, int sqlType) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}

			//根据不同数据库类型进行分页
			String dbtype = RTools.properties.getValue("dbtype");
			if ("oracle".equals(dbtype)) {
				sql = "select * from ( select ROWNUM as rowno ,ta.* from (" + sql + ") ta) tt where  tt.rowno > "
						+ (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage * pageSize + "";
			} else if ("mysql".equals(dbtype)) {
				sql = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
			}

		}
		return sql;
	}

	public String getCodeNameByOracle(String sql, Map<String, String> filter, Map<String, String> sort, String group,
			int currentPage, int pageSize, String selectCloum, int sqlType, String sel) {

		String key = null;
		String value = null;
		if (filter != null && !filter.isEmpty()) {
			// Map<String, String> map = new HashMap<String, String>();
			int index = 0;
			for (Map.Entry<String, String> entry : filter.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				if (key.contains(":")) {
					String[] ks = key.split(":");
					if (index == 0) {
						sql += " where " + ks[0] + "= '" + value + "' ";
					} else {
						sql += " and " + ks[0] + "= '" + value + "' ";
					}
				} else {
					if (index == 0) {
						sql += " where " + key + "like '%" + value + "%' ";
					} else {
						sql += " and " + key + "like '%" + value + "%' ";
					}
				}
				index++;
			}
		}
		if (sqlType != 1) {
			if (group != null && !group.isEmpty()) {
				sql += "  group by " + group;
			}
		}
		if (sqlType != 1) {
			if (sort != null && !sort.isEmpty() && sort.size() == 1) {
				for (Map.Entry<String, String> entry : sort.entrySet()) {
					key = entry.getKey();
					value = entry.getValue();
					sql += "  order by " + key + " " + value;
				}
			}
			
			//根据不同数据库类型进行分页
			String dbtype = RTools.properties.getValue("dbtype");
			if ("oracle".equals(dbtype)) {
				sql = "select " + sel + " from ( select ROWNUM as rowno ,ta.* from (" + sql + ") ta) tt where  tt.rowno > "
						+ (currentPage - 1) * pageSize + " and tt.rowno <= " + currentPage * pageSize + "";
			} else if ("mysql".equals(dbtype)) {
				sql = sql + " limit " + (currentPage - 1) * pageSize + "," + pageSize;
			}
		}
		return sql;
	}

	/**
	 * 
	* @Title: mapKeyToUpperCase
	* @Description: TODO(将map的key转为大写)
	* @param @param map    参数
	* @return void    返回类型
	* @throws
	 */
	public List<Map<String, Object>> mapKeyToUpperCase(List<Map<String, Object>> list) {
		List<Map<String, Object>> newlist = new ArrayList<>();
		for (int i = 0; i < (list != null ? list.size() : 0); i++) {
			Map<String, Object> map = list.get(i);
			newlist.add(mapKeyToUpperCase(map));
		}
		return newlist;
	}

	/**
	 * 
	* @Title: mapKeyToUpperCase
	* @Description: TODO(将map的key转为大写)
	* @param @param map    参数
	* @return void    返回类型
	* @throws
	 */
	public Map<String, Object> mapKeyToUpperCase(Map<String, Object> map) {
		Map<String, Object> mapData = null;
		if (map != null) {
			mapData = new HashMap<String, Object>();
			Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> entry = it.next();
				// map.put(String.valueOf(entry.getKey()).toUpperCase(),
				// entry.getValue());
				mapData.put(String.valueOf(entry.getKey()).toUpperCase(), entry.getValue());
			}
			map.clear();
		}
		return mapData;
	}
}
