package vn.group24.shopalbackend.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ENTERPRISE", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "ENTERPRISE_ID"))
public class Enterprise extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "SHOPAL_USER_ID")
    private Integer userId;

    @NotNull
    @Column(name = "ENTERPRISE_NAME")
    private String enterpriseName;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    @Column(name = "ADRESS")
    public String address;

    @NotNull
    @Column(name = "WEBSITE_URL")
    private String websiteUrl;

    @NotNull
    @Column(name = "AVATAR_URL")
    private String avatarUrl;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private Set<CooperationContract> cooperationContracts = new HashSet<>();
}
