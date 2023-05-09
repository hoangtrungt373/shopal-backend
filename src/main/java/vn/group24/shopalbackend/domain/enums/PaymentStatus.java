package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum PaymentStatus implements AbstractMultilingualEnum<PaymentStatus> {
    UNPAID("Chưa thanh toán", "Un paid"),
    PAID("Đã thanh toán", "Paid");

    private final String textEn;
    private final String textVn;

    PaymentStatus(String textVn, String textEn) {
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
