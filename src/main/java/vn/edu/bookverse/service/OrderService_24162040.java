package vn.edu.bookverse.service;

import jakarta.persistence.*;
import vn.edu.bookverse.config.Jpa_24162040;
import vn.edu.bookverse.entity.*;
import java.util.*;

public class OrderService_24162040 {
    public Order_24162040 checkout(User_24162040 user, Map<Long, Integer> cart, String name, String phone, String address) {
        if (cart.isEmpty()) throw new IllegalArgumentException("Giỏ hàng đang trống.");
        if (blank(name) || blank(phone) || blank(address)) throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin nhận hàng.");
        EntityManager em = Jpa_24162040.em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order_24162040 order = new Order_24162040();
            order.user = em.getReference(User_24162040.class, user.id);
            order.receiverName = name.trim(); order.phone = phone.trim(); order.address = address.trim();
            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                Book_24162040 book = em.find(Book_24162040.class, entry.getKey(), LockModeType.PESSIMISTIC_WRITE);
                int quantity = entry.getValue();
                if (book == null || quantity < 1 || quantity > book.quantity)
                    throw new IllegalArgumentException(book == null ? "Sách không tồn tại." : "Số lượng “" + book.title + "” không còn đủ.");
                book.quantity -= quantity;
                OrderItem_24162040 item = new OrderItem_24162040();
                item.order = order; item.book = book; item.title = book.title; item.quantity = quantity; item.unitPrice = book.getPrice();
                order.items.add(item); order.total = order.total.add(item.getSubtotal());
            }
            em.persist(order);
            tx.commit();
            return order;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally { em.close(); }
    }

    public List<Order_24162040> history(long userId, OrderStatus_24162040 status) {
        EntityManager em = Jpa_24162040.em();
        try {
            String jpql = "select distinct o from PurchaseOrder o left join fetch o.items where o.user.id=:uid" +
                    (status == null ? "" : " and o.status=:status") + " order by o.createdAt desc";
            TypedQuery<Order_24162040> query = em.createQuery(jpql, Order_24162040.class).setParameter("uid", userId);
            if (status != null) query.setParameter("status", status);
            return query.getResultList();
        } finally { em.close(); }
    }

    private boolean blank(String value) { return value == null || value.isBlank(); }
}
