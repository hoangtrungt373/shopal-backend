package vn.group24.shopalbackend.service;

import vn.group24.shopalbackend.domain.enums.Language;

/**
 * @author ttg
 */
public interface LanguageService {

    void changeCurrentLanguage(Language reqLan);

    String getEnumDescriptionForCurrentLanguage(String code, String className);
}
