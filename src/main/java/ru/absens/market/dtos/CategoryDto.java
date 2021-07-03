package ru.absens.market.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.absens.market.models.Category;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @Size(min = 4, max = 255, message = "Title size: 4-255")
    private String title;

    private int productsSize;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.title = category.getTitle();
        this.productsSize = category.getProducts().size();
    }
}
