package ru.absens.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.absens.market.models.DeliveryType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DeliveryTypeDto {
    private Long id;

    private String title;

    private BigDecimal price;

    public DeliveryTypeDto(DeliveryType deliveryType) {
        this.id = deliveryType.getId();
        this.title = deliveryType.getTitle();
        this.price = deliveryType.getPrice();
    }
}
