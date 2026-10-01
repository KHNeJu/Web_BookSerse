package vn.edu.bookverse.model;

import vn.edu.bookverse.entity.Book_24162040;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CartItem_24162040 {
    private final Book_24162040 book;
    private final int quantity;

    public CartItem_24162040(Book_24162040 book, int quantity) { this.book = book; this.quantity = quantity; }
    public Book_24162040 getBook() { return book; }
    public int getQuantity() { return quantity; }
    public BigDecimal getSubtotal() { return book.getPrice().multiply(BigDecimal.valueOf(quantity)); }
    public String getFormattedSubtotal() { return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(getSubtotal()); }
}
