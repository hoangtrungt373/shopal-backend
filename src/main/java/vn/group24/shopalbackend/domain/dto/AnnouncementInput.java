package vn.group24.shopalbackend.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.Enterprise;
import vn.group24.shopalbackend.domain.Product;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;

/**
 *
 * @author ttg
 */
@Builder
@Getter
@Setter
public class AnnouncementInput {
    private Product product;
    private Enterprise enterprise;
    private AnnouncementInterface anInterface;
    private AnnouncementType type;
    private AnnouncementStatus status;
    private String message;
}
