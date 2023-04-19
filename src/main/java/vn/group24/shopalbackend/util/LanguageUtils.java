package vn.group24.shopalbackend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.domain.AbstractMultilingualEntity;
import vn.group24.shopalbackend.domain.enums.AbstractMultilingualEnum;
import vn.group24.shopalbackend.service.LanguageService;

/**
 *
 * @author ttg
 */
@Component
public class LanguageUtils {

    @Autowired
    private LanguageService languageService;

    public <T extends AbstractMultilingualEntity> String getEnumDescription(String code, String className) {
        return languageService.getEnumDescriptionForCurrentLanguage(code, className);
    }

    public <U extends AbstractMultilingualEnum> String getEnumDescription(U mulEnum, String className) {
        return languageService.getEnumDescriptionForCurrentLanguage(mulEnum.name(), className);
    }
}
