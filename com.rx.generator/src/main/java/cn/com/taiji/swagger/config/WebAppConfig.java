/**   
* @Title: WebMvcConfigurerAdapter.java 
* @Package cn.com.taiji.swagger.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author ranxing   
* @date 2019年3月11日 下午4:52:28 
* @version V1.0   
*/
package cn.com.taiji.swagger.config;

import org.springframework.context.annotation.Bean;

/** 
* @ClassName: WebMvcConfigurerAdapter 
* @Description: TODO 
* @author ranxing 
* @date 2019年3月11日 下午4:52:28 
*  
*/

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter  {

	public WebAppConfig(){
		
	}
	
//    @Override 
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
}