package vn.group24.shopalbackend.controller.response.common;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.StateMutation;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AbstractStateDto extends AbstractDto {
    private StateMutation state;
    private LocalDateTime dateMutation;
    private String updateDescription;
}
