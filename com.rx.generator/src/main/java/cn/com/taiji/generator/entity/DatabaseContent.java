package cn.com.taiji.generator.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**a
 * 数据库配置信息
 * 
 * @author <a href="http://szmirren.com">Mirren</a>
 *
 */
@Entity
@Table(name = "databaseconfig")
@NamedQuery(name = "DatabaseContent.findAll", query = " SELECT r FROM DatabaseContent r ")
public class DatabaseContent  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseContent(){
		
	}
	
	/** 首页显示的名字 */
	private String displayName;
	/** 主机地址 */
	private String host;
	/** 端口号 */
	private String port;
	/** 数据库名称 */
	@Id
	private String dbName;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String userPwd;
	/** 数据库类型 */
	private String dbType;
	/** 字符编码 */
	private String encoding;

	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}


}
