package vn.edu.bookverse.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.bookverse.entity.*;
import vn.edu.bookverse.model.CartItem_24162040;
import vn.edu.bookverse.service.*;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

@WebServlet({"/checkout", "/orders"})
public class OrderServlet_24162040 extends HttpServlet {
    private final CartService_24162040 cart = new CartService_24162040();
    private final OrderService_24162040 orders = new OrderService_24162040();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User_24162040 user = user(request, response); if (user == null) return;
        if ("/checkout".equals(request.getServletPath())) {
            List<CartItem_24162040> items = cart.items(request.getSession());
            if (items.isEmpty()) { response.sendRedirect(request.getContextPath() + "/cart"); return; }
            request.setAttribute("cartItems", items);
            request.setAttribute("cartTotal", NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(cart.total(items)));
            request.getRequestDispatcher("/views/user/checkout.jsp").forward(request, response);
            return;
        }
        OrderStatus_24162040 status = status(request.getParameter("status"));
        request.setAttribute("selectedStatus", status == null ? "" : status.name());
        request.setAttribute("statuses", OrderStatus_24162040.values());
        request.setAttribute("orders", orders.history(user.id, status));
        request.getRequestDispatcher("/views/user/orders.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User_24162040 user = user(request, response); if (user == null) return;
        try {
            Order_24162040 order = orders.checkout(user, new LinkedHashMap<>(cart.quantities(request.getSession())),
                    request.getParameter("receiverName"), request.getParameter("phone"), request.getParameter("address"));
            cart.clear(request.getSession());
            response.sendRedirect(request.getContextPath() + "/orders?placed=" + order.id);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            doGet(request, response);
        }
    }

    private User_24162040 user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User_24162040 user = (User_24162040) request.getSession().getAttribute("user");
        if (user == null) response.sendRedirect(request.getContextPath() + "/login?next=checkout");
        return user;
    }

    private OrderStatus_24162040 status(String value) {
        if (value == null || value.isBlank()) return null;
        try { return OrderStatus_24162040.valueOf(value); } catch (IllegalArgumentException ex) { return null; }
    }
}
