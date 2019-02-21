package cn.com.taiji.tools.holder;

public class DataSourceContextHolder {  
  
	public static void main(String[] args) {
		DataSourceContextHolder.setDataSourceType("centerdb");//使用数据库前进行切换
	}
    private static final ThreadLocal<String> contextHolder=new ThreadLocal<String>();  
      
    public static void setDataSourceType(String dataSourceName){  
        contextHolder.set(dataSourceName);  
    }  
      
    public static String getDataSourceName(){  
        return (String) contextHolder.get();  
    }  
      
    public static void clearDataSourceType(){  
        contextHolder.remove();  
    }  
      
}