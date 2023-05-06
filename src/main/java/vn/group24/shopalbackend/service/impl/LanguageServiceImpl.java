package vn.group24.shopalbackend.service.impl;

import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import vn.group24.shopalbackend.domain.enums.Language;
import vn.group24.shopalbackend.domain.multilingual.SysLanguage;
import vn.group24.shopalbackend.repository.SysLanguageRepository;
import vn.group24.shopalbackend.service.LanguageService;

/**
 * @author ttg
 */
@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private SysLanguageRepository sysLanguageRepository;

    @PersistenceContext
    private EntityManager em;

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

    public String getEnumDescriptionForCurrentLanguage(String code, String className) {
//        Query query = em.createNativeQuery("SELECT TOP(1) t.DESCRIPTION FROM ? t WHERE t.CODE = ? AND t.LAN = ?");
//        query.setParameter(1, "config." + className)
//                .setParameter(2, code)
//                .setParameter(3, SysLanguage.getCurrentLanguage().name());
//        return (String) query.getSingleResult();
        //TODO: format this method
        StringBuilder query = new StringBuilder();
        query.append("SELECT TOP(1) t.DESCRIPTION FROM config.").append(className)
                .append(" t WHERE t.CODE = '").append(code)
                .append("' AND t.LAN = '").append(SysLanguage.getCurrentLanguage().name()).append("'");
        return (String) em.createNativeQuery(query.toString()).getSingleResult();
    }
}
