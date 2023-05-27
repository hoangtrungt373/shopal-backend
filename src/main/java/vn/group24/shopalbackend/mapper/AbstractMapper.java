package vn.group24.shopalbackend.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.domain.enums.AbstractMultilingualEnum;

/**
 *
 * @author ttg
 */
@Component
public class AbstractMapper {

    public <T extends AbstractMultilingualEnum> String getTextForCurrentLan(T enumz) {
        return Optional.ofNullable(enumz)
                .map(AbstractMultilingualEnum::getTextForCurrentLan)
                .orElseGet(() -> null);
    }
}
