package ru.absens.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.absens.market.models.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;

    @Size(min = 4, max = 255, message = "Title size: 4-255")
    private String title;

    @Min(value = 1, message = "Min price = 1")
    private BigDecimal price;

    private String categoryTitle;

    private String image;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.categoryTitle = product.getCategory().getTitle();
        this.image = product.getImage();
    }
}
