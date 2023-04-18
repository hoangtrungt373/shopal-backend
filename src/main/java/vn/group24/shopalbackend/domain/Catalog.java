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
@Table(name = "CATALOG", schema = "shop")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "CATALOG_ID"))
public class Catalog extends AbstractAuditableEntity {

    @NotNull
    @Column(name = "NAME")
    private String username;

    @NotNull
    @Column(name = "LOGO_URL")
    private String logoUrl;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "catalog", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
