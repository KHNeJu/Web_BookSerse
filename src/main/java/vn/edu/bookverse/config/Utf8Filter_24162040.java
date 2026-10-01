package vn.edu.bookverse.config;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import java.io.*;
@WebFilter("/*") public class Utf8Filter_24162040 implements Filter { public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{request.setCharacterEncoding("UTF-8");response.setCharacterEncoding("UTF-8");chain.doFilter(request,response);} }
