package vn.group24.shopalbackend.controller.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;

/**
 *
 * @author ttg
 */
@Getter
@Setter
public class AnnouncementSearchCriteriaRequest extends AbstractSearchCriteria {
    private Integer announcementId;
    private Integer demandAnnouncementId;
    private Integer productId;
    private Integer enterpriseId;
    private AnnouncementInterface anInterface;
    private AnnouncementStatus status;
    private AnnouncementType type;
    private LocalDateTime dateFrom;
    private String hash;
}
