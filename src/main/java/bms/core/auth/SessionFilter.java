package bms.core.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class SessionFilter extends HttpServlet implements Filter{
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest servletRequest, 
			ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(servletRequest, servletResponse);
	}

}
