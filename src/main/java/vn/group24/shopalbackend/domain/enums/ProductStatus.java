package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum ProductStatus implements AbstractMultilingualEnum<ProductStatus> {
    INACTIVE("Không được bán", "Inactive"),
    ACTIVE("Đang được bán", "Active");

    private final String textEn;
    private final String textVn;

    ProductStatus(String textVn, String textEn) {
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
