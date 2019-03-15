/**   
* @Title: ProductController.java 
* @Package cn.com.taiji.swagger.web 
* @Description: TODO(用一句话描述该文件做什么) 
* @author ranxing   
* @date 2019年3月11日 下午3:54:50 
* @version V1.0   
*/
package cn.com.taiji.swagger.web;


import java.util.Arrays;
import java.util.List;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.com.taiji.swagger.entity.Product;


@RestController
@RequestMapping(value = {"/product/"})public class ProductController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable Long id) {
        Product product = new Product();
        product.setName("空气净化器");
        product.setId(1L);
        product.setProductClass("filters");
        product.setProductId("T12345");
        return ResponseEntity.ok(product);
    }
}