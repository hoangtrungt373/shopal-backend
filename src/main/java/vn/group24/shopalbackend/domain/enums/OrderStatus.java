package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum OrderStatus implements AbstractMultilingualEnum<OrderStatus> {
    OPEN("Đã đặt hàng", "Open"),
    PROCESSING("Đang xử lý", "Processing"),
    IN_TRANSIT("Đang vận chuyển", "In transit"),
    DELIVERED("Đã giao", "Delivered"),
    CANCELLED("Đã hủy", "Cancelled");

    private final String textEn;
    private final String textVn;

    OrderStatus(String textVn, String textEn) {
        this.textVn = textVn;
        this.textEn = textEn;
    }

    @Override
    public String getTextVn() {
        return textVn;
    }

    @Override
    public String getTextEn() {
        return textEn;
    }
}
