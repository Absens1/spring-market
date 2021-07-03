package ru.absens.market.utils;

import lombok.Data;
import ru.absens.market.dtos.OrderItemDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private BigDecimal sum;

    public Cart() {
        items = new ArrayList<>();
        sum = BigDecimal.ZERO;
    }

    public void clear() {
        items.clear();
        recalculate();
    }

    public void recalculate() {
        sum = BigDecimal.ZERO;
        for (OrderItemDto o : items) {
            sum = sum.add(o.getPrice());
        }
    }

    public void merge(Cart another) {
        for (OrderItemDto anotherItem : another.items) {
            boolean merged = false;
            for (OrderItemDto myItem : items) {
                if (myItem.getProduct().getId().equals(anotherItem.getProduct().getId())) {
                    myItem.incrementQuantity(anotherItem.getQuantity());
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                items.add(anotherItem);
            }
        }
        recalculate();
        another.clear();
    }
}
