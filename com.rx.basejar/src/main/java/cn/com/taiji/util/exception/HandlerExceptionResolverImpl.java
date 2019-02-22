package cn.com.taiji.util.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 
 * @ClassName:  HandlerExceptionResolverImpl   
 * @Description:TODO 异常处理
 * @author: zhongdd
 * @date:   2018年5月19日 上午9:08:54   
 *     
 *
 */
public class HandlerExceptionResolverImpl extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, viewName);
			String diyMsg = "";
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
				diyMsg = "您好！系统内部错误，请联系系统管理员，谢谢！";
			} else {
				if (ex.getMessage().indexOf("org.springframework.web.method.HandlerMethod") > -1) {
					statusCode = 404;
					diyMsg = "您好！您请求的方法不存在，如有疑问，请联系系统管理员，谢谢！";
				} else {
					statusCode = 500;
					diyMsg = "您好！系统内部错误，请联系系统管理员，谢谢！";
				}
			}
			request.setAttribute("statusCode", statusCode);
			request.setAttribute("diyMsg", diyMsg);
			request.setAttribute("exceptionMessage", ex.getMessage());
			try {
				ex.printStackTrace();
			} finally {
				// TODO: handle finally clause
				return getModelAndView(viewName, ex, request);
			}

		} else {
			return null;
		}
	}

}
