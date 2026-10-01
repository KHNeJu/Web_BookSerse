package vn.edu.bookverse.entity;

public enum OrderStatus_24162040 {
    NEW("Đơn hàng mới"),
    CONFIRMED("Đã xác nhận"),
    PREPARING("Chuẩn bị hàng"),
    SHIPPING("Vận chuyển"),
    DELIVERING("Giao hàng"),
    DELIVERED("Đã giao"),
    CANCELLED("Đơn hàng hủy"),
    RETURNED("Đơn hàng hoàn");

    private final String label;

    OrderStatus_24162040(String label) { this.label = label; }
    public String getLabel() { return label; }
}
