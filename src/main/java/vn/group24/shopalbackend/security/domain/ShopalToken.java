package vn.group24.shopalbackend.security.domain;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.group24.shopalbackend.domain.AbstractEntity;
import vn.group24.shopalbackend.security.domain.enums.TokenType;

@Entity
@Table(name = "SHOPAL_TOKEN", schema = "auth")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "SHOPAL_TOKEN_ID"))
public class ShopalToken extends AbstractEntity {

    @NotNull
    @Column(name = "TOKEN", unique = true)
    public String token;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @NotNull
    @Column(name = "REVOKED")
    public Boolean revoked;

    @NotNull
    @Column(name = "EXPIRED")
    public Boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOPAL_USER_ID")
    public ShopalUser user;
}
