package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum EnterpriseRegisterRequestStatus implements AbstractMultilingualEnum<EnterpriseRegisterRequestStatus> {
    RECEIVED("Đã tiếp nhận", "Rêcived"),
    ACCEPT("Chấp nhận", "Accept"),
    VERIFIED("Đã xác minh", "Verified"),
    // TODO: handle case why refuse
    // TODO: add case need modify
    REFUSE("Refure", "Refure");

    private final String textEn;
    private final String textVn;

    EnterpriseRegisterRequestStatus(String textVn, String textEn) {
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
