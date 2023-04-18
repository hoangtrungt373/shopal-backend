package vn.group24.shopalbackend.security.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vn.group24.shopalbackend.domain.AbstractEntity;
import vn.group24.shopalbackend.security.domain.enums.UserRole;


@Entity
@Table(name = "USER_ACCOUNT", schema = "auth")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "USER_ACCOUNT_ID"))
public class UserAccount extends AbstractEntity implements UserDetails {

    @Column(name = "USERNAME")
    private String username;

    @NotNull
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    @Getter
    private UserRole role;

    @OneToMany(mappedBy = "userAccount")
    private List<UserAccountToken> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
