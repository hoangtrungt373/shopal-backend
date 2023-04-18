package vn.group24.shopalbackend.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Column(name = "BIRTHDATE")
    private LocalDate birthDate;

    @Column(name = "AVATAR_URL")
    private String avatarUrl;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Membership> memberships = new HashSet<>();

}
