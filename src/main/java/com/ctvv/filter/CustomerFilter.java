package com.ctvv.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CustomerFilter", urlPatterns = "/*")
public class CustomerFilter
		implements Filter {
	public static final String[] loginRequiredURLs = {
			"/user/account",
			"/user/cart"
	};

	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(
			ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String requestURL = httpServletRequest.getRequestURL().toString();
		String queryString = httpServletRequest.getQueryString();
		String servletPath = httpServletRequest.getServletPath();
		boolean isAdminRequest = servletPath.startsWith("/admin");
		boolean isLoggedIn = httpServletRequest.getSession().getAttribute("customer") != null;
		boolean isLoginRequest = servletPath.equals("");
		boolean isLoginPage = requestURL.endsWith("login.jsp");
		boolean isResourceRequest =
				((requestURL.endsWith(".css")) || (requestURL.endsWith(".js")) || (requestURL.endsWith(".jpg") || (requestURL.endsWith("favicon.ico"))));

		if ((!isAdminRequest) && isLogInRequired(servletPath) && (!isLoggedIn) && (!isLoginRequest) && (!isLoginPage) && (!isResourceRequest)) {
			httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login?from=" + requestURL +(queryString==null?"":"?"+queryString));
		} else {
			httpServletRequest.setAttribute("uri", requestURL + (queryString==null?"":"?"+queryString));
			chain.doFilter(request, response);
		}

	}

	private boolean isLogInRequired(String servletPath) {
		for (String s : loginRequiredURLs) {
			if (servletPath.equals(s)) return true;
		}
		return false;
	}

	public void destroy() {
	}
}
