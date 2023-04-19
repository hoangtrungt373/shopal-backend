package vn.group24.shopalbackend.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.util.UserUtils;

/**
 * @author ttg
 */
@MappedSuperclass
@Getter
@Setter
public class AbstractAuditableEntity extends AbstractEntity {

    @Column(name = "USR_LOG_I", updatable = false)
    private String createdBy;

    @Column(name = "DATE_LOG_I", updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "USR_LOG_U")
    private String lastModifiedBy;

    @Column(name = "DATE_LOG_U")
    private LocalDateTime lastModificationDate;

    @Version
    @Column(name = "VERSION")
    private Integer version;

    @PrePersist
    public void beforeSave() {
        setCreatedBy(UserUtils.getUserName());
        setCreationDate(LocalDateTime.now());
        setLastModifiedBy(UserUtils.getUserName());
        setLastModificationDate(LocalDateTime.now());
    }

    @PreUpdate
    public void beforeUpdate() {
        setLastModifiedBy(UserUtils.getUserName());
        setLastModificationDate(LocalDateTime.now());
    }
}
