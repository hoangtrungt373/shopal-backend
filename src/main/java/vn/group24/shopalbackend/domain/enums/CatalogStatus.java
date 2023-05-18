package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum CatalogStatus implements AbstractMultilingualEnum<CatalogStatus> {
    INACTIVE("Không áp dụng", "Inactive"),
    ACTIVE("Đang áp dụng", "Active");

    private final String textEn;
    private final String textVn;

    CatalogStatus(String textVn, String textEn) {
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
