package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.group24.shopalbackend.domain.enums.Language;
import vn.group24.shopalbackend.domain.multilingual.SysLanguage;
import vn.group24.shopalbackend.repository.SysLanguageRepository;
import vn.group24.shopalbackend.service.SysLanguageService;

/**
 * @author ttg
 */
@Service
@Transactional
public class SysLanguageServiceImpl implements SysLanguageService {

    @Autowired
    private SysLanguageRepository sysLanguageRepository;

    public void changeCurrentLanguage(Language reqLan) {
        if (SysLanguage.getCurrentLanguage() != reqLan) {
            List<SysLanguage> existsLanguages = sysLanguageRepository.findAll();

            existsLanguages.stream()
                    .filter(lan -> lan.getCode() == SysLanguage.getCurrentLanguage() && BooleanUtils.isTrue(lan.getActive()))
                    .findFirst()
                    .ifPresent(oldLanguage -> {
                        oldLanguage.setActive(Boolean.FALSE);
                    });

            existsLanguages.stream()
                    .filter(lan -> lan.getCode() == reqLan && BooleanUtils.isFalse(lan.getActive()))
                    .findFirst()
                    .ifPresent(newLanguage -> {
                        newLanguage.setActive(Boolean.TRUE);
                        SysLanguage.setCurrentLanguage(reqLan);
                    });
        }
    }
}
