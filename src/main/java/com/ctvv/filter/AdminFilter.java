package com.ctvv.filter;

import com.ctvv.model.Admin;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter
		implements Filter {
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(
			ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession httpSession = httpServletRequest.getSession();
		Admin admin= (Admin) httpSession.getAttribute("admin");
		String requestURL = String.valueOf(httpServletRequest.getRequestURL());
		String queryString = httpServletRequest.getQueryString();
		boolean isLoggedIn = (admin != null);
		boolean isLoginPage = (httpServletRequest.getRequestURI().endsWith("login.jsp"));
		boolean isLoginRequest = httpServletRequest.getServletPath().equals("/admin");
		if ((!isLoggedIn) && (!isLoginRequest) && (!isLoginPage)) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/admin?from=" + requestURL +(queryString==null?"":"?"+queryString));
		}
		// Trường hợp admin truy cập phần quản lý admins
		else if (httpServletRequest.getServletPath().endsWith("admins") && (isLoggedIn) && (!admin.getRole().equals("super"))) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/admin/products");
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
	}
}
