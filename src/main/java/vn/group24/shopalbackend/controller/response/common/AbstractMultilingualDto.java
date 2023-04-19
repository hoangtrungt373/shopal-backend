package vn.group24.shopalbackend.controller.response.common;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ttg
 */

@Getter
@Setter
public class AbstractMultilingualDto extends AbstractDto {
    private String nameVn;
    private String nameEn;
}
