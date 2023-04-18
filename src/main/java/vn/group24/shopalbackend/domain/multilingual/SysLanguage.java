package vn.group24.shopalbackend.domain.multilingual;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.AbstractEntity;
import vn.group24.shopalbackend.domain.enums.Language;


@Entity
@Table(name = "SYS_LANGUAGE", schema = "config")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "SYS_LANGUAGE_ID"))
public class SysLanguage extends AbstractEntity {

    private static Language CURRENT_LANGUAGE = Language.VN;

    @NotNull
    @Column(name = "CODE")
    @Enumerated(EnumType.STRING)
    public Language code;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "ACTIVE")
    private Boolean active;

    public static void setCurrentLanguage(Language language) {
        CURRENT_LANGUAGE = language;
    }

    public static Language getCurrentLanguage() {
        return CURRENT_LANGUAGE;
    }
}
