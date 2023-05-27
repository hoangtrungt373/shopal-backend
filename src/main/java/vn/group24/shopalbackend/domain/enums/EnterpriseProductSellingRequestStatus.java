package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum EnterpriseProductSellingRequestStatus implements AbstractMultilingualEnum<EnterpriseProductSellingRequestStatus> {
    RECEIVED("Đã tiếp nhận", "Received"),
    ACCEPT("Chấp nhận", "Accept"),
    REFUSE("Refure", "Refure");

    private final String textEn;
    private final String textVn;

    EnterpriseProductSellingRequestStatus(String textVn, String textEn) {
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
