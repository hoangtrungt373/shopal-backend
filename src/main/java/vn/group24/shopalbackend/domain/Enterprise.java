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
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "ENTERPRISE", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "ENTERPRISE_ID"))
public class Enterprise extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "USER_ACCOUNT_ID")
    private Integer userAccountId;

    @NotNull
    @Column(name = "ENTERPRISE_NAME")
    private String enterpriseName;

    @NotNull
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotNull
    @Column(name = "ADRESS")
    private String address;

    @NotNull
    @Column(name = "WEBSITE_URL")
    private String websiteUrl;

    @NotNull
    @Column(name = "LOGO_URL")
    private String logoUrl;

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "enterprise", fetch = FetchType.LAZY)
    private Set<CooperationContract> cooperationContracts = new HashSet<>();

    @Transient
    private String email;
}
