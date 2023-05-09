package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum ContractStatus implements AbstractMultilingualEnum<ContractStatus> {
    PENDING("Chưa áp dụng", "Pending"),
    ACTIVE("Không áp dụng", "Active"),
    INACTIVE("Hết hạn", "Inactive");

    private final String textEn;
    private final String textVn;

    ContractStatus(String textVn, String textEn) {
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
