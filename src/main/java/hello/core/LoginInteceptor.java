package hello.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import hello.user.Stu;

public class LoginInteceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//如果已经登陆 就放行  检查用户的session是否有特定标示
		if(SessionUtil.getUser(request.getSession())==null) {//未登陆
			System.out.println("未登陆-------------------拦截");
			response.sendRedirect("/open/login");
			return false;//自己处理了后面不需要再处理
			
		}else {
			System.out.println("已经登陆-------------------"+SessionUtil.getUser(request.getSession()));
			return true;
		}
	}
}
