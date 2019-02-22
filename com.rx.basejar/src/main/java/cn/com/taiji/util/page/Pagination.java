package cn.com.taiji.util.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class Pagination<T> {

	public Pagination(int currentpage, int pagesize) {
		this.setPagesize(pagesize);
		this.setCurrentpage(currentpage);
		this.setStartno((currentpage - 1) * pagesize);
	}

	public Pagination() {
	}

	/*
	 * 每页显示数据的条数
	 */
	private int pagesize;

	/*
	 * 当前页 页码
	 */
	private int currentpage;
	/*
	 * 总条数
	 */
	private long datacount;
	/*
	 * 总页数
	 */
	private int pagecount;

	/*
	 * 开始
	 */
	private int startno;
	/*
	 * 当前页的结果集
	 */
	private List<T> datalist;
	/*
	 * 需要补的空行
	 */
	private List<T> blanklist;

	public Pagination findPagination(String sql, Pagination pag, Object[] array, JdbcTemplate jdbcTemplate,
			String database_type) {
		StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
		int total = jdbcTemplate.queryForObject(totalSQL.toString(), array, Integer.class);
		// 构造oracle数据库的分页语句
		int lastIndex = pag.getCurrentpage() * pag.getPagesize();
		int startIndex = (pag.getCurrentpage() - 1) * pag.getPagesize();
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		if (database_type.equals("oracle")) {

			paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
			paginationSQL.append(sql);
			paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
			paginationSQL.append(" ) WHERE　num > " + startIndex);
		} else if (database_type.equals("mysql")) {
			paginationSQL.append(" SELECT temp.* FROM ( ");
			paginationSQL.append(sql);
			paginationSQL.append(" limit " + startIndex + "," + pag.getPagesize());
			paginationSQL.append(" ) as temp  ");
			paginationSQL.append(" ) as result");
		}

		pag.setDatalist(jdbcTemplate.queryForList(paginationSQL.toString(), array));
		pag.setPagecount((total - 1) / pag.getPagesize() + 1);
		pag.setDatacount(total);

		return pag;
	}
	
	public  Pagination<T>  findPagination(String sql, Pagination<T>  pag, Object[] array,RowMapper rowMapper,JdbcTemplate jdbcTemplate,String database_type) {
		   StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
		   totalSQL.append(sql);
		   totalSQL.append(" ) totalTable ");
		   int total = jdbcTemplate.queryForObject(totalSQL.toString(),array,Integer.class);	  
		   // 构造oracle数据库的分页语句
		   int lastIndex = pag.getCurrentpage()*pag.getPagesize();
		   int startIndex = (pag.getCurrentpage()-1)*pag.getPagesize();
		   StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		   if(database_type.equals("oracle")){
		      
			   paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
			   paginationSQL.append(sql);
			   paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
			   paginationSQL.append(" ) WHERE　num > " + startIndex);  
		   }
		   else if(database_type.equals("mysql")){
		       paginationSQL.append(" SELECT temp.* FROM ( ");
			   paginationSQL.append(sql);
			   paginationSQL.append(" limit "+startIndex+","+pag.getPagesize());
			   paginationSQL.append(" ) as temp  " );
			   paginationSQL.append(" ) as result");  
		   }
		 
		    pag.setDatalist((List<T>) jdbcTemplate.queryForList(paginationSQL.toString(), array));
			pag.setPagecount((total - 1) / pag.getPagesize() + 1);
			pag.setDatacount(total);
		   return pag;
		}
	
	
	
	

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize == 0 ? 15 : pagesize;
	}

	public int getCurrentpage() {
		return currentpage = currentpage == 0 ? 1 : currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public long getDatacount() {
		return datacount;
	}

	public void setDatacount(long datacount) {
		this.datacount = datacount;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public List<T> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<T> datalist) {
		this.datalist = datalist;
	}

	public int getStartno() {
		return startno;
	}

	public void setStartno(int startno) {
		this.startno = startno;
	}

	public List<T> getBlanklist() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < pagesize - datalist.size(); i++) {
			list.add((T) new Object());
		}
		return list;
	}

	public void setBlanklist(List<T> blanklist) {
		this.blanklist = blanklist;
	}

}
