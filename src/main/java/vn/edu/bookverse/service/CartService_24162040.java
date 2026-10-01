package vn.edu.bookverse.service;

import jakarta.servlet.http.HttpSession;
import vn.edu.bookverse.entity.Book_24162040;
import vn.edu.bookverse.model.CartItem_24162040;
import vn.edu.bookverse.repository.BookRepository_24162040;
import java.math.BigDecimal;
import java.util.*;

public class CartService_24162040 {
    private static final String CART = "cart";
    private final BookRepository_24162040 books = new BookRepository_24162040();

    @SuppressWarnings("unchecked")
    public Map<Long, Integer> quantities(HttpSession session) {
        Object value = session.getAttribute(CART);
        if (value instanceof Map<?, ?>) return (Map<Long, Integer>) value;
        Map<Long, Integer> cart = new LinkedHashMap<>();
        session.setAttribute(CART, cart);
        return cart;
    }

    public void add(HttpSession session, long bookId, int amount) {
        Book_24162040 book = requiredBook(bookId);
        Map<Long, Integer> cart = quantities(session);
        int next = cart.getOrDefault(bookId, 0) + Math.max(1, amount);
        cart.put(bookId, Math.min(next, book.quantity));
        if (book.quantity <= 0) cart.remove(bookId);
    }

    public void update(HttpSession session, long bookId, int quantity) {
        Map<Long, Integer> cart = quantities(session);
        if (quantity <= 0) { cart.remove(bookId); return; }
        Book_24162040 book = requiredBook(bookId);
        cart.put(bookId, Math.min(quantity, book.quantity));
        if (book.quantity <= 0) cart.remove(bookId);
    }

    public List<CartItem_24162040> items(HttpSession session) {
        List<CartItem_24162040> result = new ArrayList<>();
        Map<Long, Integer> cart = quantities(session);
        for (Iterator<Map.Entry<Long, Integer>> it = cart.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Long, Integer> entry = it.next();
            Book_24162040 book = books.find(entry.getKey());
            if (book == null || book.quantity <= 0) { it.remove(); continue; }
            int quantity = Math.min(entry.getValue(), book.quantity);
            entry.setValue(quantity);
            result.add(new CartItem_24162040(book, quantity));
        }
        return result;
    }

    public BigDecimal total(List<CartItem_24162040> items) {
        return items.stream().map(CartItem_24162040::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void clear(HttpSession session) { quantities(session).clear(); }

    private Book_24162040 requiredBook(long id) {
        Book_24162040 book = books.find(id);
        if (book == null) throw new IllegalArgumentException("Sách không tồn tại.");
        return book;
    }
}
