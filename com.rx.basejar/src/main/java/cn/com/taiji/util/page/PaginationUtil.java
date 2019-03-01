package cn.com.taiji.util.page;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/** 
 * 数据返回公共类
* @ClassName: PaginationUtil 
* @Description: TODO 
* @author ranxing 
* @date 2019年3月1日 下午3:08:53 
* 
* @param <T> 
*/
public class PaginationUtil<T> {

	public PaginationUtil(int currentPage, int pageSize) {
		this.setPageSize(pageSize);
		this.setCurrentPage(currentPage);
		this.setStartNo((currentPage - 1) * pageSize);
	}

	public PaginationUtil() {
	}
	/*
	 * 每页显示数据的条数
	 */
	private int pageSize;
	/*
	 * 当前页 页码
	 */
	private int currentPage;
	/**
	 * 排序字段
	 */
	private String field;
	/**
	 * 排序方式  
	 */
	private String order;
	
	/*
	 * 总条数
	 */
	private long dataCount;
	/*
	 * 总页数
	 */
	private int pageCount;
	/*
	 * 开始
	 */
	private int startNo;
	/*
	 * 当前页的结果集
	 */
	private List<T> dataList;
	/*
	 * 需要补的空行
	 */
	private List<T> blankList;

	public PaginationUtil findPagination(String sql, PaginationUtil pag, Object[] array, JdbcTemplate jdbcTemplate,
			String database_type) {
		StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
		totalSQL.append(sql);
		totalSQL.append(" ) totalTable ");
		int total = jdbcTemplate.queryForObject(totalSQL.toString(), array, Integer.class);
		// 构造oracle数据库的分页语句
		int lastIndex = pag.getCurrentPage()  * pag.getPageSize();
		int startIndex = (pag.getCurrentPage() - 1) * pag.getPageSize();
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		if (database_type.equals("oracle")) {

			paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
			paginationSQL.append(sql);
			paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
			paginationSQL.append(" ) WHERE　num > " + startIndex);
		} else if (database_type.equals("mysql")) {
			paginationSQL.append(" SELECT temp.* FROM ( ");
			paginationSQL.append(sql);
			paginationSQL.append(" limit " + startIndex + "," + pag.getPageSize());
			paginationSQL.append(" ) as temp  ");
			paginationSQL.append(" ) as result");
		}

		pag.setDataList(jdbcTemplate.queryForList(paginationSQL.toString(), array));
		pag.setPageCount((total - 1) / pag.getPageSize() + 1);
		pag.setDataCount(total);

		return pag;
	}
	
	public  PaginationUtil<T>  findPagination(String sql, PaginationUtil<T>  pag, Object[] array,RowMapper rowMapper,JdbcTemplate jdbcTemplate,String database_type) {
		   StringBuffer totalSQL = new StringBuffer(" SELECT count(1) FROM ( ");
		   totalSQL.append(sql);
		   totalSQL.append(" ) totalTable ");
		   int total = jdbcTemplate.queryForObject(totalSQL.toString(),array,Integer.class);	  
		   // 构造oracle数据库的分页语句
			int lastIndex = pag.getCurrentPage()  * pag.getPageSize();
			int startIndex = (pag.getCurrentPage() - 1) * pag.getPageSize();
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
				paginationSQL.append(" limit " + startIndex + "," + pag.getPageSize());
			   paginationSQL.append(" ) as temp  " );
			   paginationSQL.append(" ) as result");  
		   }
		 
		    pag.setDataList((List<T>) jdbcTemplate.queryForList(paginationSQL.toString(), array));
		    pag.setPageCount((total - 1) / pag.getPageSize() + 1);
			pag.setDataCount(total);
		   return pag;
		}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize == 0 ? 10 : pageSize;
	}

	public int getCurrentPage() {
		return currentPage = currentPage == 0 ? 1 : currentPage;
	}

	public void setCurrentpage(int currentPage) {
		this.currentPage = currentPage;
	}
	public List<T> getBlankList() {
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < pageSize - dataList.size(); i++) {
			list.add((T) new Object());
		}
		return list;
	}

	public void setBlankList(List<T> blankList) {
		this.blankList = blankList;
	}
	
	
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	

	public void setOrder(String order) {
		this.order = order;
	}
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public long getDataCount() {
		return dataCount;
	}

	public void setDataCount(long dataCount) {
		this.dataCount = dataCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public List<T> getDataList() {
		return dataList;
	}

	
	public String getOrder() {
		return order;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

}
