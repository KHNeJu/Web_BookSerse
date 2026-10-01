package vn.edu.bookverse.config;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*; import vn.edu.bookverse.entity.*;
@WebFilter("/admin/*") public class AdminFilter_24162040 implements Filter { public void doFilter(ServletRequest r,ServletResponse s,FilterChain c)throws IOException,ServletException{HttpServletRequest q=(HttpServletRequest)r;User_24162040 u=(User_24162040)q.getSession().getAttribute("user");if(u==null||!u.isAdmin){((HttpServletResponse)s).sendRedirect(q.getContextPath()+"/login");return;}c.doFilter(r,s);} }
