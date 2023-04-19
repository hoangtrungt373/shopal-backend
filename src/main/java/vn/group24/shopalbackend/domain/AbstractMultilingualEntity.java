package vn.group24.shopalbackend.domain;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.Language;

/**
 *
 * @author ttg
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractMultilingualEntity extends AbstractEntity {

    @NotNull
    @Column(name = "CODE")
    private String code;

    @NotNull
    @Column(name = "LAN")
    @Enumerated(EnumType.STRING)
    public Language language;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

}
