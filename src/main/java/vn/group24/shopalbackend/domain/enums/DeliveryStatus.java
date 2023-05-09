package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum DeliveryStatus implements AbstractMultilingualEnum<DeliveryStatus> {
    ORDERED("Đã đặt hàng", "Ordered"),
    PACKED("Đã đóng gói", "Packed"),
    IN_TRANSIT("Đang vận chuyển", "In transit"),
    DELIVERED("Đã vận chuyển", "Delivered"),
    CANCELLED("Đã hủy", "Cancelled");

    private final String textEn;
    private final String textVn;

    DeliveryStatus(String textVn, String textEn) {
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
