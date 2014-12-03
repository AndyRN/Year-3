package Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andy
 */
public class LoginFilter implements Filter {
    
   /**
     * @param filterConfig
     * @throws javax.servlet.ServletException
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * @param req
     * @param res
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Boolean authenticated = false;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            authenticated = true;
        }
        
        if (authenticated != true) {
            response.sendRedirect(request.getContextPath() + "/LoginPage.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }
}
