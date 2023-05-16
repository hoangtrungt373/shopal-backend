package vn.group24.shopalbackend.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.enums.Gender;


@Entity
@Table(name = "CUSTOMER", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_ID"))
public class Customer extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "USER_ACCOUNT_ID")
    private Integer userAccountId;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "AVATAR_URL")
    private String avatarUrl;

    @NotNull
    @Column(name = "JOIN_DATE")
    private LocalDate joinDate;

    @NotNull
    @Column(name = "TOTAL_BUY")
    private Integer totalBuy;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Membership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<ProductCart> productCarts = new HashSet<>();

    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @Transient
    private String loginEmail;
}
