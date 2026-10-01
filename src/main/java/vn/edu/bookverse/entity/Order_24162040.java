package vn.edu.bookverse.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity(name = "PurchaseOrder")
@Table(name = "customer_orders")
public class Order_24162040 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id") public User_24162040 user;
    @Column(nullable = false) public String receiverName;
    @Column(nullable = false) public String phone;
    @Column(nullable = false, length = 500) public String address;
    @Column(nullable = false) public String paymentMethod = "COD";
    @Enumerated(EnumType.STRING) @Column(nullable = false) public OrderStatus_24162040 status = OrderStatus_24162040.NEW;
    @Column(nullable = false) public LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false, precision = 14, scale = 0) public BigDecimal total = BigDecimal.ZERO;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id") public List<OrderItem_24162040> items = new ArrayList<>();

    public Long getId() { return id; }
    public String getReceiverName() { return receiverName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getPaymentMethod() { return paymentMethod; }
    public OrderStatus_24162040 getStatus() { return status; }
    public String getStatusLabel() { return status.getLabel(); }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getCreatedAtDisplay() { return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); }
    public BigDecimal getTotal() { return total; }
    public String getFormattedTotal() { return NumberFormat.getNumberInstance(new Locale("vi", "VN")).format(total); }
    public List<OrderItem_24162040> getItems() { return items; }
}
