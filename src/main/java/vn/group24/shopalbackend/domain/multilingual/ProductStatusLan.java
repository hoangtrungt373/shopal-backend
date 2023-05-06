package vn.group24.shopalbackend.domain.multilingual;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import vn.group24.shopalbackend.domain.AbstractMultilingualEntity;

/**
 * @author ttg
 */
@Entity
@Table(name = "PRODUCT_STATUS_LAN", schema = "config")
@Setter
@Getter
@AttributeOverride(name = "id", column = @Column(name = "PRODUCT_STATUS_LAN_ID"))
public class ProductStatusLan extends AbstractMultilingualEntity {
    public static final String TABLE_NAME = "PRODUCT_STATUS_LAN";
}
