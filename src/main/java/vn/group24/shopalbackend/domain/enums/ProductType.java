package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum ProductType implements AbstractMultilingualEnum<ProductStatus> {
    NORMAL("Normal", "Normal"),
    VOUCHER("Voucher", "Voucher"),
    BILL("Bill", "Bill");

    private final String textEn;
    private final String textVn;

    ProductType(String textVn, String textEn) {
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
