package vn.group24.shopalbackend.service;

import vn.group24.shopalbackend.domain.enums.Language;

/**
 * @author ttg
 */
public interface SysLanguageService {

    void changeCurrentLanguage(Language reqLan);
}
