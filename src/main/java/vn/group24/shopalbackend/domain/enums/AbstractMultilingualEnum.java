package vn.group24.shopalbackend.domain.enums;

import vn.group24.shopalbackend.util.Constants;

/**
 *
 * @author ttg
 */
public interface AbstractMultilingualEnum<E extends Enum<E>> {
    String name();

    String getTextVn();

    String getTextEn();

    default String getTextForCurrentLan() {
        switch (Constants.getCurrentLanguage()) {
            case EN:
                return getTextEn();
            case VN:
                return getTextVn();
            default:
                return null;
        }
    }
}
