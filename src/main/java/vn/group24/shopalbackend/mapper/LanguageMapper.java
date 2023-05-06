package vn.group24.shopalbackend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import vn.group24.shopalbackend.controller.response.common.OrderStatusDto;
import vn.group24.shopalbackend.domain.enums.OrderStatus;
import vn.group24.shopalbackend.domain.multilingual.OrderStatusLan;

/**
 *
 * @author ttg
 */
@Component
public class LanguageMapper {

    public List<OrderStatusDto> mapToOrderStatusDto(List<OrderStatusLan> orderStatusLans) {
        return orderStatusLans.stream().map(orderStatusLan -> {
            OrderStatusDto orderStatusDto = new OrderStatusDto();
            orderStatusDto.setOrderStatus(OrderStatus.valueOf(orderStatusLan.getCode()));
            orderStatusDto.setOrderStatusDescription(orderStatusLan.getDescription());
            return orderStatusDto;
        }).collect(Collectors.toList());
    }
}
