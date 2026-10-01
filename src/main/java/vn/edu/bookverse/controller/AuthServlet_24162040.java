package vn.edu.bookverse.controller;

import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.time.*;
import vn.edu.bookverse.entity.*;
import vn.edu.bookverse.service.*;

@WebServlet({"/register", "/verify-otp", "/login", "/logout"})
public class AuthServlet_24162040 extends HttpServlet {
    final AuthService_24162040 auth = new AuthService_24162040();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        String path = request.getServletPath();
        if (path.equals("/logout")) { request.getSession().invalidate(); response.sendRedirect(request.getContextPath() + "/"); return; }
        request.getRequestDispatcher("/views/user" + path + ".jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, jakarta.servlet.ServletException {
        try {
            String path = request.getServletPath();
            HttpSession session = request.getSession();
            if (path.equals("/register")) {
                String email = request.getParameter("email").toLowerCase();
                session.setAttribute("pendingName", request.getParameter("name"));
                session.setAttribute("pendingEmail", email);
                session.setAttribute("pendingPhone", request.getParameter("phone"));
                session.setAttribute("pendingPassword", request.getParameter("password"));
                session.setAttribute("otpHash", auth.requestOtp(email));
                session.setAttribute("otpExpiresAt", LocalDateTime.now().plusMinutes(5));
                response.sendRedirect("verify-otp"); return;
            }
            if (path.equals("/verify-otp")) {
                if (!auth.validOtp((String) session.getAttribute("otpHash"), (LocalDateTime) session.getAttribute("otpExpiresAt"), request.getParameter("code"))) throw new IllegalArgumentException("OTP không đúng hoặc đã hết hạn");
                auth.register((String) session.getAttribute("pendingName"), (String) session.getAttribute("pendingEmail"), (String) session.getAttribute("pendingPhone"), (String) session.getAttribute("pendingPassword"));
                session.removeAttribute("pendingName"); session.removeAttribute("pendingEmail"); session.removeAttribute("pendingPhone"); session.removeAttribute("pendingPassword"); session.removeAttribute("otpHash"); session.removeAttribute("otpExpiresAt");
                response.sendRedirect("login?ok=activated"); return;
            }
            User_24162040 user = auth.login(request.getParameter("email"), request.getParameter("password"));
            if (user == null) throw new IllegalArgumentException("Email/mật khẩu sai hoặc email chưa kích hoạt");
            session.setAttribute("user", user);
            String next = request.getParameter("next");
            response.sendRedirect(request.getContextPath() + (user.isAdmin ? "/admin/books" : "checkout".equals(next) ? "/checkout" : "/"));
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage()); request.getRequestDispatcher("/views/user" + request.getServletPath() + ".jsp").forward(request, response);
        }
    }
}
