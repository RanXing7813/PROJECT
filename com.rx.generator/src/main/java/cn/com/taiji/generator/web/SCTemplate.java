/**   
* @Title: SCTemplate.java 
* @Package cn.com.taiji.generator.web 
* @Description: TODO(用一句话描述该文件做什么) 
* @author ranxing   
* @date 2019年3月1日 上午10:39:59 
* @version V1.0   
*/
package cn.com.taiji.generator.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/** 
* @ClassName: SCTemplate 
* @Description: TODO 
* @author ranxing 
* @date 2019年3月1日 上午10:39:59 
*  
*/
public class SCTemplate {

	public static void main(String[] args) throws TemplateException {
		
		// 创建Configuration实例，指定版本
		Configuration configuration = new Configuration(Configuration.getVersion());
		try {
		    // 指定configuration对象模板文件存放的路径
		    configuration.setDirectoryForTemplateLoading(new File("C:\\myWorkSpace\\02-workspase\\taiji01\\King_rx\\com.rx.platform\\src\\main\\resource\\template"));
		    // 设置config的默认字符集，一般是UTF-8
		    configuration.setDefaultEncoding("UTF-8");
		    // 设置错误控制器
		    configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		    
		 // 获取模版
		    Template template = configuration.getTemplate("hello.ftl");
		 // 创建一个Writer对象，指定生成的文件保存的路径及文件名
		    Writer writer = new FileWriter(new File("C:\\myWorkSpace\\02-workspase\\taiji01\\King_rx\\com.rx.platform\\src\\main\\resource\\template\\templateOut\\hello.html"));
		    
		    Map map = new HashMap();
		    User user = new SCTemplate().new User();
		    user.setUsername("aaa");
		    user.setAge(12);
		    map.put("user", user);
		    template.process(map, writer);
		    
		    
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
		
		
		
	}
	public class User {
	    private String username;
	    private int age;
	    /** setter and getter **/
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
	}
	
	
	
	
	
	
}
