package ru.absens.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.absens.market.models.Order;
import ru.absens.market.models.OrderShippingInfo;
import ru.absens.market.models.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private User user;
    private List<OrderItemDto> items;
    private String deliveryTypeTitle;
    private OrderShippingInfo orderShippingInfo;
    private String cartNumber;
    private BigDecimal price;
    private LocalDate transactionTime;


    public OrderDto(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.items = order.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList()); // todo need get product quantity sum
        this.deliveryTypeTitle = order.getDeliveryType().getTitle();
        this.orderShippingInfo = order.getOrderShippingInfo();
        this.cartNumber = order.getCardNumber();
        this.price = order.getPrice();
        this.transactionTime = order.getCreatedAt().toLocalDate();
    }
}
