package vn.edu.bookverse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.bookverse.model.CartItem_24162040;
import vn.edu.bookverse.service.CartService_24162040;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

@WebServlet("/cart")
public class CartServlet_24162040 extends HttpServlet {
    private final CartService_24162040 cart = new CartService_24162040();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CartItem_24162040> items = cart.items(request.getSession());
        Object error = request.getSession().getAttribute("cartError");
        request.getSession().removeAttribute("cartError");
        request.setAttribute("error", error);
        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(cart.total(items)));
        request.getRequestDispatcher("/views/user/cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String action = request.getParameter("action");
            long bookId = Long.parseLong(request.getParameter("bookId"));
            if ("remove".equals(action)) cart.update(request.getSession(), bookId, 0);
            else if ("update".equals(action)) cart.update(request.getSession(), bookId, parse(request.getParameter("quantity")));
            else cart.add(request.getSession(), bookId, parse(request.getParameter("quantity")));
        } catch (Exception ex) { request.getSession().setAttribute("cartError", ex.getMessage()); }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private int parse(String value) { try { return Integer.parseInt(value); } catch (Exception ex) { return 1; } }
}
