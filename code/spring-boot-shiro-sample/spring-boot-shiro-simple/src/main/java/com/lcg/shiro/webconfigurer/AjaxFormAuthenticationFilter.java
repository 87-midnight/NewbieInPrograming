package com.lcg.shiro.webconfigurer;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author linchuangang
 * @createTime 2020/11/4
 **/
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject= SecurityUtils.getSubject();
        Session session=subject.getSession(true);

        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                // allow them to see the login page ;)
                return true;
            }
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.getWriter()
                    .write("{\"httpCode\":401,\"msg\":\"没有登录\",\"timestamp\":" + System.currentTimeMillis() + "}");
            httpServletResponse.getWriter().flush();
            return false;
        }
    }
}
