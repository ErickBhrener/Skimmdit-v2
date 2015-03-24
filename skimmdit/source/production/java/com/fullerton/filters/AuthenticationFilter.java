package com.fullerton.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException
    {
        HttpSession session = ((HttpServletRequest)request).getSession(false);
        System.out.println(((HttpServletRequest) request).getRequestURI());
        if(((HttpServletRequest) request).getRequestURI().equals("/skimmdit/main/list")){
        	chain.doFilter(request, response);
        }else{
        	if(session == null || session.getAttribute("username") == null)
	        {
	            ((HttpServletResponse)response).sendRedirect(
	                    ((HttpServletRequest) request).getContextPath() + "/login"
	            );
	        }
	        else
	            chain.doFilter(request, response);
        }
    }
    @Override
    public void init(FilterConfig config) throws ServletException { }

    @Override
    public void destroy() { }
}
