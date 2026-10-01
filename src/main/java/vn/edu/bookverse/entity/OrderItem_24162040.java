package vn.edu.bookverse.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Entity(name = "OrderItem")
@Table(name = "order_items")
public class OrderItem_24162040 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "order_id") public Order_24162040 order;
    @ManyToOne(optional = false) @JoinColumn(name = "book_id") public Book_24162040 book;
    @Column(nullable = false) public String title;
    @Column(nullable = false) public int quantity;
    @Column(nullable = false, precision = 12, scale = 0) public BigDecimal unitPrice;

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getSubtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
    public String getFormattedUnitPrice() { return format(unitPrice); }
    public String getFormattedSubtotal() { return format(getSubtotal()); }
    private String format(BigDecimal value) { return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(value); }
}
