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
public class AbstractStateAndAncestorManageableEntity extends AbstractAuditableEntity {

    /**
     * Since the Identity Id is only retrieved when the entity actually flushed to DB
     * so the OrigineId will have a period that its value is still "not yet valid"
     * and because this column is nonnull so we use {@link AbstractStateAndAncestorManageableEntity#NOT_YET_VALID_ID_ORIGINE}
     * with the value -1 to identify this case.
     */
    public static final Integer NOT_YET_VALID_ID_ORIGINE = -1;

    @NotNull
    @Column(name = "ID_ORIGIN", nullable = false)
    private Integer origineId = NOT_YET_VALID_ID_ORIGINE;

    @PostPersist
    private void onPostPersist() {
        /**
         * When the entity is created for the first time, its {@link origineId} id is its {@link id}.
         * And then when this entity is copied to a new generation, the {@link origineId} still remain unchanged.
         */
        if (Objects.equals(origineId, NOT_YET_VALID_ID_ORIGINE)) {
            /**
             * Update origineId to the persisted object, and then this change will be flushed during transaction commit.
             * Obviously the {@link version} will be increased by 1 in this case.
             */
            origineId = getId();
        }
    }

}