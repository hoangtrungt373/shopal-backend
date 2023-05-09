package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum Gender implements AbstractMultilingualEnum<Gender> {
    MALE("Nam", "Male"),
    FEMALE("Nữ", "Female");

    private final String textEn;
    private final String textVn;

    Gender(String textVn, String textEn) {
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
