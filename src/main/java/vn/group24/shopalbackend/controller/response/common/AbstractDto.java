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
public class AbstractDto {
    private Integer id;
    private Integer originId;
    private StateMutation state;
    private LocalDateTime dateMutation;
    private String updateDescription;
}
