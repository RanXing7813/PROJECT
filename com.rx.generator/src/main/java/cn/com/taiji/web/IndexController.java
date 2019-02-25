package cn.com.taiji.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.szmirren.entity.DatabaseContent;

/** 
* 常规代码生成首页
* @ClassName: IndexController 
* @Description: TODO 
* @author ranxing 
* @date 2019年2月25日 下午2:43:52 
*  
*/
@Controller
@RequestMapping(value = "/generator/normal")
public class IndexController {
	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 默认代码生成器首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index", method = {RequestMethod.GET })
	public String toIndexPage(Model model, DatabaseContent dto){
		model.addAttribute("dto", dto);
		return "generator/normal/index";
	}
	
	@RequestMapping(value = "toConnectionPage", method = {RequestMethod.GET })
	public String toAddConnectionPage(){
		
		return "generator/normal/connection";
	}
	
	@RequestMapping(value = "saveConnectionPage", method = {RequestMethod.GET })
	public void saveAddConnectionPage(DatabaseContent dto){
		
	}
	

}
