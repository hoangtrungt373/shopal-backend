package vn.group24.shopalbackend.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.AnnouncementInterface;
import vn.group24.shopalbackend.domain.enums.AnnouncementStatus;
import vn.group24.shopalbackend.domain.enums.AnnouncementType;


@Entity
@Table(name = "ANNOUNCEMENT", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "ANNOUNCEMENT_ID"))
public class Announcement extends AbstractAuditableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEMAND_ANNOUNCEMENT_ID")
    private Announcement demandAnnouncement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTERPRISE_ID")
    private Enterprise enterprise;

    @NotNull
    @Column(name = "INTERFACE")
    @Enumerated(EnumType.STRING)
    private AnnouncementInterface anInterface;

    @NotNull
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private AnnouncementStatus status;

    @NotNull
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

    @NotNull
    @Column(name = "DATE")
    private LocalDateTime date;

    @NotNull
    @Column(name = "MESSAGE_ANNOUNCEMENT")
    private String message;

    @NotNull
    @Column(name = "HASH")
    private String hash;
}
