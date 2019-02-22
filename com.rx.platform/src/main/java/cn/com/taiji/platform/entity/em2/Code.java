package cn.com.taiji.platform.entity.em2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @Description: 字典表
 * @author: zhongdd
 * @date: 2018年5月25日 下午4:38:14
 * @version V1.0
 *
 */
@Entity
@Table(name = "code")
@NamedQuery(name = "Code.findAll", query = " SELECT r FROM Code r ")
public class Code {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "category")
	private int category;
	@Column(name = "code_code")
	private String code_code;
	@Column(name = "code_index")
	private int code_index;
	@Column(name = "code_name")
	private String code_name;
	@Column(name = "code_type")
	private String code_type;
	@Column(name = "code_type_name")
	private String code_type_name;
	@Column(name = "create_time")
	private Date create_time;
	@Column(name = "creator_id")
	private String creator_id;
	@Column(name = "remark")
	private String remark;
	@Column(name = "state")
	private int state;
	@Column(name = "update_time")
	private Date update_time;
	@Column(name = "updater_id")
	private String updater_id;
	@Column(name = "parent_id")
	private String parent_id;
}
