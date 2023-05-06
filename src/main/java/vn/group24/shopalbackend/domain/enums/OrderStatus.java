package vn.group24.shopalbackend.domain.enums;

/**
 *
 * @author ttg
 */
public enum OrderStatus implements AbstractMultilingualEnum<OrderStatus> {
    OPEN,
    PROCESSING,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED
}
