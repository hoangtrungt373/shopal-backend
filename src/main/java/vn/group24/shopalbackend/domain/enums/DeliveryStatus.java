package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum DeliveryStatus implements AbstractMultilingualEnum<DeliveryStatus> {
    ORDERED,
    PACKED,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}
