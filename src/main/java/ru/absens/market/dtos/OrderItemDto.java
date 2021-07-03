package ru.absens.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.absens.market.models.OrderItem;
import ru.absens.market.models.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private ProductDto product;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal price;

    public OrderItemDto(OrderItem orderItem) {
        this.product = new ProductDto(orderItem.getProduct());
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.price = orderItem.getPrice();
    }

    public OrderItemDto(Product product) {
        this.product = new ProductDto(product);
        this.quantity = 0;
        this.pricePerProduct = product.getPrice();
        this.price = product.getPrice();
    }

    public void incrementQuantity(Integer quantity) {
        if (quantity == null) quantity = 1;
        this.quantity += quantity;
        this.price = this.pricePerProduct.multiply(new BigDecimal(this.quantity));
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
        this.price = this.pricePerProduct.multiply(new BigDecimal(this.quantity));
    }


}
