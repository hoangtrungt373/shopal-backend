package vn.group24.shopalbackend.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.MutationInterface;
import vn.group24.shopalbackend.domain.enums.StateMutation;

/**
 *
 * @author ttg
 */
@Getter
@Setter
@MappedSuperclass
public class AbstractRootGenerationEntity extends AbstractGenerationEntity {

    @NotNull
    @Column(name = "STATE", nullable = false)
    @Enumerated(EnumType.STRING)
    private StateMutation state;

    @NotNull
    @Column(name = "DATE_MUTATION", nullable = false)
    private LocalDateTime dateMutation;

    @NotNull
    @Column(name = "DATA_MUTATION", nullable = false)
    @Enumerated(EnumType.STRING)
    private MutationInterface dataMutation;

    @Override
    protected void onPostPersist() {
        super.onPostPersist();
        state = StateMutation.PROCESSING;
        dateMutation = LocalDateTime.now();
        dataMutation = MutationInterface.INITIAL;
    }
}
