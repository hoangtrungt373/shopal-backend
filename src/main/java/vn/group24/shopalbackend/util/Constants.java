package vn.group24.shopalbackend.util;

import vn.group24.shopalbackend.domain.enums.Language;

public class Constants {

    private Constants() {

    }

    public static final Integer DEFAULT_SEARCH_OFFSET = 0;
    public static final Integer DEFAULT_SEARCH_LIMIT = 1000;
    public static final String INITIAL_CREATE = "Initial";

    public static final String PRODUCT_IMAGE_DIRECTORY = "C:\\Users\\TTG\\Documents\\GitHub\\shopal-frontend\\public\\img\\product";
    public static final String PRODUCT_REVIEW_DIRECTORY = "C:\\Users\\TTG\\Documents\\GitHub\\shopal-frontend\\public\\img\\review";
    public static final String CUSTOMER_AVATAR_DIRECTORY = "C:\\Users\\TTG\\Documents\\GitHub\\shopal-frontend\\public\\img\\customer";

    public static final String ACCEPT_ENTERPRISE_COOPERATION_REQUEST_TEMPLATE = "accept-enterprise-cooperation-request-template";
    public static final String VERIFY_EMAIL_TEMPLATE = "verify-email-template";

    private static Language CURRENT_LANGUAGE = Language.VN;

    public static void setCurrentLanguage(Language language) {
        CURRENT_LANGUAGE = language;
    }

    public static Language getCurrentLanguage() {
        return CURRENT_LANGUAGE;
    }
}
