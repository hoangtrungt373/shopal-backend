package vn.group24.shopalbackend.domain;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PostPersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class AbstractGenerationEntity extends AbstractAuditableEntity {

    public static final Integer NOT_YET_VALID_ID_ORIGIN = -1;

    @NotNull
    @Column(name = "ID_ORIGIN", nullable = false)
    private Integer originId = NOT_YET_VALID_ID_ORIGIN;

    @PostPersist
    protected void onPostPersist() {
        if (Objects.equals(originId, NOT_YET_VALID_ID_ORIGIN)) {
            originId = getId();
        }
    }

}